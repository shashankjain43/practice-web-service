package com.snapdeal.ims.cache.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.cache.set.IIMSAerospikeSet;
import com.snapdeal.ims.cache.set.IMSAerospikeRecord;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.provider.UmsServiceProvider;

/**
 * This class is written for providing correct token to user in case of SSO
 * 
 * @author kishan
 *
 */
@Component
@Slf4j
public class UserIdCacheServiceImpl implements IUserIdCacheService {

   @Autowired
   AuthorizationContext context;

   @Autowired
   @Qualifier("IMSService")
   IUMSService imsUserService;

   @Autowired
   @Qualifier("FCUmsService")
   IUMSService fcUserService;

   @Autowired
   @Qualifier("SnapdealUmsService")
   IUMSService sdUserService;

   @Autowired
   @Qualifier("fcUserIdEmailIdSet")
   private IIMSAerospikeSet<Integer, String> fCUserIdEmailIdSet;

   @Autowired
   @Qualifier("sdUserIdEmailIdSet")
   private IIMSAerospikeSet<Integer, String> sDUserIdEmailIdSet;

   @Autowired
   @Qualifier("imsUserIdEmailIdSet")
   private IIMSAerospikeSet<String, String> imsIdUserIdSet;
   
	@Autowired
	private UmsServiceProvider serviceProvider;

   @Override
   public void putUserIdbyEmailId(String userId, String emailId) {
      if (getMerchant() == Merchant.ONECHECK || !NumberUtils.isNumber(userId)) {
         // this is the case when merchant is onecheck or user id is String
         if (emailId != null && imsIdUserIdSet.get(userId).getValue()==null) {
            imsIdUserIdSet.insert(userId, emailId);
         }
      } else if (NumberUtils.isNumber(userId) && getMerchant() == Merchant.FREECHARGE) {
         // this is the case when merchant is fc and userid is number
         if (emailId != null && fCUserIdEmailIdSet.get(Integer.parseInt(userId)).getValue()==null) {
            fCUserIdEmailIdSet.insert(Integer.parseInt(userId), emailId);
         }
      } else if (NumberUtils.isNumber(userId)) {
         // this is the case when merchant is sd and userid is number
         if (emailId != null && sDUserIdEmailIdSet.get(Integer.parseInt(userId)).getValue()==null) {
            sDUserIdEmailIdSet.insert(Integer.parseInt(userId), emailId);
         }
      }
   }

   public String getEmailIdByUserId(String userId, IUMSService umsService) {
      String emailId = null;
      GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest();
      getUserByIdRequest.setUserId(userId);
      GetUserResponse getUserResponse = umsService.getUser(getUserByIdRequest);
      if (getUserResponse != null && getUserResponse.getUserDetails() != null) {
         emailId = getUserResponse.getUserDetails().getEmailId();
      }
      return emailId;
   }

   public String getUserIdByEmailId(String emailId, IUMSService umsService) {
      String userId = null;
      GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
      getUserByEmailRequest.setEmailId(emailId);
      GetUserResponse getUserResponse = umsService.getUserByEmail(getUserByEmailRequest);
      if (getUserResponse != null && getUserResponse.getUserDetails() != null) {
         userId = getUserResponse.getUserDetails().getUserId();
      }
      return userId;
   }

   private String getEmailIdbySdUserId(int userId) {
      IMSAerospikeRecord<String> emailIdRecord = sDUserIdEmailIdSet.get(userId);
      if (emailIdRecord != null && emailIdRecord.getValue() != null) {
         return emailIdRecord.getValue();
      } else {
         return getEmailIdByUserId(String.valueOf(userId), sdUserService);
      }
   }

   private String getEmailIdbyFcUserId(int userId) {
      IMSAerospikeRecord<String> emailIdRecord = fCUserIdEmailIdSet.get(userId);
      if (emailIdRecord != null && emailIdRecord.getValue() != null) {
         return emailIdRecord.getValue();
      } else {
         return getEmailIdByUserId(String.valueOf(userId), fcUserService);
      }
   }

   private String getEmailIdbyImsUserId(String userId) {
      IMSAerospikeRecord<String> emailIdRecord = imsIdUserIdSet.get(userId);
      if (emailIdRecord != null && emailIdRecord.getValue() != null) {
         return emailIdRecord.getValue();
      } else {
         return getEmailIdByUserId(String.valueOf(userId), imsUserService);
      }
   }

   @Override
   public String getActualUserIdForTokenUserId(String userId, Merchant merchant) {
      String emailId = null;
      if (!NumberUtils.isNumber(userId)) {
         // This is the case when user id is string.It means it has been created
         // from oncecheck
         merchant = Merchant.ONECHECK;
         log.info("UserId is String hence fetching user from IMS Db");
         return userId;
      }
      if (merchant == Merchant.ONECHECK) {
         // this is the case when merchant for token is OC and userid is String
         emailId = getEmailIdbyImsUserId(userId);
      } else if (merchant == Merchant.FREECHARGE) {
         // this is the case when merchant for token is FC and userid is
         // Numberic
         emailId = getEmailIdbyFcUserId(Integer.parseInt(userId));
      } else {
         // this is the case when merchant for token is SD and userid is
         // Numberic
         emailId = getEmailIdbySdUserId(Integer.parseInt(userId));
      }
      String actualUserId = null;
      if (emailId == null) {
         // This is the case when somehow emailId is coming null due to some
         // problem in Merchant Service.
         log.error("unable to fetch email id for merchant " + merchant.toString()
                  + " with user id :" + userId);
         return userId;
      }
      // finally fetching user from respective merchant using emailId
      IsEmailExistRequest request=new IsEmailExistRequest();
      request.setEmailId(emailId);
      IsEmailExistResponse response= imsUserService.isEmailExist(request);
      
      if (getMerchant() == Merchant.ONECHECK || (response.isExist() && !serviceProvider.isIntermediateState(emailId)) ) {
         actualUserId = getUserIdByEmailId(emailId, imsUserService);
      } else if (getMerchant() == Merchant.FREECHARGE) {
         actualUserId = getUserIdByEmailId(emailId, fcUserService);
      } else {
         actualUserId = getUserIdByEmailId(emailId, sdUserService);
      }
      return actualUserId;
   }

   private Merchant getMerchant() {
      String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
      return ClientConfiguration.getMerchantById(clientId);

   }

	@Override
	public String getEmailIdFromUserId(String userId, Merchant merchant) {
		if (!NumberUtils.isNumber(userId)) {
			// This is the case when user id is string.It means it has been
			// created
			// from oncecheck
			merchant = Merchant.ONECHECK;
			log.info("UserId is String hence fetching user from IMS Db");
		}
		return getEmailIdFromUserIdAndMerchant(userId, merchant);
	}

	private String getEmailIdFromUserIdAndMerchant(String userId,
			Merchant merchant) {
		String emailId;
		if (merchant == Merchant.ONECHECK) {
			// this is the case when merchant for token is OC and userid is
			// String
			emailId = getEmailIdbyImsUserId(userId);
		} else if (merchant == Merchant.FREECHARGE) {
			// this is the case when merchant for token is FC and userid is
			// Numberic
			emailId = getEmailIdbyFcUserId(Integer.parseInt(userId));
		} else {
			// this is the case when merchant for token is SD and userid is
			// Numberic
			emailId = getEmailIdbySdUserId(Integer.parseInt(userId));
		}
		return emailId;
	}
}
