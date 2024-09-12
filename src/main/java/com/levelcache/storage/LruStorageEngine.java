package com.levelcache.storage;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import com.levelcache.policy.LruCacheEntry;

public class LruStorageEngine extends AbstractStorageEngine<LruCacheEntry> {
	
	// Pointer to track Most Recent Used Key
	private LruCacheEntry mruNode;
	// Pointer to track Least Recent Used Key
	private LruCacheEntry lruNode;

	public LruStorageEngine(int capacity) {
		super(capacity);
		this.mruNode = this.lruNode = null;
	}

	@Override
	protected Map<String, LruCacheEntry> createCacheMap() {
		return new HashMap<>(capacity);
	}

	@Override
	public synchronized void createPair(String key, String value) {
		if (cacheMap.containsKey(key)) {
			// Update the value for existing key
			LruCacheEntry node = (LruCacheEntry) cacheMap.get(key);
			node.value = value;
			updateCacheNode(node);
			return;
		}
		if (size == capacity) {
			evictLruKey();
		}
		// Add new key-value pair to the cache
		LruCacheEntry newNode = new LruCacheEntry(key, value);
		if (mruNode == null) {
			// Cache is empty
			mruNode = lruNode = newNode;
		} else {
			newNode.next = mruNode;
			mruNode.prev = newNode;
			mruNode = newNode;
		}
		cacheMap.put(key, newNode);
		++size;
	}

	private void evictLruKey() {
		LruCacheEntry nodeToRemove = lruNode;
		evictedPair = new AbstractMap.SimpleEntry<>(lruNode.key, lruNode.value);
		if (lruNode == mruNode) {
			// Only one node in the cache
			lruNode = mruNode = null;
		} else {
			// More than one node in the cache
			lruNode = lruNode.prev;
			lruNode.next = null;
		}
		nodeToRemove.prev = null;
		cacheMap.remove(nodeToRemove.key);
		size = size - 1;
	}

	private void updateCacheNode(LruCacheEntry node) {
		if (node == mruNode) {
			return;
		}
		if (node == lruNode) {
			// Node is the least recently used
			lruNode = lruNode.prev;
			lruNode.next = null;
		} else {
			// Node is in the middle of the cache
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}
		node.prev = mruNode.prev;
		node.next = mruNode;
		mruNode.prev = node;
		mruNode = node;
	}

	@Override
	public synchronized String findByKey(String key) {
		if (!cacheMap.containsKey(key)) {
			return null;
		}
		updateCacheNode((LruCacheEntry) cacheMap.get(key));
		return cacheMap.get(key).value;
	}

}
