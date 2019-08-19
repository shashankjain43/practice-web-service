package com.snapdeal.ims.client.impl;


import com.snapdeal.ims.client.ILoginUserServiceClient;
import com.snapdeal.ims.common.constant.RestURIConstants;
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

import org.apache.mina.http.api.HttpMethod;


public class LoginUserServiceClientImpl extends AbstractClientImpl implements ILoginUserServiceClient {

   @Override
   public LoginUserResponse loginUserWithPassword(LoginUserRequest request) 
      throws ServiceException, HttpTransportException {

      return prepareResponse(request, 
                             LoginUserResponse.class, 
                             HttpMethod.POST,
                             RestURIConstants.SIGN_IN_EMAIL_MOBILE);
   }

   @Override
   public ChangePasswordResponse changePassword(ChangePasswordRequest request)
      throws ServiceException, HttpTransportException {

      return prepareResponse(request, 
                             ChangePasswordResponse.class, 
                             HttpMethod.PUT,
                             RestURIConstants.USERS + RestURIConstants.CHANGE_PASSWORD);
   }
   
   @Override
   public ChangePasswordWithLoginResponse changePasswordAndLogin(ChangePasswordRequest request)
      throws ServiceException, HttpTransportException {

		return prepareResponse(request, ChangePasswordWithLoginResponse.class,
				HttpMethod.PUT, RestURIConstants.USERS
						+ RestURIConstants.CHANGE_PASSWORD_WITH_LOGIN);
   }

   @Override
   public ForgotPasswordResponse forgotPasswordWithOTP(ForgotPasswordRequest request) 
      throws ServiceException, HttpTransportException {

      return prepareResponse(request, 
                             ForgotPasswordResponse.class, 
                             HttpMethod.POST,
                             RestURIConstants.FORGOT_PASSWORD_GENEREATE_VERIFY_OTP);
   }

   @Override
   public ResetPasswordResponse resetPasswordWithOTP(ForgotPasswordVerifyRequest request) 
      throws ServiceException, HttpTransportException {

      return prepareResponse(request, 
    		  ResetPasswordResponse.class, 
                             HttpMethod.PUT,
                             RestURIConstants.FORGOT_PASSWORD_GENEREATE_VERIFY_OTP);
   }
   
   @Override
   public ResetPasswordWithLoginResponse resetPasswordWithOTPAndLogin(ForgotPasswordVerifyRequest request) 
      throws ServiceException, HttpTransportException {

      return prepareResponse(request, 
    		  ResetPasswordWithLoginResponse.class, 
                             HttpMethod.PUT,
                             RestURIConstants.FORGOT_PASSWORD_GENEREATE_VERIFY_OTP_AND_LOGIN);
   }

   @Override
   public SignoutResponse signout(SignoutRequest request) 
      throws ServiceException, HttpTransportException {

      return prepareResponse(request, 
                             SignoutResponse.class, 
                             HttpMethod.POST,
                             RestURIConstants.SIGN_OUT);
   }

   @Override
   public LoginUserResponse loginUserWithToken(LoginWithTokenRequest request) 
      throws ServiceException, HttpTransportException {

       return prepareResponse(request, 
    		   				  LoginUserResponse.class, 
                              HttpMethod.POST,
                              RestURIConstants.SIGN_IN_TOKEN);
   }

	@Override
	public SendForgotPasswordLinkResponse sendForgotPasswordLink(
			SendForgotPasswordLinkRequest request) throws ServiceException,
			HttpTransportException {
		
		return prepareResponse(request, 
							   SendForgotPasswordLinkResponse.class, 
							   HttpMethod.POST, 
							   RestURIConstants.FORGOT_PASSWORD);
	}

	@Override
	public VerifyUserResponse verifyAndResetPassword(
			VerifyAndResetPasswordRequest request) throws ServiceException,
			HttpTransportException {
		return prepareResponse(request, 
							   VerifyUserResponse.class, 
							   HttpMethod.POST, 
							   RestURIConstants.FORGOT_PASSWORD_VERIFY);
	}

   @Override
   public GetTransferTokenResponse getTransferToken(GetTransferTokenRequest request)
            throws ServiceException, HttpTransportException {
      return prepareResponse(request,
                        GetTransferTokenResponse.class,
                        HttpMethod.PUT,
                        RestURIConstants.GET_TRANSFER_TOKEN);
   }
   
   @Override
   public LoginUserResponse loginUserWithTransferToken(LoginWithTransferTokenRequest request) 
      throws ServiceException, HttpTransportException {

       return prepareResponse(request, 
                          LoginUserResponse.class, 
                              HttpMethod.POST,
                              RestURIConstants.SIGN_IN_TRANSFER_TOKEN);
	}   
}
