package live.levelcache.factory;

import live.levelcache.core.LevelCache;
import live.levelcache.core.LevelCacheImpl;
import live.levelcache.config.CacheConfiguration;
import live.levelcache.config.DefaultConfigBuilder;

import live.levelcache.exception.CacheInitializationException;

/**
 * A factory class for creating and managing instances of {@link LevelCache}.
 */
public class CacheFactory {

    /**
     * Creates a new {@link LevelCache} instance based on the provided configuration.
     *
     * @param config the configuration object used to initialize the cache
     * @return a new instance of {@link LevelCache} initialized with the given configuration
     * @throws CacheInitializationException if an error occurs during cache initialization
     */
    public static LevelCache createCache(CacheConfiguration config) throws CacheInitializationException {
        if (config == null) {
            config = DefaultConfigBuilder.getDefaultConfiguration();
        }
        LevelCache cache = new LevelCacheImpl(config);
        return cache;
    }

    /**
     * Placeholder method for removing an existing cache instance.
     * Implement as needed for specific cache management requirements.
     */
    public static void removeCache() {
    }
}
