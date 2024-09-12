package com.levelcache.benchmark;

import java.util.UUID;

import com.levelcache.config.CacheConfiguration;
import com.levelcache.config.ConfigurationBuilder;
import com.levelcache.core.LevelCache;
import com.levelcache.exception.CacheInitializationException;
import com.levelcache.exception.LevelCreationException;
import com.levelcache.exception.LevelOutOfBoundException;
import com.levelcache.factory.CacheFactory;

public class Benchmark {
	
	private static final int NUM_LEVEL = 100;
	private static final int NUM_INSERTION = 2000000;
	private static final int MAX_CACHE_LEVEL = 1000;
	
	@SuppressWarnings("unused")
	private static final byte KEY_SIZE = 10;
	@SuppressWarnings("unused")
	private static final byte VALUE_SIZE = 100;

	
	private static LevelCache addCacheLevels(LevelCache cache) {
		for(int l=1; l <= NUM_LEVEL; l++) {
			try {
				cache.addLevel(l, "LRU");
			} catch (LevelOutOfBoundException | LevelCreationException e) {
				e.printStackTrace();
			}
		}
		return cache;
	}
	
	
	private static void runCacheHitRatioTest(LevelCache cache) {
        // Implement the logic to measure cache hit ratio
    }

    private static void runCacheMissRatioTest(LevelCache cache) {
        // Implement the logic to measure cache miss ratio
    }

    private static void runAverageRetrievalTimeTest(LevelCache cache) {
    	
    }
    
    private static void runAverageInsertionTimeTest(LevelCache cache) {
    	long totalInsertionTime = 0;
    	
		for(int insertion=1; insertion<=NUM_INSERTION; insertion++) {
			String key = UUID.randomUUID().toString();
			String val = UUID.randomUUID().toString();
			
			long startTime = System.nanoTime();
			cache.put(key, val);
			long duration = System.nanoTime() - startTime;
			
			totalInsertionTime += duration;
		}
		
		double avgTimeTaken = (totalInsertionTime/NUM_INSERTION) / 1_000.0;
		System.out.println("AverageInsertionTime: "+avgTimeTaken+" micros;");
	}

    private static void runMemoryUsageTest(LevelCache cache) {
        // Implement the logic to measure memory usage
    }

    private static void runEvictionCountTest(LevelCache cache) {
        // Implement the logic to measure eviction count
    }

    private static void runConcurrencyPerformanceTest(LevelCache cache) {
        // Implement the logic to measure concurrency performance
    }
    
    public static void main(String[] args) throws CacheInitializationException {
    	CacheConfiguration cacheConfiguration = new ConfigurationBuilder()
                .setCacheName("benchmark-cache")
                .setMaxCacheLevels(MAX_CACHE_LEVEL)
                .build();
    	
        LevelCache cache = CacheFactory.createCache(cacheConfiguration);
        cache = addCacheLevels(cache);
        
        // Run benchmark tests
        runAverageInsertionTimeTest(cache);
        runAverageRetrievalTimeTest(cache);
        
        runEvictionCountTest(cache);
        runCacheHitRatioTest(cache);
        runCacheMissRatioTest(cache);
        runMemoryUsageTest(cache);
        runConcurrencyPerformanceTest(cache);
    }
}
