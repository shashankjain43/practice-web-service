package com.snapdeal.ims.migration;

import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.MigrationConstants;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.migration.PostUpgradeStatusInfo.CreateOrUpdate;
import com.snapdeal.ims.migration.dto.UpgradeDto;
import com.snapdeal.ims.migration.util.UserAccountUtil;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.service.IUMSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component("ExistSDSide")
@Slf4j
public class ExistSDSide extends ExistOneSide {

   @Autowired
   @Qualifier("sdUserAccount")
   private UserAccountUtil sdAccountUtil;
   
   @Autowired
   @Qualifier("fcUserAccount")
   private UserAccountUtil fcAccountUtil;
   
   @Autowired
   @Qualifier("ocUserAccount")
   private UserAccountUtil ocAccountUtil;
   
   @Autowired
   @Qualifier("IMSService")
   private IUMSService imsService;

   @Override
   public PostUpgradeStatusInfo performTask(UserUpgradeRequest request, Merchant originatingSource,
            UpgradationInformationDTO upgradeInfo) {
      PostUpgradeStatusInfo postUpgradeStatusInfo = new PostUpgradeStatusInfo();
      if (Merchant.SNAPDEAL == originatingSource) {

         // Account exists in SD and user logged in via SD.
         // User doesn't exist in OC
         // Create user in OC with SD password.
         UpgradeDto mergedUserDetails = sdAccountUtil.getMergedUserDetails(request.getEmailId(),
                                                                           upgradeInfo.getState(),
                                                                           request.getVerifiedType(),
                                                                           request.getMobileNumber(),
                                                                           originatingSource,
                                                                           request.getUpgradeSource());
         // FC account doesn't exist so, create user in OC and mark account
         // migration complete.
         postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
         postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         // sdAccountUtil.createUser(mergedUserDetails);
         postUpgradeStatusInfo.setUpgradeDto(mergedUserDetails);
         postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.CREATE);
      } else if (Merchant.FREECHARGE == originatingSource) {
         // sign up from SD
			log.info(MigrationConstants.MERGING_USER + request.getEmailId());
			UpgradeDto mergedUserDetails = fcAccountUtil.getMergedUserDetails(
					request.getEmailId(), upgradeInfo.getState(),
					request.getVerifiedType(), request.getMobileNumber(),
					originatingSource, request.getUpgradeSource());
         // imsService.updateUserByEmail(mergedUserDetails);
         postUpgradeStatusInfo.setUpgradeDto(mergedUserDetails);
         postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.UPDATE);
         boolean isPasswordMatched = fcAccountUtil.isPasswordMatched(request.getEmailId());
         if (isPasswordMatched) {
            postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
            postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         } else {
            log.info(MigrationConstants.SKIPPING_MERGE+request.getEmailId());
            postUpgradeStatusInfo.setCurrentState(State.FC_ACCOUNT_MIGRATED);
            postUpgradeStatusInfo.setUpgradeStatus(Upgrade.LINK_SD_ACCOUNT);
         }
      } else if(Merchant.ONECHECK == originatingSource ){
         
         UpgradeDto mergedUserDetails = ocAccountUtil.getMergedUserDetails(request.getEmailId(),
                  upgradeInfo.getState(),
                  request.getVerifiedType(),
                  request.getMobileNumber(),
                  originatingSource,
                  request.getUpgradeSource());
         
         //Setting emailId/mobile is verified
         if(ocAccountUtil.isSocialLogin(mergedUserDetails.getVerifiedType())){
            // ocAccountUtil.createUser(mergedUserDetails);
            postUpgradeStatusInfo.setUpgradeDto(mergedUserDetails);
            postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.OC_CREATE);
            postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
            postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         }
      }
		/*sendEmail(
				request.getEmailId(),
				ConfigurationConstants.UPGRADE_USER_EMAIL_TEMPLATE,
				false,
				imsUtillity.getEmailSubject(ServiceCommonConstants.FREECHARGE, ConfigurationConstants.SEND_EMAIL_UPGRADE_ACCOUNT));*/
      setEmailData(postUpgradeStatusInfo, ConfigurationConstants.UPGRADE_USER_EMAIL_TEMPLATE,
               null);
      return postUpgradeStatusInfo;
   }
}