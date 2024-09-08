package live.levelcache.manager;

import live.levelcache.config.CacheConfiguration;
import live.levelcache.core.LevelCache;
import live.levelcache.exception.CacheInitializationException;

/**
 * Manages caching operations and cache instances.
 */
public class CacheManager {

    /**
     * Creates a new cache instance based on the provided configuration.
     *
     * @param config the configuration details for creating the cache
     * @return a new instance of {@link Cache} based on the provided configuration
     */
    public static LevelCache createCache(CacheConfiguration config) {
    	LevelCache cache = null;
        try {
            cache = CacheFactory.createCache(config);
            return cache;
        } catch (CacheInitializationException e) {
            e.printStackTrace();
        }
        
        return cache;
    }
    
    /**
     * Removes the currently managed cache instance.
     */
    public static void removeCache() {
    }
}