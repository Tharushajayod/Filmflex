package com.cinerent.server.store;

import com.cinerent.server.http.HttpUtil;
import com.cinerent.server.json.JsonLite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Stores each entity as NDJSON in a .txt file (one JSON object per line).
 */
public final class TxtStore {
  private final Path dataDir;
  private final Map<String, ReentrantLock> locks = new ConcurrentHashMap<>();

  public TxtStore(Path dataDir) {
    this.dataDir = dataDir;
  }

  public List<Map<String, Object>> list(String collection) throws IOException {
    Path file = fileFor(collection);
    ReentrantLock lock = lockFor(collection);
    lock.lock();
    try {
      ensureFileExists(file);
      return readAll(file);
    } finally {
      lock.unlock();
    }
  }

  public Map<String, Object> create(String collection, Map<String, Object> input) throws IOException {
    Path file = fileFor(collection);
    ReentrantLock lock = lockFor(collection);
    lock.lock();
    try {
      ensureFileExists(file);
      Map<String, Object> rec = new LinkedHashMap<>(input);
      rec.putIfAbsent("id", genId(collection));
      rec.putIfAbsent("createdAt", Instant.now().toString());
      appendOne(file, rec);
      return rec;
    } finally {
      lock.unlock();
    }
  }

  public Map<String, Object> update(String collection, String id, Map<String, Object> patch) throws IOException {
    Path file = fileFor(collection);
    ReentrantLock lock = lockFor(collection);
    lock.lock();
    try {
      ensureFileExists(file);
      List<Map<String, Object>> all = readAll(file);
      for (int i = 0; i < all.size(); i++) {
        Map<String, Object> rec = all.get(i);
        if (id.equals(String.valueOf(rec.get("id")))) {
          Map<String, Object> updated = new LinkedHashMap<>(rec);
          updated.putAll(patch);
          updated.put("id", id);
          updated.put("updatedAt", Instant.now().toString());
          all.set(i, updated);
          rewriteAll(file, all);
          return updated;
        }
      }
      return null;
    } finally {
      lock.unlock();
    }
  }

  public boolean delete(String collection, String id) throws IOException {
    Path file = fileFor(collection);
    ReentrantLock lock = lockFor(collection);
    lock.lock();
    try {
      ensureFileExists(file);
      List<Map<String, Object>> all = readAll(file);
      boolean removed = all.removeIf(rec -> id.equals(String.valueOf(rec.get("id"))));
      if (removed) rewriteAll(file, all);
      return removed;
    } finally {
      lock.unlock();
    }
  }

  private Path fileFor(String collection) {
    String safe = collection.replaceAll("[^a-zA-Z0-9_-]", "");
    return dataDir.resolve(safe + ".txt");
  }

  private ReentrantLock lockFor(String collection) {
    return locks.computeIfAbsent(collection, k -> new ReentrantLock());
  }

  private void ensureFileExists(Path file) throws IOException {
    Files.createDirectories(file.getParent());
    if (!Files.exists(file)) {
      Files.createFile(file);
    }
  }

  private List<Map<String, Object>> readAll(Path file) throws IOException {
    List<Map<String, Object>> out = new ArrayList<>();
    try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
      String line;
      while ((line = br.readLine()) != null) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) continue;
        try {
          out.add(JsonLite.parseObject(trimmed));
        } catch (IllegalArgumentException e) {
          // Skip invalid lines rather than crashing the server.
        }
      }
    }
    return out;
  }

  private void appendOne(Path file, Map<String, Object> record) throws IOException {
    String line = JsonLite.stringify(record);
    try (BufferedWriter bw = Files.newBufferedWriter(file, StandardCharsets.UTF_8,
        java.nio.file.StandardOpenOption.APPEND)) {
      bw.write(line);
      bw.newLine();
    }
  }

  private void rewriteAll(Path file, List<Map<String, Object>> records) throws IOException {
    try (BufferedWriter bw = Files.newBufferedWriter(file, StandardCharsets.UTF_8,
        java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
      for (Map<String, Object> rec : records) {
        bw.write(JsonLite.stringify(rec));
        bw.newLine();
      }
    }
  }

  private String genId(String collection) {
    String prefix = switch (collection) {
      case "users" -> "U";
      case "movies" -> "M";
      case "rentals" -> "R";
      case "reviews" -> "RV";
      case "payments" -> "P";
      case "admins" -> "A";
      default -> "X";
    };
    return prefix + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
  }
}

