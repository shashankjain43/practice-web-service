package com.snapdeal.ims.cache.set.impl;

import org.springframework.stereotype.Service;

import com.aerospike.client.Key;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.constants.AerospikeProperties.Bin;
import com.snapdeal.ims.constants.AerospikeProperties.Set;

@Service("imsUserIdEmailIdSet")
public class IMSUSerIdEmailIdSet extends IMSAerospikeBaseClass<String, String> {

   public IMSUSerIdEmailIdSet() {
      super(Set.IMSUSERID_EMAIL_SET);
   }

   @Override
   Key getKey(String keyContent) {
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
      // TODO Auto-generated method stub
      return null;
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
   Bin getBin() {
      return Bin.IMSUSERID_EMAIL_BIN;
   }

   @Override
   void checkAndRefreshValueTTL(String keyContent, int expiration) {
      // TODO Auto-generated method stub
      
   }


}
