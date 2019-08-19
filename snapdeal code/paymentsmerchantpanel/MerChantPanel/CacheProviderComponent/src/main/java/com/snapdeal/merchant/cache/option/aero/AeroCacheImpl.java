package com.snapdeal.merchant.cache.option.aero;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.cache.option.base.AbstractCacheImpl;
import com.snapdeal.merchant.cache.service.IMerchantCacheServiceProvider;
import com.snapdeal.merchant.config.constants.AeroConfigConstants;
import com.snapdeal.merchant.dao.IClientDao;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.util.RequestContext;

@Slf4j
@Component("aerocache")
public class AeroCacheImpl extends AbstractCacheImpl {

	@Autowired
	private IMerchantCacheServiceProvider provider;

	@Autowired
	private AeroConfigConstants config;

	@Autowired
	private IClientDao<Serializable> dao;

	@PostConstruct
	public void initCache() throws MerchantException {
		try {
			provider.connectToCacheCluster(config.getClusterInfo());
		} catch (MPAerospikeException e) {
			log.error("Aerospike initialization failed", e);
			throw new MerchantException(
					RequestExceptionCodes.CACHE_INIT_ERROR.getErrCode(),
					RequestExceptionCodes.CACHE_INIT_ERROR.getErrMsg());
		}
	}

	private IClientDao<Serializable> getDao() {
		return dao;
	}

	@Override
	public void put(String key, Serializable request, RequestContext context)
			throws MerchantException {
		IClientDao<Serializable> dao = getDao();
		try {
			dao.put(key, request, context);
		} catch (MPAerospikeException e) {
			log.error("error on putting the key {} and value {}", key,
					request.toString());
			throw new MerchantException(
					RequestExceptionCodes.CACHE_PUT_ERROR.getErrCode(),
					RequestExceptionCodes.CACHE_PUT_ERROR.getErrMsg());
		}
	}

	@Override
	public Serializable get(String key, RequestContext context)
			throws MerchantException {
		IClientDao<Serializable> dao = getDao();
		try {
			return (Serializable) dao.get(key, context);
		} catch (MPAerospikeException e) {
			log.error("error on getting the key {} from cache", key);
			throw new MerchantException(
					RequestExceptionCodes.CACHE_GET_ERROR.getErrCode(),
					RequestExceptionCodes.CACHE_GET_ERROR.getErrMsg());
		}
	}

	@Override
	public boolean delete(String key, RequestContext context)
			throws MerchantException {
		IClientDao<Serializable> dao = getDao();
		try {
			return dao.delete(key, context);
		} catch (MPAerospikeException e) {
			log.error("error on delete the key {} from cache", key);
			throw new MerchantException(
					RequestExceptionCodes.CACHE_DELETE_ERROR.getErrCode(),
					RequestExceptionCodes.CACHE_DELETE_ERROR.getErrMsg());
		}
	}

	@Override
	public boolean exists(String key, RequestContext context)
			throws MerchantException {
		IClientDao<Serializable> dao = getDao();
		try {
			return dao.exist(key, context);
		} catch (MPAerospikeException e) {
			log.error("error on exist query for key {} from cache", key);
			throw new MerchantException(
					RequestExceptionCodes.CACHE_EXIST_ERROR.getErrCode(),
					RequestExceptionCodes.CACHE_EXIST_ERROR.getErrMsg());
		}
	}
	
	@Override
	public void touch(String key, RequestContext context)
			throws MerchantException {
		IClientDao<Serializable> dao = getDao();
		try {
			dao.touch(key, context);
			return;
		} catch (MPAerospikeException e) {
			log.error("error on touching the key {} from cache", key);
			throw new MerchantException(
					RequestExceptionCodes.CACHE_EXIST_ERROR.getErrCode(),
					RequestExceptionCodes.CACHE_EXIST_ERROR.getErrMsg());
		}
	}

}
