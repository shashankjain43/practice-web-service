package com.snapdeal.ims.dao.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ims.dao.ISocialUserDao;
import com.snapdeal.ims.dbmapper.ISocialUserMapper;
import com.snapdeal.ims.dbmapper.entity.SocialUser;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Repository
public class SocialUserDaoImpl implements ISocialUserDao {

   @Autowired
   private ISocialUserMapper socialUserMapper;

   @Override
   @Transactional("transactionManager")
   @Timed
   @Marked
   public void createSocialUser(SocialUser socialUser) {      
      socialUser.setUserId(socialUser.getUser().getUserId());
      socialUser.setCreatedTime(new Timestamp(new Date().getTime()));
      
      socialUserMapper.createSocialUser(socialUser);   
   }
   
   @Override
   @Timed
   @Marked
   public  SocialUser getSocialUser(SocialUser socialUser){
     return socialUserMapper.getSocialUserbyUserId(socialUser);
   }

   @Override
   @Timed
   @Marked
   public void updateSocialUser(SocialUser socialUser) {
      socialUserMapper.updateSocialUser(socialUser);
      
   }

}
