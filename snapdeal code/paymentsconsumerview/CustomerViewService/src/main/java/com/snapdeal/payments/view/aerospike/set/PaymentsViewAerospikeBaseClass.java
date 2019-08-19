package com.snapdeal.payments.view.aerospike.set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Log;
import com.aerospike.client.Record;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.payments.view.aerospike.configuration.PaymentsViewAerospikeRecord;
import com.snapdeal.payments.view.aerospike.service.IPaymentsViewCacheServiceProvider;
import com.snapdeal.payments.view.aerospike.utils.AerospikeProperties;
import com.snapdeal.payments.view.aerospike.utils.PaymentsViewAerospikeCacheUtil;
import com.snapdeal.payments.view.aerospike.utils.AerospikeProperties.Namespace;
import com.snapdeal.payments.view.aerospike.utils.AerospikeProperties.Set;
import com.snapdeal.payments.view.aerospike.utils.PaymentsViewAerospikeCacheUtil.OPERATION;

@Slf4j
public abstract class PaymentsViewAerospikeBaseClass<SetKey, SetValue> implements
         IPaymentsViewAerospikeSet<SetKey, SetValue> {

   @Autowired
   IPaymentsViewCacheServiceProvider cacheServiceProvider;

   protected Namespace namespace = Namespace.USER_SHARD_SPACE;

   protected Set set;

   @Autowired
   PaymentsViewAerospikeCacheUtil paymentsViewCacheUtil;

   public PaymentsViewAerospikeBaseClass(Set set) {
      this.set = set;
   }

   @Override
   public PaymentsViewAerospikeRecord<SetValue> get(SetKey keyContent) {
      PaymentsViewAerospikeRecord<SetValue> value = new PaymentsViewAerospikeRecord<SetValue>();

      Key key = getKey(keyContent);
      try {
         Policy policy = cacheServiceProvider.getClientConfig().getReadPolicy();
         Record record = cacheServiceProvider.getClient().get(policy, key);

         if (record != null) {
            value = new PaymentsViewAerospikeRecord<SetValue>(record, getBin());
            
            checkAndRefreshValueTTL(keyContent, record.expiration);
         }

         paymentsViewCacheUtil.logCacheOperation(OPERATION.GET, set, getBin(), keyContent, value);
      } catch (AerospikeException aex) {
         paymentsViewCacheUtil.handleAerospikeExceptionEvictKey(key,
                  "Unable to fetch record from aerospike", aex);
      }

      return value;
   }

   @Override
   public boolean insert(SetKey keyContent, SetValue value) {
      boolean writeSuccessful = true;
      Key key = getKey(keyContent);
      try {
         // write policy will contain recordExistsAction and expiration which
         // comes in value itself
         WritePolicy writePolicy = getWritePolicy(value);

         Bin valueBin = new Bin(getBin().getValue(), value);
         cacheServiceProvider.getClient().put(writePolicy, key, valueBin);

         paymentsViewCacheUtil.logCacheOperation(OPERATION.PUT, set, getBin(), keyContent, value);
      } catch (AerospikeException aex) {
    	  if(aex.getResultCode() == 5)
    	  {
    		// log.info("error occurede ",aex); 
    	  }
         paymentsViewCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to write data in aerospike: ",
                  aex);
         writeSuccessful = false;
      }
      return writeSuccessful;
   }

   @Override
   public void refreshTTL(SetKey keyContent, int ttlSeconds) {
      Key key = getKey(keyContent);

      WritePolicy writePolicy = new WritePolicy();
      writePolicy.expiration = ttlSeconds;
      writePolicy.generationPolicy = GenerationPolicy.NONE;

      try{
         cacheServiceProvider.getClient().touch(writePolicy, key);
         paymentsViewCacheUtil.logCacheOperation(OPERATION.TOUCH, set,
                  AerospikeProperties.Bin.USER_ID_BIN, keyContent, ttlSeconds + " Seconds");
      }catch (AerospikeException aex) {
         paymentsViewCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to refresh data in aerospike: ",
                  aex);
      }
   }
   
   @Override
   public boolean exist(SetKey keyContent) {
      
      PaymentsViewAerospikeRecord<SetValue> value = get(keyContent);
      if(value != null && value.getValue() != null){
         return true;
      }
      
      return false;
   }

   abstract Key getKey(SetKey keyContent);
   
   abstract WritePolicy getWritePolicy(SetValue value);

   abstract WritePolicy getDeletePolicy();

   abstract WritePolicy getUpdatePolicy(PaymentsViewAerospikeRecord<SetValue> existingRecord);

   abstract WritePolicy getRefreshTTLPolicy(int ttlSeconds);

   abstract AerospikeProperties.Bin getBin();

   abstract void checkAndRefreshValueTTL(SetKey keyContent, int expiration);

}
