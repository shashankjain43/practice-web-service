package com.snapdeal.ims.cache.service.impl;

import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeException;
import com.snapdeal.ims.cache.service.IGtokenCleanup;
import com.snapdeal.ims.cache.service.ITokenCacheService;
import com.snapdeal.ims.cache.set.IIMSAerospikeSet;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;

/**
 * This is the base service class for persisting token in Aerospike
 * 
 * @author kishan
 *
 */

@Service
@Slf4j
public class TokenCacheServiceImpl implements ITokenCacheService {

	@Autowired
	@Qualifier("gTokenIdGTokenDetailsSet")
	private IIMSAerospikeSet<String, GlobalTokenDetailsEntity> gTokenIdGTokenDetailsSet;

	@Autowired
	@Qualifier("userIdGTokenIdsSet")
	private IIMSAerospikeSet<String, HashSet<String>> userIdGTokenIdsSet;

	@Autowired
	UmsMerchantProvider merchantServiceProvider;

	@Autowired
	IGtokenCleanup gtokenCleanup;

	private IMSAerospikeRecord<GlobalTokenDetailsEntity> getGTokenRecordById(String gToken){

		IMSAerospikeRecord<GlobalTokenDetailsEntity> gTokenRecord = null;
		boolean isValidGtokenId = false;
		GlobalTokenDetailsEntity globalToken = null;

		gTokenRecord = gTokenIdGTokenDetailsSet.get(gToken);
		globalToken = gTokenRecord.getValue();

		// since there is a case where in user could have left gtoken in the
		// cache , so we need to check whether userid:gtokenId exists or not

		if (globalToken != null) {
			//checking that is token associated with any user
			IMSAerospikeRecord<HashSet<String>> userRecord = userIdGTokenIdsSet.get(globalToken.getUserId());
			boolean isUserGTokenExist = (userRecord!=null && userRecord.getValue()!=null)?true:false;

			if(isUserGTokenExist){
				isValidGtokenId = isValidGtokenIdByUserId(globalToken.getUserId(),
						globalToken.getGlobalTokenId(),userRecord.getValue());
			}
		}
		if (isValidGtokenId) {
			return gTokenRecord;
		}

		return null;
	}

	@Override
	@Timed
	@Marked
	public GlobalTokenDetailsEntity getGtokenById(String gToken) {
		GlobalTokenDetailsEntity globalToken = null;
		IMSAerospikeRecord<GlobalTokenDetailsEntity> gTokenRecord = getGTokenRecordById(gToken);

		if(gTokenRecord!=null){
			globalToken = gTokenRecord.getValue();
		}

		return globalToken;
	}

	@Override
	@Timed
	@Marked
	public boolean putGTokenById(GlobalTokenDetailsEntity gToken) {
		boolean writeSuccessful = insertGtokenIdByObject(gToken);
		int retryCount = Integer.parseInt(Configuration.getGlobalProperty(ConfigurationConstants.USERID_BY_GTOKENIDS_MAX_UPDATE_RETRIES));
		long retrySleep = Long.parseLong(Configuration.getGlobalProperty(ConfigurationConstants.USERID_BY_GTOKENIDS_RETRY_THREAD_SLEEP_IN_MILLISECONDS));

		while(retryCount>0){
			try {
				writeSuccessful = updateUserIdByGtokenIdMap(gToken, writeSuccessful);
				retryCount=0;
			} catch (AerospikeException aex) {
				if(aex.getResultCode()==13){
					//This is the case when block size exceeds the given token.
					log.info("user with id  " + gToken.getUserId() + " has large number of token, hence starting cleaning up");
					cleanUpUserIdGtokenMap(gToken,true);
					log.info("Cleanup done for user with id "+gToken.getUserId());
				}
				retryCount--;
				log.warn("updateUserIdByGtokenIdMap update retry for user with ID => "+gToken.getUserId()+" : retry left - "+retryCount);
				try {
					Thread.sleep(retrySleep);
				} catch (InterruptedException e) {
					log.debug("updateUserIdByGtokenIdMap retry thread InterruptedException");
				}
			}
		}

		return writeSuccessful;
	}

