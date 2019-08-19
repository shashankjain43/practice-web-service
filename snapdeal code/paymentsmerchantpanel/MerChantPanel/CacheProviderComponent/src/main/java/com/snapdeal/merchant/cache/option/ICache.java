package com.snapdeal.merchant.cache.option;

import java.io.Serializable;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.util.RequestContext;

public interface ICache {

	public void put(String key, Serializable request, RequestContext context)
			throws MerchantException;

	public Serializable get(String key, RequestContext context)
			throws MerchantException;

	public boolean delete(String key, RequestContext context)
			throws MerchantException;;

	public boolean exists(String key, RequestContext context)
			throws MerchantException;
	
	public void touch(String key, RequestContext context)
			throws MerchantException;

}
