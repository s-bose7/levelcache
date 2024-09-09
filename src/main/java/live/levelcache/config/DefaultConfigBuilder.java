package live.levelcache.config;

/**
 * Provides a default configuration for a cache instance using a builder pattern.
 */
public class DefaultConfigBuilder {
	
	/**
     * Returns a default cache configuration.
     *
     * @return a {@link CacheConfiguration} object representing the default cache configuration.
     */
    public static CacheConfiguration getDefaultConfiguration() {
        return new ConfigurationBuilder()
                     .setCacheName("level-cache-default-instance-amd-ryzen-cpu")
                     .setLoggingEnabled(false)
                     .setMaxCacheLevels(100)
                     .setConcurrencyLevel(5)
                     .build();
    }
}
