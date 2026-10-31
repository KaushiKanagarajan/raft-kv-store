package com.raftkv;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class KVHttpServer {
    private static final String PREFIX = "/kv/";

    private final KVStore store;
    private final HttpServer server;
    private final int port;

    public KVHttpServer(KVStore store, int port) throws IOException {
        this.store = store;
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.server.createContext(PREFIX, this::handle);
        this.server.setExecutor(Executors.newFixedThreadPool(8));
    }

    public void start() {
        server.start();
        System.out.println("KV server running on port " + port);
    }

    public void stop() {
        server.stop(0);
    }

    private void handle(HttpExchange ex) throws IOException {
        try {
            String path = ex.getRequestURI().getPath();
            String key = path.substring(PREFIX.length());

            if (key.isEmpty()) {
                send(ex, 400, "Key missing");
                return;
            }

            switch (ex.getRequestMethod()) {
                case "PUT" -> {
                    String value = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    store.put(key, value);
                    send(ex, 200, "OK");
                }
                case "GET" -> {
                    String value = store.get(key);
                    if (value == null) {
                        send(ex, 404, "Not found");
                    } else {
                        send(ex, 200, value);
                    }
                }
                case "DELETE" -> {
                    store.delete(key);
                    send(ex, 200, "Deleted");
                }
                default -> send(ex, 405, "Method not allowed");
            }
        } catch (Exception e) {
            send(ex, 500, "Error: " + e.getMessage());
        } finally {
            ex.close();
        }
    }

    private void send(HttpExchange ex, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }
}