package com.snapdeal.merchant.cache.option.memory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.snapdeal.merchant.cache.option.base.AbstractCacheImpl;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.util.RequestContext;

@Component("mapcache")
public class MapCacheImpl extends AbstractCacheImpl {
	
	private Map<String,Serializable> apiToPermissionMap;
	
	public MapCacheImpl() {
		this.apiToPermissionMap = new HashMap<String, Serializable>();
	}

	@Override
	public void put(String key,Serializable value,
			RequestContext context) throws MerchantException {
		switch (context) {
		case API_PERMISSION_CONFIG_CACHE:
			this.apiToPermissionMap.put(key, value);
			break;
		default:
			break;

		}
	}

	@Override
	public Serializable get(String key, RequestContext context)
			throws MerchantException {
		Serializable value = null;
		switch (context) {
		case API_PERMISSION_CONFIG_CACHE:
			value = this.apiToPermissionMap.get(key);
			break;
		}
		return value;
	}

}
