package com.snapdeal.ims.cache.set.impl;

import org.springframework.stereotype.Service;

import com.aerospike.client.Key;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.constants.AerospikeProperties.Bin;
import com.snapdeal.ims.constants.AerospikeProperties.Set;

@Service("sdIdUserIdSet")
public class SdIdUserIdSet extends IMSAerospikeBaseClass<Integer, String> {

   public SdIdUserIdSet() {
      super(Set.USER_SD_USERID_SET);
   }

   @Override
   Key getKey(Integer keyContent) {
      return new Key(namespace.getValue(), set.getValue(), keyContent);
   }

   @Override
   WritePolicy getWritePolicy(String value) {
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
      
      return writePolicy;
   }

   @Override
   WritePolicy getDeletePolicy() {
      return new WritePolicy();
   }

   @Override
   Bin getBin() {
      return Bin.USER_ID_BIN;
   }

   @Override
   WritePolicy getUpdatePolicy(IMSAerospikeRecord<String> existingRecord) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   WritePolicy getRefreshTTLPolicy(int ttlSeconds) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   void checkAndRefreshValueTTL(Integer keyContent, int expiration) {}

   
}
