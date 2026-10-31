package com.raftkv;

public class Main {
    public static void main(String[] args) {
        KVStore store = new KVStore();
        store.put("name", "Mapla");
        System.out.println(store.get("name"));

        store.delete("name");
        System.out.println(store.get("name"));
    }
}