package com.snapdeal.merchant.rest.service;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.GeneralUserContactUsRequest;
import com.snapdeal.merchant.request.MerchantAddUserRequest;
import com.snapdeal.merchant.request.MerchantAllUsersRequest;
import com.snapdeal.merchant.request.MerchantChangePasswordRequest;
import com.snapdeal.merchant.request.MerchantContactUsRequest;
import com.snapdeal.merchant.request.MerchantEditUserRequest;
import com.snapdeal.merchant.request.MerchantForgotPasswordRequest;
import com.snapdeal.merchant.request.MerchantGenerateOTPRequest;
import com.snapdeal.merchant.request.MerchantResendOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyUserRequest;
import com.snapdeal.merchant.response.GeneralUserContactUsResponse;
import com.snapdeal.merchant.response.MerchantAddUserResponse;
import com.snapdeal.merchant.response.MerchantAllUsersResponse;
import com.snapdeal.merchant.response.MerchantChangePasswordResponse;
import com.snapdeal.merchant.response.MerchantContactUsResponse;
import com.snapdeal.merchant.response.MerchantEditUserResponse;
import com.snapdeal.merchant.response.MerchantForgotPasswordResponse;
import com.snapdeal.merchant.response.MerchantGenerateOTPResponse;
import com.snapdeal.merchant.response.MerchantResendOTPResponse;
import com.snapdeal.merchant.response.MerchantVerifyOTPResponse;
import com.snapdeal.merchant.response.MerchantVerifyUserResponse;

public interface IUserManagementService {

	public MerchantAddUserResponse addUser(MerchantAddUserRequest request) throws MerchantException;

	public MerchantEditUserResponse editUser(MerchantEditUserRequest request) throws MerchantException;

	public MerchantAllUsersResponse getAllUsersOfMerchant(MerchantAllUsersRequest request) throws MerchantException;

	public MerchantForgotPasswordResponse forgotPassword(MerchantForgotPasswordRequest request)
			throws MerchantException;

	public MerchantChangePasswordResponse changePassword(MerchantChangePasswordRequest request)
			throws MerchantException;

	public MerchantVerifyUserResponse verifyUser(MerchantVerifyUserRequest request) throws MerchantException;

	public MerchantGenerateOTPResponse generateOTP(MerchantGenerateOTPRequest request) throws MerchantException;

	public MerchantVerifyOTPResponse verifyOTP(MerchantVerifyOTPRequest request) throws MerchantException;

	public MerchantResendOTPResponse resendOTP(MerchantResendOTPRequest request) throws MerchantException;

	public MerchantContactUsResponse MerchantUserContactUs(MerchantContactUsRequest request) throws MerchantException;

	public GeneralUserContactUsResponse GeneralUserContactUs(GeneralUserContactUsRequest request)throws MerchantException;

}
