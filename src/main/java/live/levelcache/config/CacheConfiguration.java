package live.levelcache.config;

/**
 * Configuration class for cache settings. This class holds configuration parameters
 * used to initialize and manage a cache system, such as the cache name, logging settings,
 * concurrency level, and the maximum number of cache levels.
 * <p>
 * This class is intended to be used with a builder pattern to construct instances
 * with specific configurations.
 * </p>
 */
public class CacheConfiguration {

    /**
     * Name of the cache. This is used to identify the cache instance.
     */
    protected String cacheName;

    /**
     * Flag indicating if logging is enabled for the cache.
     * When enabled, cache operations may be logged for debugging and monitoring purposes.
     */
    protected boolean loggingEnabled;

    /**
     * The level of concurrency allowed for the cache operations.
     * Determines the number of concurrent threads that can access the cache.
     */
    protected int concurrencyLevel;

    /**
     * The maximum number of cache levels allowed in the cache system.
     * Controls the depth of the cache hierarchy.
     */
    protected int maxCacheLevels;

    /**
     * Package-private constructor to enforce the use of the builder pattern for creating
     * instances of {@code CacheConfiguration}.
     * <p>
     * This constructor prevents external classes from directly instantiating this class.
     * </p>
     */
    protected CacheConfiguration() {
        // Package-private empty constructor to enforce builder usage.
    }

    /**
     * Gets the concurrency level for the cache.
     *
     * @return the concurrency level
     */
    public int getConcurrencyLevel() {
        return concurrencyLevel;
    }

    /**
     * Gets the maximum number of cache levels allowed in the cache system.
     *
     * @return the maximum number of cache levels
     */
    public int getMaxCacheLevels() {
        return maxCacheLevels;
    }

    /**
     * Gets the name of the cache.
     *
     * @return the name of the cache
     */
    public String getCacheName() {
        return cacheName;
    }

    /**
     * Checks if logging is enabled for the cache.
     *
     * @return {@code true} if logging is enabled, {@code false} otherwise
     */
    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

}
