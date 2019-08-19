package com.snapdeal.merchant.cache.provider;

import java.io.Serializable;
import java.util.Map;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.util.RequestContext;


public interface ICacheProvider {

	public void put(String key, Serializable request, RequestContext context)
			throws MerchantException;;

	public Serializable get(String key, RequestContext context)
			throws MerchantException;

	public boolean delete(String key, RequestContext context)
			throws MerchantException;
	
	public boolean exists(String key, RequestContext context)
			throws MerchantException ;
	
	public void touch(String key, RequestContext context)
			throws MerchantException ;

}
