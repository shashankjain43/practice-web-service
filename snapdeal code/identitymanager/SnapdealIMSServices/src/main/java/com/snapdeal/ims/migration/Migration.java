package com.snapdeal.ims.migration;

import com.freecharge.umsclient.exception.UmsException;
import com.klickpay.fortknox.MergeType;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.MigrationConstants;
import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.Action;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.Skip;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.errorcodes.IMSMigrationExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.fortknox.request.FortKnoxRequest;
import com.snapdeal.ims.migration.PostUpgradeStatusInfo.CreateOrUpdate;
import com.snapdeal.ims.migration.dao.MigrationDao;
import com.snapdeal.ims.migration.exception.IMSMigrationHardDeclinedException;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.migration.util.UserAccountUtil;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.GetIMSUserVerificationUrlRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.service.impl.FortKnoxServiceHelper;
import com.snapdeal.ims.service.provider.RandomUpgradeChoiceUtil;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.task.test.ITaskService;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.ims.utility.IMSUtility;
import com.snapdeal.ims.utils.PasswordHashServiceUtil;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Migration {

   @Qualifier("IMSService")
   @Autowired
   private IUMSService imsService;

   @Qualifier("FCUmsService")
   @Autowired
   private IUMSService umsServiceFC;

   @Qualifier("SnapdealUmsService")
   @Autowired
   private IUMSService umsServiceSD;
   
   @Autowired
   private ITaskService taskService;
   
   @Autowired
   private RandomUpgradeChoiceUtil randomUpgradeChoiceUtil;

   @Autowired
   private IPasswordUpgradeCacheService passwordCacheService;

   @Autowired
   private AuthorizationContext context;

   @Autowired
   private MigratorObjectFactory migratorObjectFactory;

   @Qualifier("sdUserAccount")
   @Autowired
   private UserAccountUtil sdAccountUtil;

   @Qualifier("fcUserAccount")
   @Autowired
   private UserAccountUtil fcAccountUtil;

   @Autowired
   private MigrationDao migrationDao;

   @Autowired
   private IGlobalTokenService globalTokenService;

   @Autowired
   private IUserService userService;

   @Autowired
   private FortKnoxServiceHelper fortKnoxServiceHelper;

   @Autowired
   private IMSUtility imsUtillity;

   @Autowired
   private UmsMerchantProvider merchantProvider;

   public UpgradeStatus getIMSMigrationStatus(String emailId) throws IMSMigrationHardDeclinedException {
      Merchant originatingSource = merchantProvider.getMerchant();
      UpgradeStatus upgradeStatusFromDb = migrationDao.getLatestUpgradeStatus(emailId);
      if (upgradeStatusFromDb == null) {
         upgradeStatusFromDb = createUpgradeUserStatus(emailId, originatingSource, true, false);
      }
      return upgradeStatusFromDb;
   }

   @Autowired
   private IOTPService otpService;

   public UpgradationInformationDTO getMigrationStatus(String email, boolean isExternalFlow) 
      throws IMSMigrationHardDeclinedException {

      Merchant originatingSource = merchantProvider.getMerchant();
      /***
       * if originating Source is once check \
       * 
       *  call new method getMigrationStatusForUpgradeForOneCheck()
       *  upgrade entry is there for new created user with mobile after verification
       *
       */
      if(Merchant.ONECHECK.equals(originatingSource)){
         return getMigrationStatusForUpgradeForOneCheck(email);
      }
      
      if (!isUpgradeShown(email)
               && !randomUpgradeChoiceUtil.isEmailToBePickedForUpgrade(
                        context.get(IMSRequestHeaders.CLIENT_ID.toString()),email)) {
         final UpgradationInformationDTO upgradationInformationDTO = new UpgradationInformationDTO();
         // This is the case when user has not been shown upgrade screen
         // previously and doesn't qualify to be picked up for upgrade process.
         // we have set the temporary state, as we dont have any other
         // intermediate state
         if (Merchant.SNAPDEAL == originatingSource) {
            upgradationInformationDTO.setState(State.SD_ACCOUNT_EXISTS_AND_ENABLED);
         } else if (Merchant.FREECHARGE == originatingSource) {
            upgradationInformationDTO.setState(State.FC_ACCOUNT_EXISTS_AND_ENABLED);
         }
         upgradationInformationDTO.setAction(Action.NO_ACTION_REQUIRED);
         // if user is blacklisted, then setting reason.
         if (randomUpgradeChoiceUtil.isBlackListedUser(email)) {
            log.warn("Email is blacklisted" + email);
            upgradationInformationDTO.setBlackListed(true);
            // upgradationInformationDTO.setUpgrade(Upgrade.NO_UPGRADE_REQRUIRED_BLACK_LISTED_USER);
         }
         upgradationInformationDTO.setUpgrade(Upgrade.NO_UPGRADE_REQRUIRED);
         setSkipFromCache(upgradationInformationDTO);
         return upgradationInformationDTO;
      }
      return getMigrationStatusForUpgrade(email, originatingSource, isExternalFlow);
   }
   
   private UpgradationInformationDTO getMigrationStatusForUpgradeForOneCheck(String email){
      UpgradeStatus upgradeStatusFromDb = migrationDao.getLatestUpgradeStatus(email);
      if(upgradeStatusFromDb==null){
         log.error(MigrationConstants.MIGRATION_NEVER_INITIATED);
         throw new IMSServiceException(
                  IMSMigrationExceptionCodes.MIGRATION_NEVER_INITIATED_EXCEPTION.errCode(),
                  IMSMigrationExceptionCodes.MIGRATION_NEVER_INITIATED_EXCEPTION.errMsg());
      }
      final UpgradationInformationDTO upgradationInformationDTO = new UpgradationInformationDTO();
      
      upgradationInformationDTO.setState(upgradeStatusFromDb.getCurrentState());
      upgradationInformationDTO.setUpgrade(upgradeStatusFromDb.getUpgradeStatus());
      upgradationInformationDTO.setAction(getActionFromUpgradeState(upgradeStatusFromDb.getUpgradeStatus()));
      setSkipFromCache(upgradationInformationDTO);
      
      return upgradationInformationDTO;
   }

   public UpgradationInformationDTO getMigrationStatusForUpgrade(String email,
                                                                 Merchant originatingSource, boolean ieExternalFlow) 
      throws IMSMigrationHardDeclinedException {
      
      final UpgradationInformationDTO upgradationInformationDTO = new UpgradationInformationDTO();
      log.info(MigrationConstants.MigrationEnabled + " for email id" + email);
      // Check in IMS user table if user exists with email id.
      UpgradeStatus upgradeStatusFromDb = migrationDao.getLatestUpgradeStatus(email);
      if (isUserExistInOC(email)) {
         // If IMS user exists, then there should be an entry in Upgrade table.
         // Sign-up from OC using email/mobile with password is not supported.
         log.info(MigrationConstants.USER_EXISTS_IMS_EMAIL_ID + email);
         if (upgradeStatusFromDb == null) {
            log.error(MigrationConstants.ILLEGAL_MIGRATION_STATE);
            throw new IMSServiceException(
                     IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errCode(),
                     IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errMsg());
         }
         Upgrade displayUpgradeState = upgradeStatusFromDb.getUpgradeStatus();
         // change upgrade status based on state
         if (Merchant.SNAPDEAL == originatingSource) {
            // add all the exists case for state
            if (State.SD_ACCOUNT_MIGRATED == upgradeStatusFromDb.getCurrentState()) {
               displayUpgradeState = Upgrade.UPGRADE_COMPLETED;
            } else if (State.FC_ACCOUNT_MIGRATED == upgradeStatusFromDb.getCurrentState()) {
               displayUpgradeState = Upgrade.LINK_SD_ACCOUNT;
            }
         } else if (Merchant.FREECHARGE == originatingSource) {
            if (State.FC_ACCOUNT_MIGRATED == upgradeStatusFromDb.getCurrentState()) {
               displayUpgradeState = Upgrade.UPGRADE_COMPLETED;
            } else if (State.SD_ACCOUNT_MIGRATED == upgradeStatusFromDb.getCurrentState()) {
               displayUpgradeState = Upgrade.LINK_FC_ACCOUNT;
            }
         }
         upgradationInformationDTO.setState(upgradeStatusFromDb.getCurrentState());
         upgradationInformationDTO.setUpgrade(displayUpgradeState);
         upgradationInformationDTO.setAction(getActionFromUpgradeState(displayUpgradeState));
         setSkipFromCache(upgradationInformationDTO);
         return upgradationInformationDTO;
      }
      // If we are here then that means user doesn't exist in OC
      if (upgradeStatusFromDb == null) {
         upgradeStatusFromDb = createUpgradeUserStatus(email, originatingSource, false, ieExternalFlow);
      }
      
		// case of social signup
		if (((upgradeStatusFromDb.getCurrentState() == State.FC_ACCOUNT_EXISTS_AND_ENABLED || upgradeStatusFromDb
				.getCurrentState() == State.FC_ACCOUNT_EXISTS_AND_DISABLED) 
				&& Merchant.SNAPDEAL == originatingSource)
				|| ((upgradeStatusFromDb.getCurrentState() == State.SD_ACCOUNT_EXISTS_AND_ENABLED || upgradeStatusFromDb
						.getCurrentState() == State.SD_ACCOUNT_EXISTS_AND_DISABLED)
						&& Merchant.FREECHARGE == originatingSource)) {
			
			//This is the case when existing snapdeal user does social signup on fc and vice versa
			if(originatingSource==Merchant.SNAPDEAL && upgradeStatusFromDb.getSdId()==null){
				upgradeStatusFromDb.setSdId(context.get(IMSRequestHeaders.USER_ID.toString()));	
			}else if (originatingSource==Merchant.FREECHARGE && upgradeStatusFromDb.getFcId()==null){
				upgradeStatusFromDb.setFcId(context.get(IMSRequestHeaders.USER_ID.toString()));	
			}
			
			// This is the special case when user signups from SD and without
			// migrating again signups from FC or vice-versa
			if (upgradeStatusFromDb.getCurrentState() == State.FC_ACCOUNT_EXISTS_AND_ENABLED
					|| upgradeStatusFromDb.getCurrentState() == State.SD_ACCOUNT_EXISTS_AND_ENABLED)
				upgradeStatusFromDb
						.setCurrentState(State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED);
			else if(upgradeStatusFromDb.getCurrentState() == State.FC_ACCOUNT_EXISTS_AND_DISABLED)				
				upgradeStatusFromDb
						.setCurrentState(State.SD_ENABLED_FC_DISABLED_EXISTS);
			else
				upgradeStatusFromDb
				.setCurrentState(State.SD_DISABLED_FC_ENABLED_EXISTS);
			
         // need to update the same in db
         migrationDao.updateUpgradationStatus(upgradeStatusFromDb);
      }
      // finally populating DTO to be sent to client
      upgradationInformationDTO.setState(upgradeStatusFromDb.getCurrentState());
      upgradationInformationDTO.setAction(getActionFromUpgradeState(upgradeStatusFromDb
               .getUpgradeStatus()));
      upgradationInformationDTO.setUpgrade(upgradeStatusFromDb.getUpgradeStatus());
      setSkipFromCache(upgradationInformationDTO);
      return upgradationInformationDTO;
   }

   public UpgradeStatus createUpgradeUserStatus(String email,
                                                Merchant originatingSource,
                                                boolean isSignup, boolean isExternalFlow)
   throws IMSMigrationHardDeclinedException {
      final GetUserByEmailRequest request = new GetUserByEmailRequest();
      request.setEmailId(email);
      GetUserResponse fcUseRresponse = null;
      try {
         fcUseRresponse = umsServiceFC.getUserByEmail(request);
      } catch (UmsException e) {
         log.info(MigrationConstants.USER_FETCH_FC + email);
      } catch (Exception e) {
         log.error("Exception occurs while connecting to FC",e);
         throw new IMSMigrationHardDeclinedException();
      }
      GetUserResponse sdUseRresponse = null;
      try {
         sdUseRresponse = umsServiceSD.getUserByEmail(request);
      } catch (IMSServiceException e) {
         // Need not do anything here as we already have default value as
         // null
         log.info(MigrationConstants.USER_FETCH_SD + email);
      } catch (Exception e) {
         log.error("Exception occurs while connecting to SD",e);
         throw new IMSMigrationHardDeclinedException();
      }
      UpgradeStatus upgradeStatusFromDb = migrationDao.getLatestUpgradeStatus(email);
      // Checking if upgrade status exists in DB, this is an extra check not to
      // make duplicate entry as two network calls are made and may take time.
      if (null == upgradeStatusFromDb) {
         upgradeStatusFromDb = populateUpgradeUserStatus(EmailUtils.toLowerCaseEmail(email), 
                                                         originatingSource, 
                                                         fcUseRresponse,
                                                         sdUseRresponse, 
                                                         isSignup);
         if(!isExternalFlow){
        	 migrationDao.createUpgrateUser(upgradeStatusFromDb);
             passwordCacheService.setIsUpgradeinitialized(email, true); 
         }
      }
      return upgradeStatusFromDb;
   }

   private void setSkipFromCache(final UpgradationInformationDTO upgradationInformationDTO) {
      if (Boolean
               .parseBoolean(Configuration.getGlobalProperty(ConfigurationConstants.UPGRADE_SKIP))) {
         upgradationInformationDTO.setSkip(Skip.SKIP_TRUE);
      } else {
         upgradationInformationDTO.setSkip(Skip.SKIP_FALSE);
      }
   }

   private boolean isUpgradeShown(String emailId) {
      boolean blackListedUser = randomUpgradeChoiceUtil.isBlackListedUser(emailId);
      if (blackListedUser) {
         return false;
      }
      return passwordCacheService.getUserUpgradeStatus(emailId);
   }

   private UpgradeStatus populateUpgradeUserStatus(String email, Merchant originatingSource,
            GetUserResponse fcUserResponse, GetUserResponse sdUserResponse, boolean isSignup) {
      UpgradeStatus upgradeStatus = new UpgradeStatus();
      upgradeStatus.setEmail(email);
		upgradeStatus.setUpgradeStatus(Upgrade.UPGRADE_RECOMMENDED);
		State accoutState = State.OC_ACCOUNT_NOT_EXISTS;
		
		if (fcUserResponse != null && fcUserResponse.getUserDetails() != null
				&& sdUserResponse != null
				&& sdUserResponse.getUserDetails() != null) {
			upgradeStatus.setFcId(fcUserResponse.getUserDetails().getUserId());
			upgradeStatus.setSdId(sdUserResponse.getUserDetails().getUserId());	
			
			if (fcUserResponse.getUserDetails().isEnabledState()
					&& sdUserResponse.getUserDetails().isEnabledState()) {
				accoutState = State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED;
			} else if (fcUserResponse.getUserDetails().isEnabledState()
					&& !(sdUserResponse.getUserDetails().isEnabledState())) {
				accoutState = State.SD_DISABLED_FC_ENABLED_EXISTS;
			} else if ((!fcUserResponse.getUserDetails().isEnabledState())
					&& sdUserResponse.getUserDetails().isEnabledState()) {
				accoutState = State.SD_ENABLED_FC_DISABLED_EXISTS;
			} else {
				accoutState = State.SD_FC_ACCOUNT_EXISTS_AND_DISABLED;
			}
      } else if (fcUserResponse != null && fcUserResponse.getUserDetails() != null) {
         upgradeStatus.setFcId(fcUserResponse.getUserDetails().getUserId());
			if (Merchant.SNAPDEAL == originatingSource && !isSignup) {
				if (sdUserResponse == null) {
					// When user exists in fc and tries to fetch migration
					// status from sd
					throw new IMSServiceException(
							IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
							IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
				}
				// TODO need to remove below code of current IF condition as this is unreachable

				// This is the case when user logins from sd and has account in fc
				upgradeStatus.setSdId(sdUserResponse.getUserDetails()
						.getUserId());

				if (fcUserResponse.getUserDetails().isEnabledState()
						&& sdUserResponse.getUserDetails().isEnabledState()) {
					accoutState = State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED;
				} else if (fcUserResponse.getUserDetails().isEnabledState()
						&& !(sdUserResponse.getUserDetails().isEnabledState())) {
					accoutState = State.SD_DISABLED_FC_ENABLED_EXISTS;
				} else if ((!fcUserResponse.getUserDetails().isEnabledState())
						&& sdUserResponse.getUserDetails().isEnabledState()) {
					accoutState = State.SD_ENABLED_FC_DISABLED_EXISTS;
				} else {
					accoutState = State.SD_FC_ACCOUNT_EXISTS_AND_DISABLED;
				}

			} else {
				// This is the case when user logins from fc and has account in fc
				if (fcUserResponse.getUserDetails().isEnabledState()) {
					accoutState = State.FC_ACCOUNT_EXISTS_AND_ENABLED;
				} else {
					accoutState = State.FC_ACCOUNT_EXISTS_AND_DISABLED;
				}
			}
      } else if (sdUserResponse != null && sdUserResponse.getUserDetails() != null) {
         upgradeStatus.setSdId(sdUserResponse.getUserDetails().getUserId());
			if (Merchant.FREECHARGE == originatingSource && !isSignup) {
				if (fcUserResponse == null) {
					// When user exists in sd and tries to fetch migration status from fc
					throw new IMSServiceException(
							IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
							IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
				}
				
				// TODO need to remove below code of current IF condition as this is unreachable
				
				// This is the case when user logins from fc and has account in sd				
				upgradeStatus.setFcId(fcUserResponse.getUserDetails()
						.getUserId());

				if (fcUserResponse.getUserDetails().isEnabledState()
						&& sdUserResponse.getUserDetails().isEnabledState()) {
					accoutState = State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED;
				} else if (fcUserResponse.getUserDetails().isEnabledState()
						&& !(sdUserResponse.getUserDetails().isEnabledState())) {
					accoutState = State.SD_DISABLED_FC_ENABLED_EXISTS;
				} else if ((!fcUserResponse.getUserDetails().isEnabledState())
						&& sdUserResponse.getUserDetails().isEnabledState()) {
					accoutState = State.SD_ENABLED_FC_DISABLED_EXISTS;
				} else {
					accoutState = State.SD_FC_ACCOUNT_EXISTS_AND_DISABLED;
				}

			} else {
				// This is the case when user logins from sd and has account in sd
				if (sdUserResponse.getUserDetails().isEnabledState()) {
					accoutState = State.SD_ACCOUNT_EXISTS_AND_ENABLED;
				} else {
					accoutState = State.SD_ACCOUNT_EXISTS_AND_DISABLED;
				}
			}
		} else {
			// This is the case of signup
			accoutState = State.OC_ACCOUNT_NOT_EXISTS;
		}
		upgradeStatus.setCurrentState(accoutState);
		upgradeStatus.setInitialState(accoutState);
		upgradeStatus.setCreatedDate(new Date());
		return upgradeStatus;
   }

   private Action getActionFromUpgradeState(Upgrade displayUpgradeState) {
      Action action;
      // TODO need closure from anand
      switch (displayUpgradeState) {
         case LINK_SD_ACCOUNT:
            action = Action.VERIFY_SD_PASSWORD;
            break;
         case LINK_FC_ACCOUNT:
            action = Action.VERIFY_FC_PASSWORD;
            break;
         default:
            action = Action.NO_ACTION_REQUIRED;
            break;
      }
      return action;
   }

   private boolean isUserExistInOC(String email) {

      final GetUserByEmailRequest request = new GetUserByEmailRequest();
      request.setEmailId(email);
      GetUserResponse response = null;
      try {
         response = imsService.getUserByEmail(request);
      } catch (IMSServiceException e) {
         log.info("User doesnot exist" + e);
      }

      return (response != null && response.getUserDetails() != null && response.getUserDetails()
               .isMobileVerified());
      // TODO need to take closure on mobile verified
   }

   /**
    * This function will be called from UI: APP/WEB/WAP
    * Cases: 
    *      User SIGN_UP via verify mobile, 
    *      SIGN_IN via controller, 
    *      OneCheck Social SIGN_IN via controller(with OTP)
    * 
    * Steps:
    *      1. If !SIGN_UP then verify OTP
    *      2. Fetch latest migration status
    *      3. On the basis of migration status fetch 
    *           the Utility to perform migration task
    *      4. Get the status from migration utility and update DB
    * 
    * @param UserUpgradeRequest: request
    * @return UpgradeUserResponse
    */
   @Timed
   @Marked
   @Logged
   @Transactional
   public UpgradeUserResponse doUpgrade(UserUpgradeRequest request) 
      throws IMSMigrationHardDeclinedException {
	   
	   if(randomUpgradeChoiceUtil.isBlackListedUser(request.getEmailId())){
         log.error("Blacklisted user : " + request.getEmailId());
		  throw new IMSServiceException(IMSMigrationExceptionCodes.USER_BLACKLISTED.errCode(),
				  IMSMigrationExceptionCodes.USER_BLACKLISTED.errMsg()) ;
	   }
	   
      UpgradeUserResponse response = new UpgradeUserResponse();
      String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
      String email = request.getEmailId();
      if (request.getUpgradeSource() != UpgradeSource.SIGN_UP) {
         // 1. Verify otp.
         VerifyOTPServiceRequest otpRequest = new VerifyOTPServiceRequest();
         otpRequest.setClientId(clientId);
         otpRequest.setOtp(request.getOtp());
         otpRequest.setOtpId(request.getOtpId());
         otpRequest.setToken(request.getToken());
         if(request.getUpgradeSource() == UpgradeSource.ONECHECK_SOCIAL_SIGNUP)
            otpRequest.setOtpPurpose(OTPPurpose.ONECHECK_SOCIAL_SIGNUP);
         else   
            otpRequest.setOtpPurpose(OTPPurpose.UPGRADE_USER);
         
         log.info("OTP validation for upgrade: " + otpRequest);
         VerifyOTPServiceResponse verifyOTPResponse = otpService.verifyOTP(otpRequest);
         if (null == verifyOTPResponse
                  || OtpConstants.STATUS_FAILURE.equalsIgnoreCase(verifyOTPResponse.getStatus())) {
            IMSServiceExceptionCodes code = IMSServiceExceptionCodes.INVALID_OTP_ENTERED;
            throw new IMSServiceException(code.errCode(), code.errMsg());
         }
         log.debug("OTP verified for upgrade user : " + verifyOTPResponse);
         if (StringUtils.isBlank(request.getMobileNumber())) {
            if (StringUtils.isBlank(verifyOTPResponse.getMobileNumber())) {
               log.error("Mobile number is blank for otp as well as request.");
            }
            request.setMobileNumber(verifyOTPResponse.getMobileNumber());
         } else if (!request.getMobileNumber().equals(verifyOTPResponse.getMobileNumber())) {
            throw new IMSServiceException(
                     IMSMigrationExceptionCodes.OTP_MOBILE_MISSMATCH.errCode(),
                     IMSMigrationExceptionCodes.OTP_MOBILE_MISSMATCH.errMsg());
         }
      }
      UserDetailsDTO userDetails = null;
      // This case, when email is not set and token is sent as identifier.
      // Also, we make a check if email and token data matches.
      if (UserIdentityVerifiedThrough.TOKEN_VERIFIED == request.getVerifiedType()
               || StringUtils.isBlank(request.getEmailId())) {
         if (StringUtils.isBlank(request.getToken())) {
            throw new RequestParameterException(IMSRequestExceptionCodes.TOKEN_IS_BLANK.errCode(),
                     IMSRequestExceptionCodes.TOKEN_IS_BLANK.errMsg());
         }
         GetUserByTokenRequest getUserRequest = new GetUserByTokenRequest();
         getUserRequest.setToken(request.getToken());
         getUserRequest.setUserAgent(context.get(IMSRequestHeaders.USER_AGENT.toString()));
         getUserRequest.setUserMachineIdentifier(context
                  .get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()));
         GetUserResponse userByToken = userService.getUserByToken(getUserRequest);
         userDetails = userByToken.getUserDetails();
         if (StringUtils.isNotBlank(email) && !email.equals(userDetails.getEmailId())) {
            log.error("Email in request doesn't match the user email from token.");
            throw new IMSServiceException(
                     IMSMigrationExceptionCodes.EMAIL_MISS_MATCH_VERIFICATION.errCode(),
                     IMSMigrationExceptionCodes.EMAIL_MISS_MATCH_VERIFICATION.errMsg());
         }

         email = userDetails.getEmailId();
         request.setEmailId(email);
         // Check if user is blacklisted.
         if(randomUpgradeChoiceUtil.isBlackListedUser(request.getEmailId())){
   		  throw new IMSServiceException(IMSMigrationExceptionCodes.USER_BLACKLISTED.errCode(),
   				  IMSMigrationExceptionCodes.USER_BLACKLISTED.errMsg()) ;
   	   }
      }
      // Setting upgrade channel to OTHERS is it is not set.
      if (null == request.getUpgradeChannel()) {
         log.warn("Setting upgrade channel to OTHERS for upgrading user : " + email);
         request.setUpgradeChannel(UpgradeChannel.OTHERS);
      }
      Merchant originatingSource = merchantProvider.getMerchant();
      // call getMigrationStatus() to get the latest upgrade state info.
      UpgradationInformationDTO upgradeInfo = getMigrationStatusForUpgrade(email,
               merchantProvider.getMerchant(), false);
      // Get userAccountExist implementation based on current state of user and
      if (upgradeInfo.getUpgrade() == Upgrade.UPGRADE_COMPLETED) {
         response.setSuccess(true);
         return response;
      }
      // originating source.
      UserAccountExist userAccountExist = migratorObjectFactory.getMigratorObject(
               upgradeInfo.getState(), originatingSource);

      PostUpgradeStatusInfo postUpgradeStatusInfo = userAccountExist.performTask(request,
               originatingSource, upgradeInfo);

      UpgradeStatus upgradeStatus = performTaskTransactional(postUpgradeStatusInfo, request,
               userAccountExist);
      // Fetch user from IMS DB, after upgrade to get user id.
      GetUserByEmailRequest emailReq = new GetUserByEmailRequest();
      emailReq.setEmailId(email);
      userDetails = userService.getUserByEmail(emailReq).getUserDetails();
      // TODO Complete Task Request
      // MergeType mergeType = null;
      // WalletUserMigrationStatus status = null;
      if (postUpgradeStatusInfo.getUpgradeStatus() == Upgrade.UPGRADE_COMPLETED
               && postUpgradeStatusInfo.getCurrentState() == State.OC_ACCOUNT_EXISTS) {
         // TODO Complete Task Request
         // status = WalletUserMigrationStatus.SD_FC_MIGRATED;
         fortKnoxServiceHelper.createFortKnoxTask(userDetails);
      }else if(Upgrade.isLinkState(postUpgradeStatusInfo.getUpgradeStatus())){
    	  if(postUpgradeStatusInfo.getUpgradeStatus() == Upgrade.LINK_FC_ACCOUNT){
            // mergeType = MergeType.SDOC;
            // status = WalletUserMigrationStatus.SD_MIGRATED;
            fortKnoxServiceHelper.createFortKnoxTask(userDetails, MergeType.SDOC);
    	  }else if(postUpgradeStatusInfo.getUpgradeStatus() == Upgrade.LINK_SD_ACCOUNT){
            // mergeType = MergeType.FCOC;
            // status = WalletUserMigrationStatus.FC_MIGRATED;
            fortKnoxServiceHelper.createFortKnoxTask(userDetails, MergeType.FCOC);
    	  }
      }
      // TODO Complete Task Request

      /*FortKnoxRequest fortKnoxRequeset = createFortKnoxRequest(userDetails,mergeType);
      clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
      String businessId = ClientConfiguration.getMerchantById(clientId).getMerchantName();
      String userId = imsService.getUserByEmail(request.getEmailId()).getUserId();
      taskService.createCompleteTask(userId, businessId, userId, fortKnoxRequeset, request.getEmailId(), status);*/
      response.setSuccess(true);
      response.setUserDetails(userDetails);
      log.info("Migration completed email : " + email + ", mobile: " + request.getMobileNumber()
               + ", upgrade status: " + upgradeStatus);
      return response;
   }

   @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
   public UpgradeStatus performTaskTransactional(PostUpgradeStatusInfo postUpgradeStatusInfo,
            UserUpgradeRequest request, UserAccountExist userAccountExist) {
      // in case of NO_USER_EXISTS, email upgrade dto is not set.
      String email = request.getEmailId();
      log.info("Upgrade done in memory for :" + email);
      UpgradeStatus upgradeStatus = migrationDao.getLatestUpgradeStatus(email);
      upgradeStatus.setCurrentState(postUpgradeStatusInfo.getCurrentState());
      upgradeStatus.setUpgradeStatus(postUpgradeStatusInfo.getUpgradeStatus());
      upgradeStatus.setUpgradeChannel(request.getUpgradeChannel());
      upgradeStatus.setUpgradeSource(request.getUpgradeSource());
      migrationDao.updateUpgradationStatus(upgradeStatus);
      if(StringUtils.isNotBlank(upgradeStatus.getFcId()) && postUpgradeStatusInfo.getUpgradeDto().getFcId()==0){
    	  postUpgradeStatusInfo.getUpgradeDto().setFcId(Long.valueOf(upgradeStatus.getFcId()));
    	  
      }else if (StringUtils.isNotBlank(upgradeStatus.getSdId()) && postUpgradeStatusInfo.getUpgradeDto().getSdId()==0){
    	  postUpgradeStatusInfo.getUpgradeDto().setSdId(Long.valueOf(upgradeStatus.getSdId()));
      }
      if (null != postUpgradeStatusInfo.getUpgradeDto()) {
         if (postUpgradeStatusInfo.getCreateOrUpdate() == CreateOrUpdate.CREATE) {
            migratorObjectFactory.getAccountUtil()
                     .createUser(postUpgradeStatusInfo.getUpgradeDto());
            
         } else if (postUpgradeStatusInfo.getCreateOrUpdate() == CreateOrUpdate.UPDATE) {
            imsService.updateUserByEmail(postUpgradeStatusInfo.getUpgradeDto());
         } else if (postUpgradeStatusInfo.getCreateOrUpdate() == CreateOrUpdate.OC_CREATE) {
            migratorObjectFactory.getAccountUtil(Merchant.ONECHECK).createUser(
                     postUpgradeStatusInfo.getUpgradeDto());
         }
      }
      log.info("Upgrade details updated in DB: " + email);
      log.debug("Sending upgrade email to " + email);
      
      /**
       * Add verification Url based on upgrade status and pass it to send email
       */
      Upgrade upgradeState = postUpgradeStatusInfo.getUpgradeStatus();
      String verificationUrl = null;
      if (request.getVerifiedType() != UserIdentityVerifiedThrough.GOOGLE_VERIFIED
               && request.getVerifiedType() != UserIdentityVerifiedThrough.FACEBOOK_VERIFIED
               && (upgradeState == Upgrade.LINK_FC_ACCOUNT
                        || upgradeState == Upgrade.LINK_SD_ACCOUNT || upgradeState == Upgrade.UPGRADE_COMPLETED)) {

         String userId = imsService.getUserByEmail(email).getUserId();
         GetIMSUserVerificationUrlRequest getVerificationUrlRequest = new GetIMSUserVerificationUrlRequest();
         getVerificationUrlRequest.setEmail(email);
         getVerificationUrlRequest.setPurpose(VerificationPurpose.VERIFY_NEW_USER);
         getVerificationUrlRequest.setMerchant(Merchant.ONECHECK);
         verificationUrl = imsService.getIMSUserVerificationUrl(getVerificationUrlRequest,
                  upgradeState, userId).getUrl();
      }
      userAccountExist.sendEmail(email, postUpgradeStatusInfo.getEmailTemplate(), false,
    		  postUpgradeStatusInfo.getSubjectKey(), verificationUrl);
      return upgradeStatus;
   }

   private FortKnoxRequest createFortKnoxRequest(UserDetailsDTO userDetailsDTO,MergeType mergeType) {

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
       fortKnoxRequest.setMergeType(mergeType);
      } else {
         log.warn("Card merge api is not enabled.");
      }
      return fortKnoxRequest;
   
   }

   public boolean verifyPassword(VerifyUserUpgradeRequest request, Merchant targetSrcToBeValidated) {

      String hashedPassword = getHashedPassword(request.getPassword(),
               request.getTargetSrcToBeValidated());

      String targetSourcePassword = getTargetSourcePassword(request.getEmailId(),
               request.getTargetSrcToBeValidated());

      return hashedPassword.equals(targetSourcePassword);
   }

   private String getHashedPassword(String password, Merchant targetSrcToBeValidated) {
      String hashedPassword = null;
      switch (targetSrcToBeValidated) {
         case SNAPDEAL:
            hashedPassword = PasswordHashServiceUtil.getSdHashedPassword(password);
            break;
         case FREECHARGE:
            hashedPassword = PasswordHashServiceUtil.getFcHashedPassword(password);
            break;
         case ONECHECK:
            hashedPassword = PasswordHashServiceUtil.getOcHashedPassword(password);
            break;
         default:
            break;
      }
      return hashedPassword;
   }

   private String getTargetSourcePassword(String emailId, Merchant targetSrcToBeValidated) {
      String hashedPassword = null;
      switch (targetSrcToBeValidated) {
         case SNAPDEAL:
            hashedPassword = passwordCacheService.getSdSdHashedPassword(emailId);
            break;
         case FREECHARGE:
            hashedPassword = passwordCacheService.getFcFcHashedPassword(emailId);
            break;
         case ONECHECK:
            hashedPassword = passwordCacheService.getImsSdHashedPassword(emailId);
            break;
         default:
            break;
      }
      return hashedPassword;
   }

   public UserUpgradationResponse upgradeSocialUser(String emailId) 
      throws IMSMigrationHardDeclinedException {
      
      UserUpgradationResponse res = new UserUpgradationResponse();
      Merchant originatingSource = merchantProvider.getMerchant();
      // call getMigrationStatus() to get the latest upgrade state info.
      UpgradationInformationDTO upgradeInfo = getMigrationStatusForUpgrade(emailId,
               originatingSource, false);
      UpgradeStatus upgradeStatusFromDb = migrationDao.getLatestUpgradeStatus(emailId);
      if ((upgradeInfo.getState() == State.SD_ACCOUNT_MIGRATED && originatingSource == Merchant.FREECHARGE)
               || upgradeInfo.getState() == State.FC_ACCOUNT_MIGRATED
               && originatingSource == Merchant.SNAPDEAL) {
         upgradeStatusFromDb.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         upgradeStatusFromDb.setCurrentState(State.OC_ACCOUNT_EXISTS);
         migrationDao.updateUpgradationStatus(upgradeStatusFromDb);
         imsService.createNotificationOnMigrationStateChange(null, emailId, WalletUserMigrationStatus.SD_FC_MIGRATED);
      }
      final UpgradationInformationDTO upgradationInformationDTO = new UpgradationInformationDTO();
      upgradationInformationDTO.setState(upgradeStatusFromDb.getCurrentState());
      upgradationInformationDTO.setAction(getActionFromUpgradeState(upgradeStatusFromDb
               .getUpgradeStatus()));
      upgradationInformationDTO.setUpgrade(upgradeStatusFromDb.getUpgradeStatus());
      setSkipFromCache(upgradationInformationDTO);
      res.setUpgradationInformation(upgradationInformationDTO);
      return res;
   }
}
