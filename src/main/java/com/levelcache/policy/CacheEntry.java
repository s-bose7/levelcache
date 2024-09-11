package com.levelcache.policy;

public abstract class CacheEntry {
    public String key;
    public String value;

    public CacheEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
