package com.snapdeal.ims.cache;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * Cache implementation which encapsulates the use of cache provider.
 * 
 * @param <K>
 * @param <V>
 */
@Getter
public abstract class AbstractCache<K, V> implements ICache<K, V> {

	private Map<K, V> cache;

	protected AbstractCache() {
		cache = new HashMap<K, V>();
	}

	@Override
	public V get(K key) {
		return cache.get(key);
	}

	@Override
	public void put(K key, V value) {
		cache.put(key, value);
	}
}