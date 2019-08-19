package com.snapdeal.payments.view.cache.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.view.annotations.Cache;
import com.snapdeal.payments.view.cache.ICache;

/**
 * Cache manager, is the cache container.
 */

@Slf4j
@Getter
@Component("cacheManager")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CacheManager {

	private final Map<String, ICache> _caches = new ConcurrentHashMap<String, ICache>();

	@Autowired
	List<ICache> cacheList;
	

	private ICache getCache(String cacheName) {
		return _caches.get(cacheName);
	}

	public <T extends ICache> T getCache(Class<T> cacheClass) {
		if (cacheClass.isAnnotationPresent(Cache.class)) {
			return (T) getCache(cacheClass.getAnnotation(Cache.class).name());
		} else {
			throw new IllegalArgumentException(
					"@Cache annotation should be present for cache class:"
							+ cacheClass.getName());
		}
	}
	@PostConstruct
	@Timed
	@Marked
	@Logged
	public void reloadCache() {
		for(ICache cache : cacheList ){
			cache.loadCache();
			_caches.put(cache.getCacheName(), cache);
		}
			
		log.info("Cache reloaded successfully");
	}
}