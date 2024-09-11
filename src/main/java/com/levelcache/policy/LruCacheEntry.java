package com.levelcache.policy;


public class LruCacheEntry extends CacheEntry {
	public LruCacheEntry prev, next;
    
	public LruCacheEntry(String key, String value) {
        super(key, value);
        this.prev = this.next = null;
    }
}
