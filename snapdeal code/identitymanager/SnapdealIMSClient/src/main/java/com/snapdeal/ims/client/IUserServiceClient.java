package com.snapdeal.ims.client;

import javax.validation.Valid;


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

public interface IUserServiceClient {

   /**
    * It is an API for creating new user with email.
    */
   public CreateUserResponse createUserWithEmail(CreateUserEmailRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API for creating new user with email and mobile mandatory. 
    * 
    */
   public CreateUserResponse createUserWithEmailAndMobile(CreateUserEmailMobileRequest request)
      throws ServiceException, HttpTransportException;
   /**
    * It is an API for creating new user(in interim state) with mobile generate
    * otp.
    */
   public OTPResponse createUserWithMobile(CreateUserMobileGenerateRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API for verifying an interim existing user through OTP which was
    * created through {@link createUserWithMobile}.
    */
   public CreateUserResponse verifyUserWithMobile(CreateUserMobileVerifyRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API for creating guest user with email.
    * Guest user might be created through web, in that case user will agree on
    * T&C's
    * There are cases where user will be created offline and for that any one can
    * use this api to create user, though we expect api users to pass the
    * purpose of creating user.
    */
   public CreateGuestUserResponse createGuestUserWithEmail(CreateGuestUserEmailRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API for verifying guest user.
    */
   public VerifyUserResponse verifyUser(VerifyUserRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API for creating/updating a user with social id.
    * We return token and user details, caller might use this information
    * for sign in.
    */
   public SocialUserResponse createOrLoginUserWithSocial(CreateSocialUserRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to update the user info using user id.
    */
   public GetUserResponse updateUserById(UpdateUserByIdRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to update the user info using token.
    */
   public GetUserResponse updateUserbyToken(UpdateUserByTokenRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to identify if an user exist.
    */
   public IsUserExistResponse isUserExist(IsUserExistRequest request) 
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to identify if email exist.
    */
   public IsEmailExistResponse isEmailExist(IsEmailExistRequest request) 
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to identify if mobile exist.
    */
   public IsVerifiedMobileExistResponse isVerifiedMobileExist(IsVerifiedMobileExistRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to get user info via email.
    */
   public GetUserResponse getUserByEmail(GetUserByEmailRequest request) 
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to get user info via user id.
    */
   public GetUserResponse getUserById(GetUserByIdRequest request) 
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to get the user info via mobile number.
    */
   public GetUserResponse getUserByVerifiedMobile(GetUserByMobileRequest request) 
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to get the user info via token.
    */
   public GetUserResponse getUserByToken(GetUserByTokenRequest request) 
      throws ServiceException, HttpTransportException;

   /**
    * It is a user identifier. This api is for updating user mobile number.
    */
   public UpdateMobileNumberResponse updateMobileNumber(UpdateMobileNumberRequest request)
      throws ServiceException, HttpTransportException;
   /**
   	*to enable and disable the user.set the flag true or false in userSro.
    */
   public ConfigureUserStateResponse configureUserState(ConfigureUserStateRequest request)
			 throws ServiceException, HttpTransportException ;
   
	/**
	 * It is an API used to resend Email Verification link to the user info via email.
	 */

	public ResendEmailVerificationLinkResponse resendVerificationLink(ResendEmailVerificationLinkRequest request) 
			throws ServiceException, HttpTransportException;
	
	/**
	 * It is an API used to create social user with mobile, in response it will 
	 * return otpId and userAccountStatus
	 */
	public CreateSocialUserWithMobileResponse createSocialUserWithMobile(CreateSocialUserWithMobileRequest request)
	         throws ServiceException, HttpTransportException;

	/**
    * It is an API used to verify social user with mobile, in response it will return token
    * token
    */
   public CreateUserResponse verifySocialUserWithMobile(CreateUserMobileVerifyRequest request)
            throws ServiceException, HttpTransportException;
   /**
    * API to validate token
    * @param request
    * @return
    * @throws ServiceException
    * @throws HttpTransportException
    */
   public IsTokenValidResponse validateToken(IsTokenValidRequest request) throws ServiceException,
		HttpTransportException ;

   /**
    * It is an API to generate IMS User Verification Url so as to verify the
    * email and complete the upgrade.
    */
   public GetIMSUserVerificationUrlResponse getIMSUserVerificationUrl(
            GetIMSUserVerificationUrlRequest request) throws ServiceException,
            HttpTransportException;

   
   public CloseAccountResponse closeAccount(CloseAccountByEmailRequest request)
		throws ServiceException, HttpTransportException;

   public OTPResponse createMobileOnlyUserGenerateOTP(CreateUserWithMobileOnlyRequest request) 
		throws ServiceException, HttpTransportException;
   
   public CreateUserResponse loginWithOtp(CreateUserMobileVerifyRequest request) throws ServiceException, HttpTransportException;
   
   public MobileOnlyResponse isMobileOnly(MobileOnlyRequest request) throws ServiceException, HttpTransportException;;
}