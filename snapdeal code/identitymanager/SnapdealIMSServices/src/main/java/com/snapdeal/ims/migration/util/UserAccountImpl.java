package com.snapdeal.ims.migration.util;

import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.ISocialUserDao;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dbmapper.entity.SocialUser;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.enums.UserStatus;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSMigrationExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.migration.dto.UpgradeDto;
import com.snapdeal.ims.objectMapper.IMSServiceObjectsMapper;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.dto.SocialInfo;
import com.snapdeal.ims.service.dto.UserDTO;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class UserAccountImpl implements UserAccountUtil {


   @Autowired
   private IUserDao userDao;

   @Autowired
   private ISocialUserDao socialUserDao;

   @Autowired
   @Qualifier("SnapdealUmsService")
   private IUMSService sdUmsService;

   @Autowired
   @Qualifier("FCUmsService")
   private IUMSService fcUmsService;

   @Autowired
   @Qualifier("IMSService")
   private IUMSService imsService;
   
   @Autowired
   private IUserCacheService userCacheService;

   @Autowired
   private IPasswordUpgradeCacheService passwordcacheService;
   
   @Autowired
   UmsMerchantProvider merchantProvider;

   @Override
   public boolean isPasswordMatched(String originatingSourceEmail) {

      final String otherHashedPassword = getOthersHashedPassword(originatingSourceEmail);
      final String ownHashedPasswordByOthersAlgo = getMyPasswordHashedByOthersAlgo(originatingSourceEmail);
      return otherHashedPassword.equals(ownHashedPasswordByOthersAlgo);
   }

   @Override
   public void createUser(UpgradeDto upgradeDto) {
      log.debug("Create user in IMS: " + upgradeDto);
      switch (upgradeDto.getVerifiedType()) {
         case EMAIL_PWD_VERIFIED:
         case MOBILE_OTP_VERIFIED:
         case MOBILE_PWD_VERIFIED:
         case TOKEN_VERIFIED:
            createOCUserByMobile(upgradeDto);
            break;
         case FACEBOOK_VERIFIED:
         case GOOGLE_VERIFIED:
            createSocialUser(upgradeDto);
            // createOCUserBySocial(upgradeDto);
            break;
         default:
            throw new IMSServiceException(
                     IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errCode(),
                     IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errMsg());
      }
   }

   private void createOCUserByMobile(UpgradeDto upgradeInfo) {
      imsService.createUserByVerifiedMobile(upgradeInfo);
   }

   private void createOCUserBySocial(UpgradeDto upgradeInfo) {

      final CreateSocialUserRequest request = new CreateSocialUserRequest();
      final SocialUserRequestDto socialUserRequestDto = new SocialUserRequestDto();
      socialUserRequestDto.setDisplayName(upgradeInfo.getDisplayName());
      socialUserRequestDto.setDob(upgradeInfo.getDob());
      socialUserRequestDto.setEmailId(upgradeInfo.getEmailId());
      socialUserRequestDto.setFirstName(upgradeInfo.getFirstName());
      socialUserRequestDto.setGender(upgradeInfo.getGender());
      socialUserRequestDto.setLanguagePref(upgradeInfo.getLanguagePref());
      socialUserRequestDto.setLastName(upgradeInfo.getLastName());
      socialUserRequestDto.setMiddleName(upgradeInfo.getMiddleName());
      socialUserRequestDto.setMobileNumber(upgradeInfo.getMobileNumber());
      request.setSocialUserDto(socialUserRequestDto);
      imsService.createSocialUser(request);
   }

   public void createSocialUser(UpgradeDto upgradeInfo) {
      User user = IMSServiceObjectsMapper.mapUpgradeDtoToUser(upgradeInfo);
      user.setOriginatingSrc(merchantProvider.getMerchant());
      user.setPassword(upgradeInfo.getFinalOCPassword());
      user.setMobileVerified(true);
      user.setEmailVerified(true);
      user.setStatus(UserStatus.REGISTERED);
      if(upgradeInfo.getVerifiedType()==UserIdentityVerifiedThrough.GOOGLE_VERIFIED)
      {
         user.setGoogleUser(true);
      }
      if(upgradeInfo.getVerifiedType()==UserIdentityVerifiedThrough.FACEBOOK_VERIFIED)
      {
         user.setFacebookUser(true);
      }
      
         // update user by ID.
      	imsService.deleteTempUserWithEmailOrMobile(user.getMobileNumber(), user.getEmailId());
        userDao.create(user);
         // iterate through each social user, from snapdeal.
         if (upgradeInfo.getSdSocialSource() != null) {
            for (SocialInfo socialInfo : upgradeInfo.getSdSocialSource()) {
               if (socialInfo.getSocialSrc() != null
                        && StringUtils.isNotBlank(socialInfo.getSocialId())) {
                  createSocialDetails(user, Merchant.SNAPDEAL, socialInfo);
               }
            }
         }

         // iterate through each social user, form freecharge.
         if (upgradeInfo.getFcSocialSource() != null) {
            for (SocialInfo socialInfo : upgradeInfo.getFcSocialSource()) {
               if (socialInfo.getSocialSrc() != null
                        && StringUtils.isNotBlank(socialInfo.getSocialId())) {
                  createSocialDetails(user, Merchant.FREECHARGE, socialInfo);
               }
            }
         }
      
      imsService.createWallet(user.getUserId(), merchantProvider.getMerchant()); 
    }

   private void createSocialDetails(User user, Merchant merchant, SocialInfo socialInfo) {

      SocialUser socialUser = new SocialUser();
      socialUser.setSocialId(socialInfo.getSocialId());
      socialUser.setSocialSrc(socialInfo.getSocialSrc());
      socialUser.setMerchant(merchant);
      socialUser.setAboutMe(socialInfo.getAboutMe());
      socialUser.setPhotoURL(socialInfo.getPhotoURL());
      socialUser.setSecret(socialInfo.getSocialSecret());
      socialUser.setSocialToken(socialUser.getSocialToken());
      socialUser.setUser(user);
      // Need to optimize.
      SocialUser socialUserFromDB = socialUserDao.getSocialUser(socialUser);
      if (socialUserFromDB == null) {
         socialUserDao.createSocialUser(socialUser);
      } else {
         socialUserDao.updateSocialUser(socialUser);
      }
   }

   /**
    * This method returns originating source password hashed by source algorithm
    * @param email
    * @return
    */
   protected abstract String getMyPasswordHashedByOthersAlgo(String email);

   /**
    * This method returns others source password hashed by others source algorithm
    * @param email
    * @return
    */
   protected abstract String getOthersHashedPassword(String email);

   /**
    * Utility method to merge user DTO with preference.
    * 
    * @param sdUser
    * @param fcUser
    * @param preferredSource
    * @param upgradeSource 
    * @return
    */
   protected UpgradeDto mergeDTO(UserDTO sdUser, UserDTO fcUser, Merchant preferredSource, UpgradeSource upgradeSource) {
      UserDTO targetDTO = null;
      UserDTO fromDTO = null;
      UpgradeDto upgradeDto = new UpgradeDto();
      String hashedPassword = null;
      
      if (upgradeSource == UpgradeSource.SIGN_UP) {
         switch (preferredSource) {
            case SNAPDEAL:
               targetDTO = sdUser;
               fromDTO = fcUser;
               hashedPassword = passwordcacheService.getImsSdHashedPassword(targetDTO.getEmailId());
               upgradeDto.setSdPassword(hashedPassword);
               break;
            case FREECHARGE:
               targetDTO = fcUser;
               fromDTO = sdUser;
               hashedPassword = passwordcacheService.getImsSdHashedPassword(targetDTO.getEmailId());
               upgradeDto.setFcPassword(hashedPassword);
               break;
            default:
               log.error("Invalid prefferd source. " + preferredSource + " is not supported.");
               throw new IMSServiceException(
                        IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errCode(),
                        IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER.errMsg());
         }
      }else{
         switch (preferredSource) {
            case SNAPDEAL:
               targetDTO = sdUser;
               fromDTO = fcUser;
               hashedPassword = passwordcacheService.getSdSdHashedPassword(targetDTO
                        .getEmailId());
               upgradeDto.setSdPassword(hashedPassword);
               break;
            case FREECHARGE:
               targetDTO = fcUser;
               fromDTO = sdUser;
               hashedPassword = passwordcacheService.getFcFcHashedPassword(targetDTO
                        .getEmailId());
               upgradeDto.setFcPassword(hashedPassword);
               break;
            case ONECHECK:
            default:
               log.error("Invalid prefferd source. " + preferredSource + " is not supported.");
               throw new IMSServiceException(
                        IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errCode(),
                        IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errMsg());
         }
      }
      merge(targetDTO, fromDTO);
      // Setting fc id and sd id from target dto. These id's will be either
      // fetched from source or the sd_fc_id from IMS DB.
      if(fcUser!=null && fcUser.getSocialInfo() !=null && fcUser.getSocialInfo().size()>0){
         upgradeDto.setFcSocialSource(fcUser.getSocialInfo());
      }
      if(sdUser!=null && sdUser.getSocialInfo() !=null && sdUser.getSocialInfo().size()>0){
         upgradeDto.setSdSocialSource(sdUser.getSocialInfo());
      }
      if (targetDTO.getSdUserId() > 0) {
         upgradeDto.setSdId(targetDTO.getSdUserId());
      } else if (fromDTO!=null) {
         upgradeDto.setSdId(fromDTO.getSdUserId());
      }
      if (targetDTO.getFcUserId() > 0) {
         upgradeDto.setFcId(targetDTO.getFcUserId());
      } else if(fromDTO!=null){
         upgradeDto.setFcId(fromDTO.getFcUserId());
      }
      upgradeDto.setFinalOCPassword(hashedPassword);
      mapUserDetailsToUpgradeDTO(upgradeDto, targetDTO);
      return upgradeDto;
   }

   public UpgradeDto getMergedUserDetails(String email, 
                                          State currentAccountExistence,
                                          UserIdentityVerifiedThrough userIdentityVerifiedThrough, 
                                          String mobileNumber,
                                          Merchant originatingSource,
                                          final UpgradeSource upgradeSource) {

      // Merge sdUser and fcUser, if any value exist at both sides then
      // give preference to sdUser's value
      UserDTO sdUser = null;
      UserDTO fcUser = null;
      if (currentAccountExistence == State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED
               || currentAccountExistence == State.SD_DISABLED_FC_ENABLED_EXISTS
               || currentAccountExistence == State.SD_ENABLED_FC_DISABLED_EXISTS) {
         sdUser = fetchUserFromSD(email);
         fcUser = fetchUserFromFC(email);
      } else if (currentAccountExistence == State.SD_ACCOUNT_EXISTS_AND_ENABLED
               || (currentAccountExistence == State.SD_ACCOUNT_EXISTS_AND_DISABLED && originatingSource == Merchant.FREECHARGE)) {
         fcUser = null;
         sdUser = fetchUserFromSD(email);
         if (upgradeSource == UpgradeSource.SIGN_UP) {
        	 User user = userDao.getUserByEmail(email);
            fcUser = IMSServiceObjectsMapper.mapUserToUserDTO(user,false);            
            // TODO if exception occurs, throw migration error
            originatingSource = Merchant.FREECHARGE;
         } else {
            originatingSource = Merchant.SNAPDEAL;
         }
      } else if (currentAccountExistence == State.FC_ACCOUNT_EXISTS_AND_ENABLED 
               || (currentAccountExistence == State.FC_ACCOUNT_EXISTS_AND_DISABLED && originatingSource == Merchant.SNAPDEAL)) {
         sdUser = null; 
         fcUser = fetchUserFromFC(email);
         if (upgradeSource == UpgradeSource.SIGN_UP) {
        	 User user = userDao.getUserByEmail(email);
            sdUser = IMSServiceObjectsMapper.mapUserToUserDTO(user,false);
            // TODO if exception occurs, throw migration error
            originatingSource = Merchant.SNAPDEAL;
         } else {
            originatingSource = Merchant.FREECHARGE;
         }
      } else {
         log.error("Invalid state: " + currentAccountExistence + " is not supported.");
         throw new IMSServiceException(
                  IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errCode(),
                  IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errMsg());
      }
      UpgradeDto upgradeDto = mergeDTO(sdUser, fcUser, originatingSource, upgradeSource);
      upgradeDto.setVerifiedType(userIdentityVerifiedThrough);
      upgradeDto.setMobileNumber(mobileNumber);
      if (isSocialLogin(userIdentityVerifiedThrough)) {
         upgradeDto.setFinalOCPassword(ServiceCommonConstants.DEFAULT_SOCIAL_USER_PASSWORD);
      }
      return upgradeDto;

   }

   /**
    * Check if current user logged-in via social.
    */
   public boolean isSocialLogin(UserIdentityVerifiedThrough userIdentityVerifiedThrough) {
      if (userIdentityVerifiedThrough == UserIdentityVerifiedThrough.FACEBOOK_VERIFIED
               || userIdentityVerifiedThrough == UserIdentityVerifiedThrough.GOOGLE_VERIFIED) {
         return true;
      }
      return false;
   }

   /**
    * 
    * @param upgradeDto
    * @param targetDTO
    * @returnSD_FC_ACCOUNT_EXISTS_AND_ENABLED
    */
   protected UpgradeDto mapUserDetailsToUpgradeDTO(UpgradeDto upgradeDto, UserDTO targetDTO) {
      upgradeDto.setEmailId(targetDTO.getEmailId());
      upgradeDto.setMobileNumber(targetDTO.getMobileNumber());
      upgradeDto.setFirstName(targetDTO.getFirstName());
      upgradeDto.setMiddleName(targetDTO.getMiddleName());
      upgradeDto.setLastName(targetDTO.getLastName());
      upgradeDto.setDisplayName(targetDTO.getDisplayName());
      upgradeDto.setGender(targetDTO.getGender());
      upgradeDto.setDob(targetDTO.getDob());
      upgradeDto.setLanguagePref(targetDTO.getLanguagePref());
      // Check if the account state is already set to registered, then set the
      // same in user.
      if (UserStatus.REGISTERED.getValue().equalsIgnoreCase(targetDTO.getAccountState())) {
         upgradeDto.setUserStatus(UserStatus.REGISTERED);
      }
      return upgradeDto;
   }

   /**
    * Method to fetch user DTO from source.
    * 
    * @param email
    * @return
    */
   protected UserDTO fetchUserFromSD(String email) {
		UserDTO dto = null;
		try {
			dto = sdUmsService.getUserByEmail(email);
		} catch (Exception ex) {
			log.error("Unable to fetch user from FC ", ex);
			try {
				dto = sdUmsService.getUserByEmail(email);
			} catch (Exception e) {
				log.error("Unable to fetch user from FC ", ex);
			}
		}
		return dto;
   }

   /**
    * 
    * @param email
    * @return
    */
	protected UserDTO fetchUserFromFC(String email) {
		UserDTO dto = null;
		try {
			dto = fcUmsService.getUserByEmail(email);
		} catch (Exception ex) {
			log.error("Unable to fetch user from FC ", ex);
			try {
				dto = fcUmsService.getUserByEmail(email);
			} catch (Exception e) {
				log.error("Unable to fetch user from FC ", ex);
			}
		}
		return dto;
	}
   
   /**
    * 
    * @param email
    * @return
    */
   protected User fetchUserFromOC(String mobileNumber) {
      try {
         return userCacheService.getUserByMobile(mobileNumber);
      } catch (Exception ex) {
         return null;
      }
   }

   // merge two bean by discovering differences
   protected <M> void merge(M target, M destination) {
      // If destination dto is null not need to merge.
      if(null == destination) {
         return;
      } else if (target == null) {
         // This is invalid case, target dto is from originating source.
         // this block should never be executed.
         log.error("Invalid scenario, target dto is from originating source.");
         throw new IMSServiceException(
                  IMSDefaultExceptionCodes.MIGRATION_EXCEPTION.errCode(),
                  IMSDefaultExceptionCodes.MIGRATION_EXCEPTION.errMsg());
      }
      try {
         BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
         // Iterate over all the attributes
         for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

            // Only copy writable attributes
            // skip name values.
            if (descriptor.getWriteMethod() != null
                     && !StringUtils.contains(descriptor.getName(), "Name")) {
               Object originalValue = descriptor.getReadMethod().invoke(target);

               boolean isBlank = false;
               if (originalValue instanceof String && StringUtils.isBlank((String) originalValue)) {
                  isBlank = true;
               }

               // Only copy values values where the destination values is null
               if (originalValue == null || isBlank) {
                  Object defaultValue = descriptor.getReadMethod().invoke(destination);
                  descriptor.getWriteMethod().invoke(target, defaultValue);
               }
            }
         }
      } catch (IntrospectionException | 
               IllegalAccessException | 
               IllegalArgumentException |
               InvocationTargetException e) {
         log.warn("error while merging dto.");
         throw new IMSServiceException(
                  IMSDefaultExceptionCodes.MIGRATION_EXCEPTION.errCode(),
                  IMSDefaultExceptionCodes.MIGRATION_EXCEPTION.errMsg());
      }
   }
}
