package com.snapdeal.payments.view.scheduler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.view.cache.impl.CacheManager;

@Slf4j
@Component("CacheUpdater")
public class CacheUpdater {

	@Autowired
	private CacheManager cacheManager;

	//@Scheduled(fixedDelay = PaymentsViewConstants.CACHE_UPDATE_DELAY)
	@Timed
	@Marked
	@Logged
	public void loadAll() {
		log.info("----------Cache update started[ClientCache]------------");
		cacheManager.reloadCache();
		log.info("-------------------Cache update completed-----------------");

	}

}
