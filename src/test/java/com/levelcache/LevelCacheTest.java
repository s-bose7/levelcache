package com.levelcache;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.levelcache.config.CacheConfiguration;
import com.levelcache.config.ConfigurationBuilder;
import com.levelcache.factory.CacheFactory;
import com.levelcache.core.LevelCache;
import com.levelcache.exception.CacheInitializationException;
import com.levelcache.exception.LevelCreationException;
import com.levelcache.exception.LevelOutOfBoundException;
import com.levelcache.exception.LevelRemoveException;


/**
 * Unit test for LevelCache.
 */
public class LevelCacheTest {
	
	private LevelCache cache;
	
	@Before
	public void setUp() throws CacheInitializationException {
		CacheConfiguration config = new ConfigurationBuilder()
				.setCacheName("test-cache")
				.setMaxCacheLevels(100)
				.setValueType(String.class)
				.setKeyType(String.class)
				.build();
		
		cache = CacheFactory.createCache(config);
	}
	
	@After
	public void destroy() {
		cache.clear();
	}
	
	
	private void addCacheLevels(int nums, int size, String policy) {
		for(int i=1; i<=nums; i++) {
			try {
				cache.addLevel(size, policy);
			} catch (LevelOutOfBoundException e) {
				e.printStackTrace();
			} catch (LevelCreationException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testCacheLevelCreationAndRemoval() {
		addCacheLevels(3, 5, "LRU");
		assertEquals(3, cache.getLevelCount());
		
		try {
			cache.removeLevel(1);
			cache.removeLevel(2);
			cache.removeLevel(3);
		} catch (LevelRemoveException e) {
			e.printStackTrace();
		}
		
		assertEquals(0, cache.getLevelCount());
	}
	
	@Test
	public void testCacheClearOperation() {
		addCacheLevels(2, 1, "LRU");
		assertEquals(2, cache.getLevelCount());
		cache.clear();
		assertEquals(0, cache.getLevelCount());
	}
	
	@Test
	public void testCacheInsertionAndRetrieval() {
		addCacheLevels(2, 3, "LRU");
		
		cache.put("abcd", "1234");
		cache.put("efgh", "5678");
		cache.put("pqrs", "9012");
		cache.put("mnop", "1456");
		
		assertEquals("1234", cache.get("abcd"));
		assertEquals("5678", cache.get("efgh"));
		assertEquals("9012", cache.get("pqrs"));
		assertEquals("1456", cache.get("mnop"));
	}
	
	@Test
	public void testCacheBulkReadsAndWrites() throws LevelOutOfBoundException {
		addCacheLevels(2, 5, "LRU");
		
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("103e4567", "10d3-a456-426614174000");
		dataMap.put("113e4567", "11d3-a456-426614174000");
		dataMap.put("123e4567", "12d3-a456-426614174000");
		
		cache.putAll(dataMap);
		List<String> values = cache.getAll(Arrays.asList("103e4567", "113e4567", "123e4567"));
		
		// Assertions
		assertEquals(3, values.size());
		assertEquals("10d3-a456-426614174000", values.get(0));
		assertEquals("11d3-a456-426614174000", values.get(1));
		assertEquals("12d3-a456-426614174000", values.get(2));
	}
	
	@Test
	public void testCacheLruInvalidation() {
		
	}
	
	@Test
	public void testCacheLfuInvalidation() {
		
	}
	
}