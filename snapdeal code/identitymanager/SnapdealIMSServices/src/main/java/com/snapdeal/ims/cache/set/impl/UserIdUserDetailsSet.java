package com.snapdeal.ims.cache.set.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.aerospike.client.Key;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.AerospikeProperties.Bin;
import com.snapdeal.ims.constants.AerospikeProperties.Set;
import com.snapdeal.ims.dbmapper.entity.User;

@Service("userIdUserDetailsSet")
public class UserIdUserDetailsSet extends IMSAerospikeBaseClass<String, User> {

   public UserIdUserDetailsSet() {
      super(Set.USER_DETAILS_SET);
   }

   @Override
   Key getKey(String keyContent) {
      return new Key(namespace.getValue(), set.getValue(), keyContent);
   }

   @Override
   WritePolicy getWritePolicy(User value) {
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.expiration = Integer.valueOf(Configuration
               .getGlobalProperty(ConfigurationConstants.USER_SRO_TTL_INTERVAL));
      writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
      
      return writePolicy;
   }

   @Override
   WritePolicy getDeletePolicy() {
      return new WritePolicy();
   }

   @Override
   WritePolicy getUpdatePolicy(IMSAerospikeRecord<User> existingRecord) {
      return null;
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
      return Bin.USER_DETAILS_BIN;
   }


   @Override
   void checkAndRefreshValueTTL(String userId, int expiration) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss z");
      try {
         Date baseDate = sdf.parse("01/01/2010-00:00:00 GMT");
         long currenSecondsFromBaseDate = new Date().getTime() - baseDate.getTime();

         int userDetailsRefeshTtlThresholdSeconds = Integer
                  .valueOf(Configuration
                           .getGlobalProperty(ConfigurationConstants.USER_SRO_TTL_REFRESH_THRESHOLD_INTERVAL));

         int userSROTTLSeconds = Integer.valueOf(Configuration
                  .getGlobalProperty(ConfigurationConstants.USER_SRO_TTL_INTERVAL));

         if ((expiration - currenSecondsFromBaseDate / 1000) < userDetailsRefeshTtlThresholdSeconds) {
            refreshTTL(userId, userSROTTLSeconds);
         }
      } catch (ParseException e) {
         // TODO: log it
      }
   }

}
