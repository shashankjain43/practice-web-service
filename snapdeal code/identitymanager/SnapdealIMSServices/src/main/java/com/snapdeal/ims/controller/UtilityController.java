package com.snapdeal.ims.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.service.impl.CacheUpdater;

/**
 * Controller that exposes, api's for utility services.
 */
@RestController
@RequestMapping(value = RestURIConstants.UTILITY_URI)
@Slf4j
public class UtilityController extends AbstractController {

	@Autowired
	private CacheUpdater cacheUpdater;

	/**
	 * Service to reload cache.
	 */
	@RequestMapping(value = RestURIConstants.CACHE_RELOAD, method = RequestMethod.GET)
	public String reloadCache() {
		if (log.isDebugEnabled()) {
			log.debug("Reload cache -- STARTED");
		}
		String message = cacheUpdater.loadAll();
		if (log.isDebugEnabled()) {
			log.debug("Reload cache -- DONE");
		}
		return message;
	}
}
