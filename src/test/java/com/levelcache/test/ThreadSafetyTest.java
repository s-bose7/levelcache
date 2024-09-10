package com.levelcache.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.levelcache.config.CacheConfiguration;
import com.levelcache.config.ConfigurationBuilder;
import com.levelcache.factory.CacheFactory;
import com.levelcache.core.LevelCache;
import com.levelcache.exception.CacheInitializationException;
import com.levelcache.exception.LevelOutOfBoundException;


public class ThreadSafetyTest {
	
	private LevelCache cache;
	
	@Before
	public void setUp() throws CacheInitializationException {
		CacheConfiguration config = new ConfigurationBuilder()
				.setCacheName("test-cache-thread-safety")
				.setMaxCacheLevels(10)
				.setValueType(String.class)
				.setKeyType(String.class)
				.build();
		
		cache = CacheFactory.createCache(config);
	}

	@Test
	public void testConcurrentPutAndGet() throws InterruptedException, LevelOutOfBoundException {
	    cache.addLevel(10, "LRU");
	    CountDownLatch latch = new CountDownLatch(2);
	    
	    Thread putThread = new Thread(() -> {
	        try {
	            for (int i = 0; i < 100; i++) {
	                cache.put("key" + i, "value" + i);
	            }
	        } catch (Exception e) {
	            fail("Exception in put thread: " + e.getMessage());
	        } finally {
	            latch.countDown();
	        }
	    });
	    
	    Thread getThread = new Thread(() -> {
	        try {
	            for (int i = 0; i < 100; i++) {
	                cache.get("key" + i);
	            }
	        } catch (Exception e) {
	            fail("Exception in get thread: " + e.getMessage());
	        } finally {
	            latch.countDown();
	        }
	    });
	    
	    putThread.start();
	    getThread.start();
	    
	    latch.await(10, TimeUnit.SECONDS);
	    // Verify cache state
	    assertEquals(1, cache.getLevelCount());
	}

}
