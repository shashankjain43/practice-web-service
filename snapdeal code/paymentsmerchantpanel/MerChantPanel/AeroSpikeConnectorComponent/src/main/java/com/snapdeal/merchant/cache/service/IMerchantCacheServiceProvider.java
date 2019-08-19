package com.snapdeal.merchant.cache.service;

import com.snapdeal.merchant.aero.config.AerospikeConfiguration;
import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.cache.client.ICacheClient;


public interface IMerchantCacheServiceProvider {

   public boolean isConnected();

   public void connectToCacheCluster(String clusterInfo) throws MPAerospikeException;
   
   public ICacheClient getClient() throws MPAerospikeException;

   public AerospikeConfiguration getClientConfig() throws MPAerospikeException;
}
