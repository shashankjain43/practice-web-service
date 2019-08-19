package com.snapdeal.ims.cache.service;

import com.snapdeal.ims.dbmapper.entity.User;

/**
 * @author himanshu
 *
 */
public interface IUserCacheService {

   User getUserById(String userId);

   User getUserBySdId(int sdUserId);
   
   User getUserByFcId(int fcUserId);
   
   User getUserBySdFcId(int sdFcUserId);
   
   User getUserByEmail(String email);

   User getUserByMobile(String mobileNumber);

   boolean invalidateUserById(String userId);

   boolean invalidateUserBySdId(int sdUserId);
   
   boolean invalidateUserByFcId(int fcUserId);
   
   boolean invalidateUserBySdFcId(int sdFcUserId);
   
   boolean invalidateUserByEmail(String email);

   boolean invalidateUserByMobile(String mobileNumber);
   
   boolean putUser(User user);

   boolean invalidateEmailIdByUserId(String emailId);
}
