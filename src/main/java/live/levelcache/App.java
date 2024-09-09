package live.levelcache;

import live.levelcache.config.CacheConfiguration;
import live.levelcache.config.ConfigurationBuilder;
import live.levelcache.core.LevelCache;
import live.levelcache.exception.CacheInitializationException;
import live.levelcache.factory.CacheFactory;

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
		cache.addLevel(3, "LRU");
		cache.addLevel(2, "LRU");
    }
}
