package com.levelcache.test;

import org.junit.Test;
import org.junit.Before;
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
import com.levelcache.exception.LevelOutOfBoundException;
import com.levelcache.exception.RemoveLevelException;


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
	
	@Test
	public void TestCacheLevelInsertionAndRemoval() throws LevelOutOfBoundException, RemoveLevelException {
		cache.addLevel(10, "LRU");
		cache.addLevel(12, "LRU");
		cache.addLevel(15, "LRU");
		assertEquals(3, cache.getLevelCount());
		
		cache.removeLevel(1);
		cache.removeLevel(2);
		cache.removeLevel(3);
		assertEquals(0, cache.getLevelCount());
	}
	
	@Test
	public void TestCacheInsertionAndRetrieval() throws LevelOutOfBoundException {
		assertEquals(0, cache.getLevelCount());
		cache.addLevel(3, "LRU");
		cache.addLevel(2, "LRU");
		
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
	public void TestCacheBulkReadsAndWrites() throws LevelOutOfBoundException {
		cache.addLevel(5, "LRU");
		cache.addLevel(5, "LRU");
		
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
	
}