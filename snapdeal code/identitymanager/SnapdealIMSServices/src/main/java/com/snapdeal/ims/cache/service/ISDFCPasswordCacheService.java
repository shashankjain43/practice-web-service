package com.snapdeal.ims.cache.service;

import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.enums.EmailVerificationSource;

public interface ISDFCPasswordCacheService {

   boolean updateEmailIdbyPwdMapping(String emailId, SdFcPasswordEntity sdFcPasswordEntity);

   boolean createEmailIdbyPwdMapping(String emailId, SdFcPasswordEntity sdFcPasswordEntity);

   SdFcPasswordEntity getUpgradeStatusEntity(String emailId);

   boolean setIsUpgradeShown(String emailId, boolean isUpgradeShown);

   boolean setImsSdHashedPassword(String emailId, String password);

   boolean updateImsSdHashedPassword(String emailId, String password);

   boolean setEmailVerificationSource(String emailId, EmailVerificationSource verificationSource);
}
