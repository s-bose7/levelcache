package com.levelcache.service;

import com.levelcache.storage.LfuStorageEngine;
import com.levelcache.storage.LruStorageEngine;
import com.levelcache.storage.StorageEngine;


public class CacheUnitProvider {
	
	public static CacheUnit createCacheUnit(int id, int capacity, String policy) {
		StorageEngine engine = new LruStorageEngine(capacity);
		if(policy.equalsIgnoreCase("LFU")) {
			engine = new LfuStorageEngine(capacity);
		} 
		return new CacheUnit(id, capacity, policy, engine);
	}

}
