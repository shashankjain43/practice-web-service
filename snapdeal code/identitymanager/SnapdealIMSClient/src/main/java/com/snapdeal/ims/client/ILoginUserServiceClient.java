package com.snapdeal.ims.client;

import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.ForgotPasswordVerifyRequest;
import com.snapdeal.ims.request.GetTransferTokenRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.SendForgotPasswordLinkRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.ChangePasswordWithLoginResponse;
import com.snapdeal.ims.response.ForgotPasswordResponse;
import com.snapdeal.ims.response.GetTransferTokenResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordWithLoginResponse;
import com.snapdeal.ims.response.SendForgotPasswordLinkResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.VerifyUserResponse;

public interface ILoginUserServiceClient {

   /**
    * It is an api for signing in, in the application.
    */
   public LoginUserResponse loginUserWithPassword(LoginUserRequest request) 
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to sign in the application using token.
    */
   public LoginUserResponse loginUserWithToken(LoginWithTokenRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * If user want to change password then client can use this API.
    */
   public ChangePasswordResponse changePassword(ChangePasswordRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * If user forget password. Then User can request for forget password. Then
    * Appropriate information will be given to user for reseting password.
    * Generate Otp
    */
   public ForgotPasswordResponse forgotPasswordWithOTP(ForgotPasswordRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * If user forget password. Then User can request for forget password. Then
    * Appropriate information will be given to user for reseting password.
    * Verify Otp
    */
   public ResetPasswordResponse resetPasswordWithOTP(ForgotPasswordVerifyRequest request) 
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to sign out of the application.
    */
   public SignoutResponse signout(SignoutRequest request) 
      throws ServiceException, HttpTransportException;

   	/**
	 * API to initiate forgot password with email. This api will send email to
	 * user with reset password link.
	 */
   public SendForgotPasswordLinkResponse sendForgotPasswordLink(SendForgotPasswordLinkRequest request) 
		   throws ServiceException, HttpTransportException;
   
   	/**
	 * API to verify user on click of verification link sent to his email. This
	 * api takes new password and confirm password along with encrypted code
	 * shared to the customer via email.
	 */
   public VerifyUserResponse verifyAndResetPassword(VerifyAndResetPasswordRequest request)
		   throws ServiceException, HttpTransportException;

   ChangePasswordWithLoginResponse changePasswordAndLogin(ChangePasswordRequest request)
			throws ServiceException, HttpTransportException;

   ResetPasswordWithLoginResponse resetPasswordWithOTPAndLogin(
			ForgotPasswordVerifyRequest request) throws ServiceException,
			HttpTransportException;

   
   /**
    * API to provide a handshake token when user goes from Snapdeal to one
    * check.
    * This api will be used to get the transfer token everytime the user clicks
    * One Check Wallet to complete the handshake.
    * 
    * @param request
    *           {@link GetTransferTokenRequest}
    * @return {@link GetTransferTokenResponse}
    * @throws ServiceException
    * @throws HttpTransportException
    */
   public GetTransferTokenResponse getTransferToken(GetTransferTokenRequest request)
            throws ServiceException, HttpTransportException;

   /**
    * It is an API used to sign in the application using transfer token.
    */
   public LoginUserResponse loginUserWithTransferToken(LoginWithTransferTokenRequest request)
      throws ServiceException, HttpTransportException;
}
