package com.levelcache.storage;

import java.util.Map;

/**
 * Interface for the storage engine used in a caching system.
 * The StorageEngine provides mechanisms to store, retrieve, and manage 
 * key-value pairs, as well as handle evictions and cache snapshots.
 */
public interface StorageEngine {

    /**
     * Creates and stores a new key-value pair in the cache.
     * If the cache is full, this may trigger an eviction based on the
     * underlying eviction policy.
     *
     * @param key   the key to be stored
     * @param value the value associated with the key
     */
    public void createPair(String key, String value);

    /**
     * Finds and retrieves the value associated with the given key.
     * If the key is not present, it may return null or indicate a cache miss.
     *
     * @param key the key to be searched in the cache
     * @return the value associated with the key, or null if not found
     */
    public String findByKey(String key);

    /**
     * Retrieves the key-value pair that was evicted from the cache, if any.
     * This method returns the key-value pair that was removed from the cache 
     * due to capacity limits or the eviction policy.
     *
     * @return a Map.Entry containing the evicted key and its associated value,
     *         or null if no eviction occurred
     */
    public Map.Entry<String, String> getEvictedKeyIfAny();

    /**
     * Returns a snapshot of the current cache state.
     * This snapshot represents all the key-value pairs currently stored
     * in the cache, and can be used for debugging or monitoring purposes.
     *
     * @return a Map containing all key-value pairs currently in the cache
     */
    public Map<String, String> getSnapShort();
}
