package com.levelcache.storage;

import java.util.HashMap;
import java.util.Map;

import com.levelcache.policy.CacheEntry;


public abstract class AbstractStorageEngine<T extends CacheEntry> implements StorageEngine {

    protected int size;
    protected int capacity;
    protected Map.Entry<String, String> evictedPair;
    // Allow subclasses to define specific Node types 
    protected Map<String, T> cacheMap;
    // Abstract method for creating specific Node types and cache maps
    protected abstract Map<String, T> createCacheMap();

    
    protected AbstractStorageEngine(int capacity) {
    	this.size = 0;
    	this.capacity = capacity;
        this.cacheMap = createCacheMap();
    }
    
    @Override
    public synchronized Map<String, String> getSnapShort() {
        Map<String, String> snapshotMap = new HashMap<>(cacheMap.size());
        cacheMap.forEach((key, node) -> snapshotMap.put(key, node.value));
        return snapshotMap;
    }
    
    @Override
	public synchronized Map.Entry<String, String> getEvictedKeyIfAny() {
		return evictedPair;
	}
}
