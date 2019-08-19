package com.snapdeal.ims.client.impl;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.mina.http.api.HttpMethod;

import com.snapdeal.ims.client.IOTPServiceClient;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
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

public class OTPClientServiceImpl extends AbstractClientImpl implements IOTPServiceClient {

   @Override
   public GenerateOTPResponse sendOTP(GenerateOTPRequest request) 
      throws ServiceException, HttpTransportException {

      return prepareResponse(request,
                             GenerateOTPResponse.class, 
                             HttpMethod.POST,
                             RestURIConstants.SEND_OTP);
   }

   @Override
   public GenerateOTPResponse resendOTP(ResendOTPRequest request) 
      throws ServiceException, HttpTransportException {

      return prepareResponse(request,
                             GenerateOTPResponse.class, 
                             HttpMethod.POST,
                             RestURIConstants.RESEND_OTP);
   }

	@Override
	public IsOTPValidResponse isValidOTP(IsOTPValidRequest request)
			throws ServiceException, HttpTransportException {
		 
		return prepareResponse(request,
							   IsOTPValidResponse.class, 
							   HttpMethod.GET,
							   RestURIConstants.VALID_OTP 
							   + "/" + request.getOtpId()
							   + "/" + request.getOtp());
	}
	
	@Override
	public VerifyOTPResponse verifyOTP(VerifyOTPRequest request)
	      throws ServiceException, HttpTransportException {

	      return prepareResponse(request,
	    		  				 VerifyOTPResponse.class, 
	                             HttpMethod.POST,
	                             RestURIConstants.VERIFY_OTP);
   }

	@Override
	public ConfigurationDetailsResponse getConfigurationDetails(
			ConfigurationDetailsRequest request) throws ServiceException,
			HttpTransportException {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(request.getConfigurationType())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.CONFIGURATION_FIELD_MANDATORY.errMsg(),
					IMSRequestExceptionCodes.CONFIGURATION_FIELD_MANDATORY.errCode());
		}
		
		return prepareResponse(request,
				   ConfigurationDetailsResponse.class, 
				   HttpMethod.GET,
				   RestURIConstants.GET_CONFIGURATION_DETAILS
				   + "/" + request.getConfigurationType());
	}

}
