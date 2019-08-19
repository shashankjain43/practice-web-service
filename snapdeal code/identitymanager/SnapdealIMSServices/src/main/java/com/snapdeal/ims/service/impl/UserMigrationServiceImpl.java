package com.snapdeal.ims.service.impl;

import com.snapdeal.core.utils.SDEncryptionUtils;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.cache.service.ISDFCPasswordCacheService;
import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.cache.service.IUserVerificationCacheService;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.Action;
import com.snapdeal.ims.enums.EmailVerificationSource;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.LinkUserVerifiedThrough;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Skip;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSMigrationExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.fortknox.request.FortKnoxRequest;
import com.snapdeal.ims.migration.Migration;
import com.snapdeal.ims.migration.dao.MigrationDao;
import com.snapdeal.ims.migration.exception.IMSMigrationHardDeclinedException;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserWithLinkedStateRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;
import com.snapdeal.ims.response.VerifyUserWithLinkedStateResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.service.dto.UserDTO;
import com.snapdeal.ims.task.test.ITaskService;
import com.snapdeal.ims.token.dto.TokenDetailsDTO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utils.CipherServiceUtil;
import com.snapdeal.ims.utils.IMSEncryptionUtil;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;

import lombok.extern.slf4j.Slf4j;

@Service("userMigrationService")
@Slf4j
public class UserMigrationServiceImpl implements IUserMigrationService {
    
   /*@Autowired
   private EmailSenderUtility emailSenderUtility;
   */
    @Autowired
    private IOTPService otpService;

    @Autowired
    private MigrationDao migrationDaoImpl;

   @Qualifier("dummyMigrationService")
   @Autowired
   IUserMigrationService dummyMigrationService;
   
   @Autowired
   Migration migration;

   @Autowired
   private AuthorizationContext context;

   @Autowired
   private IUserService userService;

   @Autowired
   private IActivityDataService activityDataService;

   @Autowired
   private ITokenService tokenService;
   
   @Autowired
   private FortKnoxServiceHelper fortKnoxServiceHelper;
   
   @Autowired
   private ITaskService taskService;

   @Qualifier("IMSService")
   @Autowired
   private IUMSService imsService;

   @Autowired
   private IPasswordUpgradeCacheService passwordcacheService;

   @Autowired
   private IUserVerificationCacheService userVerificationService;
   
   @Autowired
   ISDFCPasswordCacheService sdFcPasswordCacheService;
   
   @Autowired
   @Qualifier("SnapdealUmsService")
   private IUMSService snapdealUmsService;
   
   @Autowired
   private ILoginUserService loginUserService;
   
   @Autowired
   IUserDao userDao;
   
   @Autowired
   IUserCacheService userCacheService;

   @Override
   @Timed
   @Marked
   @Logged
   public UserUpgradationResponse getUserUpgradeStatus(
            UserUpgradeByEmailRequest userUpgradeByEmailRequest, boolean isExternalCall)
      throws IMSMigrationHardDeclinedException {
      boolean isV2Token = false;
      if (StringUtils.isNotBlank(userUpgradeByEmailRequest.getToken())) {
         isV2Token = isV2Token(userUpgradeByEmailRequest.getToken());
      }
      String email = userUpgradeByEmailRequest.getEmailId();
      UserDetailsDTO userDetails = null;
      if (isV2Token) {
         userDetails = getEmailFromToken(userUpgradeByEmailRequest.getToken(), true);
      } else if (StringUtils.isBlank(email)) {
         userDetails = getEmailFromToken(userUpgradeByEmailRequest.getToken(), false);
      }
      if (null != userDetails) {
         email = userDetails.getEmailId();
      }
      UserUpgradationResponse res = new UserUpgradationResponse();
      if (Boolean.valueOf(Configuration
               .getGlobalProperty(ConfigurationConstants.DUMMY_MIGRATION_ENABLED))) {
         userUpgradeByEmailRequest.setEmailId(email);
         res = dummyMigrationService.getUserUpgradeStatus(userUpgradeByEmailRequest, false);
      } else {
         UpgradationInformationDTO upgradationInformation = migration.getMigrationStatus(email, isExternalCall);
         if (isV2Token) {
            upgradationInformation.setSkip(Skip.SKIP_FALSE);
         }
         res.setUpgradationInformation(upgradationInformation);
      }
      
      return res;
   }

