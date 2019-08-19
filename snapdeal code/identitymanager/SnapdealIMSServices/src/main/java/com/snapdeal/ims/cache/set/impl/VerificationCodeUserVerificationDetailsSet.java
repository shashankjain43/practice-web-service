package com.snapdeal.ims.cache.set.impl;

import org.springframework.stereotype.Service;

import com.aerospike.client.Key;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.constants.AerospikeProperties.Bin;
import com.snapdeal.ims.constants.AerospikeProperties.Set;
import com.snapdeal.ims.dbmapper.entity.UserVerification;

@Service("verificationCodeUserVerificationDetailsSet")
public class VerificationCodeUserVerificationDetailsSet extends
         IMSAerospikeBaseClass<String, UserVerification> {

   public VerificationCodeUserVerificationDetailsSet() {
      super(Set.USER_VERIFICATION_SET);
   }

   @Override
   Key getKey(String keyContent) {
      return new Key(namespace.getValue(), set.getValue(), keyContent);
   }

   @Override
   Bin getBin() {
      return Bin.USER_VERIFICATION_BIN;
   }

   @Override
   WritePolicy getWritePolicy(UserVerification value) {
      WritePolicy writePolicy = new WritePolicy();

      writePolicy.expiration = (int) (value.getCodeExpiryTime().getTime() / 1000);
      writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
      writePolicy.generationPolicy = GenerationPolicy.EXPECT_GEN_EQUAL;

      return writePolicy;
   }

   @Override
   WritePolicy getDeletePolicy() {
      // Currently this implementation not required
      return null;
   }

   @Override
   WritePolicy getUpdatePolicy(IMSAerospikeRecord<UserVerification> existingRecord) {
      // Currently this implementation not required
      return null;
   }

   @Override
   WritePolicy getRefreshTTLPolicy(int ttlSeconds) {
      // Currently this implementation not required
      return null;
   }

   @Override
   void checkAndRefreshValueTTL(String keyContent, int expiration) {}

}
