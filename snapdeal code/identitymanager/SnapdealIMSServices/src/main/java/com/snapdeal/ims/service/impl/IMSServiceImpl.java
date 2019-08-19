package com.snapdeal.ims.service.impl;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.ISDFCPasswordCacheService;
import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.cache.service.IUserVerificationCacheService;
import com.snapdeal.ims.cache.set.IIMSAerospikeSet;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.MigrationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.ISocialUserDao;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dbmapper.entity.SocialUser;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.entity.UserHistory;
import com.snapdeal.ims.enums.ConfigureUserBasedOn;
import com.snapdeal.ims.enums.EmailVerificationSource;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.OTPRequestChannel;
import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.enums.SocialSource;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.enums.UpdatedFeild;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.enums.UpgradeChannel;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.enums.UserStatus;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSValidationExceptionCodes;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.migration.Migration;
import com.snapdeal.ims.migration.dao.MigrationDao;
import com.snapdeal.ims.migration.dto.UpgradeDto;
import com.snapdeal.ims.migration.exception.IMSMigrationHardDeclinedException;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.objectMapper.IMSServiceObjectsMapper;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.dao.PersistenceManager;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.FetchLatestOTPRequest;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.CloseAccountByEmailRequest;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateSocialUserWithMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.CreateUserMobileGenerateRequest;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.CreateUserWithMobileOnlyRequest;
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.GetIMSUserVerificationUrlRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.MobileOnlyRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.ResetPasswordRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.request.UpdateMobileNumberRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsRequestDto;
import com.snapdeal.ims.response.CloseAccountResponse;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateSocialUserWithMobileResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.GetIMSUserVerificationUrlResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.IsVerifiedMobileExistResponse;
import com.snapdeal.ims.response.MobileOnlyResponse;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.OTPResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UniqueMobileVerificationResponse;
import com.snapdeal.ims.response.UpdateMobileNumberResponse;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.IMojoClientServiceHelper;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.service.IWalletBalance;
import com.snapdeal.ims.service.IWalletService;
import com.snapdeal.ims.service.dto.UserDTO;
import com.snapdeal.ims.service.provider.RandomUpgradeChoiceUtil;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.service.provider.UmsServiceProvider;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utils.CipherServiceUtil;
import com.snapdeal.ims.utils.IMSEncryptionUtil;
import com.snapdeal.ims.utils.VerificationCodeGeneratorUtil;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.errorcodes.ExceptionCodes;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@Service("IMSService")
@Slf4j
public class IMSServiceImpl extends AbstractUMSService {

	@Autowired
	IUserDao userDao;

	@Autowired
	IActivityDataService activityDataService;

	@Autowired
	private AuthorizationContext context;

	@Autowired
	IGlobalTokenService globalTokenService;

	@Autowired
	ITokenService tokenService;

	@Autowired
	IOTPService otpService;

	@Autowired
	IWalletService walletService;

	@Autowired
	IWalletBalance walletBalance;

	@Autowired
	ISDFCPasswordCacheService sdFcPasswordCacheService;

	@Autowired
	@Qualifier("dummyMigrationService")
	IUserMigrationService dummyMigrationService;

	@Autowired
	private ILoginUserService loginUserService;

	@Autowired
	IUserCacheService userCacheService;

	@Autowired
	UmsMerchantProvider merchantProvider;

	@Autowired
	UmsServiceProvider serviceProvider;

	@Autowired
	private Migration migration;

	@Autowired
	private IUserVerificationCacheService userVerificationService;

	@Autowired
	@Qualifier("userIdGTokenIdsSet")
	private IIMSAerospikeSet<String, HashSet<String>> userIdGTokenIdsSet;

	@Autowired
	private RandomUpgradeChoiceUtil randomUpgradeChoiceUtil;

	@Autowired
	private IUserService userService;

	@Autowired
	@Qualifier("userMigrationService")
	IUserMigrationService userMigrationServiceImpl;

	@Autowired
	@Qualifier("SnapdealUmsService")
	private IUMSService snapdealUmsService;

	@Autowired
	@Qualifier("FCUmsService")
	private IUMSService fcUmsService;

	@Autowired
	private IMojoClientServiceHelper mojoClientService;

	@Autowired
	private MigrationDao migrationDao;

	@Autowired
	private FortKnoxServiceHelper fortKnoxServiceHelper;

	@Autowired
	ISocialUserDao socialUserDao; 

	@Autowired
	PersistenceManager persistenceManager;


