package com.levelcache.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
		cache.removeLevel(2);
		cache.removeLevel(1);
		assertEquals(1, cache.getLevelCount());
	}
	
	@Test
	public void TestLevelCacheInsertionAndRetrieval() throws LevelOutOfBoundException {
		assertEquals(0, cache.getLevelCount());
		cache.addLevel(3, "LRU");
		cache.addLevel(2, "LRU");
		
		cache.put("a", "1");
		cache.put("b", "2");
		cache.put("c", "3");
		assertEquals("1", cache.get("a"));
		cache.put("d", "4");
		assertEquals("3", cache.get("c"));
	}
	
}