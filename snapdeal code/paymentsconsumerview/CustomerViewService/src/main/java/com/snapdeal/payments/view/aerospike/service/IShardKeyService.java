package com.snapdeal.payments.view.aerospike.service;


public interface IShardKeyService {

   public boolean create(String key,String value);
   
   public String getShardValueByKey(String key);

}
