package com.snapdeal.ims.test;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.ims.client.IOTPServiceClient;
import com.snapdeal.ims.client.impl.OTPClientServiceImpl;
import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.OTPRequestChannel;
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
import com.snapdeal.ims.utils.ClientDetails;

public class OTPServiceClientTest {
	
	IOTPServiceClient otpServiceClient =new OTPClientServiceImpl();

	@Before
	public void setup() throws Exception {
		ClientDetails.init("localhost", "8080", "onecheck", "2", 6000000);
	}

	@Test
	public void sendOtpRequestTest() throws ServiceException, Exception {

		GenerateOTPRequest request = new GenerateOTPRequest();
		request.setEmailId("dghdh@gdh.com");
		request.setMobileNumber("9599341553");
		request.setPurpose(OTPPurpose.MOBILE_VERIFICATION);
		request.setToken("H4RVohfCB0QKUvlM-dmL2RNkgTP_BKvdGMyoJKqfxvsWqOq2phxGNCW1D8k6ys4RfKZLXaRQngNzqQNCPg-CsQ");
		GenerateOTPResponse response = otpServiceClient.sendOTP(request);
		System.out.println(response);
	}

	@Test
	public void reSendOtpRequestTest() throws ServiceException, Exception {

		ResendOTPRequest request = new ResendOTPRequest();
		request.setOtpId("55");
		request.setToken("O5ypnZkLuZkvx6YcZN8DlqmQfB82AfW2GHGiacJs1uSNiptvxqixCwljEFgZtFUOQWvtE/J0nIqfxP1N5kMnSQ==");
		GenerateOTPResponse response = otpServiceClient.resendOTP(request);
		System.out.println(response);
	}

	@Test
	public void reSendOtpThroughCallRequestTest() throws ServiceException, Exception {

		ResendOTPRequest request = new ResendOTPRequest();
		request.setOtpId("a0ae2ff6-4269-48b0-8da4-82cd22f1a2b3");
		request.setOtpChannel(OTPRequestChannel.THROUGH_CALL);
		GenerateOTPResponse response = otpServiceClient.resendOTP(request);
		System.out.println(response);
	}

	@Test
	public void isOTPValid() throws ServiceException, Exception {
		IsOTPValidRequest request = new IsOTPValidRequest();

		request.setOtp("2332");
		request.setOtpId("d5ed1c60-d4a6-4229-aaee-97c21c8b97ef");
		IsOTPValidResponse response = otpServiceClient.isValidOTP(request) ;
		System.out.println(response);
	}
	
	@Test
	public void GetConfigurationDetails() throws ServiceException, Exception {
		ConfigurationDetailsRequest request = new ConfigurationDetailsRequest();
		request.setConfigurationType(CommonConstants.CONFIGURATION_TYPE_OTP);
		ConfigurationDetailsResponse response = otpServiceClient.getConfigurationDetails(request) ;
		System.out.println(response);
	}
	
	@Test
	public void verifyOTP() throws ServiceException, Exception {
		VerifyOTPRequest request = new VerifyOTPRequest() ;
		request.setOtp("0503");
		request.setOtpId("0fb9b937-cb50-49fd-acf9-e061c1f64b31");
		request.setOtpPurpose(OTPPurpose.MOBILE_VERIFICATION);
		request.setToken("H4RVohfCB0QKUvlM-dmL2RNkgTP_BKvdGMyoJKqfxvsWqOq2phxGNCW1D8k6ys4RfKZLXaRQngNzqQNCPg-CsQ");
		VerifyOTPResponse response = otpServiceClient.verifyOTP(request)	;
		System.out.println(response);
	}
	
}