	@Override
	@Timed
	@Marked
	@Logged
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	public GetUserResponse updateUser(UpdateUserByIdRequest request) {

		String userId = request.getUserId();
		User user = getIMSUserById(userId);
		checkForNullUserAndThrowException(user);

		UserDetailsRequestDto userOld=IMSServiceObjectsMapper.getUserDetails(user);

		user = IMSServiceObjectsMapper
				.updateUserEntityFromUserDetailsRequestDto(request.getUserDetailsRequestDto(), user);

		updateUserById(user, userId);
		UserDetailsRequestDto userNew=IMSServiceObjectsMapper.getNewUserDetails(user,userOld);
		UserHistory history = new UserHistory();
		history.setUserId(user.getUserId());
		history.setField(UpdatedFeild.GENERAL_INFO);
		history.setOldValue(String.valueOf(userOld));
		history.setNewValue(String.valueOf(userNew));
		userDao.maintainUserHistory(history);

		GetUserResponse response = IMSServiceObjectsMapper.mapUserToGetUserResponse(user);

		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	public GetUserResponse updateUserByToken(UpdateUserByTokenRequest request)
			throws ValidationException {

		String userId = tokenService.getUserIdByToken(request.getToken());
		//User before update
		User user = getIMSUserById(userId);
		checkForNullUserAndThrowException(user);
		UserDetailsRequestDto userOld=IMSServiceObjectsMapper.getUserDetails(user);
		//new updated user		
		user = IMSServiceObjectsMapper
				.updateUserEntityFromUserDetailsRequestDto(request.getUserDetailsRequestDto(), user);
		updateUserById(user, userId);

		UserDetailsRequestDto userNew=IMSServiceObjectsMapper.getNewUserDetails(user,userOld);
		UserHistory history = new UserHistory();
		history.setUserId(user.getUserId());
		history.setField(UpdatedFeild.GENERAL_INFO);
		history.setOldValue(String.valueOf(userOld));
		history.setNewValue(String.valueOf(userNew));
		userDao.maintainUserHistory(history);

		GetUserResponse response = IMSServiceObjectsMapper.mapUserToGetUserResponse(user);

		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public IsEmailExistResponse isEmailExist(IsEmailExistRequest request) {

		IsEmailExistResponse response = new IsEmailExistResponse();
		String emailId = request.getEmailId();

		User user = getUserByEmailFromCacheOrDao(emailId);
		// Check if mobile is verified.
		if (null != user && user.isMobileVerified()) {
			response.setExist(true);
		}
		return response;
	}


	@Override
	@Timed
	@Marked
	@Logged
	public MobileVerificationStatusResponse isMobileVerified(
			MobileVerificationStatusRequest request) {
		String userId = request.getUserId();
		User user = getIMSUserById(userId);
		checkForNullUserAndThrowException(user);
		MobileVerificationStatusResponse response = new MobileVerificationStatusResponse();
		if (user.isMobileVerified()) {
			response.setSuccess(user.isMobileVerified());
		}

		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public UniqueMobileVerificationResponse verifyUniqueMobile(
			UniqueMobileVerificationRequest request) {

		throw new IMSServiceException(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode());
	}

	@Override
	@Timed
	@Marked
	@Logged
	public void updateUserByEmail(UpgradeDto upgradeDto) {
		User userFromDb = getIMSUserByEmail(upgradeDto.getEmailId());
		checkForNullUserAndThrowException(userFromDb);
		User user = IMSServiceObjectsMapper.mapUpgradeDtoToUser(userFromDb, upgradeDto);
		updateUserById(user, user.getUserId());
		createWallet(user.getUserId(), getMerchantEnum());
	}


	@Override
	@Timed
	@Marked
	@Logged
	public CreateUserResponse createUserByEmail(CreateUserEmailRequest request) {

		throw new IMSServiceException(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	@Timed
	@Marked
	@Logged
	// TODO NEED TO DEPRICATE THIS API And WIll CReate New API which will be
	// called by Migration
	public SocialUserResponse createSocialUser(CreateSocialUserRequest request) {
		checkBlackListedUserAndThrowException(request.getSocialUserDto().getEmailId());
		SocialUserRequestDto socialUserDetails = request.getSocialUserDto();
		User user = IMSServiceObjectsMapper.mapCreateSocialUserRequestToUser(socialUserDetails,
				getOriginatingSrc());
		SocialUser socialUser = IMSServiceObjectsMapper
				.mapCreateSocialUserRequestToSocialUser(socialUserDetails);
		socialUser.setMerchant(merchantProvider.getMerchant());
		socialUser.setPlatform(request.getPlatform());
		socialUser.setResource(request.getResource());
		// first get user ,if exists check whether social flag is true or not if
		// false create social user only and set flag as true
		User userFromDb = getIMSUserByEmail(request.getSocialUserDto().getEmailId());
		String userId = null;
		if (userFromDb == null) {
			// throw exception
			if(socialUser.getMerchant()==Merchant.ONECHECK){
				throw new IMSServiceException(
						IMSServiceExceptionCodes.LOGIN_VIA_DIFFERNT_EMAIL_ADDRESS.errCode(),
						IMSServiceExceptionCodes.LOGIN_VIA_DIFFERNT_EMAIL_ADDRESS.errCode());
			}
			else{
				throw new IMSServiceException(
						IMSServiceExceptionCodes.FEATURE_IS_SUPPORTED_SIGNUP.errCode(),
						IMSServiceExceptionCodes.FEATURE_IS_SUPPORTED_SIGNUP.errMsg());
			}
			// socialUser.setUser(user);
			// userId = user.getUserId();
			// createSocialUserTranscation(socialUser);
		} else {
			user = IMSServiceObjectsMapper.mapSocialUserRequestToUser(socialUserDetails,
					getOriginatingSrc(), userFromDb);
			if (!user.isEnabled()) {
				tokenService.signOutUser(user.getUserId());
				throw new IMSServiceException(IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
						IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
			}
			UpgradeStatus upgradeStatus = migration.getIMSMigrationStatus(user.getEmailId());
			if (upgradeStatus != null) {
				upgradeStatus.setCurrentState(State.OC_ACCOUNT_EXISTS);
				upgradeStatus.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
				upgradeStatus.setUpdatedDate(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				migrationDao.updateUpgradationStatusWithEmailVerifiedCount(upgradeStatus);
				createNotificationOnMigrationStateChange(null, user.getEmailId(),
						WalletUserMigrationStatus.EMAIL_VERIFIED);
				UserDTO userDTO = IMSServiceObjectsMapper.mapUserToUserDTO(user);
				fortKnoxServiceHelper.createFortKnoxTask(mapUserToUserDetailsDTO(userDTO));
			} else {
				// We shouldn't be here. It is unexpected behavior
				log.error("Unexpected error occured, upgradeStatus is null for emailId: "
						+ user.getEmailId());
				throw new IMSServiceException(
						IMSInternalServerExceptionCodes.INVALID_EMAIL_EXIST_IN_IMS
						.errCode(),
						IMSInternalServerExceptionCodes.INVALID_EMAIL_EXIST_IN_IMS
						.errMsg());
			}
			user.setEmailVerified(true);
			user.setStatus(UserStatus.REGISTERED);
			socialUser.setUser(user);
			updateUserByIdFromCacheAndDao(user);
			SocialUser socialUserFromDb = new SocialUser();
			socialUserFromDb.setUserId(userFromDb.getUserId());
			socialUserFromDb
			.setSocialSrc(SocialSource.forName(request.getSocialUserDto().getSocialSrc()));
			socialUserFromDb.setMerchant(merchantProvider.getMerchant());
			socialUserFromDb = socialUserDao.getSocialUser(socialUserFromDb);
			if (socialUserFromDb == null) {
				socialUserDao.createSocialUser(socialUser);
			} else {
				socialUser.setUserId(userFromDb.getUserId());
				socialUserDao.updateSocialUser(socialUser);
			}
			userId = user.getUserId();
		}
		userFromDb = getIMSUserByEmail(request.getSocialUserDto().getEmailId());
		TokenInformationDTO tokenDetails = createTokenOnLogin(user.getUserId(), user.getEmailId());
		SocialUserResponse response = IMSServiceObjectsMapper
				.mapSocialUserToSocialUserResponse(socialUser, userFromDb);
		// Migration Work Starts
		UpgradationInformationDTO upgradeDto = getUpgradationInformationDTO(
				request.getSocialUserDto().getEmailId(), true, tokenDetails.getToken());
		// Migration Work ends
		response.setUpgradationInformation(upgradeDto);
		response.setTokenInformation(tokenDetails);
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetUserResponse getUserByToken(GetUserByTokenRequest request) {
		String userId = tokenService.getUserIdByToken(request.getToken());
		User user = getIMSUserById(userId);
		checkForNullUserAndThrowException(user);
		GetUserResponse response = IMSServiceObjectsMapper.mapUserToGetUserResponse(user);
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public IsUserExistResponse isUserExist(IsUserExistRequest request) {
		IsUserExistResponse response = new IsUserExistResponse();

		User user = getIMSUserById(request.getUserId());
		if (user != null && UserStatus.TEMP != user.getStatus() && user.isMobileVerified()) {
			response.setExist(true);
		}
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public IsVerifiedMobileExistResponse isMobileExist(IsVerifiedMobileExistRequest request) {
		IsVerifiedMobileExistResponse response = new IsVerifiedMobileExistResponse();

		String mobileNumber = request.getMobileNumber();

		User user = getIMSUserByMobile(mobileNumber);

		if (user != null && user.isMobileVerified()) {
			response.setExist(true);
		}
		// TODO Check In AeroSpike .. Remove Code from Fetching User From DB
		return response;
	}

	protected void loginUserSetActivitData(LoginUserRequest imsRequest) {

		if (StringUtils.isEmpty(imsRequest.getEmailId())
				&& StringUtils.isBlank(imsRequest.getMobileNumber())) {
			// log.error("login user with mobile is not support currently");
			throw new RequestParameterException(
					IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY.errCode(),
					IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY.errMsg());
		}

		// TODO set activity data..How in case of mobile?.
		if (StringUtils.isNotBlank(imsRequest.getEmailId())) {
			activityDataService.setActivityDataByEmailId(imsRequest.getEmailId());
		}

	}

	@Override
	@Timed
	@Marked
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	public ConfigureUserStateResponse configureUserState(ConfigureUserStateRequest imsRequest) {
		User user = getUserToBeConfigured(imsRequest);
		if (user != null) {
			boolean value = user.isEnabled();
			user.setEnabled(imsRequest.isEnable());
			if (value != imsRequest.isEnable()) {
				// Updating enable field of user
				updateUserById(user, user.getUserId());
				// maintaining values in user_history table on successful update
				UserHistory history = new UserHistory();
				history.setUserId(user.getUserId());
				history.setField(UpdatedFeild.ENABLED_DISABLED);
				history.setOldValue(String.valueOf(value));
				history.setNewValue(String.valueOf(imsRequest.isEnable()));
				userDao.maintainUserHistory(history);
				publishUpdateToSNS(history);
			}

		} else {
			throwUserNotExistException();
		}

		// hard signout user.
		if (imsRequest.isEnable() == false) {
			// hard signout user.
			tokenService.signOutUser(user.getUserId());
		}
		ConfigureUserStateResponse response = new ConfigureUserStateResponse();
		response.setStatus(StatusEnum.SUCCESS);
		return response;
	}

	private User getUserToBeConfigured(ConfigureUserStateRequest request) {
		User user = null;
		ConfigureUserBasedOn configureUserBasedOn = request.getConfigureUserBasedOn();
		switch (configureUserBasedOn) {
		case EMAIL:
			if (StringUtils.isNotBlank(request.getEmailId())) {
				user = getIMSUserByEmail(request.getEmailId());
				checkForNullUserAndThrowException(user);
				return user;
			}
		case MOBILE:
			if (StringUtils.isNotBlank(request.getMobileNumber())) {
				user = getIMSUserByMobile(request.getMobileNumber());
				checkForNullUserAndThrowException(user);
				return user;
			}
		case TOKEN:
			if (StringUtils.isNotBlank(request.getToken())) {
				String userId = tokenService.getUserIdByToken(request.getToken());
				user = getIMSUserById(userId);
				checkForNullUserAndThrowException(user);
				return user;
			}
		case USER_ID:
			if (StringUtils.isNotBlank(request.getUserId())) {
				user = getIMSUserById(request.getUserId());
				checkForNullUserAndThrowException(user);
				return user;
			}
		default:
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_CONFIGURE_USER_STATE_BASED_ON.errCode(),
					IMSRequestExceptionCodes.INVALID_CONFIGURE_USER_STATE_BASED_ON.errCode());
		}
	}

	@Override
	@Timed
	@Marked
	public GetUserResponse getUserByMobile(GetUserByMobileRequest request) {
		User user = getIMSUserByMobile(request.getMobileNumber());
		if (user == null || UserStatus.TEMP.equals(user.getStatus())) {
			throwUserNotExistException();

		}
		GetUserResponse response = IMSServiceObjectsMapper.mapUserToGetUserResponse(user);
		return response;

	}

	private User getIMSUserByMobile(String mobileNumber) {
		return getUserByMobileNumberFromCacheOrDao(mobileNumber);
	}

	@Override
	@Timed
	@Marked
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	public UpdateMobileNumberResponse updateMobileNumber(UpdateMobileNumberRequest request) {
		// token, mobileNumber, otp , OtpID,
		UpdateMobileNumberResponse response = new UpdateMobileNumberResponse();
		VerifyOTPServiceRequest verifyOTPRequest = new VerifyOTPServiceRequest();

		verifyOTPRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		verifyOTPRequest.setOtp(request.getOTP());
		verifyOTPRequest.setOtpId(request.getOtpId());
		verifyOTPRequest.setToken(request.getToken());
		VerifyOTPServiceResponse verifyOTPResponse = otpService.verifyOTP(verifyOTPRequest);

		User user = null;
		if (OtpConstants.STATUS_SUCCESS.equalsIgnoreCase(verifyOTPResponse.getStatus())) {
			String userId = tokenService.getUserIdByToken(request.getToken());
			user = getIMSUserById(userId);
			checkForNullUserAndThrowException(user);
			String mobile = user.getMobileNumber();
			if (request.getMobileNumber().equals(verifyOTPResponse.getMobileNumber())
					|| StringUtils.isBlank(request.getMobileNumber())) {
				user.setMobileVerified(true);
				user.setMobileNumber(verifyOTPResponse.getMobileNumber());
			}else{
				throw new IMSServiceException(IMSServiceExceptionCodes.MOBILE_NUMBER_MISMATCH.errCode(), IMSServiceExceptionCodes.MOBILE_NUMBER_MISMATCH.errMsg());

			}

			// invalidate user from cache while update			
			if (mobile != request.getMobileNumber()) {
				// updating user's mobile number
				updateUserByIdFromCacheAndDao(user);
				// maintaining user history on successful update
				UserHistory history = new UserHistory();
				history.setUserId(user.getUserId());
				history.setField(UpdatedFeild.MOBILE_NO);
				history.setNewValue(user.getMobileNumber());
				history.setOldValue(mobile);
				userDao.maintainUserHistory(history);
				publishUpdateToSNS(history);
			}
			UserDetailsDTO dto = IMSServiceObjectsMapper.mapUserToUserDetailsDTO(user);
			response.setUserDetails(dto);
		} else {
			throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errMsg());
		}

		sendEmailHelper(user, ConfigurationConstants.UPDATE_MOBILE_NUMBER_EMAIL_TEMPLATE, false,
				ConfigurationConstants.SEND_EMAIL_UPDATE_MOBILE);

		mojoClientService.createMojoClient(response);
		return response;
	}


	private boolean checkIfUserExistInIMS(String mobileNumber){
		User user = getIMSUserByMobile(mobileNumber);
		if(user!=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	public OTPResponse createUserByMobileOnly(CreateUserWithMobileOnlyRequest request) {
		String mobileNumber = request.getMobileNumber();
		deleteTempUserWithMobile(mobileNumber);
		User user = IMSServiceObjectsMapper.mapUserDetailsRequestDtoToUser(request.getUserDetailsRequestDto(),
				getOriginatingSrc());
		user.setPlatform(request.getPlatform());
		user.setResource(request.getResource());
		GenerateOTPServiceRequest generateOTPServiceRequest = new GenerateOTPServiceRequest();
		user.setPassword(ServiceCommonConstants.DEFAULT_MOBILE_ONLY_PASSWORD);
		user.setMobileNumber(mobileNumber);
		user = createUserWithMobileOnly(user, false);
		generateOTPServiceRequest.setOtpType(OTPPurpose.SIGNUP_WITH_OTP);
		generateOTPServiceRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		generateOTPServiceRequest.setMobileNumber(request.getMobileNumber());
		generateOTPServiceRequest.setUserId(user.getUserId());
		generateOTPServiceRequest.setSendOtpBy(SendOTPByEnum.FREECHARGE);
		generateOTPServiceRequest.setMerchant(getMerchantEnum());
		String displayName = StringUtils.isNotBlank(user.getFirstName()) ? user.getFirstName() : user.getDisplayName();
		generateOTPServiceRequest.setName(StringUtils.capitalize(StringUtils.lowerCase(displayName)));
		OTPServiceResponse otpServiceResponse = otpService.generateOTP(generateOTPServiceRequest);
		OTPResponse otpResponse = new OTPResponse();
		otpResponse.setAccountState(UserStatus.TEMP.getValue());
		otpResponse.setOtpId(otpServiceResponse.getOtpId());
		return otpResponse;

	}
	@Override
	@Timed
	@Marked
	public OTPResponse createUserByMobile(CreateUserMobileGenerateRequest request)  {
		String emailId = request.getUserRequestDto().getEmailId();
		activityDataService.setActivityDataByEmailId(emailId);
		checkBlackListedUserAndThrowException(emailId);

		// this is the case when user logins from UI and given password as plain
		// text
		// we don't need this in case of migration case
		request.getUserRequestDto().setPassword(IMSEncryptionUtil.getSDEncryptedPassword(request.getUserRequestDto().getPassword()));
		User user = IMSServiceObjectsMapper.mapUserRequestDTOToUser(request.getUserRequestDto(),
				getOriginatingSrc());
		user.setPlatform(request.getPlatform());
		user.setResource(request.getResource());
		user = createUserWithMobile(user, false, UserStatus.TEMP, true);

		GenerateOTPServiceRequest generateOTPServiceRequest = new GenerateOTPServiceRequest();
		generateOTPServiceRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		generateOTPServiceRequest.setEmailId(emailId);
		generateOTPServiceRequest.setMobileNumber(request.getUserRequestDto()
				.getMobileNumber());
		generateOTPServiceRequest.setOtpType(OTPPurpose.USER_SIGNUP);
		generateOTPServiceRequest.setUserId(user.getUserId());
		generateOTPServiceRequest.setSendOtpBy(SendOTPByEnum.FREECHARGE);
		generateOTPServiceRequest.setMerchant(getMerchantEnum());
		String displayName = StringUtils.isNotBlank(user.getFirstName()) ? user.getFirstName() : user.getDisplayName() ; 
		generateOTPServiceRequest.setName(StringUtils.capitalize(StringUtils.lowerCase(displayName)));
		OTPServiceResponse otpServiceResponse = otpService
				.generateOTP(generateOTPServiceRequest);

		OTPResponse otpResponse = new OTPResponse();
		otpResponse.setAccountState(UserStatus.UNVERIFIED.getValue());
		otpResponse.setOtpId(otpServiceResponse.getOtpId());
		return otpResponse;

	}



	private void checkBlackListedUserAndThrowException(String emailId) {
		if (randomUpgradeChoiceUtil.isBlackListedUser(emailId)) {
			log.error("Black-listed user : " + emailId);
			throw new IMSServiceException(IMSServiceExceptionCodes.BLACK_LISTED_EMAIL.errCode(),
					IMSServiceExceptionCodes.BLACK_LISTED_EMAIL.errMsg());
		}
	}

	private User createUserWithMobileOnly(User user,boolean setPassword){
		user.setStatus(UserStatus.TEMP);
		user.setUserSetPassword(setPassword);
		user.setMobileOnly(true);
		userDao.create(user);
		return userDao.getUserByMobileNumber(user.getMobileNumber());
	}

	private User createUserWithMobile(User user, boolean checkInIMS, UserStatus status,
			boolean setPassword) {
		// password passed from here should be encrypted by SDEncryptionutils
		/*
		 * if (Boolean.parseBoolean(Configuration.getProperty(
		 * context.get(IMSRequestHeaders.CLIENT_ID.toString()),
		 * ConfigurationConstants.UPGRADE_ENABLED))) {
		 * }else{
		 * log.info(MigrationConstants.SKIP_MIGRATION + user.getEmailId());
		 * }
		 */
		checkUserAlreadyExistsWithMerchant(user.getEmailId(), checkInIMS);
		user.setOriginatingSrc(getOriginatingSrc());
		return createdUserWithMobileWithStatus(status, user, setPassword);
	}

	@Timed
	@Marked
	public User createdUserWithMobileWithStatus(UserStatus status, User user, boolean setPassword) {

		if (user.getStatus() != UserStatus.REGISTERED) {
			user.setStatus(status);
		}
		if (status != null && status != UserStatus.TEMP) {
			if (status == UserStatus.REGISTERED) {
				user.setEmailVerified(true);
			}
			user.setMobileVerified(true);
		}
		user.setUserSetPassword(setPassword);

		// TODO: ... CHeck in Aerospike for Mobile Number Existence. If exist
		// then Throw Mobile Already Exists
		deleteTempUserWithEmailOrMobile(user.getMobileNumber(),user.getEmailId());
		userDao.create(user);
		return user;
	}

	@Override
	public void deleteTempUserWithEmailOrMobile(String mobileNumber,
			String emailId) {
		User existingMobileUser = getIMSUserByMobile(mobileNumber);
		User existingEmailUser = getUserByEmailFromCacheOrDao(emailId);
		if (((existingMobileUser == null) && (existingEmailUser == null))
				|| ((existingMobileUser == null) && (existingEmailUser != null
				&& UserStatus.TEMP.equals(existingEmailUser.getStatus())))
				|| ((existingEmailUser == null) && (existingMobileUser != null
				&& UserStatus.TEMP.equals(existingMobileUser.getStatus())))
				|| ((existingMobileUser != null
				&& UserStatus.TEMP.equals(existingMobileUser.getStatus()))
						&& (existingEmailUser != null
						&& UserStatus.TEMP.equals(existingEmailUser.getStatus())))) {

			if (existingMobileUser != null) {				
				deleteUserByIdFromCacheAndDao(existingMobileUser.getUserId());
				log.info("deleted temp User with mobile :"+mobileNumber);
			}

			if (existingEmailUser != null) {
				deleteUserByIdFromCacheAndDao(existingEmailUser.getUserId());
				log.info("deleted temp User with email :"+emailId);
			}
		} else {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errCode(),
					IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errMsg());
		}
	}
	
	
	private void deleteTempUserWithMobile(String mobileNumber) {
		User existingMobileUser = getIMSUserByMobile(mobileNumber);
		if (existingMobileUser != null
				&& existingMobileUser.getStatus() == UserStatus.TEMP) {
			deleteUserByIdFromCacheAndDao(existingMobileUser.getUserId());
			log.info("deleted temp User with mobile :" + mobileNumber);
		}
		if (existingMobileUser != null
				&& existingMobileUser.getStatus() != UserStatus.TEMP) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errCode(),
					IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errMsg());
		}
	}

	/**
	 * @see com.snapdeal.ims.service.IUMSService#createUserByVerifiedMobile(com.snapdeal.ims.request.CreateUserMobileGenerateRequest)
	 */
	@Override
	@Timed
	@Marked
	public CreateUserResponse createUserByVerifiedMobile(UpgradeDto upgradeDto) {
		User createUserWithMobile = IMSServiceObjectsMapper.mapUpgradeDtoToUser(upgradeDto);

		SdFcPasswordEntity upgradeStatusEntity = sdFcPasswordCacheService
				.getUpgradeStatusEntity(createUserWithMobile.getEmailId());

		if (Merchant.SNAPDEAL.equals(getMerchantEnum())
				&&(upgradeStatusEntity!=null && EmailVerificationSource.EMAIL_VERIFIED_VIA_SNAPDEAL.equals(upgradeStatusEntity
						.getEmailVerificationSource()))) {

			createUserWithMobile.setEmailVerified(true);
			createUserWithMobile = createUserWithMobile(createUserWithMobile, true,
					UserStatus.REGISTERED, true);

		} else {
			createUserWithMobile = createUserWithMobile(createUserWithMobile, true,
					UserStatus.UNVERIFIED, true);
		}

		CreateUserResponse response = IMSServiceObjectsMapper
				.mapUserToCreateUserResponse(createUserWithMobile);

		if (response != null) {
			createWallet(response.getUserDetails().getUserId(), getMerchantEnum());
		}

		TokenInformationDTO tokenDetails = createTokenOnLogin(createUserWithMobile.getUserId(),
				createUserWithMobile.getEmailId());
		response.setTokenInformationDTO(tokenDetails);

		return response;
	}

	@Timed
	@Marked
	public void createWallet(String userId, Merchant merchant) {
		//TODO Karan Creating Wallet
		walletService.createSDWalletTask(userId, merchant.toString(), userId);
	}

	@Timed
	@Marked
	public void createNotificationOnMigrationStateChange(String userId, String email,
			WalletUserMigrationStatus status) {
		if (StringUtils.isBlank(userId) && StringUtils.isBlank(email)) {
			log.error("userId and email can not be null ... unexpected behaviour");
		}
		if (StringUtils.isBlank(userId)) {
			UserDTO userDto = getUserByEmail(email);
			if (userDto == null) {
				log.error("userDto should not be null at this point .. unexpected behaviour");
			} else {
				userId = userDto.getUserId();
			}
		}
		if (StringUtils.isBlank(email)) {
			UserDTO userDto = getUserById(userId);
			if (userDto == null) {
				log.error("userDto should not be null at this point .. unexpected behaviour");
			} else {
				email = userDto.getEmailId();
			}
		}
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(email)) {
			throw new IMSServiceException(ExceptionCodes.GENERIC_INTERNAL_SERVER.errCode(),
					ExceptionCodes.GENERIC_INTERNAL_SERVER.errMsg());
		}
		log.info("notity wallet for state change with user id: " + userId + " email: " + email);
		walletService.createNotificationMigrationStateChangeTask(userId, getMerchant(), email,
				status);
	}

	/**
	 * This method is migration Specific and will return true /false by checking
	 * user on Merchant Db
	 * 
	 * @param emailId
	 * @param checkInIMS
	 */
	private void checkUserAlreadyExistsWithMerchant(String emailId, boolean checkInIMS) {
		IsEmailExistRequest isEmailExistRequest = new IsEmailExistRequest();
		isEmailExistRequest.setEmailId(emailId);
		if (checkInIMS) {
			IsEmailExistResponse isEmailExistResponse = isEmailExist(isEmailExistRequest);
			if (isEmailExistResponse.isExist()) {
				throwUserAlreadyExistException();
			}
		} else if (merchantProvider.getMerchant() != Merchant.ONECHECK) {
			IsEmailExistResponse isEmailExistResponse = serviceProvider.getUMSService()
					.isEmailExist(isEmailExistRequest);
			if (isEmailExistResponse.isExist()) {
				throwUserAlreadyExistException();
			}
		}
	}

	@Override
	@Timed
	@Marked
	@Transactional
	public CreateUserResponse verifyUserWithMobile(CreateUserMobileVerifyRequest request) {

		VerifyOTPServiceRequest verifyOTPRequest = new VerifyOTPServiceRequest();
		verifyOTPRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		verifyOTPRequest.setOtp(request.getOtp());
		verifyOTPRequest.setOtpId(request.getOtpId());

		VerifyOTPServiceResponse verifyOTPResponse = null;
		// to do .. If Condition is temp .. please remove it soon.
		if (Boolean
				.valueOf(Configuration
						.getGlobalProperty(ConfigurationConstants.DUMMY_MIGRATION_ENABLED))
				&& StringUtils.equalsIgnoreCase(request.getOtp(), "12345")) {
			verifyOTPResponse = dummyMigrationService.dummyVerifyOTP(verifyOTPRequest);
			String userId = verifyOTPResponse.getUserId();
			User user = getUserByIdFromCacheOrDao(userId);
			updateUserByIdFromCacheAndDao(user);
			CreateUserResponse response = IMSServiceObjectsMapper.mapUserToCreateUserResponse(user);
			TokenInformationDTO tokenDetails = createTokenOnLogin(user.getUserId(), user.getEmailId());
			response.setTokenInformationDTO(tokenDetails);
			return response;
		} else {
			verifyOTPResponse = otpService.verifyOTP(verifyOTPRequest);
		}

		if (OtpConstants.STATUS_FAILURE.equalsIgnoreCase(verifyOTPResponse.getStatus())) {
			throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errMsg());
		}
		String userId = verifyOTPResponse.getUserId();
		User user = getUserByIdFromCacheOrDao(userId);
		if (user == null) { // To DO ... This Exception will occur If Given User
			// has been deleted By OverRiding Mobile
			// TO Do If User is soft delete then we need to modity
			// if condition and Check for Error code (Perhaps need
			// to create new error code)
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
		// Check if the mobile number is same as the number for which this otp was generated.
		if (user != null && null != user.getMobileNumber()
				&& !user.getMobileNumber().equals(verifyOTPResponse.getMobileNumber())) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errCode(),
					IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errMsg());
		}
		// This is done before as during upgrade status can be registered as well
		user.setMobileVerified(true);
		user.setStatus(UserStatus.UNVERIFIED);
		updateUserByIdFromCacheAndDao(user);
		// Migration work starts

	try {
			UserUpgradeByEmailRequest userUpgradeByEmailRequest = new UserUpgradeByEmailRequest();
			userUpgradeByEmailRequest.setEmailId(user.getEmailId());
			// This is special case of signup so we need to populate migration
			// table with status
			UpgradeStatus upgradeStatus = userMigrationServiceImpl
					.getIMSUserUpgradeStatus(userUpgradeByEmailRequest);
			// call do upgrade to finally upgrade user
			UserUpgradeRequest userUpgradeRequest = populateUpgradeUserRequest(user);
			userUpgradeRequest.setUpgradeSource(UpgradeSource.SIGN_UP);
			userUpgradeRequest.setUpgradeChannel(UpgradeChannel.OTHERS);
			log.info(MigrationConstants.MIGRATION_STARTS + user.getEmailId());
			UpgradeUserResponse upgradeUserResponse = userMigrationServiceImpl.upgradeUser(userUpgradeRequest);
			log.info(MigrationConstants.MIGRATION_ENDS + user.getEmailId()
			+ MigrationConstants.WITH_STATE + upgradeUserResponse.isSuccess());
		} catch (Exception e) {
			// this is the case when migration fails and we have done mobile
			// verified true
			user.setMobileVerified(false);
			user.setStatus(UserStatus.TEMP);
			updateUserByIdFromCacheAndDao(user);
			log.error("Upgrade failed while verifying mobile during user signup, email: "
					+ user.getEmailId(), e);
			throw e;
		}

		user = getUserByIdFromCacheOrDao(userId);
		CreateUserResponse response = IMSServiceObjectsMapper.mapUserToCreateUserResponse(user);
		TokenInformationDTO tokenDetails = createTokenOnLogin(user.getUserId(), user.getEmailId());
		response.setTokenInformationDTO(tokenDetails);
		// Migration work ends

		/* Submit wallet creation task */
		String taskId = user.getUserId();
		//TODO Karan Creating Wallet
		try {
			walletService.createSDWalletTask(user.getUserId(), getOriginatingSrc().getMerchantName(),
					taskId);
		} catch (Exception ex) {
			log.error("Create wallet task failed for : " + user.getUserId() + " + email: "
					+ user.getEmailId());
			throw ex;
		}
		return response;
	}

	private UserUpgradeRequest populateUpgradeUserRequest(User user) {
		UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
		userUpgradeRequest.setEmailId(user.getEmailId());
		userUpgradeRequest.setMobileNumber(user.getMobileNumber());
		userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.EMAIL_PWD_VERIFIED);
		return userUpgradeRequest;
	}

	private User getUserByIdFromCacheOrDao(String userId) {
		User user = userCacheService.getUserById(userId);
		if (user == null) {
			user = userDao.getUserById(userId);
			userCacheService.putUser(user);
		}
		return user;
	}

	private User getUserBySdIdFromCacheOrDao(Integer sdUserId) {
		User user = userCacheService.getUserBySdId(sdUserId);
		if (user == null) {
			user = userDao.getUserBySdId(sdUserId);
			userCacheService.putUser(user);
		}
		return user;
	}

	private User getUserByFcIdFromCacheOrDao(Integer fcUserId) {
		User user = userCacheService.getUserByFcId(fcUserId);
		if (user == null) {
			user = userDao.getUserByFcId(fcUserId);
			userCacheService.putUser(user);
		}
		return user;
	}

	private User getUserBySdFcIdFromCacheOrDao(Integer sdFcUserId) {
		User user = userCacheService.getUserBySdFcId(sdFcUserId);
		if (user == null) {
			user = userDao.getUserBySdFcId(sdFcUserId);
			userCacheService.putUser(user);
		}
		return user;
	}

	private User getUserByEmailFromCacheOrDao(String email) {
		User user = userCacheService.getUserByEmail(email);
		if (user == null) {
			user = userDao.getUserByEmail(email);
			userCacheService.putUser(user);
		}
		return user;
	}

	private User getUserByMobileNumberFromCacheOrDao(String mobileNumber) {
		User user = userCacheService.getUserByMobile(mobileNumber);
		if (user == null) {
			user = userDao.getUserByMobileNumber(mobileNumber);
			userCacheService.putUser(user);
		}
		return user;
	}

	// This method identify the UserId based on it's value and merchant, and
	// returns User record.
	private User getIMSUserById(String userId) {
		User user = null;

		// check is the input is number or varchar
		boolean isNumber = NumberUtils.isNumber(userId);

		if (isNumber) {
			int intUserId = Integer.parseInt(userId);

			// checking ims generated id or migrated id
			if (intUserId >= ServiceCommonConstants.IMS_USERID_AUTO_INCREMENT_START_VALUE) {
				user = getUserBySdFcIdFromCacheOrDao(intUserId);
			} else {
				// Check merchant from which it is migrated
				Merchant userIdMerchant = getOriginatingSrc();
				if (Merchant.SNAPDEAL.equals(userIdMerchant)) {
					user = getUserBySdIdFromCacheOrDao(intUserId);
				} else if (Merchant.FREECHARGE.equals(userIdMerchant)) {
					user = getUserByFcIdFromCacheOrDao(intUserId);
				}
			}
		} else {
			// User id is string i.e. generated by IMS
			user = getUserByIdFromCacheOrDao(userId);
		}
		return user;
	}

	private void updateUserByIdFromCacheAndDao(User user) {
		userDao.updateById(user);
		userCacheService.invalidateUserById(user.getUserId());
	}

	private void updateUserBySdIdFromCacheAndDao(User user) {
		userDao.updateBySdId(user);
		userCacheService.invalidateUserBySdId(user.getSdUserId());
	}

	private void updateUserByFcIdFromCacheAndDao(User user) {
		userDao.updateByFcId(user);
		userCacheService.invalidateUserByFcId(user.getFcUserId());
	}

	private void updateUserBySdFcIdFromCacheAndDao(User user) {
		userDao.updateBySdFcId(user);
		userCacheService.invalidateUserBySdFcId(user.getSdFcUserId());
	}

	// This method identify the UserId based on it's value and merchant, and
	// updates User record.
	private void updateUserById(User user, String userId) {

		// check is the input is number or varchar
		boolean isNumber = NumberUtils.isNumber(userId);

		if (isNumber) {
			int intUserId = Integer.parseInt(userId);

			// checking ims generated id or migrated id
			if (intUserId >= ServiceCommonConstants.IMS_USERID_AUTO_INCREMENT_START_VALUE) {
				user.setSdFcUserId(intUserId);
				updateUserBySdFcIdFromCacheAndDao(user);
			} else {
				// Check merchant from which it is migrated
				Merchant userIdMerchant = getOriginatingSrc();
				if (Merchant.SNAPDEAL.equals(userIdMerchant)) {
					user.setSdUserId(intUserId);
					updateUserBySdIdFromCacheAndDao(user);
				} else if (Merchant.FREECHARGE.equals(userIdMerchant)) {
					user.setFcUserId(intUserId);
					updateUserByFcIdFromCacheAndDao(user);
				}
			}
		} else {
			// User id is string i.e. generated by IMS
			user.setUserId(userId);
			updateUserByIdFromCacheAndDao(user);
		}
	}

	@Override
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	protected boolean changePasswordCommon(ChangePasswordRequest request, String userId) {
		boolean response;

		User user = getIMSUserById(userId);
		checkForNullUserAndThrowException(user);
		if (!user.getPassword()
				.equals(IMSEncryptionUtil.getSDEncryptedPassword((request.getOldPassword())))) {
			throw new IMSServiceException(IMSServiceExceptionCodes.WRONG_PASSWORD.errCode(),
					IMSServiceExceptionCodes.WRONG_PASSWORD.errMsg());
		}
		String OldPassword=user.getPassword();
		if(OldPassword!=request.getNewPassword())
		{
			user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(request.getNewPassword()));

			updateUserById(user, user.getUserId());
			UserHistory history = new UserHistory();
			history.setUserId(user.getUserId());
			history.setField(UpdatedFeild.PASSWORD);
			history.setOldValue(OldPassword);
			history.setNewValue(user.getPassword());
			userDao.maintainUserHistory(history);
			publishUpdateToSNS(history);

		}
		response = true;
		return response;
	}

	protected Map<String,String> getUserId(ForgotPasswordRequest request){
		Map<String,String> userDetails = new HashMap<String, String>() ;
		if (StringUtils.isNotBlank(request.getMobileNumber()) && 
				StringUtils.isNotBlank(request.getEmailId())) {
			User emailUser = getIMSUserByEmail(request.getEmailId());
			User mobileUser = getIMSUserByMobile(request.getMobileNumber());
			if (emailUser != null
					&& mobileUser != null
					&& emailUser.getUserId().equalsIgnoreCase(
							mobileUser.getUserId())) {
				userDetails.put(ServiceCommonConstants.USERID, emailUser.getUserId());
				userDetails.put(ServiceCommonConstants.NAME, emailUser.getFirstName());
				userDetails.put(ServiceCommonConstants.EMAIL_TAG, emailUser.getEmailId());
				return userDetails;
			} else {
				throw new RequestParameterException(
						IMSServiceExceptionCodes.MISMATCH_EMAIL_ID_MOBILE.errCode(),
						IMSServiceExceptionCodes.MISMATCH_EMAIL_ID_MOBILE.errMsg());
			}
		}  else if (StringUtils.isNotBlank(request.getEmailId())) {
			User user = getIMSUserByEmail(request.getEmailId());
			checkForNullUserAndThrowException(user);
			userDetails.put(ServiceCommonConstants.USERID, user.getUserId());
			userDetails.put(ServiceCommonConstants.NAME, user.getFirstName());
			userDetails.put(ServiceCommonConstants.EMAIL_TAG, user.getEmailId());
			return userDetails;

		} else if (StringUtils.isNotBlank(request.getMobileNumber())) {

			User user = getIMSUserByMobile(request.getMobileNumber());
			checkForNullUserAndThrowException(user);
			userDetails.put(ServiceCommonConstants.USERID, user.getUserId());
			userDetails.put(ServiceCommonConstants.NAME, user.getFirstName());
			userDetails.put(ServiceCommonConstants.EMAIL_TAG, user.getEmailId());
			return userDetails;

		} else {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY
					.errCode(),
					IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY
					.errMsg());
		}
	}

	@Override
	@Timed
	@Marked
	public boolean resetPasswordHelper(ResetPasswordRequest request) {
		String userId = request.getUserId();
		User user = getIMSUserById(userId);
		checkForNullUserAndThrowException(user);
		String newPassword = IMSEncryptionUtil.getSDEncryptedPassword(request.getNewPassword());
		user.setPassword(newPassword);
		updateUserById(user, userId);
		//saving history
		String oldPassword=user.getPassword();
		user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(request.getNewPassword()));
		updateUserById(user, user.getUserId());
		UserHistory history = new UserHistory();
		history.setUserId(user.getUserId());
		history.setField(UpdatedFeild.PASSWORD);
		history.setOldValue(oldPassword);
		history.setNewValue(user.getPassword());
		userDao.maintainUserHistory(history);		

		sendEmailHelper(user, ConfigurationConstants.FORGOT_PASSWORD_SUCCESS_EMAIL_TEMPLATE, false,
				ConfigurationConstants.SEND_EMAIL_FORGOT_PASSWORD_SUCCESS);
		return true;
	}

