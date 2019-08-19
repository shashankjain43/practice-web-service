package com.snapdeal.ims.service.impl;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.client.service.IClientService;
import com.snapdeal.ims.common.RandomStringGenerator;
import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.dto.UserSocialDetailsDTO;
import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.enums.AccountOwner;
import com.snapdeal.ims.enums.Action;
import com.snapdeal.ims.enums.ConfigureUserBasedOn;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.enums.Skip;
import com.snapdeal.ims.enums.SocialSource;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSValidationExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.request.CreateGuestUserEmailRequest;
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
import com.snapdeal.ims.request.GetUserByIdRequest;
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
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsByEmailRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsRequestDto;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
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
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.service.dto.SocialInfo;
import com.snapdeal.ims.service.dto.UserDTO;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utils.DateUtil;
import com.snapdeal.ims.utils.IMSEncryptionUtil;
import com.snapdeal.ims.utils.PasswordHashServiceUtil;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.service.Notifier;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.client.services.IUserClientService;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;
import com.snapdeal.ums.core.sro.user.UserRoleSRO.Role;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.ext.facebook.FacebookUserRequest;
import com.snapdeal.ums.ext.facebook.FacebookUserResponse;
import com.snapdeal.ums.ext.user.CreateUserWithDetailsRequest;
import com.snapdeal.ums.ext.user.CreateUserWithDetailsResponse;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.user.GetUserByIdResponse;
import com.snapdeal.ums.services.facebook.client.services.IFacebookUserClientService;
import com.snapdeal.ums.services.facebook.sro.FacebookProfileSRO;
import com.snapdeal.ums.services.facebook.sro.FacebookUserSRO;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Slf4j
@Service("SnapdealUmsService")
public class SnapdealUMSServiceImpl extends AbstractUMSService {

	@Autowired
	private IUserVerificationDetailsDao userVerificationDetailsDao;

	@Autowired
	private IUserService userService;

	@Autowired
	private Notifier notifier;

	@Autowired
	private IUserClientService userClientService;

	@Autowired
	private IUMSClientService umsClientService;

	@Autowired
	private IFacebookUserClientService fbUserClientService;

	@Autowired(required = false)
	private ITokenService tokenService;

	@Autowired(required = false)
	private IGlobalTokenService globalTokenService;

	@Autowired
	private IClientService clientService;

	@Autowired
	private AuthorizationContext authorizationContext;

	@Autowired
	private IOTPService otpService;

	@Autowired
	private ILoginUserService loginUserService;

	@Autowired
	private IActivityDataService activityDataService;

	@Qualifier("userMigrationService")
	@Autowired
	private IUserMigrationService userMigrationService;

	private UserSRO getUserFromUMSUsingId(String userId) {

		com.snapdeal.ums.ext.user.GetUserByIdRequest umsRequest = new com.snapdeal.ums.ext.user.GetUserByIdRequest();
		umsRequest.setId(validateAndGetUserId(userId));
		IMSServiceUtil.printUMSObject("Request", umsRequest);
		GetUserByIdResponse umsResponse = userClientService.getUserById(umsRequest);

		UserSRO umsUserSRO = null;
		// Check if call is successful
		if (umsResponse != null && null != umsResponse.getGetUserById() && umsResponse.isSuccessful()) {
			IMSServiceUtil.printUMSObject("Response", umsResponse.getGetUserById());
			umsUserSRO = umsResponse.getGetUserById();
		} else {
			IMSServiceExceptionCodes errorCode = IMSServiceExceptionCodes.USER_NOT_EXIST;
			throw new IMSServiceException(errorCode.errCode(), errorCode.errMsg());
		}
		return umsUserSRO;
	}

	private UserSRO getUserFromUMSUsingEmail(String emailId) {
		com.snapdeal.ums.ext.user.GetUserByEmailRequest umsRequest = new com.snapdeal.ums.ext.user.GetUserByEmailRequest();
		umsRequest.setEmail(StringUtils.lowerCase(emailId));

		GetUserByEmailResponse umsResponse = userClientService.getUserByEmail(umsRequest);

		UserSRO umsUserSRO = null;
		// Check if call is successful
		if (umsResponse != null && umsResponse.getGetUserByEmail() != null
				&& umsResponse.isSuccessful()) {
			IMSServiceUtil.printUMSObject("Response", umsResponse.getGetUserByEmail());
			umsUserSRO = umsResponse.getGetUserByEmail();
		} else {
			IMSServiceExceptionCodes errorCode = IMSServiceExceptionCodes.USER_NOT_EXIST;
			throw new IMSServiceException(errorCode.errCode(), errorCode.errMsg());
		}
		return umsUserSRO;
	}

	// There is no api listed at UMS for updating user based on UserId.
	// UMS is updating via emailId
	@Override
	@Timed
	@Marked
	@Logged
	public GetUserResponse updateUser(UpdateUserByIdRequest imsRequest) {

		// Validate date of birth: report early in case of error (formatting etc.)
		// if user is less then 18 yrs, set date of birth as null.
		Date dateOfBirth = null;
		try {
			dateOfBirth = IMSServiceUtil.getAndValidateDOB(imsRequest.getUserDetailsRequestDto()
					.getDob());
		} catch (RequestParameterException rpe) {
			if (rpe.getErrCode() != IMSRequestExceptionCodes.UNDER_AGED.errCode()) {
				throw rpe;
			}
		}

		// Get user from UMS
		UserSRO umsUserSRO = getUserFromUMSUsingId(imsRequest.getUserId());

		com.snapdeal.ums.ext.user.UpdateUserResponse umsUpdateResponse = null;
		if (umsUserSRO != null) {
			// Update other fields
			umsUpdateResponse = updateInfoForCreatedUser(imsRequest.getUserDetailsRequestDto()
					.getFirstName(), imsRequest.getUserDetailsRequestDto().getMiddleName(),
					imsRequest.getUserDetailsRequestDto().getLastName(), imsRequest
					.getUserDetailsRequestDto().getDisplayName(), imsRequest
					.getUserDetailsRequestDto().getGender(), dateOfBirth, umsUserSRO);

			if (umsUpdateResponse != null
					&& (!umsUpdateResponse.isSuccessful() || umsUpdateResponse.getUpdateUser() == null)) {
				throw new IMSServiceException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
						IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
			}
		} else {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}

		GetUserResponse userDetailsResponse = new GetUserResponse();
		userDetailsResponse.setUserDetails(createUserDetails(umsUpdateResponse.getUpdateUser()));
		return userDetailsResponse;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public CreateUserResponse createUserByEmail(
			CreateUserEmailRequest imsRequest) {


		// set activity data
		activityDataService.setActivityDataByEmailId(imsRequest.getUserDetailsByEmailDto().getEmailId());
		//Step1: validate DOB

		Date dateOfBirth = null;
		try {
			dateOfBirth = IMSServiceUtil.getAndValidateDOB(imsRequest.getUserDetailsByEmailDto().getDob(), false);
		} catch (RequestParameterException rpe) {
			if (rpe.getErrCode() != IMSRequestExceptionCodes.UNDER_AGED.errCode()) {
				throw rpe;
			}
		}

		//Step2: Create user using emailId and password if it doesn't exists
		UserDetailsByEmailRequestDto dto = imsRequest.getUserDetailsByEmailDto() ;
		UserSRO user =  createUserSro(dateOfBirth,
				dto.getFirstName(),
				dto.getMiddleName(),
				dto.getLastName(),
				dto.getDisplayName(),
				dto.getGender(),
				null) ;

		//email is not yet verified
		user.setEmailVerified(false);
		CreateUserWithDetailsResponse response = null ;
		try{
			response = createUser(imsRequest.getUserDetailsByEmailDto().getEmailId(),
					imsRequest.getUserDetailsByEmailDto().getPassword(), 
					Role.UNVERIFIED,false,user);
		}catch(Exception e){
			// Otherwise throw exception: INTERNAL_SERVER (user creation failed)
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());	
		}
		//Check if user creation is successful
		if(response != null && response.isSuccessful()){
			UserSRO userSRO = response.getSavedUser();
			CreateUserResponse imsResponse = new CreateUserResponse(); 
			if (userSRO != null){  			
				String name = StringUtils.isNotBlank(imsRequest.getUserDetailsByEmailDto().getFirstName()) ? imsRequest.getUserDetailsByEmailDto().getFirstName() : imsRequest.getUserDetailsByEmailDto().getFirstName() ; 
				createUserVerificationHelper(
						imsRequest.getUserDetailsByEmailDto().getEmailId(), 
						name,
						Integer.toString(userSRO.getId()),
						false,
						imsRequest.getUrl()); 

				//Step4. Set token (SignIn Credentials)
				TokenInformationDTO tokenResponse = 
						createTokenOnLogin(Integer
								.toString(userSRO.getId()), userSRO.getEmail());
				imsResponse.setTokenInformationDTO(tokenResponse);

				//Step5. Setting updated UserSRO in response
				imsResponse.setUserDetails(
						createUserDetails(userSRO));
				return imsResponse;
			}else { 
				//Otherwise throw exception: User already exists
				/**
				 * UMS is not handling DB integrity constraints properly 
				 * [In case user already exists, they are not giving proper response]
				 * 
				 * Since, we have already verified that all 4 parameters emailId(NotBlank), 
				 * password(NotBlank), Role(!=null) and Gender([optional param] should be 'm'/'f') 
				 * are correct. Any other reason of userSRO null will be treated 
				 * as 'user already exists'  
				 */
				throw new IMSServiceException(
						IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errCode(), 
						IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errMsg());
			}

		} else { 
			// Otherwise throw exception: INTERNAL_SERVER (user creation failed)
			throw new IMSServiceException(
					IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errCode(), 
					IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errMsg());	
		}
	}


	/**
	 * Utility method overloaded to create user in UMS.
	 */
	private com.snapdeal.ums.ext.user.CreateUserResponse createUser(String emailId, String passwd,
			Role role, boolean autoCreated) {
		return createUser(emailId, passwd, role, autoCreated, null, false);
	}

	/**
	 * Utility method to create user in UMS.
	 * 
	 * @param emailId
	 * @param passwd
	 * @param role
	 * @param autoCreated
	 * @param purpose
	 * @param isGuest
	 * @return
	 */

	@Timed
	@Marked
	@Logged
	private com.snapdeal.ums.ext.user.CreateUserResponse createUser(String emailId, String passwd,
			Role role, boolean autoCreated, String purpose, boolean isGuest) {

		com.snapdeal.ums.ext.user.CreateUserRequest umsRequest = new com.snapdeal.ums.ext.user.CreateUserRequest();
		umsRequest.setEmail(StringUtils.lowerCase(emailId));
		umsRequest.setAutocreated(autoCreated);
		umsRequest.setPassword(passwd);
		// If source is null in source, client name is set as source.
		if (isGuest) {
			umsRequest.setSource(purpose);
		}
		if (StringUtils.isBlank(umsRequest.getSource())) {
			umsRequest.setSource("IMS_PASS_THROUGH");
		}
		umsRequest.setInitialRole(role);

		IMSServiceUtil.printUMSObject("Request", umsRequest);
		com.snapdeal.ums.ext.user.CreateUserResponse umsResponse = userClientService
				.createUser(umsRequest);
		IMSServiceUtil.printUMSObject("Response", umsResponse.getCreateUser());

		return umsResponse;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public CreateGuestUserResponse createGuestUserByEmail(CreateGuestUserEmailRequest imsRequest)
			throws ValidationException {
		/*
		 * Step1: Check if user already created
		 * If No:
		 * a. Create new user in UMS database
		 * b. Create secure key (UUID number)
		 * c. Save secure key and userId mapping in database (with expiry time)
		 * d. Send email to user(about new user creation with email verification
		 * link)
		 * else
		 * a. Update token expiry time in Database
		 * b. Send email to user(about new user creation with email verification
		 * link)
		 */


		// set activity data
		activityDataService.setActivityDataByEmailId(imsRequest.getEmailId());

		// Step2: Create user using emailId and password if it doesn't exists
		String password = RandomStringGenerator.nextString(6);
		com.snapdeal.ums.ext.user.CreateUserResponse umsResponse = createUser(
				imsRequest.getEmailId(), password, // TODO, make configurable
				Role.UNVERIFIED, true, imsRequest.getPurpose(), true);

		// Check if user creation is successful
		if (umsResponse != null && umsResponse.isSuccessful()) {

			UserSRO userSRO = umsResponse.getCreateUser();

			// CreateUserResponse imsResponse = new CreateUserResponse();

			if (userSRO != null) {
				createUserVerificationHelper(imsRequest.getEmailId(), null,
						Integer.toString(umsResponse.getCreateUser().getId()), true,
						null);

				CreateGuestUserResponse imsResponse = new CreateGuestUserResponse();
				imsResponse.setUserId(Integer.toString(userSRO.getId()));
				imsResponse.setSdUserId(Integer.toString(userSRO.getId()));
				return imsResponse;
			} else { // Otherwise throw exception: User already exists
				/**
				 * UMS is not handling DB integrity constraints properly
				 * [In case user already exists, they are not giving proper
				 * response]
				 * 
				 * Since, we have already verified that all 4 parameters
				 * emailId(NotBlank),
				 * password(NotBlank), Role(!=null) and Gender([optional param]
				 * should be 'm'/'f')
				 * are correct. Any other reason of userSRO null will be treated
				 * as 'user already exists'
				 */
				throw new IMSServiceException(
						IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errCode(),
						IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errMsg());
			}

		} else {
			// Otherwise throw exception: INTERNAL_SERVER (user creation failed)
			throw new IMSServiceException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}
	}


	/**
	 * Utility method to call update user of UMS.
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param displayName
	 * @param gender
	 * @param dob
	 * @param userSRO
	 * @return
	 */
	private com.snapdeal.ums.ext.user.UpdateUserResponse updateInfoForCreatedUser(String firstName,
			String middleName, String lastName, String displayName, Gender gender, Date dob,
			UserSRO userSRO) {

		if (null != firstName) {
			userSRO.setFirstName(firstName);
		}
		if (null != middleName) {
			userSRO.setMiddleName(middleName);
		}
		if (null != lastName) {
			userSRO.setLastName(lastName);
		}
		if (null != displayName) {
			userSRO.setDisplayName(displayName);
		}
		if (gender != null) {
			userSRO.setGender(gender.getValue());
		}
		if (null != dob) {
			userSRO.setBirthday(dob);
		}
		com.snapdeal.ums.ext.user.UpdateUserRequest umsUpdateUserRequest = new com.snapdeal.ums.ext.user.UpdateUserRequest();
		umsUpdateUserRequest.setUser(userSRO);
		com.snapdeal.ums.ext.user.UpdateUserResponse umsUpdateUserResponse = userClientService
				.updateUser(umsUpdateUserRequest);
		return umsUpdateUserResponse;
	}

	private com.snapdeal.ums.ext.user.UpdateUserResponse updateInfoForCreatedUser(boolean state,
			UserSRO userSRO) {

		userSRO.setEnabled(state);
		com.snapdeal.ums.ext.user.UpdateUserRequest umsUpdateUserRequest = new com.snapdeal.ums.ext.user.UpdateUserRequest();
		umsUpdateUserRequest.setUser(userSRO);
		com.snapdeal.ums.ext.user.UpdateUserResponse umsUpdateUserResponse = userClientService
				.updateUser(umsUpdateUserRequest);
		return umsUpdateUserResponse;
	}

	@Timed
	@Marked
	@Logged
	public SocialUserResponse createSocialUser(CreateSocialUserRequest imsRequest) {

		String emailId = imsRequest.getSocialUserDto().getEmailId();
		// set activity data
		activityDataService.setActivityDataByEmailId(emailId);

		// Fetch user for emailId.
		com.snapdeal.ums.ext.user.GetUserByEmailRequest userByEmailRequest = new com.snapdeal.ums.ext.user.GetUserByEmailRequest();
		userByEmailRequest.setEmail(StringUtils.lowerCase(emailId));
		if (log.isDebugEnabled()) {
			log.debug("Fetch user from UMS emailId: " + emailId);
		}

		// Fetch user by email id.	
		IMSServiceUtil.printUMSObject("Request", userByEmailRequest);
		com.snapdeal.ums.ext.user.GetUserByEmailResponse userByEmailResponse = userClientService
				.getUserByEmail(userByEmailRequest);
		IMSServiceUtil.printUMSObject("Response", userByEmailResponse.getGetUserByEmail());
		UserSRO userByEmailUMSSRO = userByEmailResponse.getGetUserByEmail();
		//if src is null ,not creating user.
		if(StringUtils.isNotBlank(imsRequest.getSocialUserDto().getSocialSrc())){
			// If user doesn't exits create an user and then update with avail information. 
			if(userByEmailUMSSRO == null) {
				if (log.isDebugEnabled()) {
					log.debug("No user present for emailID: " + emailId);
					log.debug("Creating user with emailID: " + emailId + ", socialID:" + imsRequest.getSocialUserDto().getSocialId());
				}
				//when user does not exists we need social ID
				if(StringUtils.isBlank(imsRequest.getSocialUserDto().getSocialId())){
					throw new IMSServiceException(
							IMSRequestExceptionCodes.SOCIAL_ID_IS_BLANK.errCode(),
							IMSRequestExceptionCodes.SOCIAL_ID_IS_BLANK.errMsg());
				}				
				// Create user with random password.
				// autocreate false, since user initiated the login via social.
				SocialUserRequestDto socialUserDto = imsRequest.getSocialUserDto() ;
				Date dob = null;
				if (!StringUtils.isBlank(socialUserDto.getDob())) {
					try {
						dob = IMSServiceUtil.getAndValidateDOB(socialUserDto.getDob(), false);
					} catch (RequestParameterException rpe) {
						if (rpe.getErrCode() != IMSRequestExceptionCodes.UNDER_AGED.errCode()) {
							throw rpe;
						}
					}
				}
				UserSRO user = createUserSro(dob,
						socialUserDto.getFirstName(),
						socialUserDto.getMiddleName(),
						socialUserDto.getLastName(),
						socialUserDto.getDisplayName(),
						socialUserDto.getGender(),
						socialUserDto.getMobileNumber(),
						socialUserDto.getSocialSrc(),
						socialUserDto.getSocialId()) ;

				//email is verified for social user implicitly
				user.setEmailVerified(true);
				CreateUserWithDetailsResponse response = null ;
				response = createUser(emailId, 
						RandomStringUtils.randomAlphanumeric(6),
						Role.REGISTERED, 
						false,user);

				UserSRO umsUserSRO = response.getSavedUser();

				if(response != null && response.isSuccessful()){
					if (umsUserSRO != null) {
						//Update explicitly to setUID starts
						umsUserSRO.setEmailVerified(true);
						umsUserSRO.setUid(imsRequest.getSocialUserDto().getSocialId());
						com.snapdeal.ums.ext.user.UpdateUserRequest umsUpdateUserRequest = new com.snapdeal.ums.ext.user.UpdateUserRequest();
						umsUpdateUserRequest.setUser(umsUserSRO);
						com.snapdeal.ums.ext.user.UpdateUserResponse umsUpdateUserResponse = userClientService
								.updateUser(umsUpdateUserRequest);
						if(umsUpdateUserResponse !=null && umsUpdateUserResponse.getUpdateUser()!=null){
							umsUserSRO=umsUpdateUserResponse.getUpdateUser();
						}	
						//Update explicitly to setUID ends
						IMSServiceUtil.printUMSObject("Response", umsUserSRO);
						userByEmailResponse = userClientService
								.getUserByEmail(userByEmailRequest);
						IMSServiceUtil.printUMSObject("Response", userByEmailResponse.getGetUserByEmail()) ;
						userByEmailUMSSRO = userByEmailResponse
								.getGetUserByEmail();
					} else {
						log.error("User creation failed for social user id : "
								+ imsRequest.getSocialUserDto().getSocialId());
						throw new IMSServiceException(
								IMSServiceExceptionCodes.REGISTRATION_FAILED
								.errCode(),
								IMSServiceExceptionCodes.REGISTRATION_FAILED
								.errMsg());
					}
				} else {
					log.error("User creation failed for social user id : "
							+ imsRequest.getSocialUserDto().getSocialId());
					throw new IMSServiceException(
							IMSServiceExceptionCodes.REGISTRATION_FAILED
							.errCode(),
							IMSServiceExceptionCodes.REGISTRATION_FAILED
							.errMsg());
				}
			}else if (null != userByEmailUMSSRO) {
				if(!userByEmailUMSSRO.isEnabled()){
					tokenService.signOutUser(userByEmailUMSSRO.getUid());
					throw new IMSServiceException(IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
							IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
				}
				updateInfoForCreatedSocialUser(imsRequest.getSocialUserDto(),
						userByEmailUMSSRO);
			}
			// Re-check if user was created.
			// If the source is Facebook, then createFB user is called.
			if (null != imsRequest.getSocialUserDto().getSocialSrc()
					&& SocialSource.FACEBOOK == SocialSource.forName(imsRequest
							.getSocialUserDto().getSocialSrc())) {
				FacebookUserResponse umsResponse = createOrUpdateFBUser(
						imsRequest, userByEmailUMSSRO);
				if (umsResponse.isAddIfNotExistsFacebookUser()) {
					log.info("Added FB user for social id: "
							+ imsRequest.getSocialUserDto().getSocialId());
				} else {
					log.info("FB user already exists for social id: "
							+ imsRequest.getSocialUserDto().getSocialId());
				}
			}
		} else {

			// src is null.and if userByEmailUMSSro is null throw exception.
			if (null == userByEmailUMSSRO) {
				throw new IMSServiceException(
						IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
			}else if(!userByEmailUMSSRO.isEnabled()){
				tokenService.signOutUser(userByEmailUMSSRO.getUid());
				throw new IMSServiceException(IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
						IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
			}
		}
		// TODO: Add create for G+ source.
		TokenInformationDTO loginTokenResponse = createTokenOnLogin(String.valueOf(userByEmailUMSSRO
				.getId()), userByEmailUMSSRO.getEmail());
		UserSocialDetailsDTO userSocialDetails = new UserSocialDetailsDTO();
		userSocialDetails.setAboutMe(imsRequest.getSocialUserDto().getAboutMe());
		userSocialDetails.setPhotoURL(imsRequest.getSocialUserDto().getPhotoURL());
		userSocialDetails.setSocialId(imsRequest.getSocialUserDto().getSocialId());
		userSocialDetails.setSocialSource(SocialSource.forName(imsRequest.getSocialUserDto().getSocialSrc()));

		SocialUserResponse createSocialResponse = new SocialUserResponse();
		createSocialResponse.setUserDetails(createUserDetails(userByEmailUMSSRO));
		createSocialResponse.setUserSocialDetails(userSocialDetails);
		createSocialResponse.setTokenInformation(loginTokenResponse);

		// Upgrade user if user is in link state.
		activityDataService.setActivityDataByUserId(createSocialResponse.getUserDetails().getUserId());
		UpgradationInformationDTO upgradeDTO = getUpgradationInformationDTO(imsRequest.getSocialUserDto().getEmailId(), 
				true, 
				loginTokenResponse.getToken());
		createSocialResponse.setUpgradationInformation(upgradeDTO);
		return createSocialResponse;
	}

   private UserSRO createUserSro(Date dob, String firstName, String middleName, String lastName,
            String displayName, Gender gender, String mobileNumber,  String socialSrc, String socialId) {
      UserSRO user = new UserSRO();
      user.setBirthday(dob);
      user.setFirstName(firstName);
      user.setMiddleName(middleName);
      user.setLastName(lastName);
      user.setDisplayName(displayName);
      if (null != gender) {
         user.setGender(gender.getValue());
      }
      if(StringUtils.isNotBlank(mobileNumber)){
         user.setMobile(mobileNumber);
      }
      user.setSource(socialSrc);
      user.setUid(socialId);
      return user;
   }

   private void updateInfoForCreatedSocialUser(SocialUserRequestDto socialUserDto,
            UserSRO userByEmailUMSSRO) {
      Date dob = null;
      if (!StringUtils.isBlank(socialUserDto.getDob())) {
         try {
            dob = IMSServiceUtil.getAndValidateDOB(socialUserDto.getDob(), false);
         } catch (RequestParameterException rpe) {
            if (rpe.getErrCode() != IMSRequestExceptionCodes.UNDER_AGED.errCode()) {
               throw rpe;
            }
         }
      }
      userByEmailUMSSRO.setSource(socialUserDto.getSocialSrc());
      if (userByEmailUMSSRO.getUid() == null && StringUtils.isBlank(socialUserDto.getSocialId())) {
         /*
          * throw new
          * IMSServiceException(IMSRequestExceptionCodes.SOCIAL_ID_IS_BLANK
          * .errCode(),
          * IMSRequestExceptionCodes.SOCIAL_ID_IS_BLANK.errMsg());
          */
         log.warn("Social id is null" + socialUserDto.toString());

      }

      if (StringUtils.isNotBlank(socialUserDto.getSocialId())) {
         userByEmailUMSSRO.setUid(socialUserDto.getSocialId());
      }

      if(StringUtils.isNotBlank(socialUserDto.getMobileNumber())){
    	  userByEmailUMSSRO.setMobile(socialUserDto.getMobileNumber());
      }
      updateInfoForCreatedUser(socialUserDto.getFirstName(), socialUserDto.getMiddleName(),
               socialUserDto.getLastName(), socialUserDto.getDisplayName(),
               socialUserDto.getGender(), dob, userByEmailUMSSRO);
   }
   
   protected  void loginUserSetActivitData(LoginUserRequest imsRequest) {

	      if (StringUtils.isEmpty(imsRequest.getEmailId())
	            && StringUtils.isNotBlank(imsRequest.getMobileNumber())) {
	         log.error("login user with mobile is not support currently");
	         throw new RequestParameterException(IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errCode(),
	               IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errMsg());
	      }

	      // set activity data
	      activityDataService.setActivityDataByEmailId(imsRequest.getEmailId());
	     
   }

   private FacebookUserResponse createOrUpdateFBUser(CreateSocialUserRequest imsRequest,
            UserSRO umsUserSRO) {
      FacebookUserRequest umsRequest = new FacebookUserRequest();
      umsRequest.setEmail(StringUtils.lowerCase(imsRequest.getSocialUserDto().getEmailId()));

      FacebookUserSRO umsFacebookUserSRO = new FacebookUserSRO();
      FacebookProfileSRO umsFacebookProfileSro = new FacebookProfileSRO();

      umsFacebookProfileSro.setAboutMe(imsRequest.getSocialUserDto().getAboutMe());
      umsFacebookProfileSro.setFirstName(imsRequest.getSocialUserDto().getFirstName());
      umsFacebookProfileSro.setLastName(imsRequest.getSocialUserDto().getLastName());
      umsFacebookProfileSro.setMiddleName(imsRequest.getSocialUserDto().getMiddleName());

      if (StringUtils.isNotBlank(imsRequest.getSocialUserDto().getSocialId())) {
         umsFacebookUserSRO.setFacebookId(Long.parseLong(imsRequest.getSocialUserDto()
                  .getSocialId()));
      }
      umsFacebookUserSRO.setEmailId(StringUtils.lowerCase(imsRequest.getSocialUserDto()
               .getEmailId()));
      umsFacebookUserSRO.setFbProfile(umsFacebookProfileSro);
      umsFacebookUserSRO.setSnapdealUser(umsUserSRO);

      umsRequest.setFbUser(umsFacebookUserSRO);
      umsRequest.setUser(umsUserSRO);

      FacebookUserResponse umsResponse = fbUserClientService.addIfNotExistsFacebookUser(umsRequest);
      return umsResponse;
   }

   @Override
   @Timed
   @Marked
   @Logged
   public GetUserResponse getUserByToken(GetUserByTokenRequest request) {
      String userId = tokenService.getUserIdByToken(request.getToken());
      GetUserByIdRequest userByIdRequest = new GetUserByIdRequest();
      userByIdRequest.setUserMachineIdentifier(request.getUserMachineIdentifier());
      userByIdRequest.setUserId(userId);
      return getUser(userByIdRequest);
   }

   @Timed
   @Marked
   @Logged
   public GetUserResponse updateUserByToken(UpdateUserByTokenRequest request)
            throws IMSServiceException {
      String userId = tokenService.getUserIdByToken(request.getToken());
      UpdateUserByIdRequest userRequest = new UpdateUserByIdRequest();
      userRequest.setUserId(userId);
      UserDetailsRequestDto userDetailsRequestDto = new UserDetailsRequestDto();
      userDetailsRequestDto.setFirstName(request.getUserDetailsRequestDto().getFirstName());
      userDetailsRequestDto.setMiddleName(request.getUserDetailsRequestDto().getMiddleName());
      userDetailsRequestDto.setLastName(request.getUserDetailsRequestDto().getLastName());
      userDetailsRequestDto.setDisplayName(request.getUserDetailsRequestDto().getDisplayName());
      userDetailsRequestDto.setGender(request.getUserDetailsRequestDto().getGender());
      userDetailsRequestDto.setDob(request.getUserDetailsRequestDto().getDob());
      userRequest.setUserDetailsRequestDto(userDetailsRequestDto);
      return updateUser(userRequest);
   }

   /**
    * Utility method to extract information related to social user from UMS user
    * sro.
    * 
    * @param umsUserSRO
    * @return
    */
   private UserSocialDetailsDTO createUserSocialDetailsDTO(UserSRO umsUserSRO) {
      UserSocialDetailsDTO dto = null;
      SocialSource socialSrc = SocialSource.forName(umsUserSRO.getSource());
      if (null != socialSrc) {
         dto = new UserSocialDetailsDTO();
         dto.setSocialId(umsUserSRO.getUid());
         switch (socialSrc) {
            case FACEBOOK:
               dto.setPhotoURL(umsUserSRO.getPhoto());
               break;
            case GOOGLE:

               break;
            default:
               if (log.isDebugEnabled()) {
                  log.debug("");
               }
               break;
         }
      }
      return dto;
   }
   
   /*
    * private LoginUserResponse createLoginUserResponse(UserSRO umsUserSRO,
    * TokenInformationDTO tokenInformation, UserSocialDetailsDTO
    * userSocialDetails,
    * UpgradationInformationDTO upgradationInformation) {
    * LoginUserResponse loginUserResponse = new LoginUserResponse();
    * UserDetailsDTO userDetails = createUserDetails(umsUserSRO);
    * loginUserResponse.setUserDetails(userDetails);
    * loginUserResponse.setTokenInformation(tokenInformation);
    * loginUserResponse.setUserSocialDetails(userSocialDetails);
    * loginUserResponse.setUpgradationInformation(upgradationInformation);
    * 
    * return loginUserResponse;
    * }
    */
   /**
    * Utility method for mapping {@link UserSRO} to {@link UserDetailsDTO}.
    * 
    * @param umsUserSRO
    * @return
    */

   protected UserDetailsDTO createUserDetails(UserSRO umsUserSRO) {

      if (null == umsUserSRO) {
         throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
                  IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
      }

      UserDetailsDTO userDetails = new UserDetailsDTO();
      userDetails.setDisplayName(umsUserSRO.getDisplayName());
      userDetails.setDob(DateUtil.formatDate(umsUserSRO.getBirthday(),
               CommonConstants.DATE_FORMAT));
      userDetails.setEmailId(StringUtils.lowerCase(umsUserSRO.getEmail()));
      userDetails.setFirstName(umsUserSRO.getFirstName());
      userDetails.setMiddleName(umsUserSRO.getMiddleName());
      userDetails.setLastName(umsUserSRO.getLastName());
      userDetails.setGender(Gender.forValue(umsUserSRO.getGender()));
      userDetails.setSdUserId(umsUserSRO.getId());
      userDetails.setUserId(String.valueOf(umsUserSRO.getId()));
      userDetails.setAccountOwner(AccountOwner.SD);
      userDetails.setEmailVerified(umsUserSRO.isEmailVerified());
      userDetails.setMobileVerified(umsUserSRO.isMobileVerified());
      userDetails.setEnabledState(umsUserSRO.isEnabled());
      if (umsUserSRO.getCreated() != null) {
         userDetails.setCreatedTime(new Timestamp(umsUserSRO.getCreated().getTime()));
      }

      if (umsUserSRO.getUserRoles() != null) {
         if (umsUserSRO.getUserRoleSro(ServiceCommonConstants.REGISTERED) != null) {
            userDetails.setAccountState(ServiceCommonConstants.REGISTERED);
         } else {
            userDetails.setAccountState(ServiceCommonConstants.UNVERIFIED);
         }
      } else {
         userDetails.setAccountState(ServiceCommonConstants.UNVERIFIED);
      }
      if (umsUserSRO.getSource() != null) {
         if (SocialSource.FACEBOOK == SocialSource.forName(umsUserSRO.getSource())) {
            userDetails.setFbSocialId(umsUserSRO.getUid());

         } else if (SocialSource.GOOGLE == SocialSource.forName(umsUserSRO.getSource())) {
            userDetails.setGoogleSocialId(umsUserSRO.getUid());
         }
      }   
      if(StringUtils.isNotBlank(umsUserSRO.getMobile())){
    	  userDetails.setMobileNumber(umsUserSRO.getMobile());
      }
      
      return userDetails;

   }

   @Timed
   @Marked
   @Logged
   /**
    * Utility method used temporarily in pass-through system.
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
   public IsUserExistResponse isUserExist(IsUserExistRequest request) {

      IsUserExistResponse isUserExistResponse = new IsUserExistResponse();
      try {
         UserSRO userSRO = getUserFromUMSUsingId(request.getUserId());
         if (null != userSRO) {
            isUserExistResponse.setExist(true);
         }
      } catch (IMSServiceException ex) {
         log.debug("User doesn't exists for email: " + request.getUserId());
      }
      return isUserExistResponse;
   }

   @Override
   @Timed
   @Marked
   @Logged
   public IsEmailExistResponse isEmailExist(IsEmailExistRequest request) {
      com.snapdeal.ums.ext.user.IsUserExistsRequest umsRequest = new com.snapdeal.ums.ext.user.IsUserExistsRequest();

      umsRequest.setEmail(StringUtils.lowerCase(request.getEmailId()));

      com.snapdeal.ums.ext.user.IsUserExistsResponse umsResponse = userClientService
               .isUserExists(umsRequest);

      IsEmailExistResponse isEmailExistResponse = new IsEmailExistResponse();

      // Check if call is successful
      if (umsResponse != null && umsResponse.isSuccessful()) {
         isEmailExistResponse.setExist(umsResponse.getIsUserExists());
      } else {
         throw new IMSServiceException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
                  IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
      }
      return isEmailExistResponse;
   }

   /**
    * Common method to register and update password.
    * 
    * @param userSRO
    * @param password
    * @param hardRegister
    * @return
    */
   @Override
   protected boolean registerAndUpdatePassword(String userId, String password, boolean hardRegister) {
	// get user corresponding to the code
       UserSRO userSRO = getUserFromUMSUsingId(userId);
       if (userSRO == null) {
          throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
                   IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
       }
	   
      if (StringUtils.isNotBlank(password)) {
         userSRO.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(password));
      }
      // Remove role unverified if exists.
      Iterator<UserRoleSRO> iterator = userSRO.getUserRoles().iterator();
      boolean verified = true;
      while (iterator.hasNext()) {
         if (Role.UNVERIFIED.role().equals(iterator.next().getRole())) {
            iterator.remove();
            verified = false;
            break;
         }
      }
      // Add Registered Role to user role.
      if (!verified || !userSRO.isEmailVerified()) {
         userSRO.setEmailVerified(true);
         userSRO.getUserRoles().add(new UserRoleSRO(userSRO, Role.REGISTERED.role()));
      } else if (hardRegister) {
         // If user already verified, then throw exception only if hardRegister
         // is true.
         throw new IMSServiceException(IMSServiceExceptionCodes.USER_ALREADY_VERIFIED.errCode(),
                  IMSServiceExceptionCodes.USER_ALREADY_VERIFIED.errMsg());
      }
      com.snapdeal.ums.ext.user.UpdateUserRequest umsUpdateUserRequest = new com.snapdeal.ums.ext.user.UpdateUserRequest();
      umsUpdateUserRequest.setUser(userSRO);
      com.snapdeal.ums.ext.user.UpdateUserResponse umsUpdateUserResponse = userClientService
               .updateUser(umsUpdateUserRequest);
      userSRO = umsUpdateUserResponse.getUpdateUser();
      if (!umsUpdateUserResponse.isSuccessful() || userSRO == null) {
         throw new IMSServiceException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
                  IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
      }
      return true;
   }

   @Timed
   @Marked
   @Logged
   public ConfigureUserStateResponse configureUserState(ConfigureUserStateRequest imsRequest) {

      UserSRO umsUserSRO = getUserSro(imsRequest);

      com.snapdeal.ums.ext.user.UpdateUserResponse umsUpdateResponse = null;
      if (umsUserSRO != null) {
         // Update enable field
         umsUpdateResponse = updateInfoForCreatedUser(imsRequest.isEnable(), umsUserSRO);

         if (umsUpdateResponse != null
                  && (!umsUpdateResponse.isSuccessful() || umsUpdateResponse.getUpdateUser() == null)) {
            throw new IMSServiceException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
                     IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
         }
      } else {
         throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
                  IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
      }

      if(imsRequest.isEnable() == false){
	    	//hard signout user.
    	  tokenService.signOutUser(String.valueOf(umsUserSRO.getId())) ;
	      }
      ConfigureUserStateResponse response = new ConfigureUserStateResponse();
      response.setStatus(StatusEnum.SUCCESS);
      return response;
   }

   /**
    * Method to fetch user sro from ums based on the input in order of:
    * <ol>
    * <li>UserId</li>
    * <li>Token</li>
    * <li>Email</li>
    * <li>Mobile number</li>
    * </ol>
    * 
    * @param imsRequest
    * @return
    */
   private UserSRO getUserSro(ConfigureUserStateRequest imsRequest) {
      ConfigureUserBasedOn configureUserBasedOn = imsRequest.getConfigureUserBasedOn() ;
      switch(configureUserBasedOn){
	   case EMAIL:
		   if(StringUtils.isNotBlank(imsRequest.getEmailId())){
			  
			   return getUserFromUMSUsingEmail(imsRequest.getEmailId());
		   }
	   case MOBILE:
		   if(StringUtils.isNotBlank(imsRequest.getMobileNumber())){
			   log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		         throw new IMSServiceException(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
		                  IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		   }
	   case TOKEN :
		   if(StringUtils.isNotBlank(imsRequest.getToken())){
			   activityDataService.validateToken(imsRequest.getToken());
		         String userId = tokenService.getUserIdByToken(imsRequest.getToken());

		         if (StringUtils.isBlank(userId)) {
		            throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
		                     IMSServiceExceptionCodes.USER_NOT_EXIST.errCode());
		         }
		         return getUserFromUMSUsingId(userId);
		   }
	   case USER_ID:
		   if(StringUtils.isNotBlank(imsRequest.getUserId())){
			   return getUserFromUMSUsingId(imsRequest.getUserId());
		   }
	   default :
		   throw new IMSServiceException(
				   IMSRequestExceptionCodes.INVALID_CONFIGURE_USER_STATE_BASED_ON.errCode(),
				   IMSRequestExceptionCodes.INVALID_CONFIGURE_USER_STATE_BASED_ON.errMsg());
      }
   }
   
   @Override
   protected UserDTO getUserById(String userId) {
      // Fetch user from UMS using userId.
      UserSRO userFromUMSUsingId = getUserFromUMSUsingId(userId);
      UserDTO dto = mapUserSROToDTO(userFromUMSUsingId);
      return dto;
   }

   private UserDTO mapUserSROToDTO(UserSRO userSRO) {
      UserDTO dto = new UserDTO();
      dto.setAccountOwner(AccountOwner.SD);
      dto.setEmailId(userSRO.getEmail());
      dto.setUserId(String.valueOf(userSRO.getId()));
      dto.setSdUserId(userSRO.getId());
      // dto.setFcUserId();
      dto.setMobileNumber(userSRO.getMobile());
      dto.setFirstName(userSRO.getFirstName());
      dto.setMiddleName(userSRO.getMiddleName());
      dto.setLastName(userSRO.getLastName());
      dto.setDisplayName(userSRO.getDisplayName());
      dto.setGender(Gender.forValue(userSRO.getGender()));
      dto.setDob(DateUtil.formatDate(userSRO.getBirthday(), CommonConstants.DATE_FORMAT));
      // dto.setLanguagePref(userFromUMSUsingId.get);
      dto.setMobileVerified(userSRO.isMobileVerified());
      dto.setEmailVerified(userSRO.isEmailVerified());
      dto.setEnabledState(userSRO.isEnabled());
     // dto.setMobileNumber(userSRO.getMobile());
      // dto.setFbSocialId(String);
      // dto.setGoogleSocialId(String);
      if (userSRO.getUserRoles() != null
               && userSRO.getUserRoleSro(ServiceCommonConstants.REGISTERED) != null) {
         dto.setAccountState(ServiceCommonConstants.REGISTERED);
      } else {
         dto.setAccountState(ServiceCommonConstants.UNVERIFIED);
      }
      if (null != userSRO.getCreated()) {
          dto.setCreatedTime(new Timestamp(userSRO.getCreated().getTime()));
      }
      // TODO: do not use createUserSocialDetailsDTO()
      UserSocialDetailsDTO createUserSocialDetailsDTO = createUserSocialDetailsDTO(userSRO);
      List<SocialInfo> socialInfos = new ArrayList<SocialInfo>();
      if (createUserSocialDetailsDTO != null) {
         SocialInfo socialInfo = new SocialInfo();
         socialInfo.setAboutMe(createUserSocialDetailsDTO.getAboutMe());
         socialInfo.setPhotoURL(createUserSocialDetailsDTO.getPhotoURL());
         socialInfo.setSocialId(createUserSocialDetailsDTO.getSocialId());
         SocialSource forName = SocialSource.forName(userSRO.getSource());
         socialInfo.setSocialSrc(forName);
         if (forName == SocialSource.GOOGLE) {
            dto.setGoogleSocialId(createUserSocialDetailsDTO.getSocialId());
         } else if (forName == SocialSource.FACEBOOK) {
            dto.setFbSocialId(createUserSocialDetailsDTO.getSocialId());
         }
         socialInfos.add(socialInfo);
      }
      dto.setSocialInfo(socialInfos);
      return dto;
   }

   @Override
   protected UserDTO validateUserCredential(LoginUserRequest imsRequest) {
      
      UserSRO userSRO = getUserFromUMSUsingEmail(imsRequest.getEmailId());

      // Check if user is locked or not
      String userId = String.valueOf(userSRO.getId());
      loginUserService.isUserLocked(userId);

      // Check if password matches.
      if (StringUtils.equals(IMSEncryptionUtil.getSDEncryptedPassword(imsRequest.getPassword()),
               userSRO.getPassword())) {
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
      return mapUserSROToDTO(userSRO);
   }
   protected  Map<String,String> getUserId(ForgotPasswordRequest request){
		String userId = null;
		String name = null;
		String email = null;

		if (StringUtils.isNotBlank(request.getEmailId())) {
			activityDataService.setActivityDataByEmailId(request.getEmailId());
			com.snapdeal.ums.ext.user.GetUserByEmailRequest umsRequest = new com.snapdeal.ums.ext.user.GetUserByEmailRequest(
					request.getEmailId());
			com.snapdeal.ums.ext.user.GetUserByEmailResponse umsResponse = userClientService
					.getUserByEmail(umsRequest);
			UserSRO user = umsResponse.getGetUserByEmail();
			if (user == null) {
				throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
			}
			if(StringUtils.isNotBlank(request.getMobileNumber())
					&& StringUtils.isNotBlank(user.getMobile())){
				if(!user.getMobile().equalsIgnoreCase(request.getMobileNumber())){
					throw new RequestParameterException(
							IMSServiceExceptionCodes.MISMATCH_EMAIL_ID_MOBILE.errCode(),
							IMSServiceExceptionCodes.MISMATCH_EMAIL_ID_MOBILE.errMsg());
				}
			}

			userId = String.valueOf(user.getId());
			name = user.getFirstName() ;
			email= user.getEmail();

		} else if (StringUtils.isNotBlank(request.getMobileNumber())) {

			GetUserByMobileRequest req = new GetUserByMobileRequest();
			req.setMobileNumber(request.getMobileNumber());
			GetUserResponse getUserResponse = userService.getUserByMobile(req);
			userId=getUserResponse.getUserDetails().getUserId();
			name = getUserResponse.getUserDetails().getFirstName();
			email = getUserResponse.getUserDetails().getEmailId();

		} else {
			throw new RequestParameterException(IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY.errCode(),
					IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY.errMsg());
		}
		Map<String,String> userDeatils = new HashMap<String,String>();
		userDeatils.put(ServiceCommonConstants.USERID,userId) ;
		userDeatils.put(ServiceCommonConstants.NAME,name) ;
		userDeatils.put(ServiceCommonConstants.EMAIL_TAG,email);
		return userDeatils;
	}

   @Override
   @Timed
   @Marked
   public boolean resetPasswordHelper(ResetPasswordRequest request) {

      com.snapdeal.ums.ext.user.GetUserByIdRequest getUserRequest = new com.snapdeal.ums.ext.user.GetUserByIdRequest(
               Integer.parseInt(request.getUserId()));
      com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = userClientService
               .getUserById(getUserRequest);

      UserSRO user = umsResponse.getGetUserById();

      if (user == null) {
         throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
                  IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
      }
      user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(request.getNewPassword()));

      com.snapdeal.ums.ext.user.UpdateUserRequest updateRequest = new com.snapdeal.ums.ext.user.UpdateUserRequest(
               user);
      com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = userClientService
               .updateUser(updateRequest);

      if (!updateResponse.isSuccessful()) {
         throw new IMSServiceException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
                  IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
      }
      return true;
   }

   /*
    * @Override
    * public MobileVerificationStatusResponse isMobileVerified(
    * MobileVerificationStatusRequest request) throws IMSServiceException {
    * // TODO Auto-generated method stub
    * return null;
    * }
    */

	@Override
	public GetUserResponse getUserByMobile(GetUserByMobileRequest request) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());

	}

	@Override
	public UpdateMobileNumberResponse updateMobileNumber(
			UpdateMobileNumberRequest request) throws ValidationException {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public OTPResponse createUserByMobile(
			CreateUserMobileGenerateRequest request) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public CreateUserResponse verifyUserWithMobile(
			CreateUserMobileVerifyRequest request) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public IsVerifiedMobileExistResponse isMobileExist(
			IsVerifiedMobileExistRequest request) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public UniqueMobileVerificationResponse verifyUniqueMobile(
			UniqueMobileVerificationRequest request) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}


	@Override
	@Timed
	@Marked
	@Logged
	public MobileVerificationStatusResponse isMobileVerified(MobileVerificationStatusRequest request)
			throws IMSServiceException {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	@Timed
	@Marked
	public SdFcPasswordEntity putSdFcHashedPasswordByEmailId(String emailId,String password) {
		SdFcPasswordEntity entity = new SdFcPasswordEntity();
		entity.setSdFcHashedPassword(PasswordHashServiceUtil
				.getFcHashedPassword(password));
			entity.setSdSdHashedPassword(PasswordHashServiceUtil
					.getSdHashedPassword(password));
		return entity;
	}

    @Override
    @Timed
    @Marked
   public UserDTO getUserByEmail(String emailId) {
        UserSRO userFromUMSUsingEmail = getUserFromUMSUsingEmail(emailId);
        return mapUserSROToDTO(userFromUMSUsingEmail);
    }
    
    /**
     * Common method that updates the password after validation.
     * 
     * @param token
     * @return
     */
    @Override
    protected boolean changePasswordCommon(ChangePasswordRequest request, String userId) {

       com.snapdeal.ums.ext.user.GetUserByIdRequest getUserRequest = new com.snapdeal.ums.ext.user.GetUserByIdRequest(
             Integer.parseInt(userId));

       com.snapdeal.ums.ext.user.GetUserByIdResponse umsResponse = userClientService
             .getUserById(getUserRequest);

       UserSRO user = umsResponse.getGetUserById();
       if (user == null) {
          throw new IMSServiceException(
                IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
                IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
       }

       if (!user.getPassword().equals(
             IMSEncryptionUtil.getSDEncryptedPassword(request
                   .getOldPassword()))) {

          throw new IMSServiceException(
                IMSServiceExceptionCodes.WRONG_PASSWORD.errCode(),
                IMSServiceExceptionCodes.WRONG_PASSWORD.errMsg());
       }
       user.setPassword(IMSEncryptionUtil.getSDEncryptedPassword(request
             .getNewPassword()));

       com.snapdeal.ums.ext.user.UpdateUserRequest updateRequest = new com.snapdeal.ums.ext.user.UpdateUserRequest(
             user);

       com.snapdeal.ums.ext.user.UpdateUserResponse updateResponse = userClientService
             .updateUser(updateRequest);
       if (!updateResponse.isSuccessful()) {
          throw new IMSServiceException(
                IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
                IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
       }
       
       return updateResponse.isSuccessful();
    }

	private UserSRO createUserSro(Date dob, String firstName, String midlename,
			String lastName, String displayName, Gender gender ,String mobileNumber) {
		UserSRO user = new UserSRO();
		user.setBirthday(dob);
		user.setFirstName(firstName);
		user.setMiddleName(midlename);
		user.setLastName(lastName);
		user.setDisplayName(displayName);
		if (null != gender) {
			user.setGender(gender.getValue());
		}
		if(StringUtils.isNotBlank(mobileNumber)){
			user.setMobile(mobileNumber);
		}
		return user;
	}
	private CreateUserWithDetailsResponse createUser(
			String emailId, 
			String passwd, 
			Role role, 
			boolean autoCreated,UserSRO user) {
		return 	createUser(emailId, passwd, role, autoCreated, null, false , user);
	}
	private CreateUserWithDetailsResponse createUser(
			String emailId, 
			String passwd, 
			Role role, 
			boolean autoCreated,
			String purpose,
			boolean isGuest,
			UserSRO user) {

		CreateUserWithDetailsRequest createUserWithDetails  = new CreateUserWithDetailsRequest() ;

		user.setEmail(StringUtils.lowerCase(emailId));
		user.setPassword(passwd);
		createUserWithDetails.setAutocreated(autoCreated);

		// If source is null in source, client name is set as source.
		if(isGuest){
			createUserWithDetails.setSource(purpose);
		}
		if (user.getSource() == null) {
			createUserWithDetails.setSource("IMS_PASS_THROUGH");
		}else{
			createUserWithDetails.setSource(user.getSource());
		}
		createUserWithDetails.setInitialRole(role);
		createUserWithDetails.setUserWithPlainPassword(user); 

		return  userClientService.createUserWithDetails(createUserWithDetails) ;	
	}
	@Override
	@Timed
	@Marked
	public String getPasswordByEmail(String emailId){
		UserSRO userFromUMSUsingEmail = getUserFromUMSUsingEmail(emailId);
		return userFromUMSUsingEmail.getPassword();
	}

	@Override
	public CreateUserResponse createUserByEmailAndMobile(CreateUserEmailMobileRequest request) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode());
	}

	@Override
	public CreateSocialUserWithMobileResponse createSocialUserWithMobile(
			CreateSocialUserWithMobileRequest request) {

		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode());
	}

	@Override
	public CreateUserResponse verifySocialUserWithMobile(
			CreateUserMobileVerifyRequest request) {

		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode());
	}

	@Override
	public void createWallet(String userId, Merchant merchant) {
		// TODO Auto-generated method stub

		throw new IMSServiceException(
				IMSServiceExceptionCodes.INVALID_CALL.errCode(),
				IMSServiceExceptionCodes.INVALID_CALL.errCode());

	}

	@Override
	void setNotifier(GenerateOTPServiceRequest request) {
		request.setSendOtpBy(SendOTPByEnum.SNAPDEAL);
	}

	@Override
	void setNotifier(EmailMessage request) {
		request.setEmailSendBy(SendOTPByEnum.SNAPDEAL.toString());
	}

	@Override
	String getNotifier() {
		return SendOTPByEnum.SNAPDEAL.toString();
	}

	@Override
	public GetIMSUserVerificationUrlResponse getIMSUserVerificationUrl(
			GetIMSUserVerificationUrlRequest request, Upgrade upgradeStatus, String userId) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public void createNotificationOnMigrationStateChange(String userId,
			String email, WalletUserMigrationStatus status) {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.INVALID_CALL.errCode(),
				IMSServiceExceptionCodes.INVALID_CALL.errCode());
	}

	@Override
	public OTPResponse createUserByMobileOnly(CreateUserWithMobileOnlyRequest request) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());

	}

	@Override
	public CreateUserResponse verifyUserWithMobileOnly(CreateUserMobileVerifyRequest request) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public MobileOnlyResponse isMobileOnly(MobileOnlyRequest mobileOnlyRequest) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

}
