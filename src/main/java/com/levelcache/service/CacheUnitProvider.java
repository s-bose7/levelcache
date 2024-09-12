package com.levelcache.service;

import com.levelcache.storage.LfuStorageEngine;
import com.levelcache.storage.LruStorageEngine;
import com.levelcache.storage.StorageEngine;


public class CacheUnitProvider {
	
	public static CacheUnit createCacheUnit(int id, int capacity, String policy) {
		// Create StorageEngine instance with appropriate policy
		StorageEngine engine;
		
		if(policy.equalsIgnoreCase("LFU")) {
			engine = new LfuStorageEngine(capacity);
		} else {
			engine = new LruStorageEngine(capacity);
		}
		
		return new CacheUnit(id, capacity, policy, engine);
	}

}