	private void sendEmailHelper(User user,
			ConfigurationConstants templateConf, boolean blocking, ConfigurationConstants subjectKey) {
		Map<String, String> tags = new HashMap<String, String>();
		String displayName = StringUtils.isNotBlank(user.getFirstName()) ? user.getFirstName() : user.getDisplayName() ; 
		if(StringUtils.isBlank(displayName)){
			displayName = " ";
		}
		tags.put(
				com.snapdeal.ims.constants.ServiceCommonConstants.CUSTOMER_TAG,
				StringUtils.capitalize(StringUtils.lowerCase(displayName)));
		tags.put(
				com.snapdeal.ims.constants.ServiceCommonConstants.MOBILE_TAG,
				user.getMobileNumber());

		String name = displayName;
		String templateKey = getTemplateKey(templateConf);
		sendEmail(user.getEmailId(), name, tags, user.getUserId(), templateKey,
				blocking, subjectKey);
	}

	@Override
	@Timed
	@Marked
	public boolean resetDefaultSocialPasswordHelper(String emailId, String password) {
		User user = getIMSUserByEmail(emailId);
		checkForNullUserAndThrowException(user);
		user.setPassword(password);
		user.setEmailVerified(true);
		if (user.isMobileVerified()) {
			user.setStatus(UserStatus.REGISTERED);
		}
		updateUserById(user, user.getUserId());
		return true;
	}

