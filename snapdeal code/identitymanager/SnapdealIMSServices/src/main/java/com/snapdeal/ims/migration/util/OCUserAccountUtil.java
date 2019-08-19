package com.snapdeal.ims.migration.util;

import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.enums.UserIdentityVerifiedThrough;
import com.snapdeal.ims.enums.UserStatus;
import com.snapdeal.ims.errorcodes.IMSMigrationExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.migration.dto.UpgradeDto;
import com.snapdeal.ims.objectMapper.IMSServiceObjectsMapper;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.dto.UserDTO;

import org.apache.commons.lang.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service("ocUserAccount")
@Slf4j
public class OCUserAccountUtil extends UserAccountImpl {

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
   IPasswordUpgradeCacheService passwordcacheService;

   @Autowired
   private IUserDao userDao;

   /**
    * This method will update the user status (email, mobile and account status)
    * for given user
    */
   @Override
   public void createUser(UpgradeDto upgradeDto) {
      switch (upgradeDto.getVerifiedType()) {
      
         case FACEBOOK_VERIFIED:
         case GOOGLE_VERIFIED:
            updateOCSocialUser(upgradeDto);
            break;
         default:
            throw new IMSServiceException(
                     IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errCode(),
                     IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errMsg());
      }
   }
   
   /**
    * This function will does the followings
    * a.  Mark OC user Email: verified
    * b.  Mobile: verified
    * c.  Account Status: REGISTERED
    * @param upgradeDto
    */
   private void updateOCSocialUser(UpgradeDto upgradeDto) {
     
     /*Mark OC user Email: verified, mobile: verified, Account Status: REGISTERED*/
     User migratedUser = IMSServiceObjectsMapper.mapUpgradeDtoToUser(upgradeDto);
     if(migratedUser!=null && migratedUser.getEmailId()!=null){
        
        migratedUser.setEmailVerified(true);
        migratedUser.setMobileVerified(true);
        migratedUser.setStatus(UserStatus.REGISTERED);

        migratedUser.setEnabled(true);
		if (UserIdentityVerifiedThrough.FACEBOOK_VERIFIED.equals(upgradeDto
				.getVerifiedType())) {
			migratedUser.setFacebookUser(true);
		} else if (UserIdentityVerifiedThrough.GOOGLE_VERIFIED
				.equals(upgradeDto.getVerifiedType())) {
			migratedUser.setGoogleUser(true);
		}
        
        userDao.updateByEmailId(migratedUser);
     } else {
        /**
         * We shouldn't be here, It is un-expected behavior.
         * user must be created in TEMP state before hitting doMigration() 
         * with SIGN_IN upgradeStatus from oneCheck UI for social user 
         * verification using mobile OTP 
         */
        throw new IMSServiceException(
                 IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errCode(),
                 IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errMsg());
     }
   }

