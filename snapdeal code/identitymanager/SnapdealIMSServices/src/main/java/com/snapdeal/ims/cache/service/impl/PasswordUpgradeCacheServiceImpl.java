package com.snapdeal.ims.cache.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.freecharge.umsclient.exception.UmsException;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.cache.service.ISDFCPasswordCacheService;
import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.utils.PasswordHashServiceUtil;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service
@Slf4j
public class PasswordUpgradeCacheServiceImpl implements IPasswordUpgradeCacheService {

   @Autowired
   ISDFCPasswordCacheService sdFcUpgradeCacheService;

   @Qualifier("FCUmsService")
   @Autowired
   private IUMSService umsServiceFC;

   @Qualifier("SnapdealUmsService")
   @Autowired
   private IUMSService umsServiceSD;

   @Qualifier("IMSService")
   @Autowired
   private IUMSService umsServiceIMS;

   @Override
   @Timed
   @Marked
   public String getSdSdHashedPassword(String emailId) {
      SdFcPasswordEntity entity = getEmailIdbyPwdMapping(emailId);
      if (entity != null && entity.getSdSdHashedPassword()!=null) {
         return entity.getSdSdHashedPassword();
      } else {
         // get sd hashed password from sd
         try {
            String password = umsServiceSD.getPasswordByEmail(emailId);
            return password;
         } catch (IMSServiceException e) {
            log.error("Error Code is: " + e.getErrCode() + "Error message is : " + e.getErrMsg());
         }
      }
      return null;

   }

   @Override
   @Timed
   @Marked
	public String getSdFcHashedPassword(String emailId) {
		SdFcPasswordEntity entity = getEmailIdbyPwdMapping(emailId);
		if (entity != null) {
			return entity.getSdFcHashedPassword();
		} else {
			try {
				String password = umsServiceSD.getPasswordByEmail(emailId);
				return password;
			} catch (IMSServiceException e) {
				log.error("Error Code is: " + e.getErrCode()
						+ "Error message is : " + e.getErrMsg());
			}
		}
		return null;
	}

   @Override
   @Timed
   @Marked
   public String getFcSdHashedPassword(String emailId) {
      SdFcPasswordEntity entity = getEmailIdbyPwdMapping(emailId);
      if (entity != null) {
         return entity.getFcSdHashedPassword();
      }else {
          try {
              String password = umsServiceFC.getPasswordByEmail(emailId);
              return PasswordHashServiceUtil.getSdHashedPassword(password);
           } catch (UmsException umsEx) {
              log.error("Error Code is: " + umsEx.getErrorCode() + "Error message is : "
                       + umsEx.getErrorMessage());
           } catch (IMSServiceException e) {
              log.error("Error Code is: " + e.getErrCode() + "Error message is : " + e.getErrMsg());
           }
        }
      return null;
   }

   @Override
   @Timed
   @Marked
   public String getFcFcHashedPassword(String emailId) {
      SdFcPasswordEntity entity = getEmailIdbyPwdMapping(emailId);
      if (entity != null && entity.getFcFcHashedPassword()!=null) {
         return entity.getFcFcHashedPassword();
      } else {
         try {
            String password = umsServiceFC.getPasswordByEmail(emailId);
            return PasswordHashServiceUtil.getFcHashedPassword(password);
         } catch (UmsException umsEx) {
            log.error("Error Code is: " + umsEx.getErrorCode() + "Error message is : "
                     + umsEx.getErrorMessage());
         } catch (IMSServiceException e) {
            log.error("Error Code is: " + e.getErrCode() + "Error message is : " + e.getErrMsg());
         }
      }
      return null;
   }

   @Override
   @Timed
   @Marked
   public boolean getUserUpgradeStatus(String emailId) {
      SdFcPasswordEntity entity = getEmailIdbyPwdMapping(emailId);
      if (entity != null) {
         return entity.isUpgradeInitialized();
      }
      return false;
   }

   @Override
   @Timed
   @Marked
   public String getImsSdHashedPassword(String emailId) {
      SdFcPasswordEntity entity = getEmailIdbyPwdMapping(emailId);
      if (entity != null && entity.getImsSdHashedPassword()!=null) {
         return entity.getImsSdHashedPassword();
      } else {
         try {
            return umsServiceIMS.getPasswordByEmail(emailId);
         } catch (IMSServiceException e) {
            log.error("Error Code is: " + e.getErrCode() + "Error message is : " + e.getErrMsg());
         }
      }
      return null;
   }

   private SdFcPasswordEntity getEmailIdbyPwdMapping(String emailId) {
      return sdFcUpgradeCacheService.getUpgradeStatusEntity(emailId);
   }

   @Override
   @Timed
   @Marked
   public void setIsUpgradeinitialized(String emailId, boolean isUpgradeShown) {
      boolean isWriteSuccessful = sdFcUpgradeCacheService.setIsUpgradeShown(emailId, isUpgradeShown);
      if (!isWriteSuccessful) {
         throw new IMSServiceException(
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errCode(),
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errMsg());
      }
   }

   @Override
   @Timed
   @Marked
   public void createImsSdHashed(String emailId, String password) {
      boolean isWriteSuccessful = sdFcUpgradeCacheService.setImsSdHashedPassword(emailId, password);
      if (!isWriteSuccessful) {
         throw new IMSServiceException(
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errCode(),
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errMsg());
      }
   }
   
   @Override
   @Timed
   @Marked
   public void updateImsSdHashed(String emailId, String password) {
      boolean isWriteSuccessful = sdFcUpgradeCacheService.updateImsSdHashedPassword(emailId, password);
      if (!isWriteSuccessful) {
         throw new IMSServiceException(
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errCode(),
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errMsg());
      }
   }

   @Override
   @Timed
   @Marked
   public void createSdFcPasswordbyEmailId(String emailId, SdFcPasswordEntity sdFcPasswordEntity) {
      boolean isWriteSuccessful = sdFcUpgradeCacheService.createEmailIdbyPwdMapping(emailId,
               sdFcPasswordEntity);
      if (!isWriteSuccessful) {
         throw new IMSServiceException(
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errCode(),
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errMsg());
      }
   }

   @Override
   @Timed
   @Marked
   public void updateSdFcPasswordbyEmailId(String emailId, SdFcPasswordEntity sdFcPasswordEntity) {
      boolean isWriteSuccessful = sdFcUpgradeCacheService.updateEmailIdbyPwdMapping(emailId,
               sdFcPasswordEntity);
      if (!isWriteSuccessful) {
         throw new IMSServiceException(
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errCode(),
                  IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errMsg());
      }
   }

   @Override
   public String getOcSdHashedPassword(String emailId) {
      // TODO Need to correct the flow
      return null;
   }

}