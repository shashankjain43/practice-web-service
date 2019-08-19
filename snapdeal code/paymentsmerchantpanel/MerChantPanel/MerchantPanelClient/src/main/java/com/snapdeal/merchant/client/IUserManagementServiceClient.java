package com.snapdeal.merchant.client;

import com.snapdeal.merchant.client.exception.HttpTransportException;
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
import com.snapdeal.merchant.response.MerchantVerifyUserResponse;

public interface IUserManagementServiceClient {

   public MerchantAddUserResponse addUser(MerchantAddUserRequest request)
            throws MerchantException, HttpTransportException;

   public MerchantEditUserResponse editUser(MerchantEditUserRequest request)
            throws MerchantException, HttpTransportException;

   public MerchantAllUsersResponse getAllUsersOfMerchant(MerchantAllUsersRequest request)
            throws MerchantException, HttpTransportException;

   public MerchantForgotPasswordResponse forgotPassword(MerchantForgotPasswordRequest request)
            throws MerchantException, HttpTransportException;

   public MerchantChangePasswordResponse changePassword(MerchantChangePasswordRequest request)
            throws MerchantException, HttpTransportException;

   public MerchantVerifyUserResponse verifyUser(MerchantVerifyUserRequest request)
            throws MerchantException, HttpTransportException;
   
   public MerchantGenerateOTPResponse generateOTP(MerchantGenerateOTPRequest request) throws MerchantException ,HttpTransportException;

	public MerchantVerifyOTPResponse verifyOTP(MerchantVerifyOTPRequest request) throws MerchantException ,HttpTransportException;

	public MerchantResendOTPResponse resendOTP(MerchantResendOTPRequest request) throws MerchantException  ,HttpTransportException;

}