   public void cleanUpUserIdGtokenMap(GlobalTokenDetailsEntity gToken, boolean isGtokenSizeFull) {
      // We need to remove all token of user from aerospike
      // it includes clearing both gTokenIdGTokenDetailsSet (except above
      // gtokenId) and userIdGTokenIdsSet
      IMSAerospikeRecord<HashSet<String>> existingRecord = userIdGTokenIdsSet.get(gToken
               .getUserId());
      if (existingRecord != null && existingRecord.getValue() != null) {
         log.info("Deleteing from userIdGTokenIdSet : " + gToken.getGlobalTokenId()
                  + " for userId :" + gToken.getUserId());
         if (Boolean.parseBoolean(Configuration
                  .getGlobalProperty(ConfigurationConstants.GTOKEN_HARD_SIGNOUT_ON_LIMIT_REACHED))
                  || isGtokenSizeFull) {

            // hard deleting gtoken
            userIdGTokenIdsSet.delete(gToken.getUserId());
            // iterate over userGTokens set and delete all references
            for (String gTokenId : existingRecord.getValue()) {
               // delete only merchant specific tokens except given gToken
               GlobalTokenDetailsEntity globalToken = getGlobalTokenEntityFromGtokenId(gTokenId);
               if (globalToken != null
                        && globalToken.getGlobalTokenId() != gToken.getGlobalTokenId()) {
                  log.info("Deleteing from gTokenIdGTokenIdSet" + gTokenId
                           + existingRecord.getValue().size());
                  gTokenIdGTokenDetailsSet.delete(gTokenId);
                  try {
                     Thread.sleep(Long.parseLong(Configuration
                              .getGlobalProperty(ConfigurationConstants.SLEEP_BETWEEN_TOKEN_CLEANUP)));
                  } catch (Exception e) {
                     log.error("error occoured while sleeping", e);
                  }
               }
            }
         } else {
            for (String gTokenId : existingRecord.getValue()) {
               // delete only merchant specific tokens except given gToken
               GlobalTokenDetailsEntity globalToken = getGlobalTokenEntityFromGtokenId(gTokenId);
               if (globalToken == null) {
                  deleteExpiredGtokenById(gToken.getGlobalTokenId(), gToken.getUserId());
                  try {
                     Thread.sleep(Long.parseLong(Configuration
                              .getGlobalProperty(ConfigurationConstants.SLEEP_BETWEEN_TOKEN_CLEANUP)));
                  } catch (Exception e) {
                     log.error("error occoured while sleeping", e);
                  }
               }
            }
         }
      }
   }
	
	private boolean updateUserIdByGtokenIdMap(GlobalTokenDetailsEntity gToken,
			boolean writeSuccessful) {
		if (writeSuccessful) {
			// we need to populate generation the write policy with the genid of
			// this get in the subsequent put
			IMSAerospikeRecord<HashSet<String>> existingRecord = userIdGTokenIdsSet
					.get(gToken.getUserId());
			HashSet<String> gTokensIdSet = existingRecord.getValue();

			if (gTokensIdSet == null) {
				gTokensIdSet = new HashSet<String>();
				gTokensIdSet.add(gToken.getGlobalTokenId());
				writeSuccessful = userIdGTokenIdsSet.insert(gToken.getUserId(),
						gTokensIdSet);
			} else {
				gTokensIdSet.add(gToken.getGlobalTokenId());
				writeSuccessful = userIdGTokenIdsSet.update(gToken.getUserId(),
						existingRecord);
			}
		}
		return writeSuccessful;
	}

	private boolean insertGtokenIdByObject(GlobalTokenDetailsEntity gToken) {
		boolean writeSuccessful = true;

		if(gToken.getUserId()==null){
			return false;
		}

		writeSuccessful = gTokenIdGTokenDetailsSet.insert(gToken.getGlobalTokenId(), gToken);
		return writeSuccessful;
	}