	@Override
	protected UserDTO validateUserCredential(LoginUserRequest imsRequest){
		User user = null;

		if (StringUtils.isNotBlank(imsRequest.getEmailId())) {
			user = getIMSUserByEmail(imsRequest.getEmailId());
			checkForNullUserAndThrowException(user);
		} else {
			user = getIMSUserByMobile(imsRequest.getMobileNumber());
			checkForNullUserAndThrowException(user);
		}
		String userId = String.valueOf(user.getUserId());

		loginUserService.isUserLocked(userId);
		// TODO: use FC encodeingPassword mechanism here to match the password.
		if (StringUtils.equals(IMSEncryptionUtil.getSDEncryptedPassword((imsRequest.getPassword())),
				user.getPassword())) {
			// Delete user lock info from db if user enters correct password.
			loginUserService.deleteUserLockInfo(userId);
		} else {
			// Update db for attempts and lock user when maximum attempts done.
			loginUserService.updateUserLockInfo(userId);
			if (log.isWarnEnabled()) {
				log.warn(IMSValidationExceptionCodes.PASSWORD_DO_NOT_MATCH.errMsg());
			}
			throw new ValidationException(IMSValidationExceptionCodes.PASSWORD_DO_NOT_MATCH.errCode(),
					IMSValidationExceptionCodes.PASSWORD_DO_NOT_MATCH.errMsg());
		}
		return IMSServiceObjectsMapper.mapUserToUserDTO(user);
	}

