package com.levelcache.storage;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.levelcache.policy.LfuCacheEntry;


public class LfuStorageEngine extends AbstractStorageEngine<LfuCacheEntry> {
	
	private Map<Integer, LinkedHashSet<Integer>> freqToKeys;
	
	public LfuStorageEngine(int capacity) {
		super(capacity);
		this.freqToKeys = new HashMap<>();
	}
	
	@Override
	protected Map<String, LfuCacheEntry> createCacheMap() {
		return new HashMap<>();
	}

	@Override
	public synchronized void createPair(String key, String value) {
		if(cacheMap.containsKey(key)) {
			// Update the value for existing key
		}
		if(size == capacity) {
			evictLfuKey();
		}
		// Add new key-value pair to the cache
	}
	
	private void evictLfuKey() {
		
	}

	@Override
	public synchronized String findByKey(String key) {
		if (!cacheMap.containsKey(key)) {
			return null;
		}
		
		return cacheMap.get(key).value;
	}
}
