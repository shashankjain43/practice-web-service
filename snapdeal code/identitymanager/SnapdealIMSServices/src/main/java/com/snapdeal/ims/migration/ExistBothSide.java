package com.snapdeal.ims.migration;

import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSMigrationExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.migration.PostUpgradeStatusInfo.CreateOrUpdate;
import com.snapdeal.ims.migration.dto.UpgradeDto;
import com.snapdeal.ims.migration.util.UserAccountUtil;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("ExistBothSide")
public class ExistBothSide extends UserAccountExist {

   @Qualifier("sdUserAccount")
   @Autowired
   private UserAccountUtil sdUserAccount;
   
   @Qualifier("fcUserAccount")
   @Autowired
   private UserAccountUtil fcUserAccount;
   
   @Qualifier("ocUserAccount")
   @Autowired
   private UserAccountUtil ocUserAccount;

   @Autowired
   private IPasswordUpgradeCacheService passwordCacheService;

   @Autowired
   private MigratorObjectFactory migratorObjectFactory;

   @Override
   @Timed
   @Marked
   @Logged
   public PostUpgradeStatusInfo performTask(UserUpgradeRequest request,
                                            Merchant originatingSource,
                                            UpgradationInformationDTO upgradeInfo) {

      log.info("User exists in both side : " + request.getEmailId());
      final PostUpgradeStatusInfo postUpgradeStatusInfo = new PostUpgradeStatusInfo();
      // Get account util using originating source.
      UserAccountUtil accountUtil = migratorObjectFactory.getAccountUtil();
      // Upgrade dto from both the source sd/fc.
      UpgradeDto upgradeDto = accountUtil.getMergedUserDetails(request.getEmailId(),
                                                               upgradeInfo.getState(),
                                                               request.getVerifiedType(),
                                                               request.getMobileNumber(),
                                                               originatingSource,
                                                               request.getUpgradeSource());
      if(Merchant.ONECHECK.equals(originatingSource) && accountUtil.isSocialLogin(upgradeDto.getVerifiedType())){
         
         // accountUtil.createUser(upgradeDto);
         postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.CREATE);
         postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
         postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
      } else {
         performTask(request, originatingSource, upgradeInfo, postUpgradeStatusInfo, upgradeDto, accountUtil);
      }  
/*		sendEmail(request.getEmailId(),
				ConfigurationConstants.UPGRADE_USER_EMAIL_TEMPLATE, false,
				imsUtillity.getEmailSubject(ServiceCommonConstants.FREECHARGE,
						ConfigurationConstants.SEND_EMAIL_UPGRADE_ACCOUNT));*/
      postUpgradeStatusInfo.setUpgradeDto(upgradeDto);
      setEmailData(postUpgradeStatusInfo, ConfigurationConstants.UPGRADE_USER_EMAIL_TEMPLATE, null);
      return postUpgradeStatusInfo;
   }

   private void performTask(UserUpgradeRequest request, Merchant originatingSource,
            UpgradationInformationDTO upgradeInfo, PostUpgradeStatusInfo postUpgradeStatusInfo, 
            UpgradeDto upgradeDto, UserAccountUtil accountUtil) {
      // Users logged in via social
      if (accountUtil.isSocialLogin(upgradeDto.getVerifiedType())) {
         log.info("Social User : " + request.getEmailId());
         // This condition will come only in case of Sign-in.
         // If social user exists in both side and current sign-in is social
         // create user in oc and return status as migration complete
         
         if (upgradeDto.isUserSocialOnBothSide()) {
            // accountUtil.createUser(upgradeDto);
            postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.CREATE);
            postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
            postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         }
         // If user logged in via social from FC, 
         // create user and mark current state merge complete for originating
         // source.
         // mark upgrade state as link for other source.
         else if (upgradeDto.isSocialOnFC()) {
            // accountUtil.createUser(upgradeDto);
            postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.CREATE);
            if (Merchant.FREECHARGE == originatingSource) {
               postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
               postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
            } else if (Merchant.SNAPDEAL == originatingSource) {
               postUpgradeStatusInfo.setCurrentState(State.SD_ACCOUNT_MIGRATED);
               postUpgradeStatusInfo.setUpgradeStatus(Upgrade.LINK_FC_ACCOUNT);
            }
         } 
         // If user logged in via social from SD, 
         // create user and mark current state merge complete for originating
         // source.
         // mark upgrade state as link for other source.
         else if (upgradeDto.isSocialOnSD()) {
            // accountUtil.createUser(upgradeDto);
            postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.CREATE);
            if (Merchant.SNAPDEAL == originatingSource) {
               postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
               postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
            } else if (Merchant.FREECHARGE == originatingSource) {
               postUpgradeStatusInfo.setCurrentState(State.FC_ACCOUNT_MIGRATED);
               postUpgradeStatusInfo.setUpgradeStatus(Upgrade.LINK_SD_ACCOUNT);
            }
         } else {
            throw new IMSServiceException(
                     IMSMigrationExceptionCodes.INVALID_USER_VERIFIED_THROUGH.errCode(),
                     IMSMigrationExceptionCodes.INVALID_USER_VERIFIED_THROUGH.errMsg());
         }
      }
      
      // this is the case when user logged other then social.
      // Check password:
      // if password matches: upgrade complete and oc accout exists.
      // else: link for other source and mark upgrade complete for originating
      //       source.
      else {
         // creating oc user
         // accountUtil.createUser(upgradeDto);
         postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.CREATE);
         // Check if password matches.
         if (accountUtil.isPasswordMatched(upgradeDto.getEmailId())
                  && upgradeInfo.getState() == State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED) {
            log.debug("Password matches for email : " + request.getEmailId());
            postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
            postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         } else {
            if (Merchant.FREECHARGE == originatingSource) {

               postUpgradeStatusInfo.setCurrentState(State.FC_ACCOUNT_MIGRATED);
               postUpgradeStatusInfo.setUpgradeStatus(Upgrade.LINK_SD_ACCOUNT);
            } else if (Merchant.SNAPDEAL == originatingSource) {

               postUpgradeStatusInfo.setCurrentState(State.SD_ACCOUNT_MIGRATED);
               postUpgradeStatusInfo.setUpgradeStatus(Upgrade.LINK_FC_ACCOUNT);
            }
         }
      }
   }
}