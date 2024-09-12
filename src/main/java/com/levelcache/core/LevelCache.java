/**
 * The LevelCache interface defines the core operations for a multilevel caching system.
 * It provides methods to add and remove cache levels, retrieve and insert data, and
 * handle bulk operations across multiple cache levels. Each cache level can have
 * different configurations, such as size and eviction policies.
 * 
 * Exceptions related to cache operations like reading, writing, and removing levels are thrown
 * to handle edge cases and ensure reliability.
 */
package com.levelcache.core;

import java.util.List;
import java.util.Map;

import com.levelcache.exception.CacheBulkReadingException;
import com.levelcache.exception.CacheReadingException;
import com.levelcache.exception.CacheWritingException;
import com.levelcache.exception.LevelCreationException;
import com.levelcache.exception.LevelOutOfBoundException;
import com.levelcache.exception.LevelRemoveException;

/**
 * This interface defines the public API for managing a multilevel cache system.
 * It includes methods for dynamic cache level management, data retrieval,
 * insertion, and displaying cache content.
 */
public interface LevelCache {
	
    /**
     * Adds a new cache level with the specified size and eviction policy.
     * 
     * @param size the maximum number of entries that the cache level can hold
     * @param evictionPolicy the eviction policy to be used (e.g., "LRU" or "LFU")
     * @throws LevelOutOfBoundException, LevelCreationException 
     */
	public void addLevel(int size, String evictionPolicy) throws LevelOutOfBoundException, LevelCreationException;
	
    /**
     * Removes a cache level identified by its unique ID.
     * 
     * @param id the unique identifier of the cache level to remove
     * @throws RemoveLevelException if the cache level cannot be removed (e.g., if it doesn't exist)
     */
	public void removeLevel(int id) throws LevelRemoveException;
	
    /**
     * Retrieves a value from the cache by its key.
     * 
     * @param key the key associated with the value to be retrieved
     * @return the value associated with the key, or null if not found
     * @throws CacheReadingException if there is an issue retrieving the value from the cache
     */
	public String get(String key) throws CacheReadingException;
	
    /**
     * Retrieves a list of values corresponding to the provided list of keys.
     * 
     * @param keys a list of keys for which values need to be fetched
     * @return a list of values corresponding to the provided keys
     * @throws CacheBulkReadingException if there is an issue retrieving the values from the cache
     */
	public List<String> getAll(List<String> keys) throws CacheBulkReadingException;
	
    /**
     * Inserts a key-value pair into the cache.
     * 
     * @param key the key to associate with the value
     * @param value the value to be stored in the cache
     * @throws CacheWritingException if there is an issue inserting the data into the cache
     */
	public void put(String key, String value) throws CacheWritingException;
	
    /**
     * Inserts multiple key-value pairs into the cache in bulk.
     * 
     * @param data a map containing key-value pairs to be inserted into the cache
     * @throws CacheBulkReadingException if there is an issue inserting the data into the cache
     */
	public void putAll(Map<String, String> data) throws CacheBulkReadingException;
	
    /**
     * Displays the content of all cache levels.
     * Typically used for debugging or logging purposes to visualize cache contents.
     */
	public void display();
	
	/**
	 * Clear the cache to it's original state.
	 * Use with caution.
	 */
	public void clear();
	
	/**
	 * Returns the number of levels the instance has. 
	 */
	public int getLevelCount();
	
}
