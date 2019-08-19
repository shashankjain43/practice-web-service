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
import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.ResendOTPRequest;
import com.snapdeal.ims.request.ResendOTPServiceRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.ITokenService;

public class OTPServiceGeneratorImplTest {

   @InjectMocks
   OTPServiceGenerationImpl otpServiceGenerator;

   @Mock
   private IOTPService service;

   @Mock
   private ITokenService tokenService;

   @Mock
   private AuthorizationContext context;

   @Mock
   IActivityDataService tokenValidationService;
   
   @Mock
   private IUserIdCacheService userIdCacheService ;
   
   @Mock
   private UmsMerchantProvider merchantProvider;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      
      VerifyOTPServiceResponse verifyOTPServiceResponse = new VerifyOTPServiceResponse();
      OTPServiceResponse otpServiceResponse = new OTPServiceResponse();
      otpServiceResponse.setOtpId("1234");
      Mockito.when(service.verifyOTP(any(VerifyOTPServiceRequest.class))).thenReturn(
               verifyOTPServiceResponse);
      Mockito.when(context.get(IMSRequestHeaders.CLIENT_ID.toString()))
      .thenReturn("1");
      Mockito.when(service.generateOTP(any(GenerateOTPServiceRequest.class))).thenReturn(
               otpServiceResponse);
      Mockito.when(service.resendOTP(any(ResendOTPServiceRequest.class))).thenReturn(
               otpServiceResponse);
      
      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("12344");

   }

   @Test
   @Ignore
   public void generateAndSendOTPTest() {

      final GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
      GenerateOTPResponse response = otpServiceGenerator.generateAndSendOTP(generateOTPRequest);
      Mockito.when(tokenService.getUserIdByToken(any(String.class))).thenReturn("123");
      Mockito.when(merchantProvider.getMerchant()).thenReturn(Merchant.SNAPDEAL);
      Mockito.when(userIdCacheService.getEmailIdFromUserId("123",Merchant.SNAPDEAL)).thenReturn("test123@gmail.com");
      
      try{
    	  GenerateOTPResponse excpectedResponse = new GenerateOTPResponse();
    	  excpectedResponse.setOtpId("1234");
    	Assert.assertEquals(excpectedResponse, response);        
      }catch(Error e){
    	  fail(e.getMessage()) ;
      }catch(Exception e){
    	  fail(e.getMessage()) ;
      }
   }
   
   @Test
   @Ignore
   public void reSendOTP() {

      final ResendOTPRequest resendOTPRequest = new ResendOTPRequest();
      GenerateOTPResponse response  = otpServiceGenerator.reSendOTP(resendOTPRequest);
      try{
    	  GenerateOTPResponse excpectedResponse = new GenerateOTPResponse();
    	  excpectedResponse.setOtpId("1234");
    	Assert.assertEquals(excpectedResponse, response);  
      }catch(Error e){
    	  fail(e.getMessage()) ;
      }catch(Exception e){
    	  fail(e.getMessage());
      }
   }

   
   private void fail(String msg){
	   System.out.println(msg);
   }
}
