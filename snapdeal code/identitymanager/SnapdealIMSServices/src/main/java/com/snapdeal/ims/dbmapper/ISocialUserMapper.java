package com.snapdeal.ims.dbmapper;

import com.snapdeal.ims.dbmapper.entity.SocialUser;

public interface ISocialUserMapper {
   /**
    * This function will create a new social user and
    * auto generate Id in persistent store
    * 
    * @param user
    */
   public void createSocialUser(SocialUser socialUser);
   
   public SocialUser getSocialUserbyUserId(SocialUser socialUser);

   public void updateSocialUser(SocialUser socialUser);

}
