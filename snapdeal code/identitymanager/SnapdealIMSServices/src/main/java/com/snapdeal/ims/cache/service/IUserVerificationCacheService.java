package com.snapdeal.ims.cache.service;

import com.snapdeal.ims.dbmapper.entity.UserVerification;

public interface IUserVerificationCacheService {

   public boolean create(UserVerification userVerificationEntity);
   
   public UserVerification getUserVerificationDetailsByCode(String code);

	boolean deleteUserVerificationDetailsByCode(String code);

}
