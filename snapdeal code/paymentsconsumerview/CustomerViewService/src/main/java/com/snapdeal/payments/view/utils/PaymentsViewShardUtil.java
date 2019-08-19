package com.snapdeal.payments.view.utils;

import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.aerospike.client.AerospikeException;
import com.snapdeal.payments.view.aerospike.service.IShardAllocationService;
import com.snapdeal.payments.view.aerospike.service.impl.MerchantIdShardKeyCacheServiceImpl;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;
import com.snapdeal.payments.view.datasource.DataBaseShardRelationMap;

@Slf4j
@Component
public class PaymentsViewShardUtil {

   @Autowired
   @Qualifier("databaseShardRelationMap")
   private DataBaseShardRelationMap databaseShardRelationMap;

   private final Random random = new Random();

  
   @Autowired
   private MerchantIdShardKeyCacheServiceImpl merchantShardKeyCacheServiceImpl;

   @Autowired
   @Qualifier("MerchantRoundRobinAllocationService")
   IShardAllocationService shardAllocation;

   public void setDataBaseSource(String key, ViewTypeEnum type) {
      switch (type) {
         case MERCHANTVIEW:
            PaymentsViewShardContextHolder.setShardKey(getMerchantServiceShardKey(key));
            break;
         case REQUESTVIEW:
        	 //here it it shard key;
        	 PaymentsViewShardContextHolder.setShardKey(key);
        	 break;
         default:
        	 break;
      }
   }
   private String getMerchantServiceShardKey(String marchantId) {
      String shardKey = merchantShardKeyCacheServiceImpl.getShardValueByKey(marchantId);
      if (shardKey == null || shardKey.isEmpty()) {
         throw new PaymentsViewServiceException(
                  PaymentsViewServiceExceptionCodes.MERCHANT_ID_DOES_NOT_EXIST.errCode(),
                  PaymentsViewServiceExceptionCodes.MERCHANT_ID_DOES_NOT_EXIST.errMsg());
      }
      return shardKey;
   }

	public String getTaskShardKey(String key, ViewTypeEnum type) {
		String shardKey = null;
		switch (type) {
		case MERCHANTVIEW:
			shardKey = merchantShardKeyCacheServiceImpl.getShardValueByKey(key);
			if(shardKey == null || shardKey.isEmpty()){
				shardKey = shardAllocation.getShard(type.getName());
				try {
					merchantShardKeyCacheServiceImpl.create(key, shardKey);
				} catch (AerospikeException aex) {
					if (aex.getResultCode() == 5) {
						shardKey = merchantShardKeyCacheServiceImpl
								.getShardValueByKey(key);
					} else {
						throw aex;
					}
				}
			}
			break;
		case REQUESTVIEW:
			Map<String, List<String>> databaseShardMap = databaseShardRelationMap
			.getDataBaseShardRelationMap();
			List<String> shards = databaseShardMap.get(type.name);
			int randomShard = random.nextInt(shards.size() * 1000);
			shardKey = shards.get(randomShard % shards.size());
			break;
		default:
			break;
		}
		log.info("Shard Key for "+type.getName()+" is " +shardKey);
		return shardKey;
	}
}
