package com.cinerent.server.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class StaticFileHandler {
  private final Path root;

  public StaticFileHandler(Path root) {
    this.root = root;
  }

  public void handle(HttpExchange ex) throws IOException {
    String reqPath = ex.getRequestURI().getPath();
    if (reqPath == null || reqPath.isBlank() || "/".equals(reqPath)) reqPath = "/index.html";

    // Prevent path traversal
    Path target = root.resolve(reqPath.substring(1)).normalize();
    if (!target.startsWith(root.normalize())) {
      HttpUtil.text(ex, 403, "Forbidden");
      return;
    }

    if (!Files.exists(target) || Files.isDirectory(target)) {
      HttpUtil.text(ex, 404, "Not found");
      return;
    }

    String ct = contentType(target);
    byte[] bytes = Files.readAllBytes(target);
    ex.getResponseHeaders().set("Content-Type", ct);
    ex.sendResponseHeaders(200, bytes.length);
    ex.getResponseBody().write(bytes);
    ex.close();
  }

  private static String contentType(Path file) {
    String name = file.getFileName().toString().toLowerCase();
    if (name.endsWith(".html")) return "text/html; charset=utf-8";
    if (name.endsWith(".css")) return "text/css; charset=utf-8";
    if (name.endsWith(".js")) return "application/javascript; charset=utf-8";
    if (name.endsWith(".json")) return "application/json; charset=utf-8";
    if (name.endsWith(".svg")) return "image/svg+xml";
    if (name.endsWith(".png")) return "image/png";
    if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return "image/jpeg";
    if (name.endsWith(".webp")) return "image/webp";
    if (name.endsWith(".gif")) return "image/gif";
    return "application/octet-stream";
  }
}