   /**
    * This function should do the followings
    * 1. SD_FC_MergedInfo: Merge FC & SD account, give preference to SD
    * 2. Merge OC & SD_FC_MergedInfo, give preference to SD  
    */
   @Override
   public UpgradeDto getMergedUserDetails(String email, 
            State currentAccountExistence,
            UserIdentityVerifiedThrough userIdentityVerifiedThrough, 
            String mobileNumber,
            Merchant originatingSource,
            final UpgradeSource upgradeSource) {

      /* 
       * Merge sdUser and fcUser and OCUser, 
       * if any value exist at both sides then give preference to sdUser's value
       */
      UserDTO sdUser = null;
      UserDTO fcUser = null;
      UpgradeDto upgradeDto = null;
      
      /*Step0. Getting ocUserDto from OC User*/
      User ocUser = userDao.getUserByEmail(email);//fetchUserFromOC(mobileNumber);
      if(ocUser == null){
         /**
          * We shouldn't be here, It is un-expected behavior.
          * user must be created in TEMP state before hitting doMigration() 
          * with SIGN_IN upgradeStatus from oneCheck UI for social user 
          * verification using mobile OTP 
          */
         throw new IMSServiceException(
                  IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
                  IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
      }
      UserDTO ocUserDto = IMSServiceObjectsMapper.mapUserToUserDTO(ocUser);
       
      if (currentAccountExistence == State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED) {
         
         /*Step1. Fetch both SD and FC user*/
         sdUser = fetchUserFromSD(email);
         fcUser = fetchUserFromFC(email);
         
         /*Step2a. Merging SD FC userDtos*/
         upgradeDto = mergeDTO(sdUser, fcUser, Merchant.SNAPDEAL, upgradeSource);
         User mergedSDFCUser = new User(); 
         mergedSDFCUser = 
                  IMSServiceObjectsMapper.mapUpgradeDtoToUser(mergedSDFCUser, upgradeDto);
         UserDTO mergedSDFCUserDto = 
                  IMSServiceObjectsMapper.mapUserToUserDTO(mergedSDFCUser);
         
         /*Step2b. Finally merging ocUserDto and mergedSDFCUserDto*/
         upgradeDto = mergeDTO(mergedSDFCUserDto, ocUserDto, Merchant.SNAPDEAL, upgradeSource);
         
         /*Step3: Update Ids and social sources*/
         upgradeDto.setSdId(sdUser.getSdUserId());
         upgradeDto.setFcId(fcUser.getFcUserId());
         upgradeDto.setSdSocialSource(sdUser.getSocialInfo());
         upgradeDto.setFcSocialSource(fcUser.getSocialInfo());
      } else if (currentAccountExistence == State.SD_ACCOUNT_EXISTS_AND_ENABLED) {
         
         /*Step1: Fetch SD User*/
         sdUser = fetchUserFromSD(email);
         
         /*Step2: Merge existing OCUser info and  SDUserInfo, give preference to SNAPDEAL*/
         upgradeDto = mergeDTO(sdUser, ocUserDto, Merchant.SNAPDEAL, upgradeSource);
         
         /*Step3: Update Ids and social sources*/
         upgradeDto.setSdId(sdUser.getSdUserId());
         upgradeDto.setSdSocialSource(sdUser.getSocialInfo());
      } else if (currentAccountExistence == State.FC_ACCOUNT_EXISTS_AND_ENABLED) {
         /*Step1: Fetch FC User*/
         fcUser = fetchUserFromFC(email);
         
         /*Step2: Merge existing OCUser info and  FCUserInfo, give preference to ONECHECK*/
         upgradeDto = mergeDTO(ocUserDto, fcUser, Merchant.ONECHECK, upgradeSource);
         
         /*Step3: Update Ids and social sources*/
         upgradeDto.setFcId(fcUser.getFcUserId());
         upgradeDto.setFcSocialSource(fcUser.getSocialInfo());
      } else {
         
         log.error("Invalid state: " + currentAccountExistence + " is not supported.");
         throw new IMSServiceException(
                  IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errCode(),
                  IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errMsg());
         // throw exception
      }
    
      /*Step4: Set random password(in case social login) and verified type*/
      upgradeDto.setVerifiedType(userIdentityVerifiedThrough);
      if (isSocialLogin(userIdentityVerifiedThrough)) {
         //Setting default social user password
         upgradeDto.setFinalOCPassword(ServiceCommonConstants.DEFAULT_SOCIAL_USER_PASSWORD);
      }
      return upgradeDto;
   }
   
   /**
    * Utility method to merge user DTO with preference.
    * 
    * @param sdUser
    * @param fcUser
    * @param preferredSource
    * @param upgradeSource 
    * @return
    */
   @Override
   public UpgradeDto mergeDTO(UserDTO userDto1, 
                              UserDTO userDto2, 
                              Merchant preferredSource, 
                              UpgradeSource upgradeSource) {
      
      UserDTO targetDTO = null;
      UserDTO fromDTO = null;
      UpgradeDto upgradeDto = new UpgradeDto();
      
      switch (preferredSource) {
            case SNAPDEAL:
               targetDTO = userDto1;
               fromDTO = userDto2;
               break;
            case ONECHECK:
               targetDTO = userDto1;
               fromDTO = userDto2;
               break;
            case FREECHARGE:
            default:
               log.error("Invalid prefferd source. " + preferredSource + " is not supported.");
               throw new IMSServiceException(
                        IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errCode(),
                        IMSMigrationExceptionCodes.ILLEGAL_MIGRATION_STATE_EXCEPTION.errMsg());
      }
      merge(targetDTO, fromDTO);
      mapUserDetailsToUpgradeDTO(upgradeDto, targetDTO);
      return upgradeDto;
   }
   
   @Override
   protected String getMyPasswordHashedByOthersAlgo(String email) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected String getOthersHashedPassword(String email) {
      // TODO Auto-generated method stub
      return null;
   }
}