   private boolean isV2Token(String token) {
      boolean isV2Token = false;
      /*TokenRequest request = new TokenRequest();
      request.setToken(token);
      boolean tokenValid = false;
      try {
         tokenValid = tokenService.isTokenValid(request);
      } catch (AuthorizationException ex) {
         tokenValid = false;
      }
      if (!tokenValid) {
         request.setLinkUpgradeFlow(true);
         try {
            tokenValid = tokenService.isTokenValid(request);
         } catch (AuthorizationException ex) {
            tokenValid = false;
         }
         if (tokenValid) {
            isV2Token = true;
         }
      }*/
      String tokenVersion = tokenService.getTokenVersion(token);
      isV2Token = StringUtils
               .equals(tokenVersion,
                        Configuration
                                 .getGlobalProperty(ConfigurationConstants.LINK_UPGRADE_TOKEN_GENERATION_SERVICE_VERSION));
      return isV2Token;
   }

   private UserDetailsDTO getEmailFromToken(String token, boolean isLinkUserFlow) {
      // if email is null, get email from token.
      String userIdByToken = tokenService.getUserIdByToken(token, true);
      GetUserByIdRequest request = new GetUserByIdRequest();
      request.setUserId(userIdByToken);
      GetUserResponse userByToken = null;
      if (isLinkUserFlow) {
         userByToken = userService
               .getUserFromIMSInCaseOfLinkUserWithOcPassword(request);
      } else {
         userByToken = userService.getUser(request);
      }
      return userByToken.getUserDetails();
   }

   
   @Override
   @Timed
   @Marked
   @Logged
   public UpgradeUserResponse upgradeUser(UserUpgradeRequest userUpgradeRequest) 
      throws IMSMigrationHardDeclinedException {

      activityDataService.setActivityDataByEmailId(userUpgradeRequest.getEmailId());
      if (null != userUpgradeRequest.getToken()) {
         activityDataService.setActivityDataByToken(userUpgradeRequest.getToken());
      }
      if (Boolean.valueOf(Configuration
               .getGlobalProperty(ConfigurationConstants.DUMMY_MIGRATION_ENABLED))) {
         String email = userUpgradeRequest.getEmailId();
         if (StringUtils.isBlank(email)) {
            UserDetailsDTO emailFromToken = getEmailFromToken(userUpgradeRequest.getToken(), false);
            email = emailFromToken.getEmailId();
            userUpgradeRequest.setEmailId(email);
         }
         return dummyMigrationService.upgradeUser(userUpgradeRequest);
      } else {
         UpgradeUserResponse response = migration.doUpgrade(userUpgradeRequest);
         if (response != null && response.getUserDetails() != null) {
            activityDataService.setActivityDataByUserId(response.getUserDetails().getUserId());
         }
         return response;
      }
   }
   

