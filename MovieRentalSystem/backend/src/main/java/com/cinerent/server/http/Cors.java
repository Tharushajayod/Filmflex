package com.cinerent.server.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public final class Cors {
  private Cors() {}

  public static void addCorsHeaders(HttpExchange ex) {
    Headers h = ex.getResponseHeaders();
    h.set("Access-Control-Allow-Origin", "*");
    h.set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
    h.set("Access-Control-Allow-Headers", "Content-Type");
    h.set("Access-Control-Max-Age", "86400");
  }

  public static boolean handlePreflight(HttpExchange ex) throws IOException {
    if (!"OPTIONS".equalsIgnoreCase(ex.getRequestMethod())) return false;
    addCorsHeaders(ex);
    ex.sendResponseHeaders(204, -1);
    ex.close();
    return true;
  }
}

