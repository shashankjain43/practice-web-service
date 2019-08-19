package com.snapdeal.ims.service;

import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.ChangePasswordRequest;
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
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.GetIMSUserVerificationUrlRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.MobileOnlyRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.ResendEmailVerificationLinkRequest;
import com.snapdeal.ims.request.ResetPasswordRequest;
import com.snapdeal.ims.request.UpdateMobileNumberRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.ChangePasswordWithLoginResponse;
import com.snapdeal.ims.response.CloseAccountResponse;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
import com.snapdeal.ims.response.CreateSocialUserWithMobileResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.ForgotPasswordResponse;
import com.snapdeal.ims.response.GetIMSUserVerificationUrlResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.GtokenSizeResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.IsVerifiedMobileExistResponse;
import com.snapdeal.ims.response.MobileOnlyResponse;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.OTPResponse;
import com.snapdeal.ims.response.ResendEmailVerificationLinkResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordWithLoginResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UpdateMobileNumberResponse;
import com.snapdeal.ims.response.VerifyUserResponse;

public interface IUserService {

   public CreateUserResponse createUserByEmail(CreateUserEmailRequest request);
   
   public CreateUserResponse createUserByEmailAndMobile(CreateUserEmailMobileRequest request);

	// Create Social User and signIn User.
	public SocialUserResponse createSocialUser(
			CreateSocialUserRequest request);


	public GetUserResponse updateUser(UpdateUserByIdRequest request);

	public GetUserResponse updateUserByToken(
			UpdateUserByTokenRequest request);

	public GetUserResponse getUser(GetUserByIdRequest request);
	
   public GetUserResponse getUserFromIMSInCaseOfLinkUserWithOcPassword(GetUserByIdRequest request);

	public IsVerifiedMobileExistResponse isMobileExist(IsVerifiedMobileExistRequest request);
	
	public IsUserExistResponse isUserExist(IsUserExistRequest request);
	
	public IsEmailExistResponse isEmailExist(IsEmailExistRequest request);

	public GetUserResponse getUserByToken(GetUserByTokenRequest request) ;

	public GetUserResponse getUserByEmail(GetUserByEmailRequest request);

	public GetUserResponse getUserByMobile(GetUserByMobileRequest request);


	public UpdateMobileNumberResponse updateMobileNumber(
			UpdateMobileNumberRequest request);

	public CreateGuestUserResponse createGuestUserByEmail(
			CreateGuestUserEmailRequest createGuestUserByEmailRequest);

	public VerifyUserResponse verifyUser(
			VerifyUserRequest verifyUserRequest);

	public OTPResponse createUserWithMobile(
			CreateUserMobileGenerateRequest createUserMobileGenerateRequest);

	public CreateUserResponse verifyUserWithMobile(
			CreateUserMobileVerifyRequest createUserMobileVerifyRequest);
	
	public ConfigureUserStateResponse configureUserState(ConfigureUserStateRequest request);
	
	public ChangePasswordResponse changePassword(ChangePasswordRequest request);
	
	public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request);
	
	public ResetPasswordResponse resetPassword(ResetPasswordRequest request);

	MobileVerificationStatusResponse getMobileVerificationStatus(
			MobileVerificationStatusRequest request);
	
	public ResendEmailVerificationLinkResponse resendEmailVerificationLink(
						ResendEmailVerificationLinkRequest request);

   public ResetPasswordWithLoginResponse resetPasswordAndLogin(ResetPasswordRequest request)
            throws ValidationException, IMSServiceException;

   ChangePasswordWithLoginResponse changePasswordWithLogin(ChangePasswordRequest request)
            throws ValidationException, IMSServiceException;

   public CreateSocialUserWithMobileResponse createSocialUserWithMobile(
            CreateSocialUserWithMobileRequest request);

   public CreateUserResponse verifySocialUserWithMobile(CreateUserMobileVerifyRequest request);
   
   public GetIMSUserVerificationUrlResponse getIMSUserVerificationUrl(GetIMSUserVerificationUrlRequest request);
   
   public CloseAccountResponse closeAccount(CloseAccountByEmailRequest request);

   public GtokenSizeResponse getGlobalTokenSizeByEmailId(
		GetUserByEmailRequest getUserByEmailRequest);

 
   public OTPResponse createUserWithMobileOnly(CreateUserWithMobileOnlyRequest request) throws IMSServiceException;

   public CreateUserResponse verifyUserWithMobileOnly(CreateUserMobileVerifyRequest request) throws IMSServiceException;

   public MobileOnlyResponse isMobileOnly(MobileOnlyRequest mobileOnlyRequest);
   
}