   /**
    * Utility method used temporarily in pass-through system.
    * TODO: Returning default {@link UpgradationInformationDTO}
    */
   private UpgradationInformationDTO createUpgradationInformationDTO() {
      UpgradationInformationDTO dto = new UpgradationInformationDTO();
      dto.setAction(Action.NO_ACTION_REQUIRED);
      dto.setSkip(Skip.SKIP_TRUE);
      dto.setState(State.SD_ACCOUNT_EXISTS_AND_ENABLED);
      dto.setUpgrade(Upgrade.NO_UPGRADE_REQRUIRED);
      return dto;
   }

   
   @Override
   @Timed
   @Marked
   @Logged
   @Transactional
   public VerifyUpgradeUserResponse verifyUpgradeUser(
            VerifyUserUpgradeRequest verifyUserUpgradeRequest) {
      // populate activity
      activityDataService.setActivityDataByEmailId(verifyUserUpgradeRequest.getEmailId());
      if (Boolean.valueOf(Configuration
               .getGlobalProperty(ConfigurationConstants.DUMMY_MIGRATION_ENABLED))) {

         log.info("Dummy migration for request: " + verifyUserUpgradeRequest);
         return dummyMigrationService.verifyUpgradeUser(verifyUserUpgradeRequest);
      }

      boolean verified = false;
      
      UserDetailsDTO userFromToken = getEmailFromToken(verifyUserUpgradeRequest.getToken(), true);
      String email = userFromToken.getEmailId();
      // if email is passed, then matching with the email fetched by token.
      if (StringUtils.isNotBlank(verifyUserUpgradeRequest.getEmailId())
               && !StringUtils.equals(email, verifyUserUpgradeRequest.getEmailId())) {
         throw new IMSServiceException(
                  IMSMigrationExceptionCodes.EMAIL_MISS_MATCH_VERIFICATION.errCode(),
                  IMSMigrationExceptionCodes.EMAIL_MISS_MATCH_VERIFICATION.errMsg());
      }
      // Step1: Verify user based on verification type first
      switch (verifyUserUpgradeRequest.getVerifiedType()) {

         case LINK_ACCOUNT_VIA_MOBILE_OTP:

            // Verify OTP
            verified = verifyOTP(verifyUserUpgradeRequest);
            // If verified, and password used in not OC password, then setting
            // current password used to login when verifying.
            String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
            if (verified && isMerchantPasswordUsed(verifyUserUpgradeRequest.getToken())) {
               if (Merchant.SNAPDEAL == ClientConfiguration.getMerchantById(clientId)) {
                  imsService.resetDefaultSocialPasswordHelper(email,
                           passwordcacheService.getSdSdHashedPassword(email));
               } else if (Merchant.FREECHARGE == ClientConfiguration.getMerchantById(clientId)) {
                  imsService.resetDefaultSocialPasswordHelper(email,
                           passwordcacheService.getFcFcHashedPassword(email));
               }
               tokenService.signoutAllOtherTokens(verifyUserUpgradeRequest.getToken());
            }
         break;
         case LINK_ACCOUNT_VIA_PASSWORD:
            
            // Verify password
            verified = migration.verifyPassword(verifyUserUpgradeRequest,
                                                      verifyUserUpgradeRequest.getTargetSrcToBeValidated());
            break;
         case LINK_ACCOUNT_VIA_SOCIAL:
            verified = verifySocialUser(verifyUserUpgradeRequest);
            break;
         
         case LINK_ACCOUNT_VIA_PARKING:
                 imsService.resetDefaultSocialPasswordHelper(email,
                          verifyUserUpgradeRequest.getPassword());
                 tokenService.signoutAllOtherTokens(verifyUserUpgradeRequest.getToken());
        	verified = true;
        	break;
         case LINK_ACCOUNT_VIA_EMAIL_OTP:
        	 verified = verifyOTP(verifyUserUpgradeRequest);
             if(!verified){
            	 throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errCode(), 
            			 					   IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errMsg());
        	 }
        	 break;
         default:
            break;
      }

