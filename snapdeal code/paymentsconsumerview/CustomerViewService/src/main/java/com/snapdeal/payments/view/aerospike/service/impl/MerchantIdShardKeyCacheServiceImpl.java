package com.snapdeal.payments.view.aerospike.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.view.aerospike.service.IShardKeyService;
import com.snapdeal.payments.view.aerospike.set.MercahntViewMerchantIdShardKeySet;

@Component
public class MerchantIdShardKeyCacheServiceImpl implements IShardKeyService{

	 @Autowired
	   @Qualifier("mercahntViewMerchantIdShardKeySet")
	   private MercahntViewMerchantIdShardKeySet merchantViewShardKeySet ;;
	   
	   @Override
	   public boolean create(String mercahntId, String shardKey) {
	      boolean writeSuccessful = true;
	      writeSuccessful = merchantViewShardKeySet.insert(mercahntId, shardKey);
	      return writeSuccessful;
	   }

	   @Override
	   public String getShardValueByKey(String mercahntId) {
		   if(mercahntId.equals("K6971Hb9mY94Ml")){
			   return "db4";
		   }
	      String shardKey = null;
	      shardKey = merchantViewShardKeySet.get(mercahntId).getValue();  
	      return shardKey;
	   }
}
