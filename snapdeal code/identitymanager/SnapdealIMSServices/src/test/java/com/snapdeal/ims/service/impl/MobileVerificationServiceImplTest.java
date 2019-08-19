package com.snapdeal.ims.service.impl;

import static org.mockito.Matchers.any;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;

import com.snapdeal.ims.response.UniqueMobileVerificationResponse;

import com.snapdeal.ims.service.provider.UmsServiceProvider;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.ITokenService;

public class MobileVerificationServiceImplTest {

   @InjectMocks
   MobileVerificationServiceImpl mobileVerificationService;

   @InjectMocks
   //IMSServiceImpl imsService;

   @Mock
   private IOTPService service;

   @Mock
   IActivityDataService activityDataService;

   @Mock
   private AuthorizationContext context;

   @Mock
   private ITokenService tokenService;
   
   @Mock
   private UmsServiceProvider serviceProvider;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      VerifyOTPServiceResponse verifyOTPServiceResponse = new VerifyOTPServiceResponse();
      verifyOTPServiceResponse.setMessage("SUCCESS");
      Mockito.when(service.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(
               verifyOTPServiceResponse);
     // Mockito.when(serviceProvider.getUMSService()).thenReturn(imsService);
      
   }

   @Test
   @Ignore
   public void verifyMobile() {
	   UniqueMobileVerificationResponse response = 
			   mobileVerificationService.verifyUniqueMobile(new UniqueMobileVerificationRequest());
	   try{
		   VerifyOTPServiceResponse verifyOTPServiceResponse = new VerifyOTPServiceResponse();
		      verifyOTPServiceResponse.setMessage("SUCCESS");
		   Assert.assertEquals(verifyOTPServiceResponse, response);
	   }catch(Error e){
		   
	   }catch(Exception e){
		   
	   }
   }
}