	@Override
	@Timed
	@Marked
	public boolean deleteGtokenById(String gTokenId) {

		boolean deleteSuccessful=true;
		int retryCount = Integer.parseInt(Configuration.getGlobalProperty(ConfigurationConstants.USERID_BY_GTOKENIDS_MAX_UPDATE_RETRIES));
		long retrySleep = Long.parseLong(Configuration.getGlobalProperty(ConfigurationConstants.USERID_BY_GTOKENIDS_RETRY_THREAD_SLEEP_IN_MILLISECONDS));

		while(retryCount>0){
			try {
				deleteSuccessful = updateUserIdByGtokenIdMapDelete(gTokenId);
				retryCount=0;
			} catch (AerospikeException aex) {
				retryCount--;
				log.warn("updateUserIdByGtokenIdMapDelete update retry for gToken ID => "+gTokenId+" : retry left - "+retryCount);

				try {
					Thread.sleep(retrySleep);
				} catch (InterruptedException e) {
					log.debug("updateUserIdByGtokenIdMapDelete retry thread InterruptedException");
				}
			}
		}

		// finally delete gTokenid to gtokenObj for corresponding
		// gTokenId, we are not interested in state of this deletion as we
		// have ttl for the same
		gTokenIdGTokenDetailsSet.delete(gTokenId);
		return deleteSuccessful;
	}

	private boolean updateUserIdByGtokenIdMapDelete(String gTokenId){

		boolean deleteSuccessful = true;

		GlobalTokenDetailsEntity gToken = getGtokenById(gTokenId);

		if (gToken != null
				&& (gToken.getMerchant() == null || merchantServiceProvider
				.getMerchant() == gToken.getMerchant())) {
			String userId = gToken.getUserId();
			// Here again we need to create writepolicy with generation found in
			// this get
			IMSAerospikeRecord<HashSet<String>> existingRecord = userIdGTokenIdsSet.get(userId);

			if (existingRecord == null || existingRecord.getValue() == null) {
				// in case not deleted by previous call
				gTokenIdGTokenDetailsSet.delete(gTokenId);
				return deleteSuccessful;
			}

			HashSet<String> userGTokens = existingRecord.getValue();
			if (userGTokens.contains(gTokenId)) {
				userGTokens.remove(gTokenId);
				deleteSuccessful = userIdGTokenIdsSet.update(userId, existingRecord);
				gTokenIdGTokenDetailsSet.delete(gTokenId);
			}
		}
		return deleteSuccessful;      
	}
	
	private boolean deleteExpiredGtokenById(String gTokenId, String userId) {
		boolean deleteSuccessful = true;
		IMSAerospikeRecord<HashSet<String>> existingRecord = userIdGTokenIdsSet
				.get(userId);

		if (existingRecord == null || existingRecord.getValue() == null) {
			return deleteSuccessful;
		}
		HashSet<String> userGTokens = existingRecord.getValue();
		if (userGTokens.contains(gTokenId)) {
			userGTokens.remove(gTokenId);
			deleteSuccessful = userIdGTokenIdsSet
					.update(userId, existingRecord);
		}
		return deleteSuccessful;
	}


	@Override
	@Timed
	@Marked
	public boolean deleteAllTokenByUserId(String userId) {
		boolean deleteSuccessful = true;
		IMSAerospikeRecord<HashSet<String>> existingRecord = userIdGTokenIdsSet.get(userId);

		if (existingRecord != null && existingRecord.getValue() != null) {
			deleteSuccessful = userIdGTokenIdsSet.delete(userId);
			log.info("deleting user with : "+existingRecord.getValue());
			// iterate over userGTokens set and delete all references, User
			// needs not care about state of below operations as we have ttl
			for (String gTokenId : existingRecord.getValue()) {
				//delete  only merchant specific tokens
				GlobalTokenDetailsEntity globalToken = getGlobalTokenEntityFromGtokenId(gTokenId);
				if(globalToken != null
						&& (globalToken.getMerchant() == null || merchantServiceProvider
						.getMerchant() == globalToken.getMerchant())){
					log.info("deleting user with : "+gTokenId);
					gTokenIdGTokenDetailsSet.delete(gTokenId);

				}
			}
		}

		return deleteSuccessful;
	}

	private GlobalTokenDetailsEntity getGlobalTokenEntityFromGtokenId(
			String gTokenId) {
		IMSAerospikeRecord<GlobalTokenDetailsEntity> gTokenRecord = null;
		GlobalTokenDetailsEntity globalToken = null;

		gTokenRecord = gTokenIdGTokenDetailsSet.get(gTokenId);
		globalToken = gTokenRecord.getValue();
		return globalToken;
	}

