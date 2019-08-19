package com.snapdeal.ims.cache.set.impl;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.cache.util.IMSCacheUtil.OPERATION;
import com.snapdeal.ims.constants.AerospikeProperties.Bin;
import com.snapdeal.ims.constants.AerospikeProperties.Set;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service("userIdGTokenIdsSet")
public class UserIdGTokenIdsSet extends IMSAerospikeBaseClass<String, HashSet<String>> {

   public UserIdGTokenIdsSet() {
      super(Set.GTOKEN_ID_SET);
   }

   @Override
   Key getKey(String keyContent) {
      return new Key(namespace.getValue(), set.getValue(), keyContent);
   }

   @Override
   WritePolicy getWritePolicy(HashSet<String> value) {
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
      
      return writePolicy;
   }

   @Override
   WritePolicy getDeletePolicy() {
      return cacheServiceProvider.getClientConfig().getWritePolicy();
   }

   @Override
   WritePolicy getUpdatePolicy(IMSAerospikeRecord<HashSet<String>> existingRecord) {
      
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
      writePolicy.generationPolicy = GenerationPolicy.EXPECT_GEN_EQUAL;
      writePolicy.generation = existingRecord.getGeneration();
      
      return writePolicy;
   }

   @Override
   Bin getBin() {
      return Bin.GTOKEN_ID_BIN;
   }

   @Override
   WritePolicy getRefreshTTLPolicy(int ttlSeconds) {
      return null;
   }

   @Override
   void checkAndRefreshValueTTL(String keyContent, int expiration) {}
   
   @Override
   @Marked
   @Timed
   public boolean insert(String keyContent,HashSet<String> value) {
      boolean writeSuccessful = true;
      Key key = getKey(keyContent);
      try {
         // write policy will contain recordExistsAction and expiration which
         // comes in value itself
         WritePolicy writePolicy = getWritePolicy(value);

         com.aerospike.client.Bin valueBin = new com.aerospike.client.Bin(getBin().getValue(), value);

         cacheServiceProvider.getClient().put(writePolicy, key, valueBin);

         imsCacheUtil.logCacheOperation(OPERATION.PUT, set, getBin(), keyContent, value);
      } catch (AerospikeException aex) {
         
         //If Key already exists
         if(aex.getResultCode()==5){
            throw aex;
         }
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to write data in aerospike: ",
                  aex);
         writeSuccessful = false;
      }
      return writeSuccessful;
   }
   
   @Override
   @Marked
   @Timed
   public boolean update(String keyContent, IMSAerospikeRecord<HashSet<String>> valueRecord) throws AerospikeException {
      boolean writeSuccessful = true;
      Key key = getKey(keyContent);
      try {

         // This write policy should have been from the existingRecord which was
         // fetched and updated : "which is value as passed parameter"
         WritePolicy writePolicy = getUpdatePolicy(valueRecord);

         com.aerospike.client.Bin valueBin = new com.aerospike.client.Bin(getBin().getValue(), valueRecord.getValue());
         cacheServiceProvider.getClient().put(writePolicy, key, valueBin);

         imsCacheUtil.logCacheOperation(OPERATION.PUT, set, getBin(), keyContent,
                  valueRecord.getValue());
      } catch (AerospikeException aex) {
         // throw AerospikeException in case of failed generation
         if(aex.getResultCode()==3){
            throw aex;
         }
         if(aex.getResultCode()==13){
             throw aex;
          }
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to update data in aerospike: ",
                  aex);
         writeSuccessful = false;
      }
      return writeSuccessful;
   }


}
