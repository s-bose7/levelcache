
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.levelcache.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.levelcache.config.CacheConfiguration;
import com.levelcache.exception.CacheBulkReadingException;
import com.levelcache.exception.CacheBulkWritingException;
import com.levelcache.exception.CacheReadingException;
import com.levelcache.exception.CacheWritingException;
import com.levelcache.exception.LevelOutOfBoundException;
import com.levelcache.exception.RemoveLevelException;
import com.levelcache.service.CacheUnit;
import com.levelcache.service.CacheUnitProvider;
import com.levelcache.storage.StorageEngine;

/**
 * 
 */
public class LevelCacheImpl implements LevelCache {
	
	private int indexLevel;
	private CacheConfiguration config;
	// For faster writing
	private Map<Integer, CacheUnit> byIndexLevel;
	// For faster reading
	private Map<String, Integer> byKey;
	
	
	public LevelCacheImpl(CacheConfiguration config) {
		this.indexLevel = 0;
		this.config = config;
		this.byIndexLevel = new HashMap<>();
		this.byKey = new HashMap<>();
	}

	
	@Override
	public void addLevel(int size, String policy) throws LevelOutOfBoundException {
		if(indexLevel > config.getMaxCacheLevels()) {
			throw new LevelOutOfBoundException("Level "+indexLevel+" out of bound");
		}
		++indexLevel;
		CacheUnit cacheUnit = CacheUnitProvider.createCacheUnit(indexLevel, size, policy);
		byIndexLevel.put(indexLevel, cacheUnit);
	}

	
	@Override
	public void removeLevel(int id) throws RemoveLevelException {
		if (!byIndexLevel.containsKey(id)) {
	        throw new RemoveLevelException("Cache Level with ID: " + id + " is not found");
	    }
		--indexLevel;
		byIndexLevel.remove(id);
	}

	
	@Override
	public String get(String key) throws CacheReadingException {
		if(indexLevel < 1) {
			throw new CacheReadingException("No levels found: "+indexLevel);
		}
		if(!byKey.containsKey(key)) {
			// Cache Miss
			return null;
		}
		int currentLevel = byKey.get(key);
		String value = byIndexLevel.get(currentLevel).getStorageEngine().findByKey(key);
		
		// Data movement across levels when found in lower level?
		return value;
	}

	
	@Override
	public void put(String key, String value) throws CacheWritingException {
		if(indexLevel < 1) {
			throw new CacheWritingException("No levels found: "+indexLevel);
		}
		
		// Insert data into L1 (cache level 1)
		CacheUnit targetCacheUnit = byIndexLevel.get(1);
		targetCacheUnit.getStorageEngine().createPair(key, value);
		
		// Check if L1 has evicted any item
		Map.Entry<String, String>  evictedKey = targetCacheUnit.getStorageEngine().getEvictedKeyIfAny();
		// Cascade the evicted key down to the lower levels, starting from L2
		int currentLevel = 2;
		while(evictedKey != null && currentLevel <= indexLevel) {
			CacheUnit lowerCacheUnit = byIndexLevel.get(currentLevel);
	        lowerCacheUnit.getStorageEngine().createPair(evictedKey.getKey(), evictedKey.getValue());
	        // Update the new level of the evictedKey from L1 (or lower)
	        byKey.put(evictedKey.getKey(), currentLevel); 
	        // Check if L2 (or lower) evicts another key
	        evictedKey = lowerCacheUnit.getStorageEngine().getEvictedKeyIfAny();
	        currentLevel++;
		}
		
		// If the evicted key reaches beyond the last cache level
	    if (evictedKey != null) {
	    	// Cache Miss: The key is no longer in the cache system
	        byKey.remove(evictedKey.getKey());
	    }
		
		// Update the key map to indicate the inserted key is in L1
		byKey.put(key, 1);
	}

	
	@Override
	public void display() {
		for(Map.Entry<Integer, CacheUnit> entry : byIndexLevel.entrySet()) {
			StorageEngine engine = entry.getValue().getStorageEngine();
			System.out.println("L"+entry.getKey()+": "+engine.getSnapShort().toString());
		}
	}

	
	@Override
	public List<String> getAll(List<String> keys) throws CacheBulkReadingException {
		if(indexLevel < 1) {
			throw new CacheBulkReadingException("No levels found: "+indexLevel);
		}
		return null;
	}

	
	@Override
	public void putAll(Map<String, String> data) throws CacheBulkWritingException {
		if(indexLevel < 1) {
			throw new CacheBulkWritingException("No levels found: "+indexLevel);
		}
	}


	@Override
	public void clear() {
		indexLevel = 0;
		byIndexLevel.clear();
		byKey.clear();
	}


	@Override
	public int getLevelCount() {
		return indexLevel;
	}
}
