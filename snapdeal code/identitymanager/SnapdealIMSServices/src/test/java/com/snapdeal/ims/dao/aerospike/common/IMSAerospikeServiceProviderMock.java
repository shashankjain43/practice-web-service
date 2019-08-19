package com.snapdeal.ims.dao.aerospike.common;

import org.springframework.stereotype.Service;

import com.snapdeal.ims.cache.configuration.AerospikeConfiguration;
import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.cache.service.impl.IMSAerospikeClient;

@Service
public class IMSAerospikeServiceProviderMock implements IIMSCacheServiceProvider{

   AerospikeConfiguration config;
   
   AerospikeClientMock client;
   
   @Override
   public boolean isConnected() {
      return true;
   }

   @Override
   public void connectToCacheCluster(String clusterInfo) {
      config = new AerospikeConfiguration();
      config.loadConfiguration();
      
      client = new AerospikeClientMock();
   }

   @Override
   public IMSAerospikeClient getClient() {
      return client;
   }

   @Override
   public AerospikeConfiguration getClientConfig() {
      return config;
   }
   

}
