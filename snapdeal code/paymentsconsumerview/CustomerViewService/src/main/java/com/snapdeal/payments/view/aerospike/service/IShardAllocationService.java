package com.snapdeal.payments.view.aerospike.service;

public interface IShardAllocationService {

   final String RowKey = "Counter";
   final Integer ShardStartIndex = 1;

   public String getShard(String key);

}
