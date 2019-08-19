package com.snapdeal.ims.cache.set.impl;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.cache.set.IIMSAerospikeSet;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.cache.util.IMSCacheUtil;
import com.snapdeal.ims.cache.util.IMSCacheUtil.OPERATION;
import com.snapdeal.ims.constants.AerospikeProperties;
import com.snapdeal.ims.constants.AerospikeProperties.Namespace;
import com.snapdeal.ims.constants.AerospikeProperties.Set;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class IMSAerospikeBaseClass<SetKey, SetValue> implements
         IIMSAerospikeSet<SetKey, SetValue> {

   @Autowired
   IIMSCacheServiceProvider cacheServiceProvider;

   protected Namespace namespace = Namespace.USER_NAMESPACE;

   protected Set set;

   @Autowired
   IMSCacheUtil imsCacheUtil;

   public IMSAerospikeBaseClass(Set set) {
      this.set = set;
   }

   @Override
   @Timed
   @Marked
   public IMSAerospikeRecord<SetValue> get(SetKey keyContent) {
      IMSAerospikeRecord<SetValue> value = new IMSAerospikeRecord<SetValue>();

      Key key = getKey(keyContent);
      try {
         Policy policy = cacheServiceProvider.getClientConfig().getReadPolicy();
         Record record = cacheServiceProvider.getClient().get(policy, key);

         if (record != null) {
            value = new IMSAerospikeRecord<SetValue>(record, getBin());
            
            checkAndRefreshValueTTL(keyContent, record.expiration);
         }

         imsCacheUtil.logCacheOperation(OPERATION.GET, set, getBin(), keyContent, value);
      } catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key,
                  "Unable to fetch record from aerospike", aex);
      }

      return value;
   }

   @Override
   @Timed
   @Marked
   public boolean insert(SetKey keyContent, SetValue value) {
      boolean writeSuccessful = true;
      Key key = getKey(keyContent);
      try {
         // write policy will contain recordExistsAction and expiration which
         // comes in value itself
         WritePolicy writePolicy = getWritePolicy(value);

         Bin valueBin = new Bin(getBin().getValue(), value);

         cacheServiceProvider.getClient().put(writePolicy, key, valueBin);

         imsCacheUtil.logCacheOperation(OPERATION.PUT, set, getBin(), keyContent, value);
      } catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to write data in aerospike: ",
                  aex);
         writeSuccessful = false;
      }
      return writeSuccessful;
   }

   @Override
   @Timed
   @Marked
   public boolean update(SetKey keyContent, IMSAerospikeRecord<SetValue> valueRecord) {
      boolean writeSuccessful = true;
      Key key = getKey(keyContent);
      try {

         // This write policy should have been from the existingRecord which was
         // fetched and updated : "which is value as passed parameter"
         WritePolicy writePolicy = getUpdatePolicy(valueRecord);

         Bin valueBin = new Bin(getBin().getValue(), valueRecord.getValue());
         cacheServiceProvider.getClient().put(writePolicy, key, valueBin);

         imsCacheUtil.logCacheOperation(OPERATION.PUT, set, getBin(), keyContent,
                  valueRecord.getValue());
      } catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to update data in aerospike: ",
                  aex);
         writeSuccessful = false;
      }
      return writeSuccessful;
   }

   @Override
   @Timed
   @Marked
   public boolean delete(SetKey keyContent) {
      boolean deleteSuccessful = true;
      Key key = getKey(keyContent);
      try {
         WritePolicy deletePolicy = getDeletePolicy();
         cacheServiceProvider.getClient().delete(deletePolicy, key);

         imsCacheUtil.logCacheOperation(OPERATION.EVICT, set, getBin(), keyContent, null);
      } catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to delete data in aerospike: ",
                  aex);
         deleteSuccessful = false;
      }
      return deleteSuccessful;
   }

   @Override
   @Timed
   @Marked
   public void refreshTTL(SetKey keyContent, int ttlSeconds) {
      Key key = getKey(keyContent);

      WritePolicy writePolicy = new WritePolicy();
      writePolicy.expiration = ttlSeconds;
      writePolicy.generationPolicy = GenerationPolicy.NONE;

      try{
         cacheServiceProvider.getClient().touch(writePolicy, key);
         imsCacheUtil.logCacheOperation(OPERATION.TOUCH, set,
                  AerospikeProperties.Bin.USER_DETAILS_BIN, keyContent, ttlSeconds + " Seconds");
      }catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to refresh data in aerospike: ",
                  aex);
      }
   }
   
   @Override
   @Timed
   @Marked
   public boolean exist(SetKey keyContent) {
      
      IMSAerospikeRecord<SetValue> value = get(keyContent);
      if(value != null && value.getValue() != null){
         return true;
      }
      
      return false;
   }

   abstract Key getKey(SetKey keyContent);
   
   abstract WritePolicy getWritePolicy(SetValue value);

   abstract WritePolicy getDeletePolicy();

   abstract WritePolicy getUpdatePolicy(IMSAerospikeRecord<SetValue> existingRecord);

   abstract WritePolicy getRefreshTTLPolicy(int ttlSeconds);

   abstract AerospikeProperties.Bin getBin();

   abstract void checkAndRefreshValueTTL(SetKey keyContent, int expiration);

}