	@Override
	@Timed
	@Marked
	public SdFcPasswordEntity putSdFcHashedPasswordByEmailId(String emailId, String password) {
		SdFcPasswordEntity entity = serviceProvider.getUMSService()
				.putSdFcHashedPasswordByEmailId(emailId, password);
		return entity;
	}

	@Override
	protected UserDTO getUserById(String userId) {
		User user = getIMSUserById(userId);
		checkForNullUserAndThrowException(user);
		return IMSServiceObjectsMapper.mapUserToUserDTO(user);
	}

	@Transactional("transactionManager")
	private void createSocialUserTranscation(SocialUser socialUser) {
		try {
			// userDao.create(socialUser.getUser());
			socialUserDao.createSocialUser(socialUser);
		} catch (DuplicateKeyException e) {
			throw new IMSServiceException(IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errCode(),
					IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errMsg());
		}
	}

	@Override
	@Timed
	@Marked
	public UserDTO getUserByEmail(String emailId) {
		User user = getIMSUserByEmail(emailId);
		checkForNullUserAndThrowException(user);
		return IMSServiceObjectsMapper.mapUserToUserDTO(user);
	}

	// TODO: It looks wrong... need to verify it as originating source should be
	// From which Merchant User Came into OneCheck.
	private Merchant getOriginatingSrc() {
		String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
		Merchant merchant = ClientConfiguration.getMerchantById(clientId);
		return merchant;
	}