	@Override
	@Timed
	@Marked
	public boolean updateGlobalTokenExpiryTime(GlobalTokenDetailsEntity globalTokenDetailsEntity) {
		boolean updateSuccessful = true;
		GlobalTokenDetailsEntity gToken = null;

		if(globalTokenDetailsEntity==null){
			return false;
		}

		String gTokenId = globalTokenDetailsEntity.getGlobalTokenId();
		IMSAerospikeRecord<GlobalTokenDetailsEntity> gTokenExistingRecord = getGTokenRecordById(gTokenId);

		if(gTokenExistingRecord!=null){
			gToken = gTokenExistingRecord.getValue();
		}

		if (gToken == null) {
			return false;
		}
		gToken.setExpiryTime(globalTokenDetailsEntity.getExpiryTime());
		updateSuccessful = gTokenIdGTokenDetailsSet.update(gTokenId, gTokenExistingRecord);
		/*      int newTTLSeconds = (int) globalTokenDetailsEntity.getExpiryTime().getTime() / 1000;
      gTokenIdGTokenDetailsSet.refreshTTL(gTokenId, newTTLSeconds);
		 */
		return updateSuccessful;
	}

	private boolean isValidGtokenIdByUserId(String userId, String globalTokenId, HashSet<String> gtokenIds) {
		boolean isValidGToken = false;
		if (gtokenIds != null && gtokenIds.contains(globalTokenId)) {
			isValidGToken = true;
		} else {
			// deleting the gtokenId by bean mapping present since invalid
			gTokenIdGTokenDetailsSet.delete(globalTokenId);
		}
		// start async cleanup in case size of gtokens is > 50 (make this value
		// configurable in db
		log.debug("gToken size"+ gtokenIds.size());
		if (gtokenIds != null && gtokenIds.size() >= Long
				.parseLong(Configuration.getGlobalProperty(ConfigurationConstants.GLOBALTOKEN_CLEANUP_SIZE_VALUE)) && Long.parseLong(Configuration.getGlobalProperty(ConfigurationConstants.GLOBALTOKEN_CLEANUP_SIZE_VALUE))!=0) {
			log.info("Starting Async clean up");
			IMSAerospikeRecord<GlobalTokenDetailsEntity> gTokenRecord = gTokenIdGTokenDetailsSet.get(globalTokenId);
			GlobalTokenDetailsEntity globalToken = gTokenRecord.getValue();
			gtokenCleanup.asyncTokenCleanUp(globalToken);
		}
		return isValidGToken;

	}



	@Override
	@Timed
	@Marked
	public boolean deleteAllOtherToken(String userId, String globalTokenId) {
		boolean deleteSuccessful = true;

		IMSAerospikeRecord<HashSet<String>> userGTokensRecord = userIdGTokenIdsSet.get(userId);
		if(userGTokensRecord==null){
			return deleteSuccessful;
		}

		HashSet<String> gTokenIds = userGTokensRecord.getValue();
		gTokenIds.remove(globalTokenId);

		for(String gTokenId : gTokenIds){
			deleteGtokenById(gTokenId);
		}

		/*
      gTokenIds = new HashSet<String>();
      gTokenIds.add(globalTokenId);

      userGTokensRecord.setValue(gTokenIds);

      deleteSuccessful = userIdGTokenIdsSet.update(userId, userGTokensRecord);

		 */      
		return deleteSuccessful;      
	}

	@Override
	public int getGTokenIDSetSizeByUserId(String userId){
		IMSAerospikeRecord<HashSet<String>> existingRecord = userIdGTokenIdsSet.get(userId);
		if(existingRecord.getValue()==null){
			return 0;
		}else{
			HashSet<String> gTokensIdSet = existingRecord.getValue();

			Iterator<String> iter = gTokensIdSet.iterator();
			while (iter.hasNext()) {
				String gToken = iter.next();
				if (getGtokenById(gToken)==null){
					iter.remove();
				}
			}

			userIdGTokenIdsSet.update(userId, existingRecord);
			return gTokensIdSet.size();
		}
	}



}
