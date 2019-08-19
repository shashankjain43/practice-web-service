package com.snapdeal.ims.client.impl;


import org.apache.commons.codec.binary.Base64;
import org.apache.mina.http.api.HttpMethod;

import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.enums.ConfigureUserBasedOn;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.CloseAccountByEmailRequest;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.request.CreateGuestUserEmailRequest;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateSocialUserWithMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.CreateUserMobileGenerateRequest;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.CreateUserWithMobileOnlyRequest;
import com.snapdeal.ims.request.GetIMSUserVerificationUrlRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsTokenValidRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.MobileOnlyRequest;
import com.snapdeal.ims.request.ResendEmailVerificationLinkRequest;
import com.snapdeal.ims.request.UpdateMobileNumberRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.response.CloseAccountResponse;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
import com.snapdeal.ims.response.CreateSocialUserWithMobileResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.GetIMSUserVerificationUrlResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsTokenValidResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.IsVerifiedMobileExistResponse;
import com.snapdeal.ims.response.MobileOnlyResponse;
import com.snapdeal.ims.response.OTPResponse;
import com.snapdeal.ims.response.ResendEmailVerificationLinkResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UpdateMobileNumberResponse;
import com.snapdeal.ims.response.VerifyUserResponse;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

public class UserServiceClientImpl extends AbstractClientImpl implements IUserServiceClient {

	@Override
	public CreateUserResponse createUserWithEmail(CreateUserEmailRequest request) 
			throws ServiceException,HttpTransportException {

		return prepareResponse(request,
				CreateUserResponse.class, 
				HttpMethod.POST,
				RestURIConstants.CREATE_USERS_EMAIL);
	}

	@Override
	public CreateUserResponse createUserWithEmailAndMobile(CreateUserEmailMobileRequest request) 
			throws ServiceException,HttpTransportException {

		return prepareResponse(request,
				CreateUserResponse.class, 
				HttpMethod.POST,
				RestURIConstants.CREATE_USERS_EMAIL_MOBILE);
	}

	public OTPResponse createUserWithMobile(CreateUserMobileGenerateRequest request)
			throws ServiceException, HttpTransportException {

		return prepareResponse(request,
				OTPResponse.class, 
				HttpMethod.POST,
				RestURIConstants.CREATE_USERS_MOBILE_GENERATE);
	}

	@Override
	public CreateUserResponse verifyUserWithMobile(CreateUserMobileVerifyRequest request)
			throws ServiceException, HttpTransportException {

		return prepareResponse(request,
				CreateUserResponse.class, 
				HttpMethod.POST,
				RestURIConstants.CREATE_USERS_MOBILE_VERIFY);
	}

	@Override
	public CreateGuestUserResponse createGuestUserWithEmail(CreateGuestUserEmailRequest request)
			throws ServiceException, HttpTransportException {

		return prepareResponse(request,
				CreateGuestUserResponse.class, 
				HttpMethod.POST,
				RestURIConstants.CREATE_GUEST_USERS);
	}

