package com.cinerent.server.http;

import com.sun.net.httpserver.HttpExchange;
import com.cinerent.server.json.JsonLite;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class HttpUtil {
  private HttpUtil() {}

  public static String readBody(HttpExchange ex) throws IOException {
    try (InputStream in = ex.getRequestBody()) {
      return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    }
  }

  public static void json(HttpExchange ex, int status, Object obj) throws IOException {
    byte[] bytes = JsonLite.stringify(obj).getBytes(StandardCharsets.UTF_8);
    ex.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
    ex.sendResponseHeaders(status, bytes.length);
    ex.getResponseBody().write(bytes);
    ex.close();
  }

  public static void text(HttpExchange ex, int status, String body) throws IOException {
    byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
    ex.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
    ex.sendResponseHeaders(status, bytes.length);
    ex.getResponseBody().write(bytes);
    ex.close();
  }

  public static Map<String, Object> parseJsonObject(String body) throws IOException {
    if (body == null || body.isBlank()) return Map.of();
    try {
      return JsonLite.parseObject(body);
    } catch (IllegalArgumentException e) {
      throw new IOException("Invalid JSON body", e);
    }
  }
}

