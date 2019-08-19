package com.snapdeal.merchant.dao;

import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.util.RequestContext;


public interface IClientDao<T> {

	public void put(String key, T model,
			RequestContext context) throws MPAerospikeException;

	public T get(String key, RequestContext context)
			throws MPAerospikeException;
	
	public boolean exist(String key, RequestContext context) throws MPAerospikeException;

	public boolean delete(String key, RequestContext context)
			throws MPAerospikeException;
	
	public void touch(String key, RequestContext context) throws MPAerospikeException;
}
