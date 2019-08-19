package com.snapdeal.ims.cache;

/**
 * Common Cache api to be implemented by all cache.
 * 
 * @param <K>
 *            : Key data type.
 * @param <V>
 *            : Value data type.
 */
public interface ICache<K, V> {


	/**
	 * Get the value for the passed key.
	 * 
	 * @param key
	 * @returne
	 */
	public V get(K key);

	/**
	 * Put key and value into cache.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value);
}