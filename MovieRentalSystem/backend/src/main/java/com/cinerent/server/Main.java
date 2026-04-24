package com.cinerent.server;

import com.cinerent.server.http.ApiHandler;
import com.cinerent.server.http.Cors;
import com.cinerent.server.http.HttpUtil;
import com.cinerent.server.http.StaticFileHandler;
import com.cinerent.server.store.TxtStore;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public final class Main {
  public static void main(String[] args) throws Exception {
    int port = readPort();

    Path repoRoot = Path.of("").toAbsolutePath().normalize().getParent(); // .../MovieRentalSystem/backend -> .../MovieRentalSystem
    if (repoRoot == null) repoRoot = Path.of("").toAbsolutePath().normalize();

    Path dataDir = repoRoot.resolve("data");
    Path frontendDir = repoRoot.resolve("frontend");

    TxtStore store = new TxtStore(dataDir);

    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.setExecutor(Executors.newFixedThreadPool(24));

    ApiHandler api = new ApiHandler(store);
    StaticFileHandler staticFiles = new StaticFileHandler(frontendDir);

    server.createContext("/api", ex -> handleWithCors(ex, api::handle));
    server.createContext("/", ex -> handleWithCors(ex, staticFiles::handle));

    server.start();
    System.out.println("CineRent backend running on http://localhost:" + port);
    System.out.println("Frontend: http://localhost:" + port + "/index.html");
    System.out.println("Dashboard: http://localhost:" + port + "/dashboard.html");
    System.out.println("API: http://localhost:" + port + "/api/users (and /movies, /rentals, /reviews, /payments, /admins)");
  }

  private static int readPort() {
    String env = System.getenv("PORT");
    if (env != null) {
      try {
        return Integer.parseInt(env);
      } catch (NumberFormatException ignored) {
      }
    }
    return 8080;
  }

  private static void handleWithCors(HttpExchange ex, ExchangeHandler handler) throws IOException {
    if (Cors.handlePreflight(ex)) return;
    Cors.addCorsHeaders(ex);
    try {
      handler.handle(ex);
    } catch (Exception e) {
      try {
        HttpUtil.json(ex, 500, java.util.Map.of("ok", false, "error", "Server error", "detail", String.valueOf(e.getMessage())));
      } catch (Exception ignored) {
        ex.close();
      }
    }
  }

  @FunctionalInterface
  public interface ExchangeHandler {
    void handle(HttpExchange ex) throws IOException;
  }
}

