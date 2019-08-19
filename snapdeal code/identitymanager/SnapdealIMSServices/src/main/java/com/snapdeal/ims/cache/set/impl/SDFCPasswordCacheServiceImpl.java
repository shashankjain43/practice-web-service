package com.snapdeal.ims.cache.set.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.GenerationPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.cache.service.ISDFCPasswordCacheService;
import com.snapdeal.ims.cache.util.IMSCacheUtil;
import com.snapdeal.ims.cache.util.IMSCacheUtil.OPERATION;
import com.snapdeal.ims.constants.AerospikeProperties;
import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.enums.EmailVerificationSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SDFCPasswordCacheServiceImpl implements ISDFCPasswordCacheService {
	
	/*private static final Logger LOG = 
			LoggerFactory.getLogger(SDFCPasswordCacheServiceImpl.class);
*/
   @Autowired
   IIMSCacheServiceProvider imsAerospikeClient;

   @Autowired
   IMSCacheUtil imsCacheUtil;

   @Override
   public boolean updateEmailIdbyPwdMapping(String emailId, SdFcPasswordEntity sdFcPasswordEntity) {
      boolean writeSuccessful = true;
      Key key = null;
      try {
         WritePolicy writePolicy = new WritePolicy();
         SdFcPasswordEntity sdFcPasswordEntityFromCache = getUpgradeStatusEntity(emailId,
                  writePolicy);
         if (sdFcPasswordEntityFromCache == null) {
            // This is the case when user does first login from one merchant
            sdFcPasswordEntityFromCache = new SdFcPasswordEntity();
            writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
         } else {
            writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
         }
         if (sdFcPasswordEntity.getFcFcHashedPassword() == null) {
            sdFcPasswordEntityFromCache.setSdFcHashedPassword(sdFcPasswordEntity
                     .getSdFcHashedPassword());
            sdFcPasswordEntityFromCache.setSdSdHashedPassword(sdFcPasswordEntity
                     .getSdSdHashedPassword());
         } else {
            sdFcPasswordEntityFromCache.setFcSdHashedPassword(sdFcPasswordEntity
                     .getFcSdHashedPassword());
            sdFcPasswordEntityFromCache.setFcFcHashedPassword(sdFcPasswordEntity
                     .getFcFcHashedPassword());
         }
         key = putSdFcPasswordByEmailId(emailId, sdFcPasswordEntityFromCache, writePolicy);
      } catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to write data in aerospike: ",
                  aex);
         writeSuccessful = false;
      }
      return writeSuccessful;
   }

   @Override
   public boolean createEmailIdbyPwdMapping(String emailId, SdFcPasswordEntity sdFcPasswordEntity) {
      boolean writeSuccessful = true;
      Key key = null;
      try {
         WritePolicy writePolicy = new WritePolicy();
         SdFcPasswordEntity sdFcPasswordEntityFromCache = getUpgradeStatusEntity(emailId,
                  writePolicy);
         if (sdFcPasswordEntityFromCache == null) {
            // This is the case when user does first login from one merchant
            sdFcPasswordEntityFromCache = sdFcPasswordEntity;
            writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
            key = putSdFcPasswordByEmailId(emailId, sdFcPasswordEntityFromCache, writePolicy);
         } else if ((sdFcPasswordEntityFromCache.getFcFcHashedPassword() == null && sdFcPasswordEntity
                  .getFcFcHashedPassword() != null)) {
            // this is the case when user logins from other merchant
            sdFcPasswordEntityFromCache.setFcSdHashedPassword(sdFcPasswordEntity
                     .getFcSdHashedPassword());
            sdFcPasswordEntityFromCache.setFcFcHashedPassword(sdFcPasswordEntity
                     .getFcFcHashedPassword());
            writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
            key = putSdFcPasswordByEmailId(emailId, sdFcPasswordEntityFromCache, writePolicy);
         } else if (sdFcPasswordEntityFromCache.getSdSdHashedPassword() == null
                  && sdFcPasswordEntity.getSdSdHashedPassword() != null) {
            // this is the case when user logins from other merchant
            sdFcPasswordEntityFromCache.setSdFcHashedPassword(sdFcPasswordEntity
                     .getSdFcHashedPassword());
            sdFcPasswordEntityFromCache.setSdSdHashedPassword(sdFcPasswordEntity
                     .getSdSdHashedPassword());
            writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
            key = putSdFcPasswordByEmailId(emailId, sdFcPasswordEntityFromCache, writePolicy);
         }

      } catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to write data in aerospike: ",
                  aex);
         writeSuccessful = false;
      }
      return writeSuccessful;
   }
   
   @Override
   public boolean updateImsSdHashedPassword(String emailId, String password) {
      boolean writeSuccessful = true;
      Key key = null;
      try {

         key = new Key(AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
                  AerospikeProperties.Set.EMAIL_SD_FC_PASSWORD_SET.getValue(), emailId);

         WritePolicy writePolicy = new WritePolicy();
         SdFcPasswordEntity entityFromCache = getUpgradeStatusEntity(emailId, writePolicy);
         if (entityFromCache == null) {
            entityFromCache = new SdFcPasswordEntity();
            writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;

         } else {
            writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
         }
         entityFromCache.setImsSdHashedPassword(password);
         Bin bin = new Bin(AerospikeProperties.Bin.SD_FC_PASSWORD_BIN.getValue(), entityFromCache);
         imsAerospikeClient.getClient().put(writePolicy, key, bin);
      } catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to write data in aerospike: ",
                  aex);
         writeSuccessful = false;
      }
      return writeSuccessful;
   }

   private Key putSdFcPasswordByEmailId(String emailId, SdFcPasswordEntity sdFcPasswordEntity,
            WritePolicy writePolicy) {
      Key key;
      key = new Key(AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
               AerospikeProperties.Set.EMAIL_SD_FC_PASSWORD_SET.getValue(), emailId);
      Bin bin = new Bin(AerospikeProperties.Bin.SD_FC_PASSWORD_BIN.getValue(), sdFcPasswordEntity);
      imsAerospikeClient.getClient().put(writePolicy, key, bin);
      imsCacheUtil.logCacheOperation(OPERATION.PUT,
               AerospikeProperties.Set.EMAIL_SD_FC_PASSWORD_SET,
               AerospikeProperties.Bin.SD_FC_PASSWORD_BIN, emailId, sdFcPasswordEntity);
      return key;
   }

   @Override
   public SdFcPasswordEntity getUpgradeStatusEntity(String emailId) {
      return getUpgradeStatusEntity(emailId, null);

   }

   @Override
   public boolean setIsUpgradeShown(String emailId, boolean isUpgradeShown) {
      boolean writeSuccessful = true;
      Key key = null;
      try {

         key = new Key(AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
                  AerospikeProperties.Set.EMAIL_SD_FC_PASSWORD_SET.getValue(), emailId);

         WritePolicy writePolicy = new WritePolicy();
         SdFcPasswordEntity entityFromCache = getUpgradeStatusEntity(emailId, writePolicy);
         if (entityFromCache == null) {
            entityFromCache = new SdFcPasswordEntity();
            writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
         } else {
            writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
         }
         entityFromCache.setUpgradeInitialized(isUpgradeShown);
         Bin bin = new Bin(AerospikeProperties.Bin.SD_FC_PASSWORD_BIN.getValue(), entityFromCache);
         imsAerospikeClient.getClient().put(writePolicy, key, bin);
      } catch (AerospikeException aex) {
         writeSuccessful = false;
      }
      return writeSuccessful;
   }

   @Override
   public boolean setImsSdHashedPassword(String emailId, String password) {
      boolean writeSuccessful = true;
      Key key = null;
      try {

         key = new Key(AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
                  AerospikeProperties.Set.EMAIL_SD_FC_PASSWORD_SET.getValue(), emailId);

         WritePolicy writePolicy = new WritePolicy();
         SdFcPasswordEntity entityFromCache = getUpgradeStatusEntity(emailId, writePolicy);
         if (entityFromCache == null) {
            entityFromCache = new SdFcPasswordEntity();
            writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;

         } else if (entityFromCache.getImsSdHashedPassword() == null) {

            writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
         }
         entityFromCache.setImsSdHashedPassword(password);
         Bin bin = new Bin(AerospikeProperties.Bin.SD_FC_PASSWORD_BIN.getValue(), entityFromCache);
         imsAerospikeClient.getClient().put(writePolicy, key, bin);
      } catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key, "Unable to write data in aerospike: ",
                  aex);
         writeSuccessful = false;
      }
      return writeSuccessful;
   }

   private SdFcPasswordEntity getUpgradeStatusEntity(String emailId, WritePolicy policy) {
      SdFcPasswordEntity entity = null;
      Key key = null;
      try {
         key = new Key(AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
                  AerospikeProperties.Set.EMAIL_SD_FC_PASSWORD_SET.getValue(), emailId);
         Record record = imsAerospikeClient.getClient().get(
                  imsAerospikeClient.getClientConfig().getReadPolicy(), key);
         if (record != null) {
            entity = (SdFcPasswordEntity) record
                     .getValue(AerospikeProperties.Bin.SD_FC_PASSWORD_BIN.getValue());
            if (policy != null) {
               policy.generation = record.generation;
               policy.generationPolicy = GenerationPolicy.EXPECT_GEN_EQUAL;
            }
         }
         imsCacheUtil.logCacheOperation(OPERATION.GET,
                  AerospikeProperties.Set.EMAIL_SD_FC_PASSWORD_SET,
                  AerospikeProperties.Bin.SD_FC_PASSWORD_BIN, emailId, entity);

      } catch (AerospikeException aex) {
         imsCacheUtil.handleAerospikeExceptionEvictKey(key,
                  "Unable to fetch record from aerospike", aex);
      }
      return entity;
   }

   @Override
   public boolean setEmailVerificationSource(String emailId, EmailVerificationSource verificationSource) {
      boolean writeSuccessful = true;
      Key key = null;
      try {

         key = new Key(AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
                  AerospikeProperties.Set.EMAIL_SD_FC_PASSWORD_SET.getValue(), emailId);

         WritePolicy writePolicy = new WritePolicy();
         SdFcPasswordEntity entityFromCache = getUpgradeStatusEntity(emailId, writePolicy);
         if (entityFromCache == null) {
            entityFromCache = new SdFcPasswordEntity();
            writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
         } else {
            writePolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
         }
         entityFromCache.setEmailVerificationSource(verificationSource);
         Bin bin = new Bin(AerospikeProperties.Bin.SD_FC_PASSWORD_BIN.getValue(), entityFromCache);
         imsAerospikeClient.getClient().put(writePolicy, key, bin);
      } catch (AerospikeException aex) {
    	  log.error("Exception on setting Email Verification source in aerospike");
         writeSuccessful = false;
      }
      return writeSuccessful;
   }

}
