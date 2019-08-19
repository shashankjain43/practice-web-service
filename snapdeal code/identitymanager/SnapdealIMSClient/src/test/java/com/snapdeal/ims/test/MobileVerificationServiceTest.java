package com.snapdeal.ims.test;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.ims.client.IMobileVerificationServiceClient;
import com.snapdeal.ims.client.impl.MobileVerificationServiceClientImpl;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.response.CreateSocialUserWithMobileResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.UniqueMobileVerificationResponse;
import com.snapdeal.ims.utils.ClientDetails;

public class MobileVerificationServiceTest {
	
	IMobileVerificationServiceClient mobileServiceClient= new MobileVerificationServiceClientImpl();
	
	@Before
	public void setup() throws Exception {
		ClientDetails.init("localhost", "8080", "snapdeal", "1");
	}
	
	@Test
	public void verifyOtpRequestTest() throws ServiceException, Exception {
		
		UniqueMobileVerificationRequest request = new UniqueMobileVerificationRequest();
		request.setOtp("0146");
		request.setOtpId("55");
		request.setUserId("567622");
		request.setToken("O5ypnZkLuZkvx6YcZN8DlqmQfB82AfW2GHGiacJs1uSNiptvxqixCwljEFgZtFUOQWvtE/J0nIqfxP1N5kMnSQ==");

		UniqueMobileVerificationResponse response = mobileServiceClient
				.verifyUniqueMobile(request);
		System.out.println(response.toString());
	}
}
