package com.snapdeal.ims.client;

import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserWithLinkedStateRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUserWithLinkedStateResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;

public interface IUserMigrationServiceClient {

   /**
    * It is an API used to find out whether the user is already upgraded or
    * not.
    */
   public UserUpgradationResponse userUpgradeStatus(UserUpgradeByEmailRequest request)
      throws ServiceException, HttpTransportException;

   /**
    * It is an API used to upgrade the user.
    */
   public UpgradeUserResponse upgradeUser(UserUpgradeRequest request) 
      throws ServiceException, HttpTransportException;
   
   /**
    * It is an API to verify the upgraded user
    */
   
   public VerifyUpgradeUserResponse verifyUpgradeUser(VerifyUserUpgradeRequest verifyUserUpgradeRequest)
   	  throws ServiceException, HttpTransportException;
   
   /**
    * It is an API to verify the linked upgraded user
    */
   
   public VerifyUserWithLinkedStateResponse verifyUserWithLinkedState(VerifyUserWithLinkedStateRequest verifyUserLinkedRequest)
   	  throws ServiceException, HttpTransportException;
   
   
}
