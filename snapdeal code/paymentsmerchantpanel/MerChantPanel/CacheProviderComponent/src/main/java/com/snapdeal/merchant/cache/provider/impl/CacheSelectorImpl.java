package com.snapdeal.merchant.cache.provider.impl;

import java.io.Serializable;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.util.RequestContext;

@Component
public class CacheSelectorImpl extends AbstractCacheProvider {

	public void put(String key, Serializable request,
			RequestContext context) throws MerchantException {
		getProvider(context).put(key, request, context);
	}

	public Serializable get(String key, RequestContext context)
			throws MerchantException {
		return getProvider(context).get(key, context);
	}

	public boolean delete(String key, RequestContext context)
			throws MerchantException {
		return getProvider(context).delete(key, context);
	}

	public boolean exists(String key, RequestContext context)
			throws MerchantException {
		return getProvider(context).exists(key, context);
	}
	
	public void touch(String key, RequestContext context)
			throws MerchantException {
		getProvider(context).touch(key, context);
		return;
	}

}
