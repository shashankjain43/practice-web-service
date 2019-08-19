package com.snapdeal.ims.service;

import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.IsOTPValidRequest;
import com.snapdeal.ims.request.ResendOTPRequest;
import com.snapdeal.ims.request.VerifyOTPRequest;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.response.IsOTPValidResponse;
import com.snapdeal.ims.response.VerifyOTPResponse;

public interface IOTPServiceGeneration {

	public GenerateOTPResponse generateAndSendOTP(
			GenerateOTPRequest request);

	public GenerateOTPResponse reSendOTP(ResendOTPRequest request);
	
	public IsOTPValidResponse isOTPValid(IsOTPValidRequest request) ;
	
	public VerifyOTPResponse verifyOTP(VerifyOTPRequest request) ;
}
