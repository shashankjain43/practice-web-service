package com.snapdeal.merchant.dao.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.cache.service.IMerchantCacheServiceProvider;
import com.snapdeal.merchant.config.constants.AeroConfigConstants;
import com.snapdeal.merchant.config.constants.AeroQueryConfig;
import com.snapdeal.merchant.dao.IClientDao;
import com.snapdeal.merchant.util.RequestContext;

public abstract class AbstractDaoImpl<T extends Serializable> implements
		IClientDao<T> {

	@Autowired
	protected IMerchantCacheServiceProvider provider;

	@Autowired
	protected AeroConfigConstants constants;

	protected AeroQueryConfig getQueryConfigFromContextMap(
			RequestContext context) throws MPAerospikeException {
		return provider.getClientConfig().getContextToQueryConfig()
				.get(context);
	}

	@Override
	public void put(String key, T model, RequestContext context)
			throws MPAerospikeException {
		// TODO Auto-generated method stub

	}

	@Override
	public T get(String key, RequestContext context)
			throws MPAerospikeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String key, RequestContext context)
			throws MPAerospikeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exist(String key, RequestContext context)
			throws MPAerospikeException {
		// TODO Auto-generated method stub
		return false;
	}

}
