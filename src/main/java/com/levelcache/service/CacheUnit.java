package com.levelcache.service;

import com.levelcache.storage.StorageEngine;

public class CacheUnit {

	private int id;
	private int capacity;
	private String evictionPolicy;
	private StorageEngine store;

	public CacheUnit(int id, int capacity, String evictionPolicy, StorageEngine store) {
		this.id = id;
		this.capacity = capacity;
		this.evictionPolicy = evictionPolicy;
		this.store = store;
	}

	/**
	 * @return the store
	 */
	public StorageEngine getStorageEngine() {
		return store;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return the evictionPolicy
	 */
	public String getEvictionPolicy() {
		return evictionPolicy;
	}

}
