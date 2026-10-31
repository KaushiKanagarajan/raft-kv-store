package com.raftkv;

import java.util.concurrent.ConcurrentHashMap;

public class KVStore {
    private final ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();

    public void put(String key, String value) {
        data.put(key, value);
    }

    public String get(String key) {
        return data.get(key);
    }

    public void delete(String key) {
        data.remove(key);
    }
}