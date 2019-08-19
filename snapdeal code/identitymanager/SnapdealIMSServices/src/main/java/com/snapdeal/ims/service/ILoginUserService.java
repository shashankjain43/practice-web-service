package com.snapdeal.ims.service;

import org.springframework.http.HttpHeaders;

import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.response.SendForgotPasswordLinkResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.VerifyUserResponse;

/**
 * API exposes login related functionality.
 */
public interface ILoginUserService {

	LoginUserResponse loginUser(LoginUserRequest request) throws IMSServiceException;
	/*
	 * ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request,
	 * HttpHeaders header) ;
	 * 
	 * ForgotPasswordVerifyResponse verifyOtpAndUpdateNewPassword(
	 * ForgotPasswordVerifyRequest request, HttpHeaders header);
	 */
	SendForgotPasswordLinkResponse sendForgotPasswordLink(String email);
	VerifyUserResponse verifyUserAndResetPassword(VerifyAndResetPasswordRequest request);
	
	SignoutResponse signout(SignoutRequest request, HttpHeaders header);

	/**
	 * API exposes service to allow user to login via token.
	 * @param request
	 * @return
	 */
	public LoginUserResponse loginUserWithToken(LoginWithTokenRequest request);

	public void isUserLocked(String uid);
	
   public void updateUserLockInfo(String uid);
   
   public void deleteUserLockInfo(String uid);
 
   /**
    * API exposes service to allow user to login via transfer token.
    * @param request
    * @return
    */
   public LoginUserResponse loginUserWithTransferToken(LoginWithTransferTokenRequest request);
   
}