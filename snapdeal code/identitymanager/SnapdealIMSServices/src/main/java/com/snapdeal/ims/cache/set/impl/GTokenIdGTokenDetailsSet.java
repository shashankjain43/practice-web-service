package com.snapdeal.ims.cache.set.impl;

import com.aerospike.client.Key;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.constants.AerospikeProperties.Bin;
import com.snapdeal.ims.constants.AerospikeProperties.Set;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("gTokenIdGTokenDetailsSet")
public class GTokenIdGTokenDetailsSet extends IMSAerospikeBaseClass<String, GlobalTokenDetailsEntity>{

   public GTokenIdGTokenDetailsSet() {
      super(Set.GTOKEN_ID_OBJECT_SET);
   }

   @Override
   Key getKey(String keyContent) {
      return new Key(namespace.getValue(), set.getValue(), keyContent);
   }

   @Override
   WritePolicy getWritePolicy(GlobalTokenDetailsEntity value) {
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
      writePolicy.expiration = getTTL(value);
      log.info("Global Token :: " + value + ", ttl ::" + writePolicy.expiration);
      return writePolicy;
   }

   private int getTTL(GlobalTokenDetailsEntity value) {
      long ttl = Math.abs(System.currentTimeMillis() - value.getExpiryTime().getTime());
      return (int) (ttl / 1000);
   }

   @Override
   WritePolicy getDeletePolicy() {
      return cacheServiceProvider.getClientConfig().getWritePolicy();
   }

   @Override
   WritePolicy getUpdatePolicy(IMSAerospikeRecord<GlobalTokenDetailsEntity> existingRecord) {
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
      writePolicy.generation = existingRecord.getGeneration();
      writePolicy.generationPolicy = GenerationPolicy.EXPECT_GEN_EQUAL;
      writePolicy.expiration = getTTL(existingRecord.getValue());

      return writePolicy;
   }

   @Override
   WritePolicy getRefreshTTLPolicy(int ttlSeconds) {
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.expiration = ttlSeconds;
      writePolicy.generationPolicy = GenerationPolicy.NONE;

      return writePolicy;
   }

   @Override
   Bin getBin() {
      return Bin.GTOKEN_ID_OBJECT_BIN;
   }

   @Override
   void checkAndRefreshValueTTL(String keyContent, int expiration) {}


}
