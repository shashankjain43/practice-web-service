package com.snapdeal.ims.client;

import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.ConfigurationDetailsRequest;
import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.IsOTPValidRequest;
import com.snapdeal.ims.request.ResendOTPRequest;
import com.snapdeal.ims.request.VerifyOTPRequest;
import com.snapdeal.ims.response.ConfigurationDetailsResponse;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.response.IsOTPValidResponse;
import com.snapdeal.ims.response.VerifyOTPResponse;

public interface IOTPServiceClient {

	/**
	 * Generate and send OTP
	 */
	public GenerateOTPResponse sendOTP(GenerateOTPRequest request) 
	   throws ServiceException, HttpTransportException;

	/**
	 * Re send OTP
	 */
	public GenerateOTPResponse resendOTP(ResendOTPRequest request) 
	   throws ServiceException, HttpTransportException;
	
	/**
	 * is otp verified
	 */
	public IsOTPValidResponse isValidOTP(IsOTPValidRequest request) 
	   throws ServiceException, HttpTransportException;
	
	/**
	 * get configuration properties
	 */
	public ConfigurationDetailsResponse getConfigurationDetails(ConfigurationDetailsRequest request) 
	   throws ServiceException, HttpTransportException;
	/**
	 * TO verify OTP.
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @throws HttpTransportException
	 */
	public VerifyOTPResponse verifyOTP(VerifyOTPRequest request)
	  throws ServiceException, HttpTransportException ;

}
