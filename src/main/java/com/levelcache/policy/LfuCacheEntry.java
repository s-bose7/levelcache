package com.levelcache.policy;

public class LfuCacheEntry extends CacheEntry {
	public int frequency;
   
	public LfuCacheEntry(String key, String value) {
        super(key, value);
        this.frequency = 0;
    }
}
