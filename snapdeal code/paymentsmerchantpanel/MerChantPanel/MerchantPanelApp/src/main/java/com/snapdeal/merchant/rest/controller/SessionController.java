package com.snapdeal.merchant.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.merchant.common.constant.RestURIConstants;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantForgotPasswordRequest;
import com.snapdeal.merchant.request.MerchantGenerateOTPRequest;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantResendOTPRequest;
import com.snapdeal.merchant.request.MerchantSetPasswordRequest;
import com.snapdeal.merchant.request.MerchantVerifyOTPRequest;
import com.snapdeal.merchant.response.MerchantForgotPasswordResponse;
import com.snapdeal.merchant.response.MerchantGenerateOTPResponse;
import com.snapdeal.merchant.response.MerchantLoginResponse;
import com.snapdeal.merchant.response.MerchantResendOTPResponse;
import com.snapdeal.merchant.response.MerchantSetPasswordResponse;
import com.snapdeal.merchant.response.MerchantVerifyOTPResponse;
import com.snapdeal.merchant.rest.service.ISessionService;
import com.snapdeal.merchant.rest.service.IUserManagementService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api/session")
public class SessionController extends AbstractController {

   @Autowired
   private ISessionService sessionService;
   
   @Autowired
   private IUserManagementService userManagementService;


   @RequestAware
   @Marked
   @Timed
   @Logged
   @RequestMapping(method = RequestMethod.POST, value = "/v1/login")
   public @ResponseBody GenericMerchantResponse login(
            @RequestBody @Valid MerchantLoginRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      // apply validation check
      verifyError(result, servRequest);
      // call the service
      MerchantLoginResponse response = sessionService.login(request);
      return getResponse(response);
   }

   @RequestAware
   @Marked
   @Timed
   @Logged
   @RequestMapping(method = RequestMethod.POST, value = "/v1/genpwd")
   public @ResponseBody GenericMerchantResponse forgotPassword(
            @RequestBody @Valid MerchantForgotPasswordRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      // apply validation check
      verifyError(result, servRequest);
      // call the service
      MerchantForgotPasswordResponse response = userManagementService.forgotPassword(request);

      return getResponse(response);

   }

   @RequestAware
   @Marked
   @Timed
   @Logged
   @RequestMapping(method = RequestMethod.POST, value = "/v2/genotp")
   public @ResponseBody GenericMerchantResponse generateOTP(
            @RequestBody @Valid MerchantGenerateOTPRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
     
	   // apply validation check
      verifyError(result, servRequest);
      // call the service
      MerchantGenerateOTPResponse response = userManagementService.generateOTP(request);

      return getResponse(response);
}
	
   @RequestAware
   @Marked
   @Timed
   @Logged
	@RequestMapping(method = RequestMethod.POST, value = "/v2/resendotp")
   public @ResponseBody GenericMerchantResponse resendOTP(
            @RequestBody @Valid MerchantResendOTPRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      // apply validation check
      verifyError(result, servRequest);
      // call the service
      MerchantResendOTPResponse response = userManagementService.resendOTP(request);

      return getResponse(response);
}

   @RequestAware
   @Marked
   @Timed
   @Logged
   @RequestMapping(method = RequestMethod.POST, value = "/v2/verifyotp")
   public @ResponseBody GenericMerchantResponse verifyOTP(
            @RequestBody @Valid MerchantVerifyOTPRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      
	   // apply validation check
      verifyError(result, servRequest);
      // call the service
      MerchantVerifyOTPResponse response = userManagementService.verifyOTP(request);

      return getResponse(response);
}
   
   @RequestAware
   @Marked
   @Timed
   @Logged
   @RequestMapping(method = RequestMethod.POST, value = "/v1/setpassword")
   public @ResponseBody GenericMerchantResponse setPassword(
            @RequestBody @Valid MerchantSetPasswordRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      
	   // apply validation check
      verifyError(result, servRequest);
      
      // call the service
      MerchantSetPasswordResponse response = sessionService.setPassword(request);
      
      return getResponse(response);
   }
   
}
