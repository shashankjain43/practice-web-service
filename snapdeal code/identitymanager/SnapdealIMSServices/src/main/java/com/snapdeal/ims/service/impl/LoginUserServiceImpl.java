package com.snapdeal.ims.service.impl;

import com.google.common.base.Optional;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserLockDao;
import com.snapdeal.ims.dbmapper.entity.UserLockDetails;
import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.SendForgotPasswordLinkResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.service.provider.UmsServiceProvider;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.utils.PasswordHashServiceUtil;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginUserServiceImpl implements ILoginUserService {
   @Autowired
   UmsServiceProvider serviceProvider;

   @Autowired
   private IUserLockDao userLockDao;
   
   @Autowired
   IPasswordUpgradeCacheService passwordCacheService;
   
   @Autowired
   UmsMerchantProvider merchantProvider;
   @Autowired
   IActivityDataService activitydataService;

   
	@Override
	@Timed
	@Marked
	@Logged
	public LoginUserResponse loginUser(LoginUserRequest request) {
    LoginUserResponse response = serviceProvider.getUMSServiceForLoginUser(request).loginUser(
			request);
      if (response != null && response.getUserDetails() != null) {
         if ((merchantProvider.getMerchant() == Merchant.ONECHECK)) {
            String password = PasswordHashServiceUtil.getSdHashedPassword(request
                     .getPassword());
            passwordCacheService.createImsSdHashed(request.getEmailId(),
                     password);
         } else {
            SdFcPasswordEntity entity = serviceProvider.getUMSService()
                     .putSdFcHashedPasswordByEmailId(request.getEmailId(),request.getPassword());
            passwordCacheService.createSdFcPasswordbyEmailId(request.getEmailId(), entity);
         }
      }
    return response;	
	}

   
	@Override
	@Timed
	@Marked
	@Logged
	public SignoutResponse signout(SignoutRequest request, HttpHeaders header) {
		return serviceProvider.getUMSService().signOut(request);
	}

   
	@Override
	@Timed
	@Marked
	@Logged
	public LoginUserResponse loginUserWithToken(LoginWithTokenRequest request) {
		return serviceProvider.getUMSServiceByGlobalToken(
				request.getGlobalToken()).loginUserWithToken(request);
	}
	
   
	@Override
   @Timed
   @Marked
   @Logged
   public LoginUserResponse loginUserWithTransferToken(LoginWithTransferTokenRequest request) {
      return serviceProvider.getUMSServiceByTransferToken(
            request.getToken()).loginUserWithTransferToken(request);
   }

   
	@Timed
	@Marked
	@Logged
	@Override
	public SendForgotPasswordLinkResponse sendForgotPasswordLink(String email) {
      activitydataService.setActivityDataByEmailId(email);
		return serviceProvider.getUMSServiceByEmail(email).sendForgotPasswordLink(email);
	}

   
   @Override
	@Timed
	@Marked
	@Logged
	public VerifyUserResponse verifyUserAndResetPassword(
			VerifyAndResetPasswordRequest request) {
		return serviceProvider.getUMSServiceByVerificationCode(
				request.getVerificationCode()).verifyUserAndResetPassword(
				request);
	}

	
   @Override
   @Timed
   @Marked
   public void isUserLocked(String userId) {

      // Check from db if user already exists
      Optional<UserLockDetails> userLockDetailsDao = userLockDao.getLockUserEntry(userId);
      if (userLockDetailsDao.isPresent()) {
         UserLockDetails userLockDetail = userLockDetailsDao.get();

         // Check if user status is inactive i.e. locked
         if (ServiceCommonConstants.USER_LOCKED.equalsIgnoreCase(userLockDetail.getStatus())) {

            long userExpiredForTime = Math.abs(System.currentTimeMillis()
                     - (userLockDetail.getExpiryTime().getTime()));

            long remainingTime = Long.valueOf(Configuration
                     .getGlobalProperty(ConfigurationConstants.USER_LOCKED_FOR_TIME))
                     - userExpiredForTime;

            if (remainingTime <= 0) {
               // If time limit expired already, delete user info lock from db
               userLockDao.unLockUser(userId);
            } else {
               // If time limit not yet expired send user info saying after what
               // time he can login again
               throw new IMSServiceException(IMSServiceExceptionCodes.USER_IS_LOCKED.errCode(),
                        MessageFormat.format(IMSServiceExceptionCodes.USER_IS_LOCKED.errMsg(),
                                 remainingTime / (1000 * 60)));
            }
         }
      }
   }

   @Override
   @Timed
   @Marked
   public void updateUserLockInfo(String userId) {

      // Check from db if user already exists
      Optional<UserLockDetails> userLockDetailsDao = userLockDao.getLockUserEntry(userId);
      if (!userLockDetailsDao.isPresent()) {

         // If not present create a new entry with number of attempt 1 status
         // active and expiryTime as currentTime
         UserLockDetails userLockDetails = getUserLockDetails(userId);
         userLockDao.lockUserEntry(userLockDetails);
      } else {

         UserLockDetails userLockDetail = userLockDetailsDao.get();

         long timeExpiredFromFirstInvalidAttempt = Math.abs(System.currentTimeMillis()
                  - userLockDetail.getCreatedTime().getTime());

         if (timeExpiredFromFirstInvalidAttempt >= Long.valueOf(Configuration
                  .getGlobalProperty(ConfigurationConstants.USER_LOCKING_WINDOW_FRAME))) {

            userLockDao.unLockUser(userId);
            UserLockDetails userLockDetails = getUserLockDetails(userId);
            userLockDao.lockUserEntry(userLockDetails);
         } else {
            int loginAttempts = Integer.valueOf(userLockDetail.getLoginAttempts());

            // Increase number of attempts by 1
            loginAttempts++;
            userLockDetail.setLoginAttempts(loginAttempts);

            Integer maxAttempts = Integer.valueOf(Configuration
                     .getGlobalProperty(ConfigurationConstants.MAXIMUM_NUMBER_OF_LOGIN_ATTEMPTS));

            // If no. of attempts becomes n-1, alert user saying this is your
            // last attempt
            if (loginAttempts <= maxAttempts - 1) {
               userLockDao.updateLockUserEntry(userLockDetail);
               if (loginAttempts == (maxAttempts - 1)) {
                  throw new IMSServiceException(
                           IMSServiceExceptionCodes.USER_LAST_ATTEMPT.errCode(),
                           IMSServiceExceptionCodes.USER_LAST_ATTEMPT.errMsg());
               }
            }

            // If no. of attempt becomes n , lock user
            if (loginAttempts == maxAttempts) {
               userLockDetail.setStatus(ServiceCommonConstants.USER_LOCKED);
               userLockDetail.setExpiryTime(new Date(System.currentTimeMillis()));
               userLockDao.updateLockUserEntry(userLockDetail);
               long expiryTime = Integer.valueOf(Configuration
                        .getGlobalProperty(ConfigurationConstants.USER_LOCKED_FOR_TIME));
               throw new IMSServiceException(IMSServiceExceptionCodes.USER_LOCKED.errCode(),
                        MessageFormat.format(IMSServiceExceptionCodes.USER_LOCKED.errMsg(),
                                 expiryTime / (1000 * 60)));
            }
            
            /*// If no. of attempts more than 1, then show the count of incorrect attempts
            if(loginAttempts>1 && loginAttempts<maxAttempts-1){
               if (log.isWarnEnabled()) {
                  log.warn(MessageFormat.format(IMSServiceExceptionCodes.INCORRECT_LOGIN_ATTEMPTS__SECOND_AND_LATER.errMsg(), loginAttempts));
               }
               throw new IMSServiceException(
                        IMSServiceExceptionCodes.INCORRECT_LOGIN_ATTEMPTS__SECOND_AND_LATER.errCode(),
                        MessageFormat.format(IMSServiceExceptionCodes.INCORRECT_LOGIN_ATTEMPTS__SECOND_AND_LATER.errMsg(), loginAttempts));
            }*/
         }
      }
   }

   @Override
   @Timed
   @Marked
   public void deleteUserLockInfo(String userId) {
      Optional<UserLockDetails> userLockDetailsDao = userLockDao.getLockUserEntry(userId);
      if (userLockDetailsDao.isPresent()) {
         userLockDao.unLockUser(userId);
      }
   }

   private UserLockDetails getUserLockDetails(String userId) {
      UserLockDetails userLockDetails = new UserLockDetails();
      userLockDetails.setUserId(userId);
      userLockDetails.setStatus(ServiceCommonConstants.USER_UNLOCKED);
      userLockDetails.setLoginAttempts(1);
      userLockDetails.setExpiryTime(new Date(System.currentTimeMillis()));
      userLockDetails.setCreatedTime(new Date(System.currentTimeMillis()));
      return userLockDetails;
   }

}