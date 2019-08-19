package com.snapdeal.ims.service;

import com.snapdeal.ims.dbmapper.entity.User;
import java.io.IOException;

import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.migration.dto.UpgradeDto;
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
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.MobileOnlyRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.ResendEmailVerificationLinkRequest;
import com.snapdeal.ims.request.ResetPasswordRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.request.UpdateMobileNumberRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
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
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.MobileOnlyResponse;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.OTPResponse;
import com.snapdeal.ims.response.ResendEmailVerificationLinkResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordWithLoginResponse;
import com.snapdeal.ims.response.SendForgotPasswordLinkResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UniqueMobileVerificationResponse;
import com.snapdeal.ims.response.UpdateMobileNumberResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.dto.UserDTO;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;

public interface IUMSService {

	public LoginUserResponse loginUser(LoginUserRequest request);

	public MobileVerificationStatusResponse isMobileVerified(
			MobileVerificationStatusRequest request) throws IMSServiceException;


	public SignoutResponse signOut(SignoutRequest request)
			throws IMSServiceException;

	public LoginUserResponse loginUserWithToken(LoginWithTokenRequest request)
			throws IMSServiceException, ValidationException;

	public GetUserResponse getUserByEmail(GetUserByEmailRequest request);

	public GetUserResponse getUserByMobile(GetUserByMobileRequest request);

	public GetUserResponse getUser(GetUserByIdRequest request);

	public GetUserResponse updateUser(UpdateUserByIdRequest request);

	public GetUserResponse updateUserByToken(UpdateUserByTokenRequest request)
			throws ValidationException;

	public UpdateMobileNumberResponse updateMobileNumber(
			UpdateMobileNumberRequest request) throws ValidationException;

   public CreateUserResponse createUserByEmail(CreateUserEmailRequest request);
   
   public CreateUserResponse createUserByEmailAndMobile(CreateUserEmailMobileRequest request);

	public OTPResponse createUserByMobile(
			CreateUserMobileGenerateRequest request);
	
   public CreateUserResponse createUserByVerifiedMobile(
            UpgradeDto upgradeInfo);

	// In case of UMS it is for facebook
	public SocialUserResponse createSocialUser(CreateSocialUserRequest request);

	// public CreateFBUserResponse createFBUser(CreateFBUserRequest request);

	/*
	 * public GetUserResponse getUserByMobile(GetUserRequest request);
	 */

	public GetUserResponse getUserByToken(GetUserByTokenRequest request);

	public CreateGuestUserResponse createGuestUserByEmail(
			CreateGuestUserEmailRequest createGuestUserByEmailRequest);

	public IsUserExistResponse isUserExist(IsUserExistRequest request);

	public IsEmailExistResponse isEmailExist(IsEmailExistRequest request);

	public IsVerifiedMobileExistResponse isMobileExist(
			IsVerifiedMobileExistRequest request);

	public SendForgotPasswordLinkResponse sendForgotPasswordLink(String email);

	public VerifyUserResponse verifyUserAndResetPassword(
			VerifyAndResetPasswordRequest request);

	public VerifyUserResponse verifyGuestUser(
			VerifyUserRequest verifyGuestUserRequest);

	public CreateUserResponse verifyUserWithMobile(
			CreateUserMobileVerifyRequest request);

	public UniqueMobileVerificationResponse verifyUniqueMobile(
			UniqueMobileVerificationRequest request);

	public ConfigureUserStateResponse configureUserState(ConfigureUserStateRequest request);
	
	public ChangePasswordResponse changePassword(ChangePasswordRequest request) 
			throws ValidationException, IMSServiceException;
	
	public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) 
			   throws ValidationException,IMSServiceException;
	
	public ResetPasswordResponse resetPassword(ResetPasswordRequest request) 
			   throws ValidationException, 
			          IMSServiceException;

	public ResendEmailVerificationLinkResponse resendEmailVerificationLink(
			ResendEmailVerificationLinkRequest request);
	
	public ResetPasswordWithLoginResponse resetPasswordAndLogin(ResetPasswordRequest request)
            throws ValidationException, IMSServiceException;

   ChangePasswordWithLoginResponse changePasswordWithLogin(ChangePasswordRequest request)
            throws ValidationException, IMSServiceException;
	
	SdFcPasswordEntity putSdFcHashedPasswordByEmailId(String emailId,String password);

   String getPasswordByEmail(String emailId);

   UserDTO getUserByEmail(String emailId);

   void updateUserByEmail(UpgradeDto upgradeDto);

   public CreateSocialUserWithMobileResponse createSocialUserWithMobile(
            CreateSocialUserWithMobileRequest request);
   
   public CreateUserResponse verifySocialUserWithMobile(
            CreateUserMobileVerifyRequest request);
   
   public LoginUserResponse loginUserWithTransferToken(LoginWithTransferTokenRequest request)
            throws IMSServiceException, ValidationException;

   public boolean resetDefaultSocialPasswordHelper(String emailId, String password);

   public void createWallet(String userId, Merchant merchant);
   
   /**
    * while calling this method ... you are allowed to pass null for either user
    * id or for email .. but both should not be null ... 
    * if either of value is null then it will be fetched in imsserviceimpl 
    * @param userId
    * @param email
    */
   public void createNotificationOnMigrationStateChange(String userId, String email, WalletUserMigrationStatus status);
   
   public void deleteTempUserWithEmailOrMobile(String mobileNumber, String emailId);

   public GetIMSUserVerificationUrlResponse getIMSUserVerificationUrl(GetIMSUserVerificationUrlRequest request, Upgrade status, String userId);
   
   public CloseAccountResponse closeAccount(CloseAccountByEmailRequest request);

   public GtokenSizeResponse getGlobalTokenSizeByEmailId(
		GetUserByEmailRequest getUserByEmailRequest);

   public User updateUserStatus(String emailId);
   public OTPResponse createUserByMobileOnly(CreateUserWithMobileOnlyRequest request);

   public CreateUserResponse verifyUserWithMobileOnly(CreateUserMobileVerifyRequest request);

   public MobileOnlyResponse isMobileOnly(MobileOnlyRequest mobileOnlyRequest);
   
}
