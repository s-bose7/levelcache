package com.levelcache;

import com.levelcache.config.CacheConfiguration;
import com.levelcache.config.ConfigurationBuilder;
import com.levelcache.core.LevelCache;
import com.levelcache.exception.CacheInitializationException;
import com.levelcache.exception.LevelCreationException;
import com.levelcache.exception.LevelOutOfBoundException;
import com.levelcache.factory.CacheFactory;

/* 
 * Cache initialization demo 
*/
public class App {
	
    public static void main( String[] args ) throws CacheInitializationException {
        
    	CacheConfiguration cacheConfiguration = new ConfigurationBuilder()
				    			.setCacheName("cpu-cache-5c-x86-64-5679")
				    			.setLoggingEnabled(false)
				    			.setConcurrencyLevel(5)
				    			.setMaxCacheLevels(100)
				    			.build();
    	
    	LevelCache cache = CacheFactory.createCache(cacheConfiguration);
    	
		try {
			cache.addLevel(3, "LRU");
			cache.addLevel(2, "LRU");
		} catch (LevelOutOfBoundException | LevelCreationException e) {
			e.printStackTrace();
		}
    }
}
