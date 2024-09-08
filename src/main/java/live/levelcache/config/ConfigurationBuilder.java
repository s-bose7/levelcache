package live.levelcache.config;

/**
 * Builder class for creating instances of {@link CacheConfiguration}.
 * <p>
 * This class follows the builder pattern, allowing for step-by-step construction of a 
 * {@code CacheConfiguration} instance. Each method returns the builder instance to allow
 * for method chaining.
 * </p>
 */
public class ConfigurationBuilder {

    /**
     * CacheConfiguration instance that will be built by this builder.
     */
    private CacheConfiguration config;

    /**
     * Creates a new instance of {@code CacheConfigurationBuilder}.
     * Initializes a new {@link CacheConfiguration} that will be built.
     */
    public ConfigurationBuilder() {
        this.config = new CacheConfiguration();
    }

    /**
     * Sets the name of the cache.
     * 
     * @param cacheName the name of the cache
     * @return the current instance of {@code CacheConfigurationBuilder} for method chaining
     */
    public ConfigurationBuilder setCacheName(String cacheName) {
        config.cacheName = cacheName;
        return this;
    }

    /**
     * Enables or disables logging for the cache.
     * 
     * @param loggingEnabled {@code true} to enable logging, {@code false} to disable it
     * @return the current instance of {@code CacheConfigurationBuilder} for method chaining
     */
    public ConfigurationBuilder setLoggingEnabled(boolean loggingEnabled) {
        config.loggingEnabled = loggingEnabled;
        return this;
    }

    /**
     * Sets the concurrency level for the cache.
     * The concurrency level determines the number of concurrent threads that can
     * access the cache.
     * 
     * @param concurrencyLevel the concurrency level
     * @return the current instance of {@code CacheConfigurationBuilder} for method chaining
     */
    public ConfigurationBuilder setConcurrencyLevel(int concurrencyLevel) {
        config.concurrencyLevel = concurrencyLevel;
        return this;
    }

    /**
     * Sets the maximum number of cache levels.
     * 
     * @param maxCacheLevels the maximum number of cache levels
     * @return the current instance of {@code CacheConfigurationBuilder} for method chaining
     */
    public ConfigurationBuilder setMaxCacheLevels(int maxCacheLevels) {
        config.maxCacheLevels = maxCacheLevels;
        return this;
    }

    /**
     * Builds and returns the fully constructed {@link CacheConfiguration} instance.
     * 
     * @return a fully constructed {@link CacheConfiguration} instance
     */
    public CacheConfiguration build() {
        return config;
    }

}

