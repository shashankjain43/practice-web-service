package com.snapdeal.ims.cache.service;

import com.snapdeal.ims.enums.Merchant;

public interface IUserIdCacheService {

   String getActualUserIdForTokenUserId(String userId, Merchant merchant);

   void putUserIdbyEmailId(String userId, String emailId);

	String getEmailIdFromUserId(String userId, Merchant merchant);

}
