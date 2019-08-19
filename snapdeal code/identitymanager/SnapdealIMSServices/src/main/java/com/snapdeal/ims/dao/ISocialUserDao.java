package com.snapdeal.ims.dao;

import com.snapdeal.ims.dbmapper.entity.SocialUser;

public interface ISocialUserDao {

   /**
    * This function will create a new user with social details and
    * auto generate Id in persistent store
    * 
    * @param socialUser
    */
   public void createSocialUser(SocialUser socialUser);

   SocialUser getSocialUser(SocialUser socialUser);
   
   void updateSocialUser(SocialUser socialUser);

}
