package com.snapdeal.merchant.cache.provider.impl;

import java.io.Serializable;
import java.util.EnumMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.merchant.cache.enums.CacheOption;
import com.snapdeal.merchant.cache.option.ICache;
import com.snapdeal.merchant.cache.provider.ICacheProvider;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.util.RequestContext;


public abstract class AbstractCacheProvider implements ICacheProvider {
	
	@Autowired
	protected EnumMap<CacheOption,ICache> cacheOptions;
	
	protected ICache getProvider(RequestContext context) {
		CacheOption option = null;
		switch(context) {
			case API_PERMISSION_CONFIG_CACHE: 
				option = CacheOption.MAP; break;
			case EMAIL_TOKEN_LIST_CACHE:
			case TOKEN_USER_CACHE:
				option = CacheOption.AERO; break;
			default:
				break;
		}
		return cacheOptions.get(option);		
	}

	@Override
	public void put(String key, Serializable request,
			RequestContext context) throws MerchantException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable get(String key, RequestContext context)
			throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String key, RequestContext context)
			throws MerchantException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists(String key, RequestContext context)
			throws MerchantException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void touch(String key, RequestContext context)
			throws MerchantException {
		// TODO Auto-generated method stub
		return ;
	}

}
