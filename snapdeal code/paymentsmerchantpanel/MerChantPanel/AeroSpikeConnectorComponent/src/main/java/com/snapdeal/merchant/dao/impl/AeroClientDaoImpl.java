package com.snapdeal.merchant.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.config.constants.AeroQueryConfig;
import com.snapdeal.merchant.util.RequestContext;

@Repository("aeroclient")
public class AeroClientDaoImpl extends AbstractDaoImpl<Serializable> {
	
	@Override
	public void put(String cacheKey, Serializable entity, RequestContext context)
			throws MPAerospikeException {
		AeroQueryConfig queryConfig = getQueryConfigFromContextMap(context);
		Key key = new Key(constants.getNamespace(), queryConfig.getQuerySet(),
				cacheKey);
		Bin bin = new Bin(queryConfig.getQueryBin(), entity);
		provider.getClient().put(queryConfig.getQueryWritePolicy(),
				key, bin);

	}

	@Override
	public Serializable get(String cacheKey, RequestContext context)
			throws MPAerospikeException {
		AeroQueryConfig queryConfig = getQueryConfigFromContextMap(context);
		Key key = new Key(constants.getNamespace(), queryConfig.getQuerySet(),
				cacheKey);
		Record record = provider.getClient().get(queryConfig.getQueryPolicy(),
				key);
		if(record == null)
			return null;
		
		return (Serializable) record.getValue(queryConfig.getQueryBin());
	}

	@Override
	public boolean delete(String cacheKey, RequestContext context)
			throws MPAerospikeException {
		AeroQueryConfig queryConfig = getQueryConfigFromContextMap(context);
		Key key = new Key(constants.getNamespace(), queryConfig.getQuerySet(),
				cacheKey);
		boolean status = provider.getClient().delete(
				queryConfig.getQueryWritePolicy(), key);
		return status;
	}

	@Override
	public boolean exist(String cacheKey, RequestContext context)
			throws MPAerospikeException {
		AeroQueryConfig queryConfig = getQueryConfigFromContextMap(context);
		Key key = new Key(constants.getNamespace(), queryConfig.getQuerySet(),
				cacheKey);
		boolean status = provider.getClient().exists(
				queryConfig.getQueryWritePolicy(), key);
		return status;
	}
	
	@Override
	public void touch(String cacheKey, RequestContext context)
			throws MPAerospikeException {
		AeroQueryConfig queryConfig = getQueryConfigFromContextMap(context);
		Key key = new Key(constants.getNamespace(), queryConfig.getQuerySet(),
				cacheKey);
		provider.getClient().touch(queryConfig.getQueryWritePolicy(), key);
		return;
	}

}
