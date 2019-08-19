package com.snapdeal.ims.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache manager, is the cache container.
 * 
 * @author Subhash
 */
public class CacheManager {

	private CacheManager() {
	}

	private static CacheManager _instance = new CacheManager();
	private final Map<String, ICache> _caches = new ConcurrentHashMap<String, ICache>();

	public static CacheManager getInstance() {
		return _instance;
	}

	private ICache getCache(String cacheName) {
		return _caches.get(cacheName);
	}

	@SuppressWarnings("unchecked")
	public <T extends ICache> T getCache(Class<T> cacheClass) {
		if (cacheClass.isAnnotationPresent(Cache.class)) {
			return (T) getCache(cacheClass.getAnnotation(Cache.class).name());
		} else {
			throw new IllegalArgumentException(
					"@Cache annotation should be present for cache class:"
							+ cacheClass.getName());
		}
	}

	public void setCache(ICache cache) {
		Class<? extends Object> cacheClass = cache.getClass();
		if (cacheClass.isAnnotationPresent(Cache.class)) {
			Cache annotation = cacheClass.getAnnotation(Cache.class);
			_caches.put(annotation.name(), cache);
		} else {
			throw new IllegalArgumentException(
					"@Cache annotation should be present for cache class:"
							+ cache.getClass().getName());
		}

	}
}