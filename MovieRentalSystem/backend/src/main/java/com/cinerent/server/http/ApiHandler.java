package com.cinerent.server.http;

import com.cinerent.server.store.TxtStore;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class ApiHandler {
  private final TxtStore store;

  public ApiHandler(TxtStore store) {
    this.store = store;
  }

  public void handle(HttpExchange ex) throws IOException {
    String method = ex.getRequestMethod().toUpperCase();
    String path = ex.getRequestURI().getPath(); // /api/users or /api/users/U123

    String rest = path.substring("/api".length()); // "" or "/users/.."
    if (rest.isEmpty() || "/".equals(rest)) {
      HttpUtil.json(ex, 200, Map.of(
          "ok", true,
          "endpoints", List.of(
              "/api/users", "/api/movies", "/api/rentals",
              "/api/reviews", "/api/payments", "/api/admins"
          )
      ));
      return;
    }

    String[] parts = rest.split("/");
    // parts[0] is "" because rest starts with "/"
    String collection = parts.length >= 2 ? parts[1] : "";
    String id = parts.length >= 3 ? parts[2] : null;

    if (!isAllowedCollection(collection)) {
      HttpUtil.json(ex, 404, Map.of("ok", false, "error", "Unknown collection"));
      return;
    }

    switch (method) {
      case "GET" -> {
        if (id != null && !id.isBlank()) {
          List<Map<String, Object>> all = store.list(collection);
          Map<String, Object> found = all.stream()
              .filter(r -> id.equals(String.valueOf(r.get("id"))))
              .findFirst()
              .orElse(null);
          if (found == null) {
            HttpUtil.json(ex, 404, Map.of("ok", false, "error", "Not found"));
          } else {
            HttpUtil.json(ex, 200, Map.of("ok", true, "data", found));
          }
        } else {
          HttpUtil.json(ex, 200, Map.of("ok", true, "data", store.list(collection)));
        }
      }
      case "POST" -> {
        if (id != null) {
          HttpUtil.json(ex, 400, Map.of("ok", false, "error", "POST to collection only (no id)"));
          return;
        }
        Map<String, Object> input = HttpUtil.parseJsonObject(HttpUtil.readBody(ex));
        Map<String, Object> created = store.create(collection, input);
        HttpUtil.json(ex, 201, Map.of("ok", true, "data", created));
      }
      case "PUT" -> {
        if (id == null || id.isBlank()) {
          HttpUtil.json(ex, 400, Map.of("ok", false, "error", "PUT requires id"));
          return;
        }
        Map<String, Object> patch = HttpUtil.parseJsonObject(HttpUtil.readBody(ex));
        Map<String, Object> updated = store.update(collection, id, patch);
        if (updated == null) {
          HttpUtil.json(ex, 404, Map.of("ok", false, "error", "Not found"));
        } else {
          HttpUtil.json(ex, 200, Map.of("ok", true, "data", updated));
        }
      }
      case "DELETE" -> {
        if (id == null || id.isBlank()) {
          HttpUtil.json(ex, 400, Map.of("ok", false, "error", "DELETE requires id"));
          return;
        }
        boolean removed = store.delete(collection, id);
        if (!removed) {
          HttpUtil.json(ex, 404, Map.of("ok", false, "error", "Not found"));
        } else {
          HttpUtil.json(ex, 200, Map.of("ok", true));
        }
      }
      default -> HttpUtil.json(ex, 405, Map.of("ok", false, "error", "Method not allowed"));
    }
  }

  private boolean isAllowedCollection(String c) {
    return switch (c) {
      case "users", "movies", "rentals", "reviews", "payments", "admins" -> true;
      default -> false;
    };
  }
}

