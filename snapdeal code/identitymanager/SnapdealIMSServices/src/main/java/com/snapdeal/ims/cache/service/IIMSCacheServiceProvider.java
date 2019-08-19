package com.snapdeal.ims.cache.service;

import com.snapdeal.ims.cache.configuration.AerospikeConfiguration;
import com.snapdeal.ims.cache.service.impl.IMSAerospikeClient;


public interface IIMSCacheServiceProvider {

   public boolean isConnected();

   public void connectToCacheCluster(String clusterInfo);
   
   public IMSAerospikeClient getClient();

   public AerospikeConfiguration getClientConfig();
}
