package com.snapdeal.ims.migration.util;

import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sdUserAccount")
public class SDUserAccountUtil extends UserAccountImpl {

   @Autowired
   private IPasswordUpgradeCacheService passwordcacheService;

   @Override
   public String getMyPasswordHashedByOthersAlgo(String emailId) {
      return passwordcacheService.getSdFcHashedPassword(emailId);
   }

   @Override
   public String getOthersHashedPassword(String emailId) {
      return passwordcacheService.getFcFcHashedPassword(emailId);
   }
}