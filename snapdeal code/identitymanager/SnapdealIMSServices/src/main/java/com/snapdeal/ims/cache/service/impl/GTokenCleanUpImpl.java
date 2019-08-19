package com.snapdeal.ims.cache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.cache.service.IGtokenCleanup;
import com.snapdeal.ims.cache.service.ITokenCacheService;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GTokenCleanUpImpl implements IGtokenCleanup{

	@Autowired
	ITokenCacheService tokenCacheService;
	
	@Autowired
	@Qualifier("aeropsikeCleanupTaskExecutor")
	private TaskExecutor taskExecutor;

	@Override
	public void asyncTokenCleanUp(final GlobalTokenDetailsEntity gToken) {
		try {

			taskExecutor.execute(new Runnable() {
				public void run() {
					log.info("Async clean up Start thread");
					tokenCacheService.cleanUpUserIdGtokenMap(gToken,false);
				}
			});

		} catch (RuntimeException e) {
			// Log failures
			log.error("Exception occured while token clean up in aerospike: " +  e);
		}

	}

}
