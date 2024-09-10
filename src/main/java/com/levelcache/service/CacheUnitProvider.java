package com.levelcache.service;

import com.levelcache.storage.InMemoryLFUStorage;
import com.levelcache.storage.InMemoryLRUStorage;
import com.levelcache.storage.StorageEngine;


public class CacheUnitProvider {
	
	public static CacheUnit createCacheUnit(int id, int capacity, String policy) {
		StorageEngine engine = new InMemoryLRUStorage(capacity);
		if(policy.equalsIgnoreCase("LFU")) {
			engine = new InMemoryLFUStorage();
		} 
		return new CacheUnit(id, capacity, policy, engine);
	}

}
