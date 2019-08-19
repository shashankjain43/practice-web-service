package com.snapdeal.payments.view.aerospike.service;

import com.snapdeal.payments.view.aerospike.configuration.AerospikeConfiguration;
import com.snapdeal.payments.view.aerospike.configuration.PaymentsViewAerospikeClient;



public interface IPaymentsViewCacheServiceProvider {

   public boolean isConnected();

   public void init();
   
   public PaymentsViewAerospikeClient getClient();

   public AerospikeConfiguration getClientConfig();
}
