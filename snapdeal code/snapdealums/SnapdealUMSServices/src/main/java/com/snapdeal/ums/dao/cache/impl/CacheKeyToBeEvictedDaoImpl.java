/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 16-Aug-2010
 *  @author bala
 */
package com.snapdeal.ums.dao.cache.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.CacheKeyToBeEvictedDO;

@Repository
public class CacheKeyToBeEvictedDaoImpl {

	private static final Logger LOG = LoggerFactory
			.getLogger(CacheKeyToBeEvictedDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;


	public void saveCacheKeyToBeEvicted(
			CacheKeyToBeEvictedDO cacheKeyToBeEvictedDO) {

		sessionFactory.getCurrentSession().save(cacheKeyToBeEvictedDO);

	}
	
public List<CacheKeyToBeEvictedDO> GetFirstSetOfCacheKeysToBeEvicted(int firstResult, int maxResult) {
		
		Query q = sessionFactory.getCurrentSession().createQuery("FROM CacheKeyToBeEvictedDO");
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResult);
		return (List<CacheKeyToBeEvictedDO>)q.list();
		
	}

	
	public int deleteCacheKeysAfterEviction(List<Integer> idsToBeDeleted){
		Query q = sessionFactory.getCurrentSession().createQuery("delete from CacheKeyToBeEvictedDO ck where ck.id in (:idsToBeDeleted)");
		q.setParameterList("idsToBeDeleted", idsToBeDeleted);
		return q.executeUpdate();
	}
	
	public CacheKeyToBeEvictedDO getCacheKeyToBeEvictedDO(String namespace,
			String set, String key) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from CacheKeyToBeEvictedDO ck where ck.namespace=:namespace and ck.set =:set and ck.key=:key");
		query.setParameter("namespace", namespace);
		query.setParameter("set", set);
		query.setParameter("key", key);
		CacheKeyToBeEvictedDO ck = (CacheKeyToBeEvictedDO) query.uniqueResult();
		return ck;
	}

}