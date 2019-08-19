package com.snapdeal.ums.services.cache.impl;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aerospike.client.Key;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.entity.CacheKeyToBeEvictedDO;
import com.snapdeal.ums.dao.cache.impl.CacheKeyToBeEvictedDaoImpl;

/**
 * Handles CacheKeyToBeEvicted related services.
 * 
 * @author ashish
 * 
 */
@Service
public class CacheKeyToBeEvictedServiceImpl

{

	private static final Logger LOG = LoggerFactory
			.getLogger(CacheKeyToBeEvictedServiceImpl.class);

	@Autowired
	private CacheKeyToBeEvictedDaoImpl cacheKeyToBeEvictedDaoImpl;

	public void deleteCacheKeysAfterEviction(List<Integer> idsToBeDeleted) {

		if (idsToBeDeleted != null && idsToBeDeleted.size() > 0) {
			int countOfIdsDeleted = cacheKeyToBeEvictedDaoImpl
					.deleteCacheKeysAfterEviction(idsToBeDeleted);
			LOG.info("Deleted {} Ids out of passed {} Ids.", new Object[] { countOfIdsDeleted, idsToBeDeleted.size() });
		}
	}

	public List<CacheKeyToBeEvictedDO> getFirstSetOfCacheKeysToBeEvicted() {

		return cacheKeyToBeEvictedDaoImpl.GetFirstSetOfCacheKeysToBeEvicted(0,
				1000);
	}

	@Transactional
	public void saveCacheKeyToBeEvicted(Key key) {

		Object[] keyArray = new Object[] { key };
		LOG.info("Attempting to insert {} into table: cache_key_to_be_evicted",
				keyArray);
		String keyString = null;
		if (key == null || key.userKey == null
				|| key.userKey.getObject() == null) {
			LOG.warn("NULL key received. Cant save it, in db, as CacheKeyToBeEvictedDO.");

		}

		keyString = key.userKey.getObject().toString();

		// First get the ck if it already exists. This is important in this case
		// because every save request results into auto-increment counter to
		// increment- despite these details already exist in db and unique
		// contrain getting violated.
		// In real-time, in case of aching failure, the
		// we are likely to observer counter exceed its limit

		CacheKeyToBeEvictedDO evictedCacheKey = getCacheKeyToBeEvictedDO(
				key.namespace, key.setName, keyString);

		if (evictedCacheKey == null) {
			cacheKeyToBeEvictedDaoImpl
					.saveCacheKeyToBeEvicted(new CacheKeyToBeEvictedDO(
							key.namespace, key.setName, keyString, DateUtils
									.getCurrentTime()));

		} else {
			LOG.info(
					"Key: {} already exists in table: cache_key_to_be_evicted.",
					keyArray);

		}
	}

	private CacheKeyToBeEvictedDO getCacheKeyToBeEvictedDO(String namespace,
			String set, String key) {

		return cacheKeyToBeEvictedDaoImpl.getCacheKeyToBeEvictedDO(namespace,
				set, key);
	}
}