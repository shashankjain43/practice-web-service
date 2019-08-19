package com.snapdeal.ims.otp.service;

import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.FetchLatestOTPRequest;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.request.ConfigurationDetailsRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.IsOTPVerifiedServiceRequest;
import com.snapdeal.ims.request.ResendOTPServiceRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.response.ConfigurationDetailsResponse;
import com.snapdeal.ims.response.IsOTPValidResponse;

/**
 * Service Interface to generate OTP. Generated OTP is sent to the user
 * specified primary mobile number and the notification about the generate OTP
 * activity is sent to the user defined email ID
 * 
 * @author varun
 *
 */
public interface IOTPService {

	public OTPServiceResponse generateOTP(GenerateOTPServiceRequest generateOTPRequest);

	public OTPServiceResponse resendOTP(ResendOTPServiceRequest request);

	public VerifyOTPServiceResponse verifyOTP(VerifyOTPServiceRequest request);
	
	public IsOTPValidResponse isOTPValid(IsOTPVerifiedServiceRequest request) ;
	
	public UserOTPEntity getOTPInfo(FetchLatestOTPRequest request);

}
