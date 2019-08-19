package com.snapdeal.ims.migration.util;

import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service("fcUserAccount")
@Slf4j
public class FCUserAccountUtil extends UserAccountImpl {

   @Autowired
   IPasswordUpgradeCacheService passwordcacheService;

   @Override
   public String getMyPasswordHashedByOthersAlgo(String emailId) {
      return passwordcacheService.getFcSdHashedPassword(emailId);
   }

   @Override
   public String getOthersHashedPassword(String emailId) {
      return passwordcacheService.getSdSdHashedPassword(emailId);
   }
}