      //If user verified successfully than update migration status
      if (verified) {
         updateMigrationStatus(email, userFromToken);
      }
      VerifyUpgradeUserResponse verifyUpgradeUserResponse = new VerifyUpgradeUserResponse();
      verifyUpgradeUserResponse.setSuccess(verified);
      verifyUpgradeUserResponse.setUserDetails(userFromToken);
      // populate activity on success
      activityDataService.setActivityDataByUserId(userFromToken.getUserId());
      return verifyUpgradeUserResponse;
   }

   private boolean isMerchantPasswordUsed(String token) {
      TokenRequest request = new TokenRequest();
      request.setToken(token);
      boolean isMerchantPasswordUsed = false;
      try {
         // if token is valid, then OC password is not used.
         isMerchantPasswordUsed = tokenService.isTokenValid(request);
      } catch (AuthorizationException ex) {
         log.warn("Token is invalid checking if OC password is used: " + token);
      }
      return isMerchantPasswordUsed;
   }

   private boolean verifySocialUser(VerifyUserUpgradeRequest verifyUserUpgradeRequest) {
      
      imsService.resetDefaultSocialPasswordHelper(verifyUserUpgradeRequest.getEmailId(),
               ServiceCommonConstants.DEFAULT_SOCIAL_USER_PASSWORD);
      tokenService.signoutAllOtherTokens(verifyUserUpgradeRequest.getToken());
      return true;
   }
   
   
   @Override
   @Timed
   @Marked
   @Logged
   public boolean upgradeUserStatusViaResetPassword(String emailId, UserDetailsDTO dto){
      updateMigrationStatus(emailId, dto);
      return true;
   }
   
   private void updateMigrationStatus(String emailId,
         UserDetailsDTO userDto) {

      // Get latest migration status from DB
      UpgradeStatus upgradeStatus = migrationDaoImpl
            .getLatestUpgradeStatus(emailId);
      /*
       * Upgrade table - need to update current status, upgrade, updated date,
       * Based on source - SNAPDEAL/FREECHARGE -> state - OC_ACCOUNT_EXIST ->
       * upgrade - UPGRADE_COMPLETED -> updated Time: to current time
       * Upgrade_Details - need to add a row with upgrade_id, status, upgrade,
       * date
       */
      if (upgradeStatus != null) {
         upgradeStatus.setCurrentState(State.OC_ACCOUNT_EXISTS);
         upgradeStatus.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         upgradeStatus.setUpdatedDate(new Timestamp(Calendar.getInstance()
               .getTimeInMillis()));
         migrationDaoImpl.updateUpgradationStatusWithEmailVerifiedCount(upgradeStatus);
         imsService.createNotificationOnMigrationStateChange(null, emailId,
                  WalletUserMigrationStatus.SD_FC_MIGRATED);
         fortKnoxServiceHelper.createFortKnoxTask(userDto);
/*       FortKnoxRequest fortKnoxRequeset = createFortKnoxRequest(userDto);
         String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
         String businessId = ClientConfiguration.getMerchantById(clientId).getMerchantName();
         String userId = imsService.getUserByEmail(emailId).getUserId();
         taskService.createCompleteTask(userId, businessId, userId, fortKnoxRequeset, emailId, WalletUserMigrationStatus.SD_FC_MIGRATED);*/
      } else {
         // We shouldn't be here. It is unexpected behavior
         log.error("Unexpected error occured, upgradeStatus is null for emailId: "
               + emailId);
         throw new IMSServiceException(
               IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
                     .errCode(),
               IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
                     .errMsg());
      }
   }

   private FortKnoxRequest createFortKnoxRequest(UserDetailsDTO userDetailsDTO) {

      boolean mergeCardEnable = Boolean.valueOf(Configuration
               .getGlobalProperty(ConfigurationConstants.MERGE_CARD_ENABLED));
      FortKnoxRequest fortKnoxRequest = new FortKnoxRequest();
      if (mergeCardEnable) {
         fortKnoxRequest.setEmailId(userDetailsDTO.getEmailId());
         fortKnoxRequest.setUserId(userDetailsDTO.getUserId());
         fortKnoxRequest.setTaskId(userDetailsDTO.getUserId());
         fortKnoxRequest.setSdUserId(String.valueOf(userDetailsDTO
               .getSdUserId()));
       fortKnoxRequest.setFcUserId(String.valueOf(userDetailsDTO
               .getFcUserId()));
       fortKnoxRequest.setMergeType(null);
      } else {
         log.warn("Card merge api is not enabled.");
      }
      return fortKnoxRequest;
   
   }

   private boolean verifyOTP(VerifyUserUpgradeRequest verifyUserUpgradeRequest) {
      
      final VerifyOTPServiceRequest verifyOTPServiceRequest = new VerifyOTPServiceRequest();
      String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
      verifyOTPServiceRequest.setOtp(verifyUserUpgradeRequest.getOtp());
      verifyOTPServiceRequest.setOtpId(verifyUserUpgradeRequest.getOtpId());
      verifyOTPServiceRequest.setToken(verifyUserUpgradeRequest.getToken());
      verifyOTPServiceRequest.setClientId(clientId);

      VerifyOTPServiceResponse verifyOTPServiceResponse = otpService
               .verifyOTP(verifyOTPServiceRequest);
      return OtpConstants.STATUS_SUCCESS.equalsIgnoreCase(verifyOTPServiceResponse.getStatus());
   }

   //to do .. remove this method from here and interface as It is temp
   
   @Override
   public VerifyOTPServiceResponse dummyVerifyOTP(
         VerifyOTPServiceRequest request) {
      // TODO Auto-generated method stub
      return null;
   }
   
   @Override
   @Timed
   @Marked
   @Logged
   public UpgradeStatus getIMSUserUpgradeStatus(UserUpgradeByEmailRequest userUpgradeByEmailRequest)
      throws IMSMigrationHardDeclinedException {
      return migration.getIMSMigrationStatus(userUpgradeByEmailRequest.getEmailId());
   }

   
   @Override
   @Timed
   @Marked
   @Logged
   public UserUpgradationResponse upgradeSocialUser(String emailId) {
      return migration.upgradeSocialUser(emailId);
   }
   
   @Override
   @Timed
   @Marked
   @Transactional
   public VerifyUserWithLinkedStateResponse verifyUserWithLinkedState(
            VerifyUserWithLinkedStateRequest verifyUserLinkedRequest) {
	   
      VerifyUserWithLinkedStateResponse response = new VerifyUserWithLinkedStateResponse();
      String requestEmail = verifyUserLinkedRequest.getEmailId();
      String requestPassword = verifyUserLinkedRequest.getPassword();
      String requestCode = verifyUserLinkedRequest.getCode();
      
      // populate activity
      activityDataService.setActivityDataByEmailId(requestEmail);
      
      String verificationCode;
      try {
         verificationCode = CipherServiceUtil.decrypt(requestCode);

      } catch (CipherException e) {

         log.error("Error occured while decrypting the verification code.");
         throw new IMSServiceException(
                  IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED.errCode(),
                  IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED.errMsg());
      }

      // null check for verification details
      // fetch user details from verification code
      UserVerification userVerificationDetails = userVerificationService
               .getUserVerificationDetailsByCode(verificationCode);

      if (userVerificationDetails == null) {
         throw new IMSServiceException(
                  IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED.errCode(),
                  IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED.errMsg());
      }

      String userEmail = userVerificationDetails.getEmailId();
      VerificationPurpose userVerificationPurpose = userVerificationDetails.getPurpose();

      if (!userEmail.equals(requestEmail)) {
         log.error("Mismatch between verification code email :" + userEmail
                  + " and email in request" + requestEmail);
         throw new IMSServiceException(
                  IMSServiceExceptionCodes.MISMATCH_EMAIL_VERIFICATION.errCode(),
                  IMSServiceExceptionCodes.MISMATCH_EMAIL_VERIFICATION.errMsg());
      }

      LoginUserRequest loginRequest = new LoginUserRequest();
      LoginUserResponse loginResponse = null;
      loginRequest.setEmailId(userEmail);
      loginRequest.setPassword(requestPassword);
      // For parking
      // 1. Login using userService
      // 2. upgrade status details
      // case: upgrade recomended, completed, link state
      // upgrade recomended: set email verification true via snapdeal
      // completed: set email verified in db
      // link state : change password only if token is V1 token.
      // in case of v2 token, password is already of IMS

      // Verification purpose new user:(linkig user)
      // get upgrade status:
      // completed: email verifid in db
      // link state: change password
      // login

      // if giving old token then signout the newly created token

      if (userVerificationPurpose.equals(VerificationPurpose.PARKING_INTO_WALLET)) {

         loginResponse = loginUserService.loginUser(loginRequest);
         Upgrade upgradeStatus = loginResponse.getUpgradationInformation().getUpgrade();

         switch (upgradeStatus) {

            case UPGRADE_RECOMMENDED:
               sdFcPasswordCacheService.setEmailVerificationSource(userEmail,
                        EmailVerificationSource.EMAIL_VERIFIED_VIA_SNAPDEAL);
               break;
            case UPGRADE_COMPLETED:

               User userByEmail = userDao.getUserByEmail(requestEmail);
               if (userByEmail == null) {
                  throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
                           IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
               }
               userByEmail.setEmailVerified(true);
               userDao.updateById(userByEmail);
               userCacheService.invalidateUserById(userByEmail.getUserId());
               break;
            case LINK_SD_ACCOUNT:

               VerifyUserUpgradeRequest verifyUserRequest = new VerifyUserUpgradeRequest();
               verifyUserRequest.setEmailId(requestEmail);
               verifyUserRequest.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(requestPassword));
               verifyUserRequest.setVerifiedType(LinkUserVerifiedThrough.LINK_ACCOUNT_VIA_PARKING);
               verifyUserRequest.setToken(loginResponse.getTokenInformation().getToken());
               VerifyUpgradeUserResponse verifyUpgradeUserResponse = verifyUpgradeUser(
                        verifyUserRequest);
               break;
            default:
				log.error("Feature not supported :- While Parking following 3 cases applicable only UPGRADE_RECOMMENDED, UPGRADE_COMPLETED, LINK_SD_ACCOUNT");
            	throw new IMSServiceException(
                        IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
                        IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
         }

      } else if (userVerificationDetails.getPurpose().equals(VerificationPurpose.VERIFY_NEW_USER)) {

         UpgradeStatus migrationDetails = migration.getIMSMigrationStatus(userEmail);
         Upgrade upgradeStatus = migrationDetails.getUpgradeStatus();
         switch (upgradeStatus) {

         // TODO: This is removed so that we reset password in cases of upgrade
         // user.
/*               User userByEmail = userDao.getUserByEmail(requestEmail);
               if (userByEmail == null) {
                  throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
                           IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
               }
               userByEmail.setEmailVerified(true);
               userDao.updateById(userByEmail);
               userCacheService.invalidateUserById(userByEmail.getUserId());

               loginResponse = loginUserService.loginUser(loginRequest);

               break;
*/
         	case UPGRADE_COMPLETED:
            case LINK_FC_ACCOUNT:
			case LINK_SD_ACCOUNT:
				// signout in case of reset password
				String oldPassword = imsService
						.getPasswordByEmail(requestEmail);
				imsService.resetDefaultSocialPasswordHelper(requestEmail,
						IMSEncryptionUtil.getSDEncryptedPassword(requestPassword));

				if ((StringUtils.isNotBlank(oldPassword) && !IMSEncryptionUtil.getSDEncryptedPassword(requestPassword).equals(
								oldPassword))
						|| Boolean
								.parseBoolean(Configuration
										.getGlobalProperty(ConfigurationConstants.VERIFYLINKSTATUS_PASSWORD_RESET_SAME_PWD))) {

					if (StringUtils.isNotBlank(verifyUserLinkedRequest
							.getToken())) {
						SignoutRequest signoutRequest = new SignoutRequest();
						signoutRequest.setToken(verifyUserLinkedRequest
								.getToken());
						signoutRequest.setHardSignout(true);
						tokenService.signOut(signoutRequest);
					}
				}

               loginResponse = loginUserService.loginUser(loginRequest);
               updateMigrationStatus(requestEmail,loginResponse.getUserDetails());
               break;
            default:
               log.error("Feature not supported :- While Verify_New_User 3 cases applicable only UPGRADE_COMPLETED, LINK_FC_ACCOUNT, LINK_FC_ACCOUNT");
               throw new IMSServiceException(
                        IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
                        IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
         }
      } else {
    	  log.error("Invalid Verification Purpose :- Only 2 cases applicable PARKING_INTO_WALLET,VERIFY_NEW_USER");
         throw new IMSServiceException(
                  IMSServiceExceptionCodes.INVALID_VERIFICATION_PURPOSE.errCode(),
                  IMSServiceExceptionCodes.INVALID_VERIFICATION_PURPOSE.errMsg());

      }
      String requestToken = verifyUserLinkedRequest.getToken();
      if (requestToken != null) {

         GetUserByTokenRequest getUserByTokenRequest = new GetUserByTokenRequest();
         getUserByTokenRequest.setToken(requestToken);
         GetUserResponse getUserResponse = null;
         try {
            getUserResponse = userService.getUserByToken(getUserByTokenRequest);

         } catch (IMSServiceException | AuthorizationException | InternalServerException
                  | AuthenticationException ex) {
            response.setLoginUserResponse(loginResponse);
            response.setSuccess(true);
            return response;
         }
         UserDetailsDTO userDetails = getUserResponse.getUserDetails();
         if (userDetails.getEmailId().equals(userEmail)) {
            TokenDetailsDTO tokenDetails = tokenService.getTokenDetailsForToken(requestToken);
            GlobalTokenDetailsEntity globalTokenDetailsForToken = tokenService
                     .getGlobalTokenDetailsForToken(requestToken);

            TokenInformationDTO tokenInformation = new TokenInformationDTO();
            tokenInformation.setGlobalToken(globalTokenDetailsForToken.getGlobalToken());
            tokenInformation
                     .setGlobalTokenExpiry(globalTokenDetailsForToken.getExpiryTime().toString());
            tokenInformation.setToken(requestToken);
            
            String tokenExpiry = tokenDetails.getExpiryTime() == null ? globalTokenDetailsForToken
                     .getExpiryTime().toString() : tokenDetails.getExpiryTime().toString();
            tokenInformation.setTokenExpiry(tokenExpiry);

            // sign out newly created token.
            SignoutRequest signoutRequest = new SignoutRequest();
            signoutRequest.setToken(loginResponse.getTokenInformation().getToken());
            tokenService.signOut(signoutRequest);
            loginResponse.setTokenInformation(tokenInformation);
         } else {
            // if user is different, the signout already logged in user.
            SignoutRequest signoutRequest = new SignoutRequest();
            signoutRequest.setToken(requestToken);
            tokenService.signOut(signoutRequest);
         }
      }
      response.setLoginUserResponse(loginResponse);
      response.setSuccess(true);
      userVerificationService.deleteUserVerificationDetailsByCode(verificationCode);
      // populate activity on success
      activityDataService.setActivityDataByUserId(loginResponse.getUserDetails().getUserId());
      return response;
   }

}
