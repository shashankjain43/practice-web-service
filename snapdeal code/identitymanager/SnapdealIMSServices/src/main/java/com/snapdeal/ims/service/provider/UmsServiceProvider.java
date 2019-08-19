package com.snapdeal.ims.service.provider;

import com.snapdeal.core.utils.SDEncryptionUtils;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.IsVerifiedMobileExistResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.token.service.ITransferTokenService;
import com.snapdeal.ims.utils.CipherServiceUtil;
import com.snapdeal.ims.utils.IMSEncryptionUtil;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UmsServiceProvider {

   @Autowired
   @Qualifier("SnapdealUmsService")
   private IUMSService snapdealUmsService;

   @Autowired
   @Qualifier("IMSService")
   private IUMSService imsService;

   @Autowired
   @Qualifier("FCUmsService")
   private IUMSService fcUmsService;

   @Autowired
   @Qualifier("IMSServiceImplOCPasswordLogin")
   private IUMSService imsServiceUpgradeLink;

   @Autowired
   private AuthorizationContext context;

   @Autowired
   private UmsMerchantProvider merchantProvider;

   @Autowired
   private IGlobalTokenService globalTokenService;

   @Autowired
   private ITransferTokenService transferTokenService;
   
   @Autowired
   private ITokenService tokenService;

   @Autowired
   private IUserMigrationService userMigrationService;

   @Autowired
   private IUserVerificationDetailsDao userVerificationDetails;

   @Autowired
   private IPasswordUpgradeCacheService passwordUpgradeCacheService;

   @Autowired
   private UmsMerchantProvider umsMerchantProvider;

   public IUMSService getMigrationEnabledUMSService() {
      if (isUpgradeEnabled()) {
         return imsService;
      }
      return getUMSService();
   }

   public IUMSService getUMSService() {

      boolean flag = new Boolean(
               Configuration
                        .getGlobalProperty(ConfigurationConstants.ONE_CHECK_ACCOUNT_CREATION_ENABLE));
      if (flag) {
         return imsService;
      } else {
         final Merchant merchant = merchantProvider.getMerchant();
         log.debug("Merchant is : " + merchant.name());
         switch (merchant) {
            case SNAPDEAL:
               return snapdealUmsService;
            case FREECHARGE:
               return fcUmsService;
            case ONECHECK:
               return imsService;
            default:
               throw new InternalServerException(
                        IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
                        IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
         }
      }
   }

   public IUMSService getUMSServiceByToken(String token) {
      
      String userId = null;
      if (StringUtils.isNotBlank(token)) {
         userId = tokenService.getUserIdByToken(token);
      }
      return getUMSServiceById(userId);
      
   }

   public IUMSService getUMSServiceByVerificationCode(String code) {
      if(isUpgradeEnabled()){
         String userId = null;
         if (StringUtils.isNotBlank(code)) {
            userId = userVerificationDetails.getUserIdbyVerificationCode(code);
         }
         return getUMSServiceById(userId);
      }
      return getUMSService();
   }

   public IUMSService getUMSServiceByGlobalToken(String globalToken) {
      if(isUpgradeEnabled()){
         String userId = null;
         if (StringUtils.isNotBlank(globalToken)) {
            userId = globalTokenService.getUserIdByGlobalToken(globalToken,
                     context.get(IMSRequestHeaders.CLIENT_ID.toString()));
         }
         return getUMSServiceById(userId);
      }
      return getUMSService();
   }

   public IUMSService getUMSServiceByTransferToken(String transferToken) {
      String gToken = transferTokenService.getGlobalTokenByTransferToken(transferToken);
      String userIdByGlobalToken = globalTokenService.getUserIdByGlobalToken(gToken,
               context.get(IMSRequestHeaders.CLIENT_ID.toString()));
      if (isUserExist(userIdByGlobalToken)) {
         return imsService;
      }
      return getUMSService();
   }

   public IUMSService getUMSServiceById(String userId) {
      
      if (isUserExist(userId)) {
         UserDetailsDTO userDetail = getUser(userId);
         if (userDetail != null) {
        	 if(userDetail.isMobileOnly()){
        		 return imsService;
        	 }
        	 else if (!isIntermediateState(userDetail.getEmailId())) {
               return imsService;
            }
         }
      }
      
      return getUMSService();
   }

   public IUMSService getUMSServiceByMobile(String mobileNumber) {
      /*if (StringUtils.isNotBlank(mobileNumber)) {
         if (isVerifiedMobileExist(mobileNumber)) {
            UserDetailsDTO userDetail = getUserByMobile(mobileNumber);
            if (userDetail != null) {
               if (!isIntermediateState(userDetail.getEmailId())) {
                  return imsService;
               }
            }
         }
    	  return imsService;
      }*/
      return imsService;
   }

   public IUMSService getUMSServiceByEmail(String emailId) {
      if (isEmailExist(emailId) && !isIntermediateState(emailId))
         return imsService;
      
      return getUMSService();
   }
   
   public IUMSService getUMSServiceByEmailWithoutMigration(String emailId) {
      if (isUpgradeEnabled() && StringUtils.isNotBlank(emailId)) {
         if (isEmailExist(emailId))
            return imsService;
      }
      return getUMSService();
   }

   private boolean isVerifiedMobileExist(String mobileNumber) {
      IsVerifiedMobileExistRequest request = new IsVerifiedMobileExistRequest();
      request.setMobileNumber(mobileNumber);
      IsVerifiedMobileExistResponse response = imsService.isMobileExist(request);
      if (response != null && response.isExist()) {
         return true;
      }
      return false;
   }
   
	private String getEmailIdForVerifiedMobile(String mobileNumber) {
		GetUserByMobileRequest request = new GetUserByMobileRequest();
		String emailId = null;
		request.setMobileNumber(mobileNumber);
		GetUserResponse response = imsService.getUserByMobile(request);
		if (response != null && response.getUserDetails() != null
				&& response.getUserDetails().isMobileVerified()) {
			emailId = response.getUserDetails().getEmailId();
		} else {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
		return emailId;
	}

   // Check if acount exists with email id and email is verified.
   private boolean isEmailExist(String emailId) {
      IsEmailExistRequest request = new IsEmailExistRequest();
      request.setEmailId(emailId);
      IsEmailExistResponse response = imsService.isEmailExist(request);
      if (response != null && response.isExist()) {
         return true;
      }
      return false;
   }

   // Check if account exists with user id.
   private boolean isUserExist(String userId) {
      IsUserExistRequest request = new IsUserExistRequest();
      request.setUserId(userId);
      IsUserExistResponse response = imsService.isUserExist(request);
      if (response != null && response.isExist()) {
         return true;
      }
      return false;
   }

   public IUMSService getIMSService() {
      return imsService;
   }

   public IUMSService getUMSServiceForConfigureUserState(ConfigureUserStateRequest request) {
      if (StringUtils.isNotBlank(request.getUserId()) && isUserExist(request.getUserId())) {
         return imsService;
      }
      if (StringUtils.isNotBlank(request.getMobileNumber())
               && isVerifiedMobileExist(request.getMobileNumber())) {
         return imsService;
      }
      if (StringUtils.isNotBlank(request.getEmailId()) && isEmailExist(request.getEmailId())) {
         return imsService;
      }
      if (StringUtils.isNotBlank(request.getToken())) {
         return getUMSServiceByToken(request.getToken());
      }
      return getUMSService();
   }

	public IUMSService getUMSServiceForLoginUser(LoginUserRequest request) {
		String emailId = request.getEmailId();
		boolean emailExist=false;
		if (StringUtils.isNotBlank(request.getMobileNumber())) {
			emailId = getEmailIdForVerifiedMobile(request.getMobileNumber());
			request.setEmailId(emailId);
			emailExist = true;
		}
		// Check if user exists in OC.
		// Redirect using migration status.
		else if (StringUtils.isNotBlank(request.getEmailId())) {
			// if user exists in ims, check for upgrade status.
			emailExist = isOCAccountExist(emailId);
		}
		if (emailExist) {
			UserUpgradationResponse userUpgradeStatus = getMigrationStatus(emailId);

			Upgrade currentUpgradeStatus = userUpgradeStatus
					.getUpgradationInformation().getUpgrade();
			// if upgrade is complete, valid case and returning imsServiceImpl
			if (currentUpgradeStatus == Upgrade.UPGRADE_COMPLETED
					|| merchantProvider.getMerchant() == Merchant.ONECHECK
					|| Upgrade.NO_UPGRADE_REQRUIRED == currentUpgradeStatus) {
				return imsService;
			} else if (currentUpgradeStatus == Upgrade.LINK_FC_ACCOUNT
					|| currentUpgradeStatus == Upgrade.LINK_SD_ACCOUNT) {
				if (matchOCPasssword(request.getEmailId(),
						request.getPassword())) {
					return imsServiceUpgradeLink;
            } else if (StringUtils.isNotBlank(request.getMobileNumber())) {
               // Case: E1P1M1 is used to upgrade from other merchant.
               // Now M1 is used to login before linking.
               IMSServiceExceptionCodes code = IMSServiceExceptionCodes.MOBILE_USER_NOT_VERIFIED;
               throw new IMSServiceException(code.errCode(), MessageFormat.format(code.errMsg(),
                        umsMerchantProvider.getMerchant()));
				}			   
			}
		}
		return getUMSService();
	}

   private UserUpgradationResponse getMigrationStatus(String emailId) {
      UserUpgradeByEmailRequest userUpgradeByEmailRequest = new UserUpgradeByEmailRequest();
      userUpgradeByEmailRequest.setEmailId(emailId);
      UserUpgradationResponse userUpgradeStatus = userMigrationService
               .getUserUpgradeStatus(userUpgradeByEmailRequest, false);
      return userUpgradeStatus;
   }

   /**
    * TODO: password check
    * Check if user entered password is OC password.
    * 
    * @param email
    * @param password
    * @return
    */
   private boolean matchOCPasssword(String email, String password) {
      return IMSEncryptionUtil.getSDEncryptedPassword(password).equals(
               passwordUpgradeCacheService.getImsSdHashedPassword(email));
   }

   private boolean isOCAccountExist(String emailId) {
      final GetUserByEmailRequest request = new GetUserByEmailRequest();
      request.setEmailId(emailId);
      GetUserResponse response = null;
      try {
         response = imsService.getUserByEmail(request);
      } catch (IMSServiceException e) {
         log.info("User doesnot exist" + e);
      }
      return (response != null && response.getUserDetails() != null && response.getUserDetails()
               .isMobileVerified());
   }

   private UserDetailsDTO getUser(String userId) {
      GetUserByIdRequest request = new GetUserByIdRequest();
      request.setUserId(userId);
      GetUserResponse userDetail = imsService.getUser(request);
      return userDetail.getUserDetails();
   }

   /*private UserDetailsDTO getUserByMobile(String mobileNumber) {
      GetUserByMobileRequest request = new GetUserByMobileRequest();
      request.setMobileNumber(mobileNumber);
      GetUserResponse userDetail = imsService.getUserByMobile(request);
      return userDetail.getUserDetails();
   }*/

   public boolean isIntermediateState(String emailId) {
      // If merchant is ONECHECK, then not checking the intermediate
      // state.
      // This call is made to create wallet, so for this call, intermediate
      // state is false.
      String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
      Client clientById = ClientConfiguration.getClientById(clientId);
      Merchant merchant = ClientConfiguration.getMerchantById(clientId);

      if (Merchant.ONECHECK.equals(merchant)) {
         log.info("Skipping user upgrade status call to check intermediate call for client : "
                  + clientById + ", and email id: " + emailId);
         return false;
      }
      final UpgradationInformationDTO upgradationInformationDTO = getMigrationStatus(emailId)
               .getUpgradationInformation();
      // State state = upgradationInformationDTO.getState();
      Upgrade upgrade = upgradationInformationDTO.getUpgrade();
      switch (upgrade) {
         case LINK_FC_ACCOUNT:
         case LINK_SD_ACCOUNT:
            return true;
         default:
            return false;
      }
   }

   public boolean isUpgradeEnabled(){
      
      return Boolean.parseBoolean(Configuration.getProperty(
               context.get(IMSRequestHeaders.CLIENT_ID.toString()),
               ConfigurationConstants.UPGRADE_ENABLED));
   }
   
   public IUMSService getUMSServiceForVerifyUser(VerifyUserRequest request) {
      String verificationCode;
      try {
         verificationCode = CipherServiceUtil.decrypt(request.getCode());
      } catch (CipherException e) {
          log.error("Error ocured while decrypting the verification code.");
          throw new IMSServiceException(
                   IMSServiceExceptionCodes.UNABLE_TO_DECRYPT_CODE.errCode(),
                   IMSServiceExceptionCodes.UNABLE_TO_DECRYPT_CODE.errMsg());
       }
      String userId = userVerificationDetails.getUserIdbyVerificationCode(verificationCode);
      if (userId != null) {
         if (isUserExist(userId)) {
               return imsService;
         }
      }
      throw new IMSServiceException(
              IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED.errCode(),
              IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED.errMsg());
   }

}