	// API is Used for VerifyGuestUser and VerifyUserAndResetPassword
	@Override
	protected boolean registerAndUpdatePassword(String userId, String password,
			boolean hardRegister){ // get user corresponding
		// to the code
		User user = getIMSUserById(userId);
		checkForNullUserAndThrowException(user);
		if (StringUtils.isNotBlank(password)) {
			user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword((password)));
		}
		// Add Registered Role to user role.
		if (!user.isEmailVerified()) {
			user.setEmailVerified(true);
			user.setStatus(UserStatus.REGISTERED);
		} else if (hardRegister) {
			// If user already verified, then throw exception only if
			// hardRegister
			// is true.
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_ALREADY_VERIFIED.errCode(),
					IMSServiceExceptionCodes.USER_ALREADY_VERIFIED.errMsg());
		}

		// invalidate user from cache and update in dao
		updateUserByIdFromCacheAndDao(user);
		//update migration status in db
		UpgradeStatus upgradeStatus = migration.getIMSMigrationStatus(user.getEmailId());
		if (upgradeStatus != null && upgradeStatus.getUpgradeStatus() == Upgrade.UPGRADE_COMPLETED) {
			upgradeStatus.setUpdatedDate(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			migrationDao.updateUpgradationStatusWithEmailVerifiedCount(upgradeStatus);
		}
		return true;
	}

	private void deleteUserByIdFromCacheAndDao(String userId) {
		userDao.delete(userId);
		userCacheService.invalidateUserById(userId);
	}

	private User getIMSUserByEmail(String emailId) {
		User user = getUserByEmailFromCacheOrDao(emailId);
		return user;
	}

	private void checkForNullUserAndThrowException(User user) {
		if (user == null || UserStatus.TEMP.equals(user.getStatus())) {
			throwUserNotExistException();
		}
	}

	@Override
	@Timed
	@Marked
	public String getPasswordByEmail(String emailId) {
		User user = getIMSUserByEmail(emailId);
		checkForNullUserAndThrowException(user);
		return user.getPassword();
	}

	@Override
	public CreateUserResponse createUserByEmailAndMobile(CreateUserEmailMobileRequest request) {
		throw new IMSServiceException(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	/**
	 * This function will do the followings
	 * Step0:
	 * Request Validation:
	 * a. Checks valid emailId
	 * b. Checks valid mobile
	 * c. Checks valid social source
	 * Step1:
	 * Create user with state 'TEMP' and password
	 * as ServiceCommonConstants.DEFAULT_SOCIAL_USER_PASSWORD
	 * -Checks if mobile already exists in one check - If Yes, throws exception
	 * -Checks if email already exist in one check - If Yes, throws exception
	 * Step2:
	 * Create social user transaction
	 * Step3:
	 * Generate and send OTP with OTP Purpose 'ONECHECK_SOCIAL_USER_SIGNUP'
	 * Step4:
	 * Return the response
	 * CreateSocialUserWithMobileResponse{otpId, UserStatus as 'TEMP'}
	 */
	@Override
	@Timed
	@Marked
	public CreateSocialUserWithMobileResponse createSocialUserWithMobile(
			CreateSocialUserWithMobileRequest request) {

		/* Step0: Validate request */
		validateSocialUserRequest(request);

		/*-----Create user with state 'TEMP'-----*/
		/* Step1a. mapping user from SocialUserDTO */
		final User user = IMSServiceObjectsMapper
				.mapCreateSocialUserRequestToUser(request.getSocialUserDto(), getOriginatingSrc());
		user.setPlatform(request.getPlatform());
		user.setResource(request.getResource());
		/* Step1b. Checking under age, if yes: put null */
		Date dateOfBirth = IMSServiceUtil
				.getAndValidateDOBForSocialUser(request.getSocialUserDto().getDob());
		user.setDob(dateOfBirth);

		/* Step1c. validating and getting socialSource */
		SocialSource socialSource = validateAndGetSocialSource(
				request.getSocialUserDto().getSocialSrc());

		/* Step1d. Setting type of social source */
		if (socialSource.equals(SocialSource.FACEBOOK))
			user.setFacebookUser(true);
		else
			user.setGoogleUser(true);

		/* Step1f. Setting default password for social user */
		user.setPassword(ServiceCommonConstants.DEFAULT_SOCIAL_USER_PASSWORD);
		// setting email verified to true
		user.setEmailVerified(true);
		createUserWithMobile(user, true, UserStatus.TEMP, false);

		/* Step2. Create social user transaction */
		final SocialUser socialUser = IMSServiceObjectsMapper
				.mapCreateSocialUserRequestToSocialUser(request.getSocialUserDto());
		socialUser.setUser(user);
		socialUser.setMerchant(getOriginatingSrc());
		socialUser.setUserId(user.getUserId());
		socialUser.setSocialSrc(socialSource);
		createSocialUserTranscation(socialUser);

		/* Step3. Generate and send OTP */
		final GenerateOTPServiceRequest generateOTPServiceRequest = new GenerateOTPServiceRequest();
		generateOTPServiceRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		generateOTPServiceRequest.setEmailId(request.getSocialUserDto().getEmailId());
		generateOTPServiceRequest.setMobileNumber(request.getSocialUserDto().getMobileNumber());
		generateOTPServiceRequest.setOtpType(OTPPurpose.ONECHECK_SOCIAL_SIGNUP);
		generateOTPServiceRequest.setUserId(user.getUserId());
		generateOTPServiceRequest.setSendOtpBy(SendOTPByEnum.FREECHARGE);
		generateOTPServiceRequest.setMerchant(getMerchantEnum());
		String displayName = StringUtils.isNotBlank(user.getFirstName()) ? user.getFirstName() : user.getDisplayName() ; 
		generateOTPServiceRequest.setName(StringUtils.capitalize(StringUtils.lowerCase(displayName)));
		final OTPServiceResponse otpServiceResponse = otpService
				.generateOTP(generateOTPServiceRequest);

		/* Step4. Return the response */
		final CreateSocialUserWithMobileResponse createSocialUserWithMobileResponse = new CreateSocialUserWithMobileResponse();
		createSocialUserWithMobileResponse.setAccountStatus(UserStatus.UNVERIFIED.getValue());
		createSocialUserWithMobileResponse.setOtpId(otpServiceResponse.getOtpId());
		return createSocialUserWithMobileResponse;
	}

	private void validateSocialUserRequest(CreateSocialUserWithMobileRequest request) {

		/* Step0 a. Validate emailId */
		IMSServiceUtil.validateEmail(request.getSocialUserDto().getEmailId());

		/*
		 * Step0 b. Validate mobileNumber: Only blank check, valid check is
		 * already applied at controller end
		 */
		if (StringUtils.isBlank(request.getSocialUserDto().getMobileNumber())) {
			throw new IMSServiceException(IMSServiceExceptionCodes.MOBILE_NUMBER_IS_BLANK.errCode(),
					IMSServiceExceptionCodes.MOBILE_NUMBER_IS_BLANK.errMsg());
		}

		/* Step0 c. Validate socialSrc */
		if (StringUtils.isBlank(request.getSocialUserDto().getSocialSrc())) {
			throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errCode(),
					IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errMsg());
		}

		/* Step0 d. Social Id shouldn't be null */
		if (StringUtils.isBlank(request.getSocialUserDto().getSocialId())) {
			throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_SOCIAL_ID.errCode(),
					IMSServiceExceptionCodes.INVALID_SOCIAL_ID.errMsg());
		}
	}

	private SocialSource validateAndGetSocialSource(String socialSrcStr) {

		try {

			SocialSource socialSource = SocialSource.forName(socialSrcStr);
			return socialSource;
		} catch (IllegalArgumentException e) {
			throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errCode(),
					IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errMsg());
		}
	}

	/**
	 * This API will verify oneCheck social mobile user
	 * It will do followings
	 * a. Get userId using OTP
	 * b. Get the scoialVerifiedType using userId
	 * - Fetch Social User info from IMS DB
	 * - Get the required details
	 * c. if migration is enabled
	 * call doMigration() with SIGN_IN
	 * else
	 * update user status email/mobile: verified, account status: REGISTERED
	 * d. Generate token and attach with response
	 */
	@Override
	@Timed
	@Marked
	public CreateUserResponse verifySocialUserWithMobile(CreateUserMobileVerifyRequest request) {

		/* Step1: Fetch OTP Info */
		FetchLatestOTPRequest fetchLatestOTPRequest = new FetchLatestOTPRequest();
		fetchLatestOTPRequest.setOtpId(request.getOtpId());
		fetchLatestOTPRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));

		UserOTPEntity userOTPEntity = otpService.getOTPInfo(fetchLatestOTPRequest);

		/* Step2a: Fetch social user info */
		User ocUser = getUserByIdFromCacheOrDao(userOTPEntity.getUserId());
		if (ocUser == null) {

			/**
			 * We shouldn't be here, It is un-expected behavior.
			 * user must be created in TEMP state before hitting verifyAPI
			 * with SIGN_IN upgradeStatus from oneCheck UI for social user
			 * verification using mobile OTP
			 */
			IMSServiceException e = new IMSServiceException(
					IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
			log.error("Un-expected error occured during social user " + "verification, OTPId: "
					+ request.getOtpId(), e);
			throw e;
		}

		/* Step2b. Throw exception if not a social user */
		if (!ocUser.isFacebookUser() && !ocUser.isGoogleUser()) {
			/**
			 * We shouldn't be here, It is un-expected behavior.
			 * user must be social and must be created in TEMP state
			 */
			IMSServiceException e = new IMSServiceException(
					IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errCode(),
					IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errMsg());
			log.error("Un-expected error occured during social user "
					+ "verification...expecting social user, emailId: " + ocUser.getEmailId(), e);
			throw e;
		}

		/* Step2c. Return if user is already verified */
		if (ocUser.getStatus().equals(UserStatus.REGISTERED) && ocUser.isEmailVerified()
				&& ocUser.isMobileVerified()) {

			log.info("User already verified, userId: " + ocUser.getUserId());
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_ALREADY_VERIFIED.errCode(),
					IMSServiceExceptionCodes.USER_ALREADY_VERIFIED.errMsg());
		}

		/* Check if migration is enabled */
		if (Boolean.parseBoolean(
				Configuration.getProperty(context.get(IMSRequestHeaders.CLIENT_ID.toString()),
						ConfigurationConstants.UPGRADE_ENABLED))) {

			/* Step3: Call doUpgrade to verify and upgrade user info */
			try {
				final UserUpgradeRequest userUpgradeRequest = new UserUpgradeRequest();
				userUpgradeRequest.setOtp(request.getOtp());
				userUpgradeRequest.setOtpId(request.getOtpId());
				userUpgradeRequest.setEmailId(ocUser.getEmailId());
				userUpgradeRequest.setMobileNumber(ocUser.getMobileNumber());
				if (ocUser.isFacebookUser()) {
					userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.FACEBOOK_VERIFIED);
				} else {
					userUpgradeRequest.setVerifiedType(UserIdentityVerifiedThrough.GOOGLE_VERIFIED);
				}
				userUpgradeRequest.setUpgradeChannel(UpgradeChannel.OTHERS);
				userUpgradeRequest.setUpgradeSource(UpgradeSource.ONECHECK_SOCIAL_SIGNUP);

				log.info(MigrationConstants.MIGRATION_STARTS + ocUser.getEmailId());

				final UpgradeUserResponse upgradeUserResponse = migration.doUpgrade(userUpgradeRequest);

				log.info(MigrationConstants.MIGRATION_ENDS + ocUser.getEmailId()
				+ MigrationConstants.WITH_STATE + upgradeUserResponse.isSuccess());

				if (upgradeUserResponse == null
						|| (upgradeUserResponse != null && !upgradeUserResponse.isSuccess())) {

					log.error("Upgrade is unsuccessfull for userId: " + ocUser.getEmailId());
					throw new IMSServiceException(IMSServiceExceptionCodes.REGISTRATION_FAILED.errCode(),
							IMSServiceExceptionCodes.REGISTRATION_FAILED.errMsg());
				}
			} catch (IMSMigrationHardDeclinedException e) {
				log.error(e.getErrMsg());
				throw e;
			}
		} else {
			VerifyOTPServiceRequest verifyOTPRequest = new VerifyOTPServiceRequest();
			verifyOTPRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
			verifyOTPRequest.setOtp(request.getOtp());
			verifyOTPRequest.setOtpId(request.getOtpId());
			// verifyOTP and update status
			VerifyOTPServiceResponse verifyOTPResponse = otpService.verifyOTP(verifyOTPRequest);
			if (OtpConstants.STATUS_FAILURE.equalsIgnoreCase(verifyOTPResponse.getStatus())) {
				throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errCode(),
						IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errMsg());
			}
			String userId = verifyOTPResponse.getUserId();
			User user = getUserByIdFromCacheOrDao(userId);
			if (user == null) { // To DO ... This Exception will occur If Given
				// User
				// has been deleted By OverRiding Mobile
				// TO Do If User is soft delete then we need to
				// modity
				// if condition and Check for Error code (Perhaps
				// need
				// to create new error code)
				throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
			}
			user.setMobileVerified(true);
			user.setStatus(UserStatus.REGISTERED);
			updateUserByIdFromCacheAndDao(user);
			log.info(MigrationConstants.SKIP_MIGRATION + ocUser.getEmailId());
		}

		/* Step4. Get and set token details in response */
		final TokenInformationDTO tokenDetails = createTokenOnLogin(ocUser.getUserId(),
				ocUser.getEmailId());
		CreateUserResponse response = IMSServiceObjectsMapper
				.mapUserToCreateUserResponse(getUserByIdFromCacheOrDao(ocUser.getUserId()));
		response.setTokenInformationDTO(tokenDetails);

		/* Step5. Submit wallet creation task */
		String taskId = ocUser.getUserId();
		//TODO Karan Creating Wallet
		walletService.createSDWalletTask(ocUser.getUserId(), getOriginatingSrc().name(), taskId);
		return response; 
	}

	@Override
	void setNotifier(GenerateOTPServiceRequest request) {
		request.setSendOtpBy(SendOTPByEnum.FREECHARGE);
	}

	@Override
	void setNotifier(EmailMessage request) {
		request.setEmailSendBy(SendOTPByEnum.FREECHARGE.toString());
	}

	@Override
	String getNotifier() {
		return SendOTPByEnum.FREECHARGE.toString();
	}

	@Override
	public GetIMSUserVerificationUrlResponse getIMSUserVerificationUrl(
			GetIMSUserVerificationUrlRequest request, Upgrade upgradeStatus, String userId) {

		String verificationCode = null;
		if (Boolean.parseBoolean(Configuration
				.getGlobalProperty(ConfigurationConstants.VERIFY_HAPPY_FLOW_TESTING_MODE))
				&& (request.getPurpose().equals(VerificationPurpose.VERIFY_NEW_USER) && (userId != null))) {
			verificationCode = "V01#" + request.getEmail();

		} else {
			verificationCode = VerificationCodeGeneratorUtil.getVerificationCode();
		}
		String expiryTime = Configuration
				.getGlobalProperty(ConfigurationConstants.VERIFICATION_CODE_EXPIRY_UPGRADE_FLOW);

		int expiryTimeInMinutes = Integer.valueOf(Configuration
				.getGlobalProperty(ConfigurationConstants.DEFAULT_VERIFICATION_CODE_EXPIRY));
		if (expiryTime != null && StringUtils.isNumeric(expiryTime)) {
			expiryTimeInMinutes = Integer.valueOf(expiryTime);
		}
		UserVerification userVerificationEntity = new UserVerification();
		userVerificationEntity.setCode(verificationCode);
		userVerificationEntity.setPurpose(request.getPurpose());
		userVerificationEntity.setEmailId(request.getEmail());
		userVerificationEntity.setMerchant(request.getMerchant());
		if (userId != null) {
			userVerificationEntity.setUserId(userId);
		}else if (request.getEmail()!=null){
			User user = getUserByEmailFromCacheOrDao(request.getEmail());
			if(user!=null){
				userVerificationEntity.setUserId(user.getUserId());
			}			
		}

		saveUserUpgradeVerificationCode(userVerificationEntity, expiryTimeInMinutes);
		String encryptedVerificationCode = null;
		try {
			encryptedVerificationCode = CipherServiceUtil.encrypt(verificationCode);
		} catch (CipherException e) {
			log.error("Exception on encrypting verification code.");
			throw new IMSServiceException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}

		// based on verification purpose new User and parking. if user is upgrade
		// completed
		/**
		 * add new parameter to service in case of new User
		 */

		// static link thank yu page
		String link = null;
		if (request.getPurpose().equals(VerificationPurpose.VERIFY_NEW_USER)
				&& upgradeStatus == Upgrade.UPGRADE_COMPLETED) {
			link = Configuration
					.getGlobalProperty(ConfigurationConstants.VERIFICATION_FLOW_COMPLETED_LINK);

			// or link sd account
		} else if ((request.getPurpose().equals(VerificationPurpose.VERIFY_NEW_USER)
				&& upgradeStatus == Upgrade.LINK_FC_ACCOUNT)
				|| (request.getPurpose().equals(VerificationPurpose.VERIFY_NEW_USER)
						&& upgradeStatus == Upgrade.LINK_SD_ACCOUNT)) {
			link = Configuration.getGlobalProperty(
					ConfigurationConstants.VERIFICATION_FLOW_ONE_SIDE_MIGRATED_LINK);

		} else if (request.getPurpose().equals(VerificationPurpose.PARKING_INTO_WALLET)) {

			link = Configuration
					.getGlobalProperty(ConfigurationConstants.VERIFICATION_FLOW_PARKING_LINK);
		} else {
			throw new IMSServiceException(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		}

		GetIMSUserVerificationUrlResponse response = new GetIMSUserVerificationUrlResponse();
		Map<String, String> urlParameters = new HashMap<>();
		urlParameters.put(ConfigurationConstants.EnumConstants.VERIFICATION_FLOW_CODE_PLACEHOLDER,
				encryptedVerificationCode);
		urlParameters.put(ConfigurationConstants.EnumConstants.VERIFICATION_FLOW_EMAIL_PLACEHOLDER,
				request.getEmail());
		response.setUrl(StrSubstitutor.replace(link, urlParameters));
		return response;
	}

	@Override
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	public CloseAccountResponse closeAccount(CloseAccountByEmailRequest request) {
		activityDataService.setActivityDataByEmailId(request.getEmailId());
		User user = getUserByEmailFromCacheOrDao(request.getEmailId());
		if (user == null) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}

		walletBalance.checkWalletBalance(user);
		userCacheService.invalidateUserById(user.getUserId());
		userCacheService.invalidateEmailIdByUserId(request.getEmailId());
		userDao.archiveUser(request.getEmailId());
		userDao.deleteUpgradedUserStatus(request.getEmailId());
		userDao.deleteUser(request.getEmailId());
		globalTokenDao.deleteAllTokenForUser(user.getUserId());

		UserHistory history = new UserHistory();
		history.setUserId(user.getUserId());
		history.setField(UpdatedFeild.IS_DELETED);
		history.setOldValue(user.getStatus().getValue());
		history.setNewValue("DELETED");
		userDao.maintainUserHistory(history);

		CloseAccountResponse response = new CloseAccountResponse();
		response.setStatus(true);
		return response;
	}

	@Override
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	public CreateUserResponse verifyUserWithMobileOnly(CreateUserMobileVerifyRequest request) {
		VerifyOTPServiceRequest verifyOTPRequest = new VerifyOTPServiceRequest();
		verifyOTPRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		verifyOTPRequest.setOtp(request.getOtp());
		verifyOTPRequest.setOtpId(request.getOtpId());

		FetchLatestOTPRequest fetchLatestOTPRequest = new FetchLatestOTPRequest();
		fetchLatestOTPRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		fetchLatestOTPRequest.setOtpId(request.getOtpId());

		Optional<UserOTPEntity> currentOtpInfo = persistenceManager
				.getOTPFromId(fetchLatestOTPRequest);
		if(!currentOtpInfo.isPresent()){
			throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_OTP_ID.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ID.errMsg());
		}
		String emailId= currentOtpInfo.get().getEmail();
		OTPPurpose otpPurpose = OTPPurpose.valueOf(currentOtpInfo.get().getOtpType());
		CreateUserResponse response = null;
		VerifyOTPServiceResponse verifyOTPResponse = otpService.verifyOTP(verifyOTPRequest);

		if (OtpConstants.STATUS_FAILURE.equalsIgnoreCase(verifyOTPResponse.getStatus())) {
			throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errMsg());
		}
		switch (otpPurpose) {
		case LOGIN_WITH_EMAIL_OTP:
			response = loginWithEmailOTP(verifyOTPResponse, emailId);
			break;
		case LOGIN_WITH_MOBILE_OTP:
			response = loginWithMobileOTP(verifyOTPResponse);
			break;
		case SIGNUP_WITH_OTP:
			response = signUpWithOTP(verifyOTPResponse);
			break;
		}
		return response;
	}

	private CreateUserResponse loginWithMobileOTP(VerifyOTPServiceResponse verifyOTPResponse){
		String userId = verifyOTPResponse.getUserId();
		User user = getUserByIdFromCacheOrDao(userId);
		checkForNullUserAndThrowException(user);
		if (user != null && null != user.getMobileNumber()
				&& !user.getMobileNumber().equals(verifyOTPResponse.getMobileNumber())) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errCode(),
					IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errMsg());
		}
		if (!user.isEnabled()) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
					IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
		}
		TokenInformationDTO tokenDetails = createTokenOnLogin(user.getUserId(), user.getEmailId());
		CreateUserResponse response = IMSServiceObjectsMapper.mapUserToCreateUserResponse(user);
		response.setTokenInformationDTO(tokenDetails);
		return response;
	}

	private CreateUserResponse loginWithEmailOTP(VerifyOTPServiceResponse verifyOTPResponse,String email){
		String userId = verifyOTPResponse.getUserId();
		User user = getUserByIdFromCacheOrDao(userId);
		checkForNullUserAndThrowException(user);
		if (user != null && null != user.getEmailId()
				&& !user.getEmailId().equals(email)) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.WRONG_EMAIL_ID.errCode(),
					IMSServiceExceptionCodes.WRONG_EMAIL_ID.errMsg());
		}
		if (!user.isEnabled()) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
					IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
		}
		TokenInformationDTO tokenDetails = createTokenOnLogin(user.getUserId(), user.getEmailId());
		CreateUserResponse response = IMSServiceObjectsMapper.mapUserToCreateUserResponse(user);
		response.setTokenInformationDTO(tokenDetails);
		return response;

	}


	private CreateUserResponse signUpWithOTP(VerifyOTPServiceResponse verifyOTPResponse){
		if(merchantProvider.getMerchant() != Merchant.ONECHECK){
			throw new IMSServiceException(
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		}
		String userId = verifyOTPResponse.getUserId();
		User user = getUserByIdFromCacheOrDao(userId);
		if (user == null) { 
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
		if(!user.isMobileOnly()){
			throw new IMSServiceException(IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errCode()
					,IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errMsg());
		}

		if(user.isMobileVerified()){
			throw new IMSServiceException(IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXISTS_MOBILEONLY.errCode()
					, IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXISTS_MOBILEONLY.errMsg());
		}
		if (user != null && null != user.getMobileNumber()
				&& !user.getMobileNumber().equals(verifyOTPResponse.getMobileNumber())) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errCode(),
					IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errMsg());
		}
		if (!user.isEnabled()) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
					IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
		}
		user.setMobileVerified(true);
		user.setStatus(UserStatus.UNVERIFIED);
		updateUserByIdFromCacheAndDao(user);

		user = getUserByIdFromCacheOrDao(userId);

		TokenInformationDTO tokenDetails = createTokenOnLogin(user.getUserId(), user.getEmailId());
		CreateUserResponse response = IMSServiceObjectsMapper.mapUserToCreateUserResponse(user);
		response.setTokenInformationDTO(tokenDetails);
		String taskId = user.getUserId();
		try {
			walletService.createSDWalletTask(user.getUserId(), getOriginatingSrc().getMerchantName(),
					taskId);
		} catch (Exception ex) {
			log.error("Create wallet task failed for : " + user.getUserId() + " + email: "
					+ user.getEmailId());
			throw ex;
		}
		return response;

	}

	@Override
	public MobileOnlyResponse isMobileOnly(MobileOnlyRequest mobileOnlyRequest) {
		User user = getUserByMobileNumberFromCacheOrDao(mobileOnlyRequest.getMobileNumber());
		MobileOnlyResponse mobileOnlyResponse = new MobileOnlyResponse();
		if(user==null || user.getStatus().equals(UserStatus.TEMP) || user.getStatus().equals(UserStatus.GUEST)){
			mobileOnlyResponse.setMobileOnly(false);
			mobileOnlyResponse.setStatus(false);
		}else{
			if(user.getStatus().equals(UserStatus.REGISTERED) || user.getStatus().equals(UserStatus.UNVERIFIED) ){
				mobileOnlyResponse.setStatus(true);
				if(user.isMobileOnly()){
					mobileOnlyResponse.setMobileOnly(true);
				}else{
					mobileOnlyResponse.setMobileOnly(false);
				}
			}
		}
		return mobileOnlyResponse;
	}


	@Override
	@Timed
	@Marked
	public User updateUserStatus(String emailId) {
		User user = getIMSUserByEmail(emailId);
		checkForNullUserAndThrowException(user);
		user.setEmailVerified(true);
		if (user.isMobileVerified()) {
			user.setStatus(UserStatus.REGISTERED);
		}
		updateUserById(user, user.getUserId());
		return user;
	}
}