	@Override
	public VerifyUserResponse verifyUser(VerifyUserRequest request)
			throws ServiceException, HttpTransportException {
		if (StringUtils.isBlank(request.getCode())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.CODE_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.CODE_IS_BLANK.errCode());

		}
		return prepareResponse(request,
				VerifyUserResponse.class, 
				HttpMethod.GET,
				RestURIConstants.CREATE_GUEST_USERS_VERIFY+ "/" + request.getCode());
	}

	@Override
	public SocialUserResponse createOrLoginUserWithSocial(CreateSocialUserRequest request)
			throws ServiceException, HttpTransportException {

		return prepareResponse(request,
				SocialUserResponse.class, 
				HttpMethod.POST,
				RestURIConstants.CREATE_SIGN_IN_SOCIAL);
	}

	@Override
	public GetUserResponse updateUserById(UpdateUserByIdRequest request)
			throws ServiceException, HttpTransportException {

		if (StringUtils.isBlank(request.getUserId())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.USER_ID_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.USER_ID_IS_BLANK.errCode());
		}

		return prepareResponse(request,
				GetUserResponse.class, 
				HttpMethod.PUT,
				RestURIConstants.USERS + "/" + request.getUserId());
	}

	@Override
	public GetUserResponse updateUserbyToken(UpdateUserByTokenRequest request)
			throws ServiceException, HttpTransportException {
		if (StringUtils.isBlank(request.getToken())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.TOKEN_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.TOKEN_IS_BLANK.errCode());
		}

		return prepareResponse(request,
				GetUserResponse.class, 
				HttpMethod.PUT,
				RestURIConstants.GET_UPDATE_USER_TOKEN + "/" 
						+ request.getToken());
	}

	@Override
	public IsUserExistResponse isUserExist(IsUserExistRequest request) 
			throws ServiceException, HttpTransportException {
		if (StringUtils.isBlank(request.getUserId())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.USER_ID_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.USER_ID_IS_BLANK.errCode());
		}
		return prepareResponse(request,
				IsUserExistResponse.class, 
				HttpMethod.GET,
				RestURIConstants.IS_USER_EXIST + request.getUserId());
	}

	@Override
	public IsEmailExistResponse isEmailExist(IsEmailExistRequest request) 
			throws ServiceException, HttpTransportException {

		if (StringUtils.isBlank(request.getEmailId())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errCode());
		}
		return prepareResponse(request,
				IsEmailExistResponse.class, 
				HttpMethod.GET,
				RestURIConstants.IS_EMAIL_EXIST + new String(Base64.encodeBase64String(request.getEmailId().getBytes())));
	}

	@Override
	public IsVerifiedMobileExistResponse isVerifiedMobileExist(IsVerifiedMobileExistRequest request)
			throws ServiceException, HttpTransportException {

		if (StringUtils.isBlank(request.getMobileNumber())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK.errCode());
		}
		return prepareResponse(request,
				IsVerifiedMobileExistResponse.class, 
				HttpMethod.GET,
				RestURIConstants.IS_MOBILE_EXIST + request.getMobileNumber());
	}

	@Override
	public GetUserResponse getUserByEmail(GetUserByEmailRequest request) 
			throws ServiceException, HttpTransportException {

		if (StringUtils.isBlank(request.getEmailId())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errCode());
		} 
		return prepareResponse(request,
				GetUserResponse.class, 
				HttpMethod.GET,
				RestURIConstants.GET_USER_EMAIL + "/" 
						+ new String(Base64.encodeBase64String(request.getEmailId().getBytes())));

	}

	@Override
	public GetUserResponse getUserById(GetUserByIdRequest request) 
			throws ServiceException, HttpTransportException {

		if (StringUtils.isBlank(request.getUserId())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.USER_ID_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.USER_ID_IS_BLANK.errCode());
		} 
		return prepareResponse(request,
				GetUserResponse.class, 
				HttpMethod.GET,
				RestURIConstants.USERS + "/" + request.getUserId());
	}

	@Override
	public CloseAccountResponse closeAccount (CloseAccountByEmailRequest request) 
			throws ServiceException, HttpTransportException {

		if (StringUtils.isBlank(request.getEmailId())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errCode());
		} 
		return prepareResponse(request,
				CloseAccountResponse.class, 
				HttpMethod.DELETE,
				RestURIConstants.GET_USER_EMAIL + "/" 
						+ new String(Base64.encodeBase64String(request.getEmailId().getBytes())));

	}

	@Override
	public GetUserResponse getUserByVerifiedMobile(GetUserByMobileRequest request) 
			throws ServiceException, HttpTransportException {

		if (StringUtils.isBlank(request.getMobileNumber())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK.errCode());
		}

		return prepareResponse(request,
				GetUserResponse.class, 
				HttpMethod.GET,
				RestURIConstants.GET_USER_MOBILE + "/" 
						+ request.getMobileNumber());
	}

	@Override
	public GetUserResponse getUserByToken(GetUserByTokenRequest request) 
			throws ServiceException, HttpTransportException {

		if (StringUtils.isBlank(request.getToken())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.TOKEN_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.TOKEN_IS_BLANK.errCode());
		}
		return prepareResponse(request,
				GetUserResponse.class, 
				HttpMethod.GET,
				RestURIConstants.GET_UPDATE_USER_TOKEN + "/" 
						+ request.getToken());
	}

	@Override
	public UpdateMobileNumberResponse updateMobileNumber(UpdateMobileNumberRequest request)
			throws ServiceException, HttpTransportException {

		return prepareResponse(request,
				UpdateMobileNumberResponse.class, 
				HttpMethod.PUT,
				RestURIConstants.USERS +
				RestURIConstants.UPDATE_MOBILE);

	}

	@Override
	public ConfigureUserStateResponse configureUserState(ConfigureUserStateRequest request)
			throws ServiceException, HttpTransportException {
		if(StringUtils.isNotBlank(request.getUserId())){
			request.setConfigureUserBasedOn(ConfigureUserBasedOn.USER_ID);
		}else if(StringUtils.isNotBlank(request.getEmailId())){
			request.setConfigureUserBasedOn(ConfigureUserBasedOn.EMAIL);
		}else if(StringUtils.isNotBlank(request.getToken())){
			request.setConfigureUserBasedOn(ConfigureUserBasedOn.TOKEN);
		}else if(StringUtils.isNotBlank(request.getMobileNumber())){
			request.setConfigureUserBasedOn(ConfigureUserBasedOn.MOBILE);
		}
		
		return prepareResponse(request,
				ConfigureUserStateResponse.class, 
				HttpMethod.PUT,
				RestURIConstants.USERS +
				RestURIConstants.ACTIVATE_STATE);

	}

	@Override
	public ResendEmailVerificationLinkResponse resendVerificationLink(
			ResendEmailVerificationLinkRequest request) throws ServiceException, HttpTransportException {
		return prepareResponse(request,
				ResendEmailVerificationLinkResponse.class, HttpMethod.POST,
				RestURIConstants.USERS + RestURIConstants.RESEND_VERIFY_EMAIL);

	}

	@Override
	public CreateSocialUserWithMobileResponse createSocialUserWithMobile(
			CreateSocialUserWithMobileRequest request) throws ServiceException,
	HttpTransportException {

		return prepareResponse(request,
				CreateSocialUserWithMobileResponse.class, HttpMethod.POST,
				RestURIConstants.USERS + RestURIConstants.CREATE_SOCIAL_USER_WITH_MOBILE);
	}

	@Override
	public CreateUserResponse verifySocialUserWithMobile(
			CreateUserMobileVerifyRequest request) throws ServiceException,
	HttpTransportException {

		return prepareResponse(request,
				CreateUserResponse.class, HttpMethod.POST,
				RestURIConstants.USERS + RestURIConstants.VERIFY_SOCIAL_USER_WITH_MOBILE);
	}
	@Override
	public IsTokenValidResponse validateToken(IsTokenValidRequest request) throws ServiceException,
	HttpTransportException {

		if (StringUtils.isBlank(request.getToken())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.TOKEN_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.TOKEN_IS_BLANK.errCode());
		} 
		return prepareResponse(request,
				IsTokenValidResponse.class, 
				HttpMethod.GET,
				RestURIConstants.USERS+RestURIConstants.VALIDATE_TOKEN + "/" 
						+ request.getToken());
	}
	@Override
	public GetIMSUserVerificationUrlResponse getIMSUserVerificationUrl(
			GetIMSUserVerificationUrlRequest request) throws ServiceException,
	HttpTransportException {
		return prepareResponse(request, GetIMSUserVerificationUrlResponse.class, HttpMethod.POST,
				RestURIConstants.USERS + RestURIConstants.GET_IMS_USER_VERIFICATION_URL);
	}

	
	@Override
	public OTPResponse createMobileOnlyUserGenerateOTP(CreateUserWithMobileOnlyRequest request)
			throws ServiceException, HttpTransportException {
		return prepareResponse(request, OTPResponse.class,HttpMethod.POST,
				RestURIConstants.USERS + "/mobileOnly/generate");
	}

	@Override
	public CreateUserResponse loginWithOtp(CreateUserMobileVerifyRequest request)
			throws ServiceException, HttpTransportException {
		return prepareResponse(request, CreateUserResponse.class,HttpMethod.POST,RestURIConstants.USERS + "/mobileOnly/verify");
	}

	@Override
	public MobileOnlyResponse isMobileOnly(MobileOnlyRequest request) throws ServiceException, HttpTransportException {
		if (StringUtils.isBlank(request.getMobileNumber())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK.errCode());
		} 
		return prepareResponse(request, MobileOnlyResponse.class,HttpMethod.GET,RestURIConstants.USERS + "/exists/mobileOnly/{mobileNumber}".replace("{mobileNumber}", request.getMobileNumber()));
	}
}