package com.snapdeal.merchant.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.GeneralUserContactUsRequest;
import com.snapdeal.merchant.request.MerchantAddUserRequest;
import com.snapdeal.merchant.request.MerchantAllUsersRequest;
import com.snapdeal.merchant.request.MerchantChangePasswordRequest;
import com.snapdeal.merchant.request.MerchantContactUsRequest;
import com.snapdeal.merchant.request.MerchantEditUserRequest;
import com.snapdeal.merchant.request.MerchantLogoutRequest;
import com.snapdeal.merchant.request.MerchantVerifyUserRequest;
import com.snapdeal.merchant.response.GeneralUserContactUsResponse;
import com.snapdeal.merchant.response.MerchantAddUserResponse;
import com.snapdeal.merchant.response.MerchantAllUsersResponse;
import com.snapdeal.merchant.response.MerchantChangePasswordResponse;
import com.snapdeal.merchant.response.MerchantContactUsResponse;
import com.snapdeal.merchant.response.MerchantEditUserResponse;
import com.snapdeal.merchant.response.MerchantLogoutResponse;
import com.snapdeal.merchant.response.MerchantVerifyUserResponse;
import com.snapdeal.merchant.rest.service.ISessionService;
import com.snapdeal.merchant.rest.service.IUserManagementService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

@Controller
@RequestMapping("/api/user")
public class UserManagementController extends AbstractController {

   @Autowired
   private IUserManagementService userManagementService;
   
   @Autowired
   private ISessionService sessionService;

   @RequestAware
   @Marked
   @Logged
   @Timed
   @RequestMapping(method = RequestMethod.POST, value = "/v1/adduser")
   public @ResponseBody GenericMerchantResponse addUser(
            @RequestBody @Valid MerchantAddUserRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
     
	   // apply validation check
      verifyError(result, servRequest);
      
      // call the service
      MerchantAddUserResponse response = userManagementService.addUser(request);
      
      return getResponse(response);
   }

   @RequestAware
   @Marked
   @Logged
   @Timed
   @RequestMapping(method = RequestMethod.POST, value = "/v1/edituser")
   public @ResponseBody GenericMerchantResponse editUser(
            @RequestBody @Valid MerchantEditUserRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      
	   // apply validation check
      verifyError(result, servRequest);
      
      // call the service
      
      MerchantEditUserResponse response = userManagementService.editUser(request);
      
      return getResponse(response);
   }

   @RequestAware
   @Marked
   @Logged
   @Timed
   @RequestMapping(method = RequestMethod.GET, value = "/v1/getall")
   public @ResponseBody GenericMerchantResponse getAllUSersOfMerchant(
            MerchantAllUsersRequest request, BindingResult result, HttpServletRequest servRequest)
                     throws MerchantException {
      
	   // call the service
      MerchantAllUsersResponse response = userManagementService.getAllUsersOfMerchant(request);
      return getResponse(response);
   }

   
   @RequestMapping(method = RequestMethod.POST, value = "/v1/updatepwd")
   public @ResponseBody GenericMerchantResponse changePassword(
            @RequestBody @Valid MerchantChangePasswordRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      
	   // apply validation check
      verifyError(result, servRequest);
      
      // call the service
      MerchantChangePasswordResponse response = userManagementService.changePassword(request);

      return getResponse(response);
   }
   
   
   @RequestMapping(method = RequestMethod.GET, value = "/v1/verify/{loginName}")
   public @ResponseBody GenericMerchantResponse verifyUser(
             @PathVariable("loginName") String loginName ,MerchantVerifyUserRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      
	   // apply validation check
      verifyError(result, servRequest);
      
      // call the service
      request.setLoginName(loginName);
      
      MerchantVerifyUserResponse response = userManagementService.verifyUser(request);

      return getResponse(response);
   }
   
   @RequestAware
   @Marked
   @Timed
   @RequestMapping(method = RequestMethod.POST, value = "/v1/logout")
   public @ResponseBody GenericMerchantResponse sessionlogout(
            @RequestBody @Valid MerchantLogoutRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      
	   // apply validation check
      verifyError(result, servRequest);
      
      // call the service
      MerchantLogoutResponse response = sessionService.logout(request);
      
      return getResponse(response);
   }
   
   
   @RequestAware
   @Marked
   @Logged
   @Timed
   @RequestMapping(method = RequestMethod.POST, value = "/v2/merchantuser/contactus")
   public @ResponseBody GenericMerchantResponse MerchantUserContactUs(
            @RequestBody @Valid MerchantContactUsRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      
	   // apply validation check
      verifyError(result, servRequest);
    
      // call the service  
     MerchantContactUsResponse response = userManagementService.MerchantUserContactUs(request);
      
      return getResponse(response);
   }

   
   @RequestAware
   @Marked
   @Logged
   @Timed
   @RequestMapping(method = RequestMethod.POST, value = "/v2/generaluser/contactus")
   public @ResponseBody GenericMerchantResponse GeneralUserContactUs(
            @RequestBody @Valid GeneralUserContactUsRequest request, BindingResult result,
            HttpServletRequest servRequest) throws MerchantException {
      
	   // apply validation check
      verifyError(result, servRequest);
    
      // call the service  
     GeneralUserContactUsResponse response = userManagementService.GeneralUserContactUs(request);
      
      return getResponse(response);
   }
   
   


      
}