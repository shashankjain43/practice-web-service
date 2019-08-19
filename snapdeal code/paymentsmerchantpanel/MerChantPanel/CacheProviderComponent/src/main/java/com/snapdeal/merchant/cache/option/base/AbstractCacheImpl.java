package com.snapdeal.merchant.cache.option.base;

import java.io.Serializable;
import java.util.Map;

import com.snapdeal.merchant.cache.option.ICache;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.util.RequestContext;


public abstract class AbstractCacheImpl implements ICache {

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
