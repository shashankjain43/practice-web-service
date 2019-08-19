package com.snapdeal.merchant.client.impl;

import java.lang.reflect.Type;

import org.apache.mina.http.api.HttpMethod;

import com.google.gson.reflect.TypeToken;
import com.snapdeal.merchant.client.IUserManagementServiceClient;
import com.snapdeal.merchant.client.exception.HttpTransportException;
import com.snapdeal.merchant.client.util.RestURIConstants;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantAddUserRequest;
import com.snapdeal.merchant.request.MerchantAllUsersRequest;
import com.snapdeal.merchant.request.MerchantChangePasswordRequest;
import com.snapdeal.merchant.request.MerchantEditUserRequest;
import com.snapdeal.merchant.request.MerchantForgotPasswordRequest;
import com.snapdeal.merchant.request.MerchantGenerateOTPRequest;
import com.snapdeal.merchant.request.MerchantResendOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyUserRequest;
import com.snapdeal.merchant.response.MerchantAddUserResponse;
import com.snapdeal.merchant.response.MerchantAllUsersResponse;
import com.snapdeal.merchant.response.MerchantChangePasswordResponse;
import com.snapdeal.merchant.response.MerchantEditUserResponse;
import com.snapdeal.merchant.response.MerchantForgotPasswordResponse;
import com.snapdeal.merchant.response.MerchantGenerateOTPResponse;
import com.snapdeal.merchant.response.MerchantResendOTPResponse;
import com.snapdeal.merchant.response.MerchantVerifyOTPResponse;
import com.snapdeal.merchant.response.MerchantGetTransactionResponse;
import com.snapdeal.merchant.response.MerchantVerifyUserResponse;

public class UserManagementServiceClientImpl extends AbstractClientImpl
         implements IUserManagementServiceClient {

   @Override
   public MerchantAddUserResponse addUser(MerchantAddUserRequest request)
            throws MerchantException, HttpTransportException {

	   Type type = new TypeToken<GenericMerchantResponse<MerchantAddUserResponse>>() {
		}.getType();
      return prepareResponse(request, MerchantAddUserResponse.class, type,HttpMethod.POST,
               RestURIConstants.USER + RestURIConstants.ADD_USER);

   }

   @Override
   public MerchantEditUserResponse editUser(MerchantEditUserRequest request)
            throws MerchantException, HttpTransportException {
	   Type type = new TypeToken<GenericMerchantResponse<MerchantEditUserResponse>>() {
		}.getType();
      return prepareResponse(request, MerchantEditUserResponse.class,type, HttpMethod.POST,
               RestURIConstants.USER + RestURIConstants.EDIT_USER);
   }

   @Override
   public MerchantAllUsersResponse getAllUsersOfMerchant(MerchantAllUsersRequest request)
            throws MerchantException, HttpTransportException {
	   Type type = new TypeToken<GenericMerchantResponse<MerchantAllUsersRequest>>() {
		}.getType();
      return prepareResponse(request, MerchantAllUsersResponse.class,type, HttpMethod.GET,
               RestURIConstants.USER + RestURIConstants.GET_ALL_USERS);
   }

   @Override
   public MerchantForgotPasswordResponse forgotPassword(MerchantForgotPasswordRequest request)
            throws MerchantException, HttpTransportException {
	   Type type = new TypeToken<GenericMerchantResponse<MerchantForgotPasswordResponse>>() {
		}.getType();
      return prepareResponse(request, MerchantForgotPasswordResponse.class,type, HttpMethod.POST,
               RestURIConstants.USER + RestURIConstants.FORGOT_PASSWORD);
   }

   @Override
   public MerchantChangePasswordResponse changePassword(MerchantChangePasswordRequest request)
            throws MerchantException, HttpTransportException {
	   Type type = new TypeToken<GenericMerchantResponse<MerchantChangePasswordResponse>>() {
		}.getType();
      return prepareResponse(request, MerchantChangePasswordResponse.class,type, HttpMethod.POST,
               RestURIConstants.USER + RestURIConstants.CHANGE_PASSWORD);
   }

   @Override
   public MerchantVerifyUserResponse verifyUser(MerchantVerifyUserRequest request)
            throws MerchantException, HttpTransportException {

	   Type type = new TypeToken<GenericMerchantResponse<MerchantVerifyUserResponse>>() {
		}.getType();
      return prepareResponse(request, MerchantVerifyUserResponse.class, type, HttpMethod.GET,
               RestURIConstants.USER + RestURIConstants.VERIFY_USER
                        .replace(RestURIConstants.GET_LOGIN_NAME_PARAM, request.getLoginName()));
   }

@Override
public MerchantGenerateOTPResponse generateOTP(MerchantGenerateOTPRequest request)
		throws MerchantException, HttpTransportException {
	
	Type type = new TypeToken<GenericMerchantResponse<MerchantGenerateOTPResponse>>() {
	}.getType();
  return prepareResponse(request, MerchantGenerateOTPResponse.class, type, HttpMethod.POST,
           RestURIConstants.SESSION + RestURIConstants.GENOTP);
}

@Override
public MerchantVerifyOTPResponse verifyOTP(MerchantVerifyOTPRequest request)
		throws MerchantException, HttpTransportException {
	

	Type type = new TypeToken<GenericMerchantResponse<MerchantVerifyOTPResponse>>() {
	}.getType();
  return prepareResponse(request, MerchantVerifyOTPResponse.class, type, HttpMethod.POST,
           RestURIConstants.SESSION + RestURIConstants.VERIFY_OTP);
  
}

@Override
public MerchantResendOTPResponse resendOTP(MerchantResendOTPRequest request)
		throws MerchantException, HttpTransportException {
	
	Type type = new TypeToken<GenericMerchantResponse<MerchantResendOTPResponse>>() {
	}.getType();
  return prepareResponse(request, MerchantResendOTPResponse.class, type, HttpMethod.POST,
           RestURIConstants.SESSION + RestURIConstants.RESEND_OTP);
  
}

}
