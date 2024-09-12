package com.levelcache.storage;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.levelcache.policy.LfuCacheEntry;


public class LfuStorageEngine extends AbstractStorageEngine<LfuCacheEntry> {
	
	private int minFrequency;
	private Map<Integer, LinkedHashSet<String>> frequencyMap;
	
	public LfuStorageEngine(int capacity) {
		super(capacity);
		this.frequencyMap = new HashMap<>();
	}
	
	@Override
	protected Map<String, LfuCacheEntry> createCacheMap() {
		return new HashMap<>(capacity);
	}
	
	private void updateFrequencyMap(int currentFrequency, int updatedFrequency, String cacheKey) {
		if(currentFrequency < updatedFrequency) {
			frequencyMap.get(currentFrequency).remove(cacheKey);
			// If currentFrequency holds no key, remove the frequency from map
			if(currentFrequency == minFrequency && frequencyMap.get(currentFrequency).isEmpty()) {
				frequencyMap.remove(currentFrequency);
				++minFrequency;
			}
		}
		frequencyMap.putIfAbsent(updatedFrequency, new LinkedHashSet<>());
		frequencyMap.get(updatedFrequency).add(cacheKey);
	}

	@Override
	public synchronized void createPair(String key, String value) {
		if(cacheMap.containsKey(key)) {
			// Update the value for existing key
			LfuCacheEntry entry = cacheMap.get(key);
			entry.setValue(value);
			// Update frequency for existing key
			updateFrequencyMap(entry.frequency, entry.frequency+1, key);
			entry.frequency++;
			return;
		}
		if(size == capacity) {
			evictLfuKey();
		}
		// Add new key-value pair to the cache
		size++;
		minFrequency = 1;
		LfuCacheEntry newEntry = new LfuCacheEntry(key, value);
		cacheMap.put(key, newEntry);
		updateFrequencyMap(1, 1, key);
	}
	
	private void evictLfuKey() {
		size -= 1;
		String key = frequencyMap.get(minFrequency).iterator().next();
		frequencyMap.get(minFrequency).remove(key);
		String value = cacheMap.get(key).value;
		cacheMap.remove(key);
		// Set new evictedPair
		evictedPair = new AbstractMap.SimpleEntry<>(key, value);
	}

	@Override
	public synchronized String findByKey(String key) {
		if (!cacheMap.containsKey(key)) {
			return null;
		}
		// Update frequency for existing key
		LfuCacheEntry entry = cacheMap.get(key);
		updateFrequencyMap(entry.frequency, entry.frequency+1, key);
		entry.frequency++;
		// Return value to the caller
		return entry.value;
	}
}
