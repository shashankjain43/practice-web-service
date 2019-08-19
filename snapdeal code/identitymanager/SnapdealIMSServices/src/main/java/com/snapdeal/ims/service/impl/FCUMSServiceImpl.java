package com.snapdeal.ims.service.impl;


import com.freecharge.umsclient.exception.UmsException;
import com.freecharge.umsclient.request.vo.ChangePassword;
import com.freecharge.umsclient.request.vo.CreateFcUser;
import com.freecharge.umsclient.request.vo.CreateSocial;
import com.freecharge.umsclient.request.vo.ForgotPassword;
import com.freecharge.umsclient.request.vo.UpdatePassword;
import com.freecharge.umsclient.request.vo.UpdateUserById;
import com.freecharge.umsclient.response.vo.SocialUserVO;
import com.freecharge.umsclient.response.vo.StatusVO;
import com.freecharge.umsclient.response.vo.User;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.dto.UserSocialDetailsDTO;
import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.enums.AccountOwner;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.enums.SocialSource;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSValidationExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.ChangePasswordRequest;
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
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.request.dto.UserDetailsRequestDto;
import com.snapdeal.ims.request.dto.UserRequestDto;
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
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.dto.SocialInfo;
import com.snapdeal.ims.service.dto.UserDTO;
import com.snapdeal.ims.service.provider.FCUmsProvider;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utils.PasswordHashServiceUtil;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.service.Notifier;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.metrics.util.PaymentConstants;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("FCUmsService")
public class FCUMSServiceImpl extends AbstractUMSService {

	@Autowired
	private IOTPService otpService;

	@Autowired
	private IUserVerificationDetailsDao userVerificationDetailsDao;

	@Autowired
	private Notifier notifier;

	@Autowired
	private IActivityDataService activityDataService;

	@Autowired
	private ITokenService tokenService;

	@Autowired(required = false)
	private IGlobalTokenService globalTokenService;

	@Autowired
	private AuthorizationContext authorizationContext;

	@Autowired
	private FCUmsProvider fcUms;

	@Autowired
	private ILoginUserService loginUserService;

	/*TODO
	 * issues
	 * 1.)create SocialUser .what to set Fcid.
	 * 2.)src is not getting set.
	 * 3.)password matching issue in loginUser.
	 * 4.)mobile number is mandatory on fc.but on our create request we dont have mobileNumber.
	 * 5.) why we are sending verification link on fc side.i think there is no need of that.
	 */
	/**
	 * flow:
	 * 1.) get user form userid from fc.
	 * 2.)check firstname+middleName+lastName all are not null.
	 * 3.)update name on fc side.
	 * 4.)create response using updated name.
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public GetUserResponse updateUser(UpdateUserByIdRequest imsRequest) {
		Integer userId = validateAndGetUserId(imsRequest.getUserId());
		String appRequestId = getRequestId();
		User user = getUserById(userId);
		String firstName = imsRequest.getUserDetailsRequestDto().getFirstName();
		String middleName = imsRequest.getUserDetailsRequestDto().getMiddleName();
		String lastName = imsRequest.getUserDetailsRequestDto().getLastName();

		GetUserResponse userDetailsResponse = new GetUserResponse();
		// if all are null no need to update.
		if (null != firstName || null != middleName || null != lastName) {
			user.setName(getFullName(firstName, middleName, lastName));
			UpdateUserById updateUserByIdRequest = new UpdateUserById();
			updateUserByIdRequest.setName(user.getName());
			updateUserByIdRequest.setUserId(user.getUserId());
			updateUserByIdRequest.setMobileNo(user.getMobileNo());
			updateUserByIdRequest.setTraceId(appRequestId);
			StatusVO status = fcUms.getUms().updateUser(updateUserByIdRequest);

			if (status.getStatus().equalsIgnoreCase("OK")) {
				userDetailsResponse.setUserDetails(createUserDetails(user));
				return userDetailsResponse;
			} else {
				throw new IMSServiceException(
						IMSServiceExceptionCodes.ERROR_ON_UPDATING_USER.errCode(), 
						IMSServiceExceptionCodes.ERROR_ON_UPDATING_USER.errMsg());
			}
		} else {
			userDetailsResponse.setUserDetails(createUserDetails(user));
			return userDetailsResponse;   
		}
	}

	@Override
	@Timed
	@Marked
	@Logged
	public CreateUserResponse createUserByEmail(
			CreateUserEmailRequest imsRequest) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	/*
	 * get userid from token. then using this token fetch user from fc.
	 * @see com.snapdeal.ims.service.IUMSService#getUserByToken(com.snapdeal.ims.request.GetUserByTokenRequest)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public GetUserResponse getUserByToken(GetUserByTokenRequest request) {
		//tested
		activityDataService.validateToken(request.getToken());
		String userId = tokenService.getUserIdByToken(request.getToken());
		GetUserByIdRequest userByIdRequest = new GetUserByIdRequest();
		userByIdRequest.setUserId(userId);
		return getUser(userByIdRequest);
	}

	/*
	 * get userid from token,then call update user.
	 * @see com.snapdeal.ims.service.IUMSService#updateUserByToken(com.snapdeal.ims.request.UpdateUserByTokenRequest)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public GetUserResponse updateUserByToken(UpdateUserByTokenRequest request) {
		activityDataService.validateToken(request.getToken());
		String userId = tokenService.getUserIdByToken(request.getToken());
		UpdateUserByIdRequest updateUserByIdRequest = new UpdateUserByIdRequest();
		updateUserByIdRequest.setUserId(userId);
		UserDetailsRequestDto dto = new UserDetailsRequestDto();
		dto.setFirstName(request.getUserDetailsRequestDto().getFirstName());
		dto.setMiddleName(request.getUserDetailsRequestDto().getMiddleName());
		dto.setLastName(request.getUserDetailsRequestDto().getLastName());
		updateUserByIdRequest.setUserDetailsRequestDto(dto);
		return updateUser(updateUserByIdRequest);
	}

	@Override
	@Timed
	@Marked
	@Logged
	public SocialUserResponse createSocialUser(
			CreateSocialUserRequest imsRequest) {
		String emailId = imsRequest.getSocialUserDto().getEmailId();
		imsRequest.getSocialUserDto().setMobileNumber("0000000000");
		activityDataService.setActivityDataByEmailId(emailId);
		SocialUserRequestDto dto = imsRequest.getSocialUserDto();
		if (StringUtils.isBlank(dto.getSocialSrc())) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.SOCIAL_SOURCE_IS_BLANK.errCode(),
					IMSRequestExceptionCodes.SOCIAL_SOURCE_IS_BLANK.errMsg());
		}
		IsEmailExistRequest emailRequest = new IsEmailExistRequest();
		emailRequest.setEmailId(emailId);
		IsEmailExistResponse isEmailResponse = isEmailExist(emailRequest);
		int userId ;
		String appRequestId = getRequestId();
		if(!isEmailResponse.isExist()){
			CreateSocial createSocialUserRequest = fillFCSocialUserRequestFromIMSRequest(dto);
			printFCUMSObject("Request", createSocialUserRequest);
			createSocialUserRequest.setTraceId(appRequestId);
			userId = fcUms.getUms().createUser(createSocialUserRequest);
		} else {
			User user = getFCUserByEmail(emailId);
			userId = user.getUserId();
			if(!user.getIsActive()){
				tokenService.signOutUser(String.valueOf(userId));
				throw new IMSServiceException(IMSServiceExceptionCodes.USER_IS_DISABLED.errCode(),
						IMSServiceExceptionCodes.USER_IS_DISABLED.errMsg());
			}
		}
		if(!StringUtils.isBlank(String.valueOf(userId))) {
			SocialUserVO socialUserV0 = getUserWithSocialInfo(emailId);
			User user = socialUserV0.getUserVO();
			TokenInformationDTO loginTokenResponse = createTokenOnLogin(String
					.valueOf(user.getUserId()), user.getEmail());
			UserSocialDetailsDTO socialDTO=createUserSocialDetailsDTO(socialUserV0,imsRequest);
			SocialUserResponse createSocialResponse = new SocialUserResponse();
			UserDetailsDTO userDetailsDTO=createUserDetails(user);
			if (socialDTO != null) {
				if (SocialSource.FACEBOOK == socialDTO.getSocialSource()) {
					userDetailsDTO.setFbSocialId(socialDTO.getSocialId());

				} else if (SocialSource.GOOGLE ==socialDTO.getSocialSource()) {
					userDetailsDTO.setGoogleSocialId(socialDTO.getSocialId());
				}
			} 
			userDetailsDTO.setEmailVerified(true);
			createSocialResponse.setUserDetails(userDetailsDTO);
			activityDataService.setActivityDataByUserId(userDetailsDTO.getUserId());
			UpgradationInformationDTO upgradeDto = getUpgradationInformationDTO(emailId, 
					true,
					loginTokenResponse.getToken());
			createSocialResponse.setUpgradationInformation(upgradeDto);
			createSocialResponse.setUserSocialDetails(socialDTO);
			createSocialResponse.setTokenInformation(loginTokenResponse);
			return createSocialResponse;
		} else {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.REGISTRATION_FAILED.errCode(),
					IMSServiceExceptionCodes.REGISTRATION_FAILED.errMsg());
		}
	}

	private UserSocialDetailsDTO createUserSocialDetailsDTO(
			SocialUserVO socialUserV0, CreateSocialUserRequest imsRequest) {
		UserSocialDetailsDTO userSocialDetailsDTO = new UserSocialDetailsDTO();
		if (null != socialUserV0.getSocialInfo()
				&& !socialUserV0.getSocialInfo().isEmpty()) {
			for (com.freecharge.umsclient.response.vo.SocialInfo FCSocialinfo : socialUserV0
					.getSocialInfo())
				if (null != FCSocialinfo
				&& SocialSource.forName(imsRequest.getSocialUserDto()
						.getSocialSrc()) == SocialSource
						.forName(FCSocialinfo.getSocialSource())) {
					userSocialDetailsDTO.setSocialSource(SocialSource
							.forName(imsRequest.getSocialUserDto()
									.getSocialSrc()));
					userSocialDetailsDTO
					.setPhotoURL(FCSocialinfo.getPhotoURL());
					userSocialDetailsDTO
					.setSocialId(FCSocialinfo.getSocialId());
				}
		}
		return userSocialDetailsDTO;
	}	

	@Override
	@Timed
	@Marked
	@Logged
	public MobileVerificationStatusResponse isMobileVerified(
			MobileVerificationStatusRequest request) throws IMSServiceException {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	@Timed
	@Marked
	@Logged
	public VerifyUserResponse verifyGuestUser(
			VerifyUserRequest verifyGuestUserRequest) {
		throw new IMSServiceException(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	@Timed
	@Marked
	@Logged
	public IsUserExistResponse isUserExist(IsUserExistRequest request) {
		IsUserExistResponse response = new IsUserExistResponse();
		try {
			Integer userId = validateAndGetUserId(request.getUserId());
			getUserById(userId);
		} catch (UmsException | IMSServiceException e) {
			log.debug(e.getMessage());
			response.setExist(false);
			return response;
		}
		response.setExist(true);
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public IsEmailExistResponse isEmailExist(IsEmailExistRequest request) {
		//tested
		IsEmailExistResponse response = new IsEmailExistResponse();
		try {
			getUserByEmail(request.getEmailId());
		} catch (UmsException e) {
			log.debug(e.getMessage());
			response.setExist(false);
			return response;
		}
		response.setExist(true);
		return response;
	}

	@Override
	protected boolean registerAndUpdatePassword(String userId, String password,
			boolean hardRegister) {
		// get user corresponding to the code
		String appRequestId = getRequestId();
		User user = getUserById(Integer.valueOf(userId));
		UpdatePassword fcUpdatePassword = new UpdatePassword();
		fcUpdatePassword.setPassword(password);
		fcUpdatePassword.setUserId(user.getUserId());
		fcUpdatePassword.setTraceId(appRequestId);

		StatusVO status = fcUms.getUms().updatePassword(fcUpdatePassword);
		if (status.getStatus().equalsIgnoreCase("OK")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Timed
	@Marked
	@Logged
	public ConfigureUserStateResponse configureUserState(
			ConfigureUserStateRequest request) {
		User user = getUser(request);
		StatusVO status = null;
		String appRequestId = getRequestId() ;
		if(request.isEnable()){
			status = fcUms.getUms().activateUser(user.getEmail(),appRequestId);
		} else {
			status =fcUms.getUms().deActivateUser(user.getEmail(),appRequestId);
		}
		ConfigureUserStateResponse response = new ConfigureUserStateResponse();
		if(null != status && status.getStatus().equalsIgnoreCase("OK")) {
			response.setStatus(StatusEnum.SUCCESS);
		} else {
			response.setStatus(StatusEnum.FAILURE);
		}

		if(request.isEnable() == false){
			//hard signout user.
			tokenService.signOutUser(String.valueOf(user.getUserId())) ;
		}
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
	private User getUser(ConfigureUserStateRequest imsRequest) {
		User user = null;
		if (!StringUtils.isBlank(imsRequest.getUserId())) {
			try {
				user = getUserById(validateAndGetUserId(imsRequest.getUserId()));
			} catch(UmsException e) {
				log.debug(e.getMessage());
			}
		} else if (!StringUtils.isBlank(imsRequest.getToken())) {

			activityDataService.validateToken(imsRequest.getToken());
			String userId = tokenService.getUserIdByToken(imsRequest.getToken());

			if (StringUtils.isBlank(userId)) {
				throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errCode());
			}
			user = getUserById(validateAndGetUserId(userId));
		} else if (!StringUtils.isBlank(imsRequest.getEmailId())) {
			IMSServiceUtil.validateEmail(imsRequest.getEmailId());
			try {
				user = getFCUserByEmail(imsRequest.getEmailId());
			} catch(UmsException e) {
				log.debug(e.getMessage());
			}
		} else if (!StringUtils.isBlank(imsRequest.getMobileNumber())) {
			log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
			throw new IMSServiceException(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		}
		return user;
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

	@Override
	protected boolean changePasswordCommon(ChangePasswordRequest  request,String userId){
		getUserById(validateAndGetUserId(userId));

		// if user exists change Password....
		ChangePassword changePasswordRequest = new ChangePassword();
		changePasswordRequest.setUserId(Integer.parseInt(userId));
		changePasswordRequest.setOldPassword(request.getOldPassword());
		changePasswordRequest.setNewPassword(request.getNewPassword());
		changePasswordRequest.setTraceId(getRequestId());
		StatusVO status = fcUms.getUms().changePassword(changePasswordRequest);
		if (!status.getStatus().equalsIgnoreCase("OK")) {
			log.debug(status.getStatus());
			throw new IMSServiceException(
					IMSServiceExceptionCodes.WRONG_PASSWORD.errCode(),
					IMSServiceExceptionCodes.WRONG_PASSWORD.errMsg());
		} 

		return status.getStatus().equalsIgnoreCase("OK");
	}

	protected  Map<String,String> getUserId(ForgotPasswordRequest request){
		User user = null ;
		if (request.getEmailId() != null) {
			activityDataService.setActivityDataByEmailId(request.getEmailId());
			user = getFCUserByEmail(request.getEmailId());
			if(StringUtils.isNotBlank(request.getMobileNumber())
					&& !user.getMobileNo().equalsIgnoreCase(request.getMobileNumber())){
				throw new RequestParameterException(
						IMSServiceExceptionCodes.MISMATCH_EMAIL_ID_MOBILE.errCode(),
						IMSServiceExceptionCodes.MISMATCH_EMAIL_ID_MOBILE.errMsg());
			}
		}//TO DO add fetch user from mobile.currenlt no user fetch mobile condition.

		StatusVO status = forgotPassword(user);

		if(status.getStatus().equalsIgnoreCase("OK")){
			Map<String,String> userDetails = new HashMap<String,String>() ;
			userDetails.put(ServiceCommonConstants.USERID,String.valueOf(user.getUserId()));
			userDetails.put(ServiceCommonConstants.NAME, user.getName());
			userDetails.put(ServiceCommonConstants.EMAIL_TAG,user.getEmail());
			return userDetails;
		} else {       
			throw new IMSServiceException(
					IMSServiceExceptionCodes.ERROR_ON_FORGOT_PASSWORD.errCode(),
					IMSServiceExceptionCodes.ERROR_ON_FORGOT_PASSWORD.errMsg()) ;
		}


	}


	private StatusVO forgotPassword(User user) {
		ForgotPassword forgotPasswordRequest = new ForgotPassword();
		forgotPasswordRequest.setUserId(user.getUserId());
		//generate Random encryption key.
		String encryptionKey = RandomStringUtils.randomAlphabetic(10);
		forgotPasswordRequest.setEncryptKey(encryptionKey);

		//setting expiry date = currentDate + 1 day.
		Date today = new Date();

		Date expiryDate = new Date(today.getTime() + (1000 * 60 * 60 * 24));
		forgotPasswordRequest.setExpireTime(
				new SimpleDateFormat("dd-MM-yyyy").format(expiryDate));
		forgotPasswordRequest.setTraceId(getRequestId());
		StatusVO status = fcUms.getUms().forgotPassword(forgotPasswordRequest);
		return status;
	}

	@Override
	@Timed
	@Marked
	public boolean resetPasswordHelper(ResetPasswordRequest request) {

		User userObject = getUserById(Integer.parseInt(request.getUserId()));

		UpdatePassword fcUpdatePassword = new UpdatePassword();
		fcUpdatePassword.setPassword(request.getNewPassword());
		fcUpdatePassword.setUserId(userObject.getUserId());
		fcUpdatePassword.setTraceId(getRequestId());
		StatusVO status = fcUms.getUms().updatePassword(fcUpdatePassword);
		if (status.getStatus().equalsIgnoreCase("OK")) {
			return true;
		}
		return false;
	}

	private CreateSocial fillFCSocialUserRequestFromIMSRequest(
			SocialUserRequestDto dto) {
		CreateSocial createSocialUserRequest = new CreateSocial();
		createSocialUserRequest.setEmail(dto.getEmailId());
		createSocialUserRequest.setMobileNo(dto.getMobileNumber()); 
		createSocialUserRequest.setSocialExpiry(dto.getSocialExpiry());
		createSocialUserRequest.setSocialId(dto.getSocialId());
		createSocialUserRequest.setSocialImage(dto.getPhotoURL());                                                  // mandatory
		createSocialUserRequest.setSocialName(dto.getDisplayName()); 
		createSocialUserRequest.setSocialSecret(dto.getSocialSecret());
		createSocialUserRequest.setSocialToken(dto.getSocialToken());
		createSocialUserRequest.setSource(dto.getSocialSrc());
		return createSocialUserRequest;
	}

	private void printFCUMSObject(String type, Object user) {
		log.debug("UMS " + type + " :");
		if (log.isDebugEnabled()) {
			if (user == null) {
				log.debug("user is null ");
				return;
			}
			StringBuilder sb = new StringBuilder();
			Field[] fields = user.getClass().getDeclaredFields();
			sb.append(user.getClass().getSimpleName() + " [ ");
			for (Field f : fields) {
				String fName = f.getName();
				f.setAccessible(true);
				try {
					if (fName == "serialVersionUID"
							|| org.apache.commons.lang.StringUtils.containsIgnoreCase(fName, "password"))
						continue;
					sb.append(fName + " = " + f.get(user) + ", ");
				} catch (IllegalArgumentException e) {
					log.debug(e.getMessage());
				} catch (IllegalAccessException e) {
					log.debug(e.getMessage());
				}
			}
			sb.append("]");
			log.debug(user.toString());
			log.debug(sb.toString());
		}
	}

	private UserDetailsDTO createUserDetails(User user) {
		UserDetailsDTO userDetails = new UserDetailsDTO();
		userDetails.setUserId(String.valueOf(user.getUserId()));
		userDetails.setFcUserId(user.getUserId());
		userDetails.setEmailId(StringUtils.lowerCase(user.getEmail()));
		userDetails.setMobileNumber(user.getMobileNo());
		userDetails.setDisplayName(user.getName());
		userDetails.setFirstName(user.getName());
		userDetails.setAccountOwner(AccountOwner.FC);
		userDetails.setEnabledState(user.getIsActive() == null ? true : user.getIsActive());
		if(user.getMobileVerified()) {
			userDetails.setAccountState(ServiceCommonConstants.REGISTERED);
		} else{
			userDetails.setAccountState(ServiceCommonConstants.UNVERIFIED);
		}
		userDetails.setMobileVerified(user.getMobileVerified());
		if (null != user.getCreatedOn()
				&& org.apache.commons.lang.StringUtils.isNumeric(user.getCreatedOn())) {
			userDetails.setCreatedTime(new Timestamp(Long.valueOf(user.getCreatedOn())));
		}
		return userDetails;
	}

	private List<SocialInfo> fillSocialInfoFromFC(SocialUserVO socialUserV0,UserDTO dto) {
		List<SocialInfo> IMSSocialInfos = new ArrayList<SocialInfo>();
		if (null != socialUserV0.getSocialInfo() && !socialUserV0.getSocialInfo().isEmpty()) {
			for(com.freecharge.umsclient.response.vo.SocialInfo FCSocialinfo : socialUserV0.getSocialInfo())
				if(null !=  FCSocialinfo) {
					dto.setEmailVerified(true);
					SocialInfo IMSSocialInfo = new SocialInfo();
					SocialSource currentSource = SocialSource.forName(FCSocialinfo.getSocialSource());
					IMSSocialInfo.setSocialSrc(currentSource);
					IMSSocialInfo.setSocialId(FCSocialinfo.getSocialId());
					IMSSocialInfo.setSocialToken(FCSocialinfo.getSocialToken());
					IMSSocialInfo.setSocialSecret(FCSocialinfo.getSocialSecret());
					IMSSocialInfo.setSocialExpiry(FCSocialinfo.getSocialExpiry());
					IMSSocialInfo.setPhotoURL(FCSocialinfo.getPhotoURL());
					IMSSocialInfos.add(IMSSocialInfo);
					if (currentSource == SocialSource.GOOGLE) {
						dto.setGoogleSocialId(FCSocialinfo.getSocialId());
					} else if (currentSource == SocialSource.FACEBOOK) {
						dto.setFbSocialId(FCSocialinfo.getSocialId());
					}
				}
		}
		return IMSSocialInfos;
	}

	private CreateFcUser createFCUserRequestFromIMSRequest(
			CreateUserEmailMobileRequest imsRequest) {
		UserRequestDto dto = imsRequest
				.getUserRequestDto();
		CreateFcUser createFcUserRequest = new CreateFcUser();
		createFcUserRequest.setEmail(dto.getEmailId());
		String fullName = getFullName(dto.getFirstName(), dto.getMiddleName(), dto.getLastName());
		createFcUserRequest.setName(fullName);
		createFcUserRequest.setPassword(dto.getPassword());
		createFcUserRequest.setMobileNo(dto.getMobileNumber());
		createFcUserRequest.setTraceId(getRequestId());
		return createFcUserRequest;
	}

	/**
	 * Utility to split full name into firstName, middleName and last name.
	 * 
	 * @param fullName
	 * @return
	 */
	/*    private static String[] splitNameFromFullName(String fullName) {
        String[] names = org.apache.commons.lang.StringUtils.split(fullName,
                ServiceCommonConstants.FC_NAME_DELIM);
        if (null != names && names.length == 3) {
            for (int index = 0; index < names.length; index++) {
                names[index] = names[index].substring(1);
            }
            return names;
        } else {
            return null;
        }
    }*/

	/**
	 * In IMS, we have firstName, middleName and lastName. This information is
	 * stored in FC side using delimiter. <br/>
	 * FullName=F[firstName]#M[middleName]#L[lastName]
	 * 
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @return
	 */
	private String getFullName(String firstName, 
			String middleName,
			String lastName) {
		StringBuilder fullName = new StringBuilder();
		if(firstName!=null)
			fullName.append(firstName).append(ServiceCommonConstants.FC_NAME_DELIM);
		if(middleName!=null)
			fullName.append(middleName).append(ServiceCommonConstants.FC_NAME_DELIM);
		if(lastName!=null)
			fullName.append(lastName);
		return fullName.toString();
	}

	private User getFCUserByEmail(String email) {
		SocialUserVO userWithSocialInfo = getUserWithSocialInfo(email);
		User user = userWithSocialInfo.getUserVO();
		return user;
	}

	private SocialUserVO getUserWithSocialInfo(String email) {
		SocialUserVO userWithSocialInfo = fcUms.getUms().getUserWithSocialInfo(
				StringUtils.lowerCase(email), getRequestId());
		return userWithSocialInfo;
	}

	private User getUserById(int id) {
		User user = fcUms.getUms().getUser(id,getRequestId());
		return user;
	}

	@Override
	protected UserDTO validateUserCredential(LoginUserRequest imsRequest) {
		SocialUserVO socialUserV0 = getUserWithSocialInfo(imsRequest.getEmailId());
		User user = socialUserV0.getUserVO();
		String userId = String.valueOf(user.getUserId());
		loginUserService.isUserLocked(userId);
		if (user.getPlainPassword().equals(imsRequest.getPassword())) {
			// Delete user lock info from db if user enters correct password.
			loginUserService.deleteUserLockInfo(userId);
		} else {
			// Update db for attempts and lock user when maximum attempts done.
			loginUserService.updateUserLockInfo(userId);
			if (log.isWarnEnabled()) {
				log.warn(IMSValidationExceptionCodes.PASSWORD_DO_NOT_MATCH.errMsg());
			}
			throw new ValidationException(
					IMSValidationExceptionCodes.PASSWORD_DO_NOT_MATCH.errCode(),
					IMSValidationExceptionCodes.PASSWORD_DO_NOT_MATCH.errMsg());
		}
		return mapUserToDTO(socialUserV0);
	}

	/**
	 * Utility method to map {@link User} to {@link UserDTO}.
	 * 
	 * @param user
	 * @return
	 */
	private UserDTO mapUserToDTO(SocialUserVO socialUserV0) {
		UserDTO dto = new UserDTO();
		User user = socialUserV0.getUserVO();  
		dto.setAccountOwner(AccountOwner.FC);
		dto.setUserId(String.valueOf(user.getUserId()));
		dto.setFcUserId(user.getUserId());
		dto.setEmailId(StringUtils.lowerCase(user.getEmail()));
		dto.setMobileNumber(user.getMobileNo());
		dto.setFirstName(user.getName());
		dto.setDisplayName(dto.getFirstName());
		if(user.getMobileVerified()) {
			dto.setAccountState(ServiceCommonConstants.REGISTERED);
		} else {
			dto.setAccountState(ServiceCommonConstants.UNVERIFIED);
		}
		dto.setMobileVerified(user.getMobileVerified());
		if (null != user.getCreatedOn()
				&& org.apache.commons.lang.StringUtils.isNumeric(user.getCreatedOn())) {
			dto.setCreatedTime(new Timestamp(Long.valueOf(user.getCreatedOn())));
		}
		dto.setEnabledState(user.getIsActive() == null ? true : user.getIsActive());
		List<SocialInfo> socialInfos = fillSocialInfoFromFC(socialUserV0,dto);
		dto.setSocialInfo(socialInfos);
		return dto;
	}

	@Override
	protected UserDTO getUserById(String userId) {
		SocialUserVO socialUserV0 = fcUms.getUms().getUserWithSocialInfo(validateAndGetUserId(userId), getRequestId());
		return mapUserToDTO(socialUserV0);
	}

	@Override
	@Timed
	@Marked
	public SdFcPasswordEntity putSdFcHashedPasswordByEmailId(String emailId,
			String password) {
		SdFcPasswordEntity entity = new SdFcPasswordEntity();
		if(serviceProvider.isUpgradeEnabled()){
			entity.setFcFcHashedPassword(PasswordHashServiceUtil
					.getFcHashedPassword(password));
			entity.setFcSdHashedPassword(PasswordHashServiceUtil
					.getSdHashedPassword(password));
			return entity;
		}
		User user = getFCUserByEmail(emailId);
		if (user != null) {
			entity.setFcFcHashedPassword(PasswordHashServiceUtil
					.getFcHashedPassword(user.getPlainPassword()));
			entity.setFcSdHashedPassword(PasswordHashServiceUtil
					.getSdHashedPassword(user.getPlainPassword()));
		}		
		return entity;
	}

	@Override
	public GetUserResponse getUserByMobile(GetUserByMobileRequest request) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public UpdateMobileNumberResponse updateMobileNumber(UpdateMobileNumberRequest request)
			throws ValidationException {
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		/*    // token, mobileNumber, otp , OtpID,

      UpdateMobileNumberResponse response = new UpdateMobileNumberResponse();
      int userId = validateAndGetUserId(tokenService.getUserIdByToken(request
            .getToken()));
      // On IMS layer user is fetch from dao.here from fc.
      User user = fcUms.getUms().getUser(userId);
      VerifyOTPServiceRequest verifyOTPRequest = new VerifyOTPServiceRequest();
      verifyOTPRequest.setClientId(authorizationContext
            .get(IMSRequestHeaders.CLIENT_ID.toString()));
      verifyOTPRequest.setOtp(request.getOTP());
      verifyOTPRequest.setOtpId(request.getOtpId());
      verifyOTPRequest.setToken(request.getToken());

      VerifyOTPServiceResponse verifyOTPResponse = otpService
            .verifyOTP(verifyOTPRequest);
      if (OtpConstants.STATUS_SUCCESS.equalsIgnoreCase(verifyOTPResponse
            .getStatus())) {
         if (request.getMobileNumber().equals(
               verifyOTPResponse.getMobileNumber())
               || StringUtils.isBlank(request.getMobileNumber())) {
            user.setMobileNo(request.getMobileNumber());
         } else {
            // TODO throw exception.
         }
      }
      if (OtpConstants.STATUS_FAILURE.equalsIgnoreCase(verifyOTPResponse
            .getStatus())) {
         throw new IMSServiceException(
               IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errCode(),
               IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errMsg());
      }
      // TODO check what happens with name after Update.
      // TODO which to use updateByID or updateByEmail.currently i am using by
      // Id.
      UpdateUserById updateUserById = new UpdateUserById();
      updateUserById.setName(user.getName());
      updateUserById.setMobileNo(request.getMobileNumber());
      updateUserById.setUserId(userId);
      StatusVO status = fcUms.getUms().updateUser(updateUserById);
      if (status.getStatus().equalsIgnoreCase("OK")) {
         user.setMobileNo(request.getMobileNumber());
         UserDetailsDTO dto = createUserDetails(user);
         response.setUserDetails(dto);
      } else {
         // TODO throw exception.
      }

      return response;*/
	}

