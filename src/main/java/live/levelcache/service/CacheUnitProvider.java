package live.levelcache.service;

import live.levelcache.storage.InMemoryLFUStorage;
import live.levelcache.storage.InMemoryLRUStorage;
import live.levelcache.storage.StorageEngine;


public class CacheUnitProvider {
	
	public static CacheUnit createCacheUnit(int id, int capacity, String policy) {
		StorageEngine engine = new InMemoryLRUStorage(capacity);
		if(policy.equalsIgnoreCase("LFU")) {
			engine = new InMemoryLFUStorage();
		} 
		return new CacheUnit(id, capacity, policy, engine);
	}

}
