package com.levelcache.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.levelcache.config.CacheConfiguration;
import com.levelcache.config.ConfigurationBuilder;
import com.levelcache.core.LevelCache;
import com.levelcache.exception.CacheInitializationException;
import com.levelcache.exception.LevelOutOfBoundException;
import com.levelcache.factory.CacheFactory;

public class Benchmark {
	
	private static void runCacheHitRatioTest(LevelCache cache) {
        // Implement the logic to measure cache hit ratio
    }

    private static void runCacheMissRatioTest(LevelCache cache) {
        // Implement the logic to measure cache miss ratio
    }

    private static void runAverageRetrievalTimeTest(LevelCache cache) {
    	
    }
    
    private static void runAverageInsertionTimeTest(LevelCache cache) {
    	int insert = 1;
    	long totalInsertionTime = 0;
    	
		while(insert <= 200000) {
			String keyUUIDString = UUID.randomUUID().toString();
			long startTime = System.nanoTime();
			cache.put(keyUUIDString, "foobar");
			totalInsertionTime += System.nanoTime() - startTime;
			insert++;
		}
		
		double avgTimeTaken = (totalInsertionTime/insert) / 1_000.0;
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
    
    public static void main(String[] args) throws CacheInitializationException, LevelOutOfBoundException {
    	CacheConfiguration cacheConfiguration = new ConfigurationBuilder()
                .setCacheName("benchmark-cache")
                .setMaxCacheLevels(10)
                .build();
    	
        LevelCache cache = CacheFactory.createCache(cacheConfiguration);
        cache.addLevel(10, "LRU");
    	cache.addLevel(50, "LRU");
    	cache.addLevel(90, "LRU");
        
        // Run benchmark tests
        runCacheHitRatioTest(cache);
        runCacheMissRatioTest(cache);
        runAverageInsertionTimeTest(cache);
        runAverageRetrievalTimeTest(cache);
        runMemoryUsageTest(cache);
        runEvictionCountTest(cache);
        runConcurrencyPerformanceTest(cache);
    }
}
