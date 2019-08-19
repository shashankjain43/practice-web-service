package com.snapdeal.ims.service.impl;

import com.amazonaws.services.sns.model.InternalErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.MigrationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.dto.SNSNotificationDTO;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.dto.UserSocialDetailsDTO;
import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.entity.UserHistory;
import com.snapdeal.ims.enums.Action;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.LinkUserVerifiedThrough;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.OTPRequestChannel;
import com.snapdeal.ims.enums.Skip;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSValidationExceptionCodes;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.migration.dto.UpgradeDto;
import com.snapdeal.ims.migration.exception.IMSMigrationHardDeclinedException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.CloseAccountByEmailRequest;
import com.snapdeal.ims.request.CreateGuestUserEmailRequest;
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.ResendEmailVerificationLinkRequest;
import com.snapdeal.ims.request.ResetPasswordRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.ChangePasswordWithLoginResponse;
import com.snapdeal.ims.response.CloseAccountResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.ForgotPasswordResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.GtokenSizeResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.ResendEmailVerificationLinkResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordWithLoginResponse;
import com.snapdeal.ims.response.SendForgotPasswordLinkResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.ISNSTaskService;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.service.dto.SocialInfo;
import com.snapdeal.ims.service.dto.UserDTO;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.service.provider.UmsServiceProvider;
import com.snapdeal.ims.snsClient.IMSSNSClient;
import com.snapdeal.ims.token.dao.IGlobalTokenDetailsDAO;
import com.snapdeal.ims.token.request.LoginTokenRequest;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.token.service.ITransferTokenService;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.ims.utility.IMSUtility;
import com.snapdeal.ims.utils.CipherServiceUtil;
import com.snapdeal.ims.utils.IMSEncryptionUtil;
import com.snapdeal.ims.utils.PasswordHashServiceUtil;
import com.snapdeal.ims.utils.VerificationCodeGeneratorUtil;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.service.Notifier;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractUMSService implements IUMSService {

	@Autowired
	private AuthorizationContext context;
	@Autowired
	private IUserVerificationDetailsDao userVerificationDetailsDao;

	@Autowired
	private NotifierServiceDelegater notifierService;

	@Autowired
	private Notifier notifier;

	@Autowired
	private IGlobalTokenService globalTokenService;

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private ITransferTokenService transferTokenService;

	@Autowired
	private IActivityDataService activityDataService;

	@Autowired
	UmsServiceProvider serviceProvider;

	@Autowired
	private ILoginUserService loginUserService;

	@Qualifier("userMigrationService")
	@Autowired
	private IUserMigrationService userMigrationService;

	@Autowired
	private IOTPService otpService;

	@Autowired
	IPasswordUpgradeCacheService passwordCacheService;

	@Autowired
	IUserIdCacheService userIdCacheService;

	@Autowired
	UmsMerchantProvider merchantServiceProvider;

	@Autowired
	IMSUtility imsUtility;

	@Autowired
	@Qualifier("userMigrationService")
	IUserMigrationService migrationService;

	@Autowired
	protected IGlobalTokenDetailsDAO globalTokenDao;

	
	@Autowired
   ISNSTaskService snsTaskService;

   @Autowired
   IMSSNSClient imsSnsClient;

   /**
    *
    * @param user
    */
   public void publishUpdateToSNS(UserHistory userHistory) {
      if(Boolean.parseBoolean(Configuration.getGlobalProperty(ConfigurationConstants.IMS_SNSPUBLISH))){
         publishSNSTask(userHistory);  
      }
   }

   private void publishSNSTask(UserHistory userHistory) {
      SNSNotificationDTO snsNotification = new SNSNotificationDTO();
      snsNotification.setUserID(userHistory.getUserId());
      snsNotification.setEvent(userHistory.getField().toString());
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Calendar cal = Calendar.getInstance();
      snsNotification.setUpdatedTime(dateFormat.format(cal.getTime()));
      List<String> oldValues = new ArrayList<String>();
      oldValues.add(userHistory.getOldValue());
      snsNotification.setOldValues(oldValues);
      List<String> newValues = new ArrayList<String>();
      newValues.add(userHistory.getNewValue());
      snsNotification.setNewValues(newValues);
      List<String> updatedFields = new ArrayList<String>();
      switch (userHistory.getField()) {
      case MOBILE_NO:   
         updatedFields.add("MOBILE_NO");
         break;
      case PASSWORD:
         break;
      case ENABLED_DISABLED:
         updatedFields.add("is_enabled");
      default:
         break;
      }
      snsNotification.setUpdatedFields(updatedFields);
      try {
         imsSnsClient.publishMessage(snsNotification.serialize());
      } catch (JsonProcessingException e) {
         log.error(e.getMessage());
         throw new IMSServiceException(
               IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
               IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
      } catch (InternalErrorException e) {
         createSNSTask(snsNotification);
      }
      return;
   }

   private void createSNSTask(SNSNotificationDTO snsNotification) {
      try {
         snsTaskService.createSNSTask(snsNotification.serialize(), UUID
               .randomUUID().toString());
      } catch (JsonProcessingException e) {
         log.error(e.getMessage());
         throw new IMSServiceException(
               IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
               IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
      }
   }



	/**
	 * API to get user based on user-id.
	 *
	 * @param imsRequest
	 * @return {@link GetUserResponse}
	 */
	@Timed
	@Marked
	@Logged
	public final GetUserResponse getUser(GetUserByIdRequest imsRequest) {

		// Get user details based on user id.
		UserDTO dto = getUserById(imsRequest.getUserId());
		GetUserResponse imsResponse = new GetUserResponse();
		imsResponse.setUserDetails(mapUserToUserDetailsDTO(dto));
		return imsResponse;
	}

	/**
	 * API to get user based on email.
	 *
	 * @param imsRequest
	 * @return
	 */
	@Timed
	@Marked
	@Logged
	public final GetUserResponse getUserByEmail(GetUserByEmailRequest imsRequest) {
		// Get user details based on user id.
		UserDTO dto = getUserByEmail(imsRequest.getEmailId());
		GetUserResponse imsResponse = new GetUserResponse();
		imsResponse.setUserDetails(mapUserToUserDetailsDTO(dto));
		return imsResponse;
	}

	/**
	 * Sign-out API using token, supports hard signout.
	 *
	 * @param request
	 * @return
	 */
	@Timed
	@Marked
	@Logged
	public final SignoutResponse signOut(SignoutRequest request) {
		request.setUserMachineIdentifier(context
				.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()));
		request.setUserAgent(context.get(IMSRequestHeaders.USER_AGENT
				.toString()));
		log.debug("Sign-out user with token: " + request);
		activityDataService.setActivityDataByToken(request.getToken());
		return tokenService.signOut(request);
	}

	/**
	 * API to login user using token.
	 *
	 * @param request
	 * @return {@link LoginUserResponse}
	 * @throws IMSServiceException
	 * @throws ValidationException
	 */
	@Timed
	@Marked
	@Logged
	public final LoginUserResponse loginUserWithToken(
			LoginWithTokenRequest request) throws IMSServiceException,
			ValidationException {

		String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());

		String userId = globalTokenService.getUserIdByGlobalToken(
				request.getGlobalToken(), clientId);

		// set activity data
		activityDataService.setActivityDataByUserId(userId);

		UserDTO userDetails = getUserById(userId);

		if(!userDetails.isEnabledState()){
			tokenService.signOutUser(userId);
			throw new IMSServiceException(
					IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
					IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
		}

		TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setToken(request.getGlobalToken());
		tokenRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		tokenRequest.setUserAgent(context.get(IMSRequestHeaders.USER_AGENT.toString()));
		tokenRequest.setUserMachineIdentifier(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER
				.toString()));

		TokenInformationDTO tokenInformation = globalTokenService
				.getTokenFromGlobalToken(tokenRequest);
		
		LoginUserResponse loginUserResponse;
		
		if(userDetails.isMobileOnly()){
			 loginUserResponse = createLoginUserResponse(userDetails, tokenInformation,null);
		}else{
			 loginUserResponse = createLoginUserResponse(userDetails, tokenInformation,
					getUpgradationInformationDTO(EmailUtils.toLowerCaseEmail(userDetails.getEmailId())));
		}
		return loginUserResponse;
	}

	/**
	 * API implementation for login user using username and password.
	 *
	 * @param imsRequest
	 * @return {@link LoginUserResponse}
	 */
	@Timed
	@Marked
	@Logged
	public LoginUserResponse loginUser(LoginUserRequest imsRequest) {
		loginUserSetActivitData(imsRequest) ;
		// Validate logic is different based on the merchant type.
		// validateUserCredential() method is used to validate credential and
		// fetch UserDTO.
		UserDTO userDTO = validateUserCredential(imsRequest);

		if(!userDTO.isEnabledState()){
			tokenService.signOutUser(userDTO.getUserId());
			throw new IMSServiceException(
					IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
					IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
		}

		TokenInformationDTO tokenInformation = createTokenOnLogin(
	            userDTO.getUserId(), userDTO.getEmailId());
	      // set activity data
	      activityDataService.setActivityDataByEmailId(imsRequest.getEmailId());

	      LoginUserResponse loginUserResponse = createLoginUserResponse(userDTO,
	            tokenInformation,
	            getUpgradationInformationDTO(imsRequest.getEmailId()));
	      return loginUserResponse;
	}
		

	@Timed
	@Marked
	@Logged
	public final ChangePasswordResponse changePassword(
			ChangePasswordRequest request) throws ValidationException,
	IMSServiceException {

		String userId = tokenService.getUserIdByToken(request.getToken());
		activityDataService.setActivityDataByUserId(userId);
		activityDataService.validateToken(request.getToken());

		if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {

			throw new IMSServiceException(
					IMSServiceExceptionCodes.PASSWORD_MISMATCH.errCode(),
					IMSServiceExceptionCodes.PASSWORD_MISMATCH.errMsg());
		} else if (request.getNewPassword().equals(request.getOldPassword())) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.NEW_PASSWORD_CAN_NOT_BE_SAME_AS_OLD_PASSWORD
					.errCode(),
					IMSServiceExceptionCodes.NEW_PASSWORD_CAN_NOT_BE_SAME_AS_OLD_PASSWORD
					.errMsg());
		}

		boolean status = changePasswordCommon(request, userId);
		if (status)
			tokenService.signoutAllOtherTokens(request.getToken());

		// set password in cache
		updateChangedPasswordInCache(request.getNewPassword(), userId);
		ChangePasswordResponse response = new ChangePasswordResponse();
		response.setSuccess(status);
		return response;
	}

	/**
	 * Special case of change password with generation of new token and
	 * persisting the global token used in current request
	 */
	@Timed
	@Marked
	@Logged
	public ChangePasswordWithLoginResponse changePasswordWithLogin(
			ChangePasswordRequest request) throws ValidationException,
	IMSServiceException {

		String userId = tokenService.getUserIdByToken(request.getToken());
		activityDataService.setActivityDataByUserId(userId);
		activityDataService.validateToken(request.getToken());

		if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {

			throw new IMSServiceException(
					IMSServiceExceptionCodes.PASSWORD_MISMATCH.errCode(),
					IMSServiceExceptionCodes.PASSWORD_MISMATCH.errMsg());
		} else if (request.getNewPassword().equals(request.getOldPassword())) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.NEW_PASSWORD_CAN_NOT_BE_SAME_AS_OLD_PASSWORD
					.errCode(),
					IMSServiceExceptionCodes.NEW_PASSWORD_CAN_NOT_BE_SAME_AS_OLD_PASSWORD
					.errMsg());
		}

		boolean status = changePasswordCommon(request, userId);

		GetUserByTokenRequest getUserByTokenRequest = new GetUserByTokenRequest();
		getUserByTokenRequest.setToken(request.getToken());
		GetUserResponse getUserResponse = getUserByToken(getUserByTokenRequest);

		tokenService.signOut(createSignoutRequest(request.getToken()));

		LoginUserRequest loginUserRequest = new LoginUserRequest();
		loginUserRequest.setEmailId(getUserResponse.getUserDetails()
				.getEmailId());
		loginUserRequest.setPassword(request.getNewPassword());
		LoginUserResponse loginUserResponse = loginUser(loginUserRequest);

		ChangePasswordWithLoginResponse response = new ChangePasswordWithLoginResponse();
		response.setSuccess(status);
		response.setTokenInformation(loginUserResponse.getTokenInformation());
		response.setUserDetails(loginUserResponse.getUserDetails());
		response.setUserSocialDetails(loginUserResponse.getUserSocialDetails());
		response.setUpgradationInformation(loginUserResponse
				.getUpgradationInformation());

		return response;
	}

	@Timed
	@Marked
	@Logged
	public ResetPasswordWithLoginResponse resetPasswordAndLogin(
			ResetPasswordRequest request) throws ValidationException,
	IMSServiceException {
		ResetPasswordResponse resetPasswordResponse = resetPassword(request);

		GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest();
		getUserByIdRequest.setUserId(request.getUserId());
		GetUserResponse getUserResponse = getUser(getUserByIdRequest);

		LoginUserRequest loginUserRequest = new LoginUserRequest();
		loginUserRequest.setEmailId(getUserResponse.getUserDetails()
				.getEmailId());
		loginUserRequest.setPassword(request.getNewPassword());
		LoginUserResponse loginUserResponse = loginUser(loginUserRequest);

		ResetPasswordWithLoginResponse response = new ResetPasswordWithLoginResponse();
		response.setSuccess(resetPasswordResponse.isSuccess());
		response.setTokenInformation(loginUserResponse.getTokenInformation());
		response.setUserDetails(loginUserResponse.getUserDetails());
		response.setUserSocialDetails(loginUserResponse.getUserSocialDetails());
		response.setUpgradationInformation(loginUserResponse
				.getUpgradationInformation());

		return response;
	}

	protected abstract boolean changePasswordCommon(
			ChangePasswordRequest request, String userId);

	protected abstract Map<String, String> getUserId(
			ForgotPasswordRequest request);

	@Timed
	@Marked
	@Logged
	public final ForgotPasswordResponse forgotPassword(
			ForgotPasswordRequest request) {

		Map<String, String> userDeatils = getUserId(request);
		String userId = userDeatils.get(ServiceCommonConstants.USERID);
		String name = userDeatils.get(ServiceCommonConstants.NAME);
	
		String email = userDeatils.get(ServiceCommonConstants.EMAIL_TAG);
		if (StringUtils.isBlank(email)) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
		GenerateOTPServiceRequest genOtpRequest = new GenerateOTPServiceRequest();

		// if user locked then throw exception else generate otp and proceed
		// normaly
		loginUserService.isUserLocked(userId);
		genOtpRequest.setOtpType(OTPPurpose.FORGOT_PASSWORD);

		boolean sentOTPOnEmail = Boolean
				.parseBoolean(Configuration
						.getGlobalProperty(ConfigurationConstants.RESET_PASSWORD_SEND_OTP_ON_EMAIL));

		boolean sentOTPOnMobile = Boolean
				.parseBoolean(Configuration
						.getGlobalProperty(ConfigurationConstants.RESET_PASSWORD_SEND_OTP_ON_MOBILE));
		if (sentOTPOnEmail && sentOTPOnMobile) {
			genOtpRequest.setEmailId(request.getEmailId());
			genOtpRequest.setMobileNumber(request.getMobileNumber());
		} else if (sentOTPOnMobile) {
			if (StringUtils.isNotBlank(request.getMobileNumber())) {
				genOtpRequest.setMobileNumber(request.getMobileNumber());
			} else {
				throw new IMSServiceException(
						IMSServiceExceptionCodes.MOBILE_NUMBER_IS_BLANK
								.errCode(),
						IMSServiceExceptionCodes.MOBILE_NUMBER_IS_BLANK
								.errMsg());
			}
		} else if (sentOTPOnEmail) {
			if (StringUtils.isNotBlank(request.getEmailId())) {
				genOtpRequest.setEmailId(request.getEmailId());
			} else if (StringUtils.isNotBlank(email)) {
				genOtpRequest.setEmailId(email);
			} else {
				throw new IMSServiceException(
						IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errCode(),
						IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errMsg());
			}
		}
		genOtpRequest.setName(StringUtils.capitalize(StringUtils
				.lowerCase(name)));
		genOtpRequest.setUserId(userId);
		genOtpRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID
				.toString()));
		// Setting V0FREECHARGE Notifier So If it is passthrough .. email
		// template can be pick up.
		genOtpRequest.setMerchant(getMerchantEnum());
		setV0FreechargeNotifier(genOtpRequest);
		// adding activity data
		activityDataService.setActivityDataByUserId(userId);
		// TODO discuss exception handling? null check?
		OTPServiceResponse genOtpResponse = otpService
				.generateOTP(genOtpRequest);

		ForgotPasswordResponse response = new ForgotPasswordResponse();
		response.setOtpId(genOtpResponse.getOtpId());
		response.setUserId(userId);

		return response;

	}

	protected void setV0FreechargeNotifier(GenerateOTPServiceRequest request)
	{
		setNotifier(request);
	}

	abstract void setNotifier(GenerateOTPServiceRequest message);
	abstract void setNotifier(EmailMessage message);

	abstract String getNotifier();


	@Timed
	@Marked
	@Logged
	public final SendForgotPasswordLinkResponse sendForgotPasswordLink(String email) {
		log.debug("Forgot password request initiated for email Id: " + email);
		// Check if user exists with email id.
		UserDTO dto = getUserByEmail(email);
		if (null == dto) {
			throwUserNotExistException();
		}
		// get verification code that will be sent to user.
		String verificationCode = VerificationCodeGeneratorUtil
				.getVerificationCode();
		saveVerificationCode(String.valueOf(dto.getUserId()), verificationCode,
				VerificationPurpose.FORGOT_PASSWORD);
		String merchant = getMerchant();
		String verificationLink = getVerificationURL(
				merchant,
				ConfigurationConstants.FORGET_PASSWORD_BY_EMAIL_VERIFICATION_URL);
		String templateKey = imsUtility.getEmailTemplateKey(merchant,
				ConfigurationConstants.FORGET_PASSWORD_BY_EMAIL_TEMPLATE);

		String displayName = StringUtils.isNotBlank(dto.getFirstName()) ? dto
				.getFirstName() : dto.getDisplayName();
		sendVerificationMail(email, String.valueOf(dto.getUserId()),
				displayName, verificationCode, templateKey,// forgotPasswordTemplateLocation,
				verificationLink, false);
		log.debug("Forgot password link is sent to user at email: " + email);
		// Sending successful response to client if email is successfully sent.
		SendForgotPasswordLinkResponse response = new SendForgotPasswordLinkResponse();
		response.setSuccess(true);
		return response;
	}

	/**
	 * API to re-send email verification link to user.
	 *
	 * @param request
	 * @return
	 */
	@Timed
	@Marked
	@Logged
	public final ResendEmailVerificationLinkResponse resendEmailVerificationLink(
			ResendEmailVerificationLinkRequest request) {
		log.debug("Resent email Verification link initiated for : "
				+ request.getEmailId());

		// Check if user Exists with email Id
		UserDTO user = getUserByEmail(request.getEmailId());
		if (null == user) {
			throwUserNotExistException();
		}
		String name = StringUtils.isNotBlank(user.getFirstName()) ? user
				.getFirstName() : user.getDisplayName();
		createUserVerificationHelper(request.getEmailId(), name,
				user.getUserId(), false, null);

		log.debug("Resend Email Verification link is sent to user at email: "
				+ user.getEmailId());
		// Sending successful response to client if email is successfully sent.
		ResendEmailVerificationLinkResponse response = new ResendEmailVerificationLinkResponse();
		response.setSuccess(true);
		return response;
	}

	/**
	 * Method to validate user credential. Different implementation will have
	 * different logic for credential match. <br/>
	 * Account gets lock, for stipulated period if user tries to login using
	 * invalid credential for more then 5 times.
	 *
	 * @param imsRequest
	 * @return
	 */
	protected abstract UserDTO validateUserCredential(
			LoginUserRequest imsRequest);

	protected abstract void loginUserSetActivitData(LoginUserRequest imsRequest);

	/**
	 * Utility method to create LoginUserResponse.
	 *
	 * @param userDetails
	 * @param tokenInformation
	 * @param userSocialDetails
	 * @param upgradationInformation
	 * @return
	 */
	protected LoginUserResponse createLoginUserResponse(UserDTO userDetails,
			TokenInformationDTO tokenInformation,
			UpgradationInformationDTO upgradationInformation) {

		LoginUserResponse response = new LoginUserResponse();
		response.setUserDetails(mapUserToUserDetailsDTO(userDetails));
		response.setTokenInformation(tokenInformation);
		response.setUpgradationInformation(upgradationInformation);
		UserSocialDetailsDTO userSocialDetails = new UserSocialDetailsDTO();

		List<SocialInfo> socialInfo = userDetails.getSocialInfo();
		if (null != socialInfo && !socialInfo.isEmpty()) {
			userSocialDetails.setAboutMe(socialInfo.get(0).getAboutMe());
			userSocialDetails.setPhotoURL(socialInfo.get(0).getPhotoURL());
			userSocialDetails.setSocialId(socialInfo.get(0).getSocialId());
		}
		response.setUserSocialDetails(userSocialDetails);
		return response;
	}

	protected UserDetailsDTO mapUserToUserDetailsDTO(UserDTO user) {
		UserDetailsDTO dto = new UserDetailsDTO();
		dto.setAccountOwner(user.getAccountOwner());
		dto.setEmailId(user.getEmailId());
		dto.setUserId(user.getUserId());
		dto.setSdUserId(user.getSdUserId());
		dto.setFcUserId(user.getFcUserId());
		dto.setMobileNumber(user.getMobileNumber());
		dto.setFirstName(user.getFirstName());
		dto.setMiddleName(user.getMiddleName());
		dto.setLastName(user.getLastName());
		dto.setDisplayName(user.getDisplayName());
		dto.setGender(user.getGender());
		dto.setDob(user.getDob());
		dto.setLanguagePref(user.getLanguagePref());
		dto.setMobileVerified(user.isMobileVerified());
		dto.setEmailVerified(user.isEmailVerified());
		dto.setFbSocialId(user.getFbSocialId());
		dto.setGoogleSocialId(user.getGoogleSocialId());
		dto.setAccountState(user.getAccountState());
		dto.setCreatedTime(user.getCreatedTime());
		dto.setEnabledState(user.isEnabledState());
		return dto;
	}

	/**
	 * Method to fetch user details by userId.
	 *
	 * @param userId
	 * @return
	 */
	protected abstract UserDTO getUserById(String userId);

	/**
	 * Method to fetch user details by userId.
	 *
	 * @param userId
	 * @return
	 */
	public abstract UserDTO getUserByEmail(String emailId);

	@Timed
	@Marked
	@Logged
	public VerifyUserResponse verifyGuestUser(VerifyUserRequest imsRequest) {

		// get code from db
		String verificationCode;
		try {
			verificationCode = CipherServiceUtil.decrypt(imsRequest.getCode());
		} catch (CipherException e) {
			log.error("Error ocured while decrypting the verification code.");
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED
							.errCode(),
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED
							.errMsg());
		}
		// get code from db
		UserVerification userVerificationCodeFromDb = userVerificationDetailsDao
				.getUserVerificationDetailsByCode(verificationCode);
		if (userVerificationCodeFromDb == null) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED
							.errCode(),
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED
							.errMsg());
		}

		registerAndUpdatePassword(userVerificationCodeFromDb.getUserId(), null,
				true);
		/*
		 * userVerificationDetailsDao
		 * .deleteUserVerificationDetailsByCode(userVerificationCodeFromDb
		 * .getCode());
		 */
		userVerificationDetailsDao.deleteUserVerificationCode(verificationCode);
		VerifyUserResponse verifyUserResponse = new VerifyUserResponse();
		verifyUserResponse.setStatus(StatusEnum.SUCCESS);
		return verifyUserResponse;
	}

	@Timed
	@Marked
	@Logged
	public final VerifyUserResponse verifyUserAndResetPassword(
			VerifyAndResetPasswordRequest request) {
		// get code from db
		String verificationCode;
		try {
			verificationCode = CipherServiceUtil.decrypt(request
					.getVerificationCode());
		} catch (CipherException e) {
			log.error("Error ocured while decrypting the verification code.");
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED
							.errCode(),
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED
							.errMsg());
		}
		UserVerification userVerificationCodeFromDb = userVerificationDetailsDao
				.getUserVerificationDetailsByCode(verificationCode);
		if (userVerificationCodeFromDb == null
				|| (userVerificationCodeFromDb.getPurpose() != VerificationPurpose.FORGOT_PASSWORD && userVerificationCodeFromDb
						.getPurpose() != VerificationPurpose.VERIFY_GUEST_USER)) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED
							.errCode(),
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED
							.errMsg());
		}
		activityDataService.setActivityDataByUserId(userVerificationCodeFromDb
				.getUserId());
		// Check if new and confirm password are same.
		if (!request.getNewPassword().equals(request.getConfirmPassword())) {

			throw new IMSServiceException(
					IMSServiceExceptionCodes.PASSWORD_MISMATCH.errCode(),
					IMSServiceExceptionCodes.PASSWORD_MISMATCH.errMsg());
		}
		boolean isSuccessful = registerAndUpdatePassword(
				userVerificationCodeFromDb.getUserId(),
				request.getNewPassword(), false);

		VerifyUserResponse response = new VerifyUserResponse();
		if (isSuccessful) {
			userVerificationDetailsDao
					.deleteUserVerificationCode(verificationCode);
			response.setStatus(StatusEnum.SUCCESS);
		} else {
			response.setStatus(StatusEnum.FAILURE);
		}
		return response;
	}

	protected abstract boolean registerAndUpdatePassword(String userId,
			String password, boolean hardRegister);

	/**
	 * This method returns UpgradationInformationDTO based on upgrade flag
	 *
	 * @param emailId
	 * @return
	 */
	protected UpgradationInformationDTO getUpgradationInformationDTO(
			String emailId) {
		return getUpgradationInformationDTO(emailId, false, null);
	}

	/**
	 * This method returns UpgradationInformationDTO based on upgrade flag
<<<<<<< HEAD
	 * Following: 1.
	 *
	 * @param emailId
	 *            : user's email id.
	 * @param isSocialLogin
	 *            : If this call was from social login.
	 * @param token
	 *            : Token is required when the call is from social login.
	 * @return
	 */
	protected UpgradationInformationDTO getUpgradationInformationDTO(
			String emailId, boolean isSocialLogin, String token) {

		UpgradationInformationDTO dto = new UpgradationInformationDTO();
		dto.setAction(Action.NO_ACTION_REQUIRED);
		dto.setSkip(Skip.SKIP_TRUE);
		dto.setState(State.SD_ACCOUNT_EXISTS_AND_ENABLED);
		dto.setUpgrade(Upgrade.NO_UPGRADE_REQRUIRED);
		if (Boolean.parseBoolean(Configuration.getProperty(
				context.get(IMSRequestHeaders.CLIENT_ID.toString()),
				ConfigurationConstants.UPGRADE_ENABLED))) {
			UserUpgradeByEmailRequest userUpgradeByEmailRequest = new UserUpgradeByEmailRequest();
			userUpgradeByEmailRequest.setEmailId(emailId);
			try {
				// Get upgrade status.
				UserUpgradationResponse userUpgradationResponse = userMigrationService
						.getUserUpgradeStatus(userUpgradeByEmailRequest, false);
				// Check if current login is via social and user is in link
				// state
				// If user is in link state, then verify user to make a smooth
				// transition.
				if (isSocialLogin
						&& Upgrade.isLinkState(userUpgradationResponse
								.getUpgradationInformation().getUpgrade())) {
					VerifyUserUpgradeRequest verifyUserUpgradeRequest = new VerifyUserUpgradeRequest();
					verifyUserUpgradeRequest.setEmailId(emailId);
					verifyUserUpgradeRequest
							.setVerifiedType(LinkUserVerifiedThrough.LINK_ACCOUNT_VIA_SOCIAL);
					verifyUserUpgradeRequest.setToken(token);
					log.debug("Verify user when logged in via social and user is in link state, email : "
							+ emailId);
					VerifyUpgradeUserResponse verifyUpgradeUser = userMigrationService
							.verifyUpgradeUser(verifyUserUpgradeRequest);
					if (null != verifyUpgradeUser
							&& verifyUpgradeUser.isSuccess()) {
						log.info("Verify user successful for social signed in user : "
								+ emailId);
						userUpgradationResponse = userMigrationService
								.getUserUpgradeStatus(
										userUpgradeByEmailRequest, false);
					} else {
						log.error("Linking for Social user failed when loggin with social, email id: "
								+ emailId);
					}
				}
				return userUpgradationResponse.getUpgradationInformation();
			} catch (IMSMigrationHardDeclinedException e) {
				log.error("Unusual exception occured" + e.getMessage());
				return dto;
			}
		}
		log.info(MigrationConstants.SKIP_MIGRATION + emailId);
		return dto;
	}
	/**
	 * Utility method to create signout request.
	 *
	 * @param token
	 * @return
	 */
	protected SignoutRequest createSignoutRequest(String token) {
		SignoutRequest req = new SignoutRequest();
		req.setHardSignout(true);
		req.setToken(token);
		req.setUserAgent(context.get(IMSRequestHeaders.USER_AGENT.toString()));
		req.setUserMachineIdentifier(context
				.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()));
		return req;
	}

	protected void createUserVerificationHelper(String emailId,
			String firstName, String userId, boolean isGuest, String url) {

		// Step6: Create Verification code
		String verificationCode = VerificationCodeGeneratorUtil
				.getVerificationCode();

		// Step7: Save in database

		VerificationPurpose purpose = VerificationPurpose.VERIFY_NEW_USER;

		String merchant = getMerchant();

		String templateKey = imsUtility.getEmailTemplateKey(merchant,
				ConfigurationConstants.CREATE_USER_BY_EMAIL_TEMPLATE);

		String verificationLink = null;
		if (StringUtils.isNotBlank(url)) {
			verificationLink = url;
		} else {
			verificationLink = getVerificationURL(
					merchant,
					ConfigurationConstants.CREATE_USER_BY_EMAIL_VERIFICATION_URL);
		}
		if (isGuest) {
			templateKey = imsUtility.getEmailTemplateKey(merchant,
					ConfigurationConstants.GUEST_CREATE_USER_BY_EMAIL_TEMPLATE);
			verificationLink = getVerificationURL(
					merchant,
					ConfigurationConstants.GUEST_CREATE_USER_BY_EMAIL_VERIFICATION_URL);

			purpose = VerificationPurpose.VERIFY_GUEST_USER;
		}

		saveVerificationCode(userId, verificationCode, purpose);

		// Step8: Send email
		sendVerificationMail(emailId, userId, firstName, verificationCode,
				templateKey, verificationLink, false);

		return;
	}

	protected String getVerificationURL(String merchant,
			ConfigurationConstants configurationConstant) {

		String url = Configuration.getClientProperty(merchant,
				configurationConstant);
		if (StringUtils.isBlank(url)) {
			url = Configuration.getGlobalProperty(configurationConstant);
		}

		return url;
	}

	protected String getMerchant() {

		String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
		return ClientConfiguration.getMerchantById(clientId).getMerchantName();
	}

	protected Merchant getMerchantEnum() {

		String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
		return ClientConfiguration.getMerchantById(clientId);
	}

	protected String getEmailReplyTo(String merchant,
			ConfigurationConstants configConstants) {
		String replyToEmailId = Configuration.getClientProperty(merchant,
				configConstants);
		if (StringUtils.isBlank(replyToEmailId)) {
			replyToEmailId = Configuration.getGlobalProperty(configConstants);
		}
		return replyToEmailId;
	}

	protected String getEmailSubject(String merchant,
			ConfigurationConstants configConstants) {
		String subject = Configuration.getClientProperty(merchant,
				configConstants);
		if (StringUtils.isBlank(subject)) {
			subject = Configuration.getGlobalProperty(configConstants);
		}
		return subject;
	}

	protected String getEmailFromMail(String merchant,
			ConfigurationConstants configConstants) {
		String fromEmailId = Configuration.getClientProperty(merchant,
				configConstants);
		if (StringUtils.isBlank(fromEmailId)) {
			fromEmailId = Configuration.getGlobalProperty(configConstants);
		}
		return fromEmailId;
	}

	/**
	 * Save verification code into DB.
	 *
	 * @param userId
	 * @param verificationCode
	 * @param purpose
	 */
	protected void saveVerificationCode(String userId, String verificationCode,
			VerificationPurpose purpose) {
		String expiryTime = null;
		switch (purpose) {
		case VERIFY_NEW_USER:
		case VERIFY_GUEST_USER:
			expiryTime = Configuration
					.getGlobalProperty(ConfigurationConstants.VERIFICATION_CODE_EXPIRY_NEW_USER);
			break;
		case FORGOT_PASSWORD:
			expiryTime = Configuration
					.getGlobalProperty(ConfigurationConstants.VERIFICATION_CODE_EXPIRY_FORGOT_PASSWORD);
			break;
		case PARKING_INTO_WALLET:
			expiryTime = Configuration
					.getGlobalProperty(ConfigurationConstants.VERIFICATION_CODE_EXPIRY_UPGRADE_FLOW);
		}
		int expiryTimeInMinutes = Integer
				.valueOf(Configuration
						.getGlobalProperty(ConfigurationConstants.DEFAULT_VERIFICATION_CODE_EXPIRY));
		if (expiryTime != null && StringUtils.isNumeric(expiryTime)) {
			expiryTimeInMinutes = Integer.valueOf(expiryTime);
		}
		UserVerification userVerificationEntity = new UserVerification();
		userVerificationEntity.setUserId(userId);
		userVerificationEntity.setCode(verificationCode);
		userVerificationEntity.setPurpose(purpose);
		// Convert time in minutes to milliseconds.
		long milliSecTime = expiryTimeInMinutes * 60 * 1000;
		userVerificationEntity.setCodeExpiryTime(new Timestamp(System
				.currentTimeMillis() + milliSecTime));
		userVerificationDetailsDao.create(userVerificationEntity);
	}
	/**
	 * Save Upgrade User verification code into DB.
	 *
	 * @param userId
	 * @param verificationCode
	 * @param purpose
	 */
	protected void saveUserUpgradeVerificationCode(
			UserVerification userVerificationEntity, int expiryTimeInMin) {

		// Convert time in minutes to milliseconds.
		long milliSecTime = expiryTimeInMin * 60 * 1000;
		userVerificationEntity.setCodeExpiryTime(new Timestamp(System
				.currentTimeMillis() + milliSecTime));
		userVerificationDetailsDao.create(userVerificationEntity);
	}

	@Async
	protected void sendVerificationMail(String emailId, String userId,
			String firstName, String verificationCode, String templateKey,
			String verificationLink, boolean blocking) {

		String encryptedVerificationCode;
		try {
			encryptedVerificationCode = CipherServiceUtil
					.encrypt(verificationCode);
		} catch (CipherException e) {
			log.error("Exception on encrypting verification code.");
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}

		if (verificationLink == null) {
			log.error("verificationLink is null ... please check configuration");
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}

		String url = MessageFormat.format(verificationLink,
				encryptedVerificationCode);
		// Step7: Send email with verification code
		String name = firstName;
		if (StringUtils.isBlank(name)) {
			name = " ";
		}

		Map<String, String> tags = new HashMap<String, String>();
		tags.put(com.snapdeal.ims.constants.ServiceCommonConstants.URL_TAG, url);
		tags.put(
				com.snapdeal.ims.constants.ServiceCommonConstants.CUSTOMER_TAG,
				StringUtils.capitalize(StringUtils.lowerCase(name)));
		tags.put(com.snapdeal.ims.constants.ServiceCommonConstants.EMAIL_TAG,
				emailId);

		/*
		 * We may need to verify email, putting test mode configuration
		 */
		if (isTestingModeOn()) {
			emailId = Configuration
					.getGlobalProperty(ConfigurationConstants.VERIFICATION_TEST_EMAIL_ID);
		}

		sendEmail(emailId, name, tags, userId, templateKey, blocking);
	}

	protected boolean isTestingModeOn() {
		String emailTestingModeOn = Configuration
				.getGlobalProperty(ConfigurationConstants.VERIFICATION_EMAIL_TEST_MODE);
		if (emailTestingModeOn != null && emailTestingModeOn.equals("1")) {
			return true;
		}
		return false;
	}

	protected void sendEmail(String email, String name,
			Map<String, String> tags, String userId, String templateKey,
			boolean blocking) {
		sendEmail(email, name, tags, userId, templateKey, blocking, null);
	}

	protected void sendEmail(String email, String name,
			Map<String, String> tags, String userId, String templateKey,
			boolean blocking, ConfigurationConstants subjectConstantkey) {
		EmailMessage emailMessage = new EmailMessage();
		setNotifier(emailMessage);
		List<String> toList = new ArrayList<String>();
		toList.add(email);
		emailMessage.addRecepients(toList);

		// Setting below configuration in database for different merchant
		// String subject =
		// com.snapdeal.ims.constants.ServiceCommonConstants.CREATE_USER_VERIFICATION_EMAIL_SUBJECT;
		// String replyTo =
		// com.snapdeal.ims.constants.ServiceCommonConstants.REPLY_TO;
		// String fromEmailId =
		// com.snapdeal.ims.constants.ServiceCommonConstants.FROM;

		String merchant = getMerchant();
		tags.put(ServiceCommonConstants.MERCHANT_TAG,
				StringUtils.capitalize(StringUtils.lowerCase(merchant)));
		String replyToEmailId = getEmailReplyTo(merchant,
				ConfigurationConstants.SEND_EMAIL_REPLY_TO_EMAIL_ID);

		String subject = null;
		if (subjectConstantkey == null) {
			subject = getEmailSubject(emailMessage.getEmailSendBy(),
					ConfigurationConstants.SEND_EMAIL_SUBJECT);
		} else {
			subject = getEmailSubject(emailMessage.getEmailSendBy(),
					subjectConstantkey);
		}

		String fromEmailId = getEmailFromMail(merchant,
				ConfigurationConstants.SEND_EMAIL_FROM_EMAIL_ID);

		emailMessage.setFrom(fromEmailId);

		emailMessage.setTemplateKey(templateKey);
		emailMessage.setSubject(subject);

		emailMessage.setReplyTo(replyToEmailId);
		emailMessage.setTags(tags);
		String taskId = "VERIFICATION_MAIL-" + userId + "-"
				+ UUID.randomUUID().getLeastSignificantBits();
		emailMessage.setTaskId(taskId);
		log.debug("Email message: " + emailMessage);
		notifierService.sendEmail(emailMessage, blocking);
	}

	/**
	 * Helper method to create a new token on successful login after user-name
	 * and password verification.
	 *
	 * @param userId
	 * @param isLinkUpgradeFlow
	 * @return {@link TokenInformationDTO}
	 */
	protected final TokenInformationDTO createTokenOnLogin(String userId,
			String emailId, boolean isLinkUpgradeFlow) {

		LoginTokenRequest loginTokenRequest = new LoginTokenRequest();

		// set activity data
		activityDataService.setActivityDataByUserId(userId);
		loginTokenRequest.setUserId(userId);
		loginTokenRequest.setEmailId(emailId);
		loginTokenRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID
				.toString()));
		loginTokenRequest.setUserAgent(context.get(IMSRequestHeaders.USER_AGENT
				.toString()));
		loginTokenRequest.setUserMachineIdentifier(context
				.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()));
		loginTokenRequest.setUpgradeFlow(isLinkUpgradeFlow);
		TokenInformationDTO tokenResponse = globalTokenService
				.createTokenOnLogin(loginTokenRequest);
		return tokenResponse;
	}

	protected final TokenInformationDTO createTokenOnLogin(String userId,
			String emailId) {
		return createTokenOnLogin(userId, emailId, false);
	}

	protected Integer validateAndGetUserId(String userId) {
		try {
			/**
			 * Since UMS is accepting userId as integer, any parsing error in
			 * userId will be considered as user does not exists
			 */

			userId = userId.trim();
			return Integer.valueOf(userId);
		} catch (NumberFormatException e) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	@Timed
	@Marked
	@Logged
	public final ResetPasswordResponse resetPassword(
			ResetPasswordRequest request) throws ValidationException,
			IMSServiceException {

		String userId = request.getUserId();
		loginUserService.isUserLocked(userId);
		// Check if user exists with user id.
		UserDTO userById = getUserById(request.getUserId());
		activityDataService.setActivityDataByUserId(userId);

		if (!request.getConfirmPassword().equals(request.getNewPassword())) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.PASSWORD_MISMATCH.errCode(),
					IMSServiceExceptionCodes.PASSWORD_MISMATCH.errMsg());
		}
		VerifyOTPServiceResponse verifyOTPServiceResponse = validateOtp(
				request.getUserId(), request.getOtp(), request.getOtpId(),
				context.get(IMSRequestHeaders.CLIENT_ID.toString()));

		if (!StringUtils.equalsIgnoreCase(request.getUserId(),
				verifyOTPServiceResponse.getUserId())) {
			throw new ValidationException(
					IMSValidationExceptionCodes.USERID_DOES_NOT_MATCH.errCode(),
					IMSValidationExceptionCodes.USERID_DOES_NOT_MATCH.errMsg());
		}
		ResetPasswordResponse response = new ResetPasswordResponse();

		// Check if user is in intermediate state.
		// In this case, we can confirm that email id is verified, so verifying
		// the user.
		boolean isIntermediateUpgradeStateUser = serviceProvider
				.isIntermediateState(userById.getEmailId());
		if (isIntermediateUpgradeStateUser) {
			migrationService.upgradeUserStatusViaResetPassword(
					userById.getEmailId(), mapUserToUserDetailsDTO(userById));
			serviceProvider.getIMSService().resetDefaultSocialPasswordHelper(
					userById.getEmailId(),
					IMSEncryptionUtil.getSDEncryptedPassword((request
							.getNewPassword())));
		}
		boolean status = resetPasswordHelper(request);
		tokenService.signOutUser(request.getUserId());
		// set password in cache
		updateChangedPasswordInCache(request.getNewPassword(), userId);
		response.setSuccess(status);
		return response;
	}

	private void updateChangedPasswordInCache(String newPassword, String userId) {
		String emailId = userIdCacheService.getEmailIdFromUserId(userId,
				merchantServiceProvider.getMerchant());
		if ((merchantServiceProvider.getMerchant() == Merchant.ONECHECK)) {
			String password = PasswordHashServiceUtil
					.getSdHashedPassword(newPassword);
			passwordCacheService.createImsSdHashed(emailId, password);
		} else {
			SdFcPasswordEntity entity = serviceProvider.getUMSService()
					.putSdFcHashedPasswordByEmailId(emailId, newPassword);
			passwordCacheService.updateSdFcPasswordbyEmailId(emailId, entity);
		}
	}

	abstract protected boolean resetPasswordHelper(ResetPasswordRequest request);

	protected VerifyOTPServiceResponse validateOtp(String userId, String otp,
			String otpId, String clientId) {
		VerifyOTPServiceRequest verifyOtpRequest = new VerifyOTPServiceRequest();
		verifyOtpRequest.setOtp(otp);
		verifyOtpRequest.setOtpId(otpId);
		verifyOtpRequest.setClientId(clientId);

		VerifyOTPServiceResponse verifyOtpResponse = otpService
				.verifyOTP(verifyOtpRequest);

		if (!verifyOtpResponse.getStatus().equalsIgnoreCase(
				OtpConstants.STATUS_SUCCESS)) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errMsg());
		} else if (!userId.equalsIgnoreCase(verifyOtpResponse.getUserId())) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.INVALID_OTP_ID.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ID.errMsg());
		}
		return verifyOtpResponse;
	}

	protected void throwUserNotExistException() {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
				IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
	}

	protected void throwUserAlreadyExistException() {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errCode(),
				IMSServiceExceptionCodes.ACCOUNT_ALREADY_EXIST.errMsg());
	}

	/**
	 * @see com.snapdeal.ims.service.IUMSService#createUserByVerifiedMobile(com.snapdeal.ims.request.CreateUserMobileGenerateRequest)
	 */
	@Override
	public CreateUserResponse createUserByVerifiedMobile(UpgradeDto upgradeDto) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public void updateUserByEmail(UpgradeDto upgradeDto) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	/**
	 * API to login user using transfer token.
	 *
	 * @param request
	 * @return {@link LoginUserResponse}
	 * @throws IMSServiceException
	 * @throws ValidationException
	 */
	@Timed
	@Marked
	@Logged
	public final LoginUserResponse loginUserWithTransferToken(
			LoginWithTransferTokenRequest request) throws IMSServiceException,
			ValidationException {

		String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());

		String gToken = transferTokenService
				.getGlobalTokenByTransferToken(request.getToken());

		String userId = globalTokenService.getUserIdByGlobalToken(gToken,
				clientId);

		// set activity data
		activityDataService.setActivityDataByUserId(userId);

		UserDTO userDetails = getUserById(userId);

		if (!userDetails.isEnabledState()) {
			tokenService.signOutUser(userId);
			throw new IMSServiceException(
					IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
					IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
		}

		TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setToken(gToken);
		tokenRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID
				.toString()));
		tokenRequest.setUserAgent(context.get(IMSRequestHeaders.USER_AGENT
				.toString()));
		tokenRequest.setUserMachineIdentifier(context
				.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()));

		TokenInformationDTO tokenInformation = globalTokenService
				.getTokenFromGlobalToken(tokenRequest);

		LoginUserResponse loginUserResponse = createLoginUserResponse(
				userDetails, tokenInformation,
				getUpgradationInformationDTO(EmailUtils
						.toLowerCaseEmail(userDetails.getEmailId())));
		return loginUserResponse;

	}

	@Override
	@Timed
	@Marked
	@Logged
	public CreateGuestUserResponse createGuestUserByEmail(
			CreateGuestUserEmailRequest createGuestUserByEmailRequest) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public boolean resetDefaultSocialPasswordHelper(String emailId,
			String password) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	protected String getTemplateKey(
			ConfigurationConstants configurationConstants) {
		return imsUtility.getEmailTemplateKey(getNotifier(),
				configurationConstants);
	}

	@Override
	public CloseAccountResponse closeAccount(CloseAccountByEmailRequest request) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public void deleteTempUserWithEmailOrMobile(String mobileNumber,
			String emailId) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.INVALID_CALL.errCode(),
				IMSServiceExceptionCodes.INVALID_CALL.errCode());
	}

	@Override
	public GtokenSizeResponse getGlobalTokenSizeByEmailId(
			GetUserByEmailRequest getUserByEmailRequest) {
		GetUserResponse userResponse = getUserByEmail(getUserByEmailRequest);
		int size = tokenService.getGTokenIDSetSizeByUserId(userResponse
				.getUserDetails().getUserId());
		GtokenSizeResponse gtokenResponse = new GtokenSizeResponse();
		gtokenResponse.setSize(size);
		return gtokenResponse;
	}

	@Override
	public User updateUserStatus(String emailId) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}
}
