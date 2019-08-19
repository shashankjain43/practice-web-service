package com.snapdeal.payments.view.cache.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.view.cache.ICache;
import com.snapdeal.payments.view.cache.impl.CacheManager;
import com.snapdeal.payments.view.cache.impl.ClientCache;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewAuthorizationExceptionCodes;
import com.snapdeal.payments.view.commons.exception.service.AuthorizationException;
import com.snapdeal.payments.view.commons.request.Client;

@Slf4j
@Component
public class ClientConfiguration {

	@Autowired
	private CacheManager cacheManager;

	public Client getClientByName(String clientName) {

		if (clientName == null)
			return null;

		Client client = null;
		final ICache<String, Client> clientCache = cacheManager
				.getCache(ClientCache.class);
		if (null == clientCache) {
			log.error("Configuration not present for client name:" + clientName);
			throw new AuthorizationException(
					PaymentsViewAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT
							.errCode(),
					PaymentsViewAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT
							.errMsg());
		} else {
			client = clientCache.get(clientName);
			if (null == client) {
				log.error("Configuration not present for client name:"
						+ clientName);
				throw new AuthorizationException(
						PaymentsViewAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT
								.errCode(),
						PaymentsViewAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT
								.errMsg());
			}
		}
		return client;
	}
}
