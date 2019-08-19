package com.snapdeal.payments.view.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.scheduler.CacheUpdater;
import com.snapdeal.payments.view.service.impl.UtilityService;

@Slf4j
@RestController
public class UtilityController extends AbstractViewController{

	@Autowired
	private CacheUpdater cacheUpdater;
	
	@Autowired
	private UtilityService utilityService ;

	@RequestMapping(value = "/health", method = { RequestMethod.HEAD,
			RequestMethod.GET })
	public String doShallowPing() {
		return utilityService.healthCheck() ;
	}

	/**cd Cus
	 * Service to reload cache.
	 */
	@RequestMapping(value = RestURIConstants.VIEW
			+ RestURIConstants.CACHE_RELOAD, method = RequestMethod.GET)
	public String reloadCache() {
		if (log.isDebugEnabled()) {
			log.debug("Reload cache -- STARTED");
		}
		String message = "Cache Reloaded Successfully";
		if (log.isDebugEnabled()) {
			log.debug("Reload cache -- DONE");
		}
		return message;
	}

}