	@Override
	public OTPResponse createUserByMobile(CreateUserMobileGenerateRequest request) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public IsVerifiedMobileExistResponse isMobileExist(IsVerifiedMobileExistRequest request) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}


	@Override
	public CreateUserResponse verifyUserWithMobile(CreateUserMobileVerifyRequest request) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	public UniqueMobileVerificationResponse verifyUniqueMobile(
			UniqueMobileVerificationRequest request) {
		log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		throw new IMSServiceException(
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
				IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

	@Override
	@Timed
	@Marked
	public UserDTO getUserByEmail(String emailId) {
		SocialUserVO socialUserV0 = getUserWithSocialInfo(emailId);
		return mapUserToDTO(socialUserV0);
	}

	@Override
	@Timed
	@Marked
	public String getPasswordByEmail(String emailId) {
		User fcUserByEmail = getFCUserByEmail(emailId);
		return fcUserByEmail.getPlainPassword();
	}

	@Override
	@Timed
	@Marked
	public CreateUserResponse createUserByEmailAndMobile(
			CreateUserEmailMobileRequest imsRequest) {

		activityDataService.setActivityDataByEmailId(
				imsRequest.getUserRequestDto().getEmailId());

		// create user on fc...
		int userId = fcUms.getUms().createUser(
				createFCUserRequestFromIMSRequest(imsRequest));

		User user = getUserById(userId);


		//Not sending any mail in case of FC.
		/* createUserVerificationHelper(imsRequest.getUserRequestDto().getEmailId(), 
                                   imsRequest.getUserRequestDto().getFirstName(), 
                                   Integer.toString(userId), 
                                   false,
                                   null);*/

		// Step4. Set token (SignIn Credentials)
		TokenInformationDTO tokenResponse = createTokenOnLogin(Integer
				.toString(userId), imsRequest.getUserRequestDto().getEmailId());

		CreateUserResponse imsResponse = new CreateUserResponse();
		imsResponse.setTokenInformationDTO(tokenResponse);
		imsResponse.setUserDetails(createUserDetails(user));
		return imsResponse;
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

	protected void setV0FreechargeNotifier(GenerateOTPServiceRequest request)
	{	
		log.debug("setting notifier in otp request for forgot password as VOFREECHARGE");
		request.setSendOtpBy(SendOTPByEnum.V0FREECHARGE);
	}
	private String getRequestId(){
		return MDC.get((PaymentConstants.REQUEST_ID));
		/*return authorizationContext.get(IMSRequestHeaders.APP_REQUEST_ID
				.toString()) ;*/
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

	@Override
	public OTPResponse createUserByMobileOnly(CreateUserWithMobileOnlyRequest request) {
	      log.error(IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	      throw new IMSServiceException(
	            IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
	            IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}
}
