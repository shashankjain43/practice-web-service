package com.snapdeal.ims.cache.service.impl;

import com.snapdeal.ims.cache.service.IUserVerificationCacheService;
import com.snapdeal.ims.cache.set.IIMSAerospikeSet;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationCacheServiceImpl implements  IUserVerificationCacheService{

   @Autowired
   @Qualifier("verificationCodeUserVerificationDetailsSet")
   private IIMSAerospikeSet<String, UserVerification> verificationCodeUserVerificationDetailsSet;
   
   @Override
   @Timed
   @Marked
   public boolean create(UserVerification userVerificationEntity) {
      boolean writeSuccessful = true;
      
      writeSuccessful = verificationCodeUserVerificationDetailsSet.insert(userVerificationEntity.getCode(), userVerificationEntity);
      
      return writeSuccessful;
   }

   @Override
   @Timed
   @Marked
   public UserVerification getUserVerificationDetailsByCode(String code) {
      UserVerification userVerification = null;
      
      userVerification = verificationCodeUserVerificationDetailsSet.get(code).getValue();
      
      return userVerification;
   }
   
   @Override
   @Timed
   @Marked
   public boolean deleteUserVerificationDetailsByCode(String code) {      
      return verificationCodeUserVerificationDetailsSet.delete(code);
   }

}
