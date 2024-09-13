
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.levelcache.config.CacheConfiguration;
import com.levelcache.service.CacheUnit;
import com.levelcache.service.CacheUnitProvider;
import com.levelcache.storage.StorageEngine;

import com.levelcache.exception.CacheBulkReadingException;
import com.levelcache.exception.CacheBulkWritingException;
import com.levelcache.exception.CacheInitializationException;
import com.levelcache.exception.CacheReadingException;
import com.levelcache.exception.CacheWritingException;
import com.levelcache.exception.LevelCreationException;
import com.levelcache.exception.LevelOutOfBoundException;
import com.levelcache.exception.LevelRemoveException;

/**
 * 
 */
public class LevelCacheImpl implements LevelCache {

	private int indexLevel;
	private CacheConfiguration config;
	private final ReadWriteLock rwLock;
	// For faster writing
	private Map<Integer, CacheUnit> byIndexLevel;
	// For faster reading
	private Map<String, Integer> byKey;

	public LevelCacheImpl(CacheConfiguration config) throws CacheInitializationException {
		if (config.getCacheName().isBlank()) {
			throw new CacheInitializationException("Cache Name cannot be blank");
		}
		this.indexLevel = 0;
		this.config = config;
		this.byIndexLevel = new ConcurrentHashMap<>();
		this.byKey = new ConcurrentHashMap<>();
		this.rwLock = new ReentrantReadWriteLock();
	}

	@Override
	public void addLevel(int size, String policy) throws LevelOutOfBoundException, LevelCreationException {
		rwLock.writeLock().lock();
		try {
			if (size < 1) {
				throw new LevelCreationException("Invalid size: " + size);
			}
			if (indexLevel > config.getMaxCacheLevels()) {
				throw new LevelOutOfBoundException("Level " + indexLevel + " out of bound");
			}
			++indexLevel;
			CacheUnit cacheUnit = CacheUnitProvider.createCacheUnit(indexLevel, size, policy);
			byIndexLevel.put(indexLevel, cacheUnit);
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	@Override
	public void removeLevel(int id) throws LevelRemoveException {
		rwLock.writeLock().lock();
		try {
			if (!byIndexLevel.containsKey(id)) {
				throw new LevelRemoveException("Cache Level with ID: " + id + " is not found");
			}
			--indexLevel;
			byIndexLevel.remove(id);
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	@Override
	public String get(String key) throws CacheReadingException {
		rwLock.readLock().lock();
		try {
			if (indexLevel < 1) {
				throw new CacheReadingException("No levels found: " + indexLevel);
			}
			if (!byKey.containsKey(key)) {
				return null; // Cache Miss
			}
			return byIndexLevel.get(byKey.get(key)).getStorageEngine().findByKey(key);

		} finally {
			rwLock.readLock().unlock();
		}
	}

	@Override
	public void put(String key, String value) throws CacheWritingException {
		rwLock.writeLock().lock();
		try {
			if (indexLevel < 1) {
				throw new CacheWritingException("No levels found: " + indexLevel);
			}
			insertAndCascade(key, value);

		} finally {
			rwLock.writeLock().unlock();
		}
	}

	private void insertAndCascade(String key, String value) {
		// Insert data into L1 (cache level 1)
		CacheUnit targetCacheUnit = byIndexLevel.get(1);
		targetCacheUnit.getStorageEngine().createPair(key, value);

		// Check if L1 has evicted any item
		Map.Entry<String, String> evictedKey = targetCacheUnit.getStorageEngine().getEvictedKeyIfAny();
		// Cascade the evicted key down to the lower levels, starting from L2
		int currentLevel = 2;
		while (evictedKey != null && currentLevel <= indexLevel) {
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
		rwLock.readLock().lock();
		try {
			for (Map.Entry<Integer, CacheUnit> entry : byIndexLevel.entrySet()) {
				StorageEngine engine = entry.getValue().getStorageEngine();
				System.out.println("L" + entry.getKey() + ": " + engine.getSnapShort().toString());
			}
		} finally {
			rwLock.readLock().unlock();
		}
	}

	@Override
	public List<String> getAll(List<String> keys) throws CacheBulkReadingException {
		rwLock.readLock().lock();
		try {
			if (indexLevel < 1) {
				throw new CacheBulkReadingException("No levels found: " + indexLevel);
			}
			List<String> values = new ArrayList<>();
			for (String key : keys) {
				if (!byKey.containsKey(key)) {
					values.add("");
					continue;
				} 
				int currentLevel = byKey.get(key);
				String value = byIndexLevel.get(currentLevel).getStorageEngine().findByKey(key);	
				values.add(value);
			}
			return values;

		} finally {
			rwLock.readLock().unlock();
		}
	}

	@Override
	public void putAll(Map<String, String> data) throws CacheBulkWritingException {
		rwLock.writeLock().lock();
		try {
			if (indexLevel < 1) {
				throw new CacheBulkWritingException("No levels found: " + indexLevel);
			}
			for (Map.Entry<String, String> entry : data.entrySet()) {
				insertAndCascade(entry.getKey(), entry.getValue());
			}

		} finally {
			rwLock.writeLock().unlock();
		}
	}

	@Override
	public void clear() {
		rwLock.writeLock().lock();
		try {
			indexLevel = 0;
			byKey.clear();
			byIndexLevel.clear();

		} finally {
			rwLock.writeLock().unlock();
		}
	}

	@Override
	public int getLevelCount() {
		rwLock.readLock().lock();
		try {
			return indexLevel;
		} finally {
			rwLock.readLock().unlock();
		}
	}
}
