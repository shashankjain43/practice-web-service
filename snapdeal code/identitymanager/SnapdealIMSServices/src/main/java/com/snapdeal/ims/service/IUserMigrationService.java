package com.snapdeal.ims.service;


import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.migration.exception.IMSMigrationHardDeclinedException;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.VerifyUserWithLinkedStateRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUserWithLinkedStateResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;

public interface IUserMigrationService {

   /**
    * API to get upgrade status based on email.
    * 
    * @param userUpgradeByEmailRequest
 * @param isExternalCall TODO
    * @return
    */
   UserUpgradationResponse getUserUpgradeStatus(UserUpgradeByEmailRequest userUpgradeByEmailRequest, boolean isExternalCall)
      throws IMSMigrationHardDeclinedException;

   /**
    * API to upgrade user.
    * 
    * @param userUpgradeRequest
    * @return
    */
   UpgradeUserResponse upgradeUser(UserUpgradeRequest userUpgradeRequest)
      throws IMSMigrationHardDeclinedException;

	/**
	 * This API will be used for User Account Linking with One-check account using
	 * 1. Password
	 * 2. or OTP
	 * @param verifyUserUpgradeRequest
	 * @return
	 */
	VerifyUpgradeUserResponse verifyUpgradeUser(VerifyUserUpgradeRequest verifyUserUpgradeRequest);
	
	
	VerifyOTPServiceResponse dummyVerifyOTP(VerifyOTPServiceRequest request);


   UpgradeStatus getIMSUserUpgradeStatus(UserUpgradeByEmailRequest userUpgradeByEmailRequest);

   UserUpgradationResponse upgradeSocialUser(String emailId);
   
   boolean upgradeUserStatusViaResetPassword(String emailId, UserDetailsDTO dto);
   
   public VerifyUserWithLinkedStateResponse verifyUserWithLinkedState(VerifyUserWithLinkedStateRequest verifyUserLinkedRequest); 
	
}