package com.snapdeal.ims.cache.set.impl;

import org.springframework.stereotype.Service;

import com.aerospike.client.Key;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.constants.AerospikeProperties.Bin;
import com.snapdeal.ims.constants.AerospikeProperties.Set;
import com.snapdeal.ims.entity.SdFcPasswordEntity;

@Service("emailIdSdFcHashedPasswordsSet")
public class EmailIdHashedPasswordsSet extends IMSAerospikeBaseClass<String, SdFcPasswordEntity> {

   public EmailIdHashedPasswordsSet() {
      super(Set.EMAIL_SD_FC_PASSWORD_SET);
   }

   @Override
   Bin getBin() {
      return Bin.SD_FC_PASSWORD_BIN;
   }

   @Override
   Key getKey(String keyContent) {
      return new Key(namespace.getValue(), set.getValue(), keyContent);
   }

   @Override
   WritePolicy getWritePolicy(SdFcPasswordEntity value) {
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;

      return writePolicy;
   }

   @Override
   WritePolicy getDeletePolicy() {
      return new WritePolicy();
   }

   @Override
   WritePolicy getUpdatePolicy(IMSAerospikeRecord<SdFcPasswordEntity> existingRecord) {
      return null;
   }

   @Override
   WritePolicy getRefreshTTLPolicy(int ttlSeconds) {
      return null;
   }

   @Override
   void checkAndRefreshValueTTL(String keyContent, int expiration) {
   }

}
