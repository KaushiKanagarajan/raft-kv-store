package com.raftkv;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8081;

        KVStore store = new KVStore();
        KVHttpServer server = new KVHttpServer(store, port);
        server.start();
    }
}