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

@Component("ExistFCSide")
@Slf4j
public class ExistFCSide extends ExistOneSide {

   @Autowired
   @Qualifier("fcUserAccount")
   private UserAccountUtil fcAccountUtil;
   
   @Autowired
   @Qualifier("sdUserAccount")
   private UserAccountUtil sdUserAccount;

   @Autowired
   @Qualifier("ocUserAccount")
   private UserAccountUtil ocUserAccount;

   @Autowired
   @Qualifier("IMSService")
   private IUMSService imsService;

   @Override
   public PostUpgradeStatusInfo performTask(UserUpgradeRequest request, 
                                            Merchant originatingSource,
                                            UpgradationInformationDTO upgradeInfo) {
      final PostUpgradeStatusInfo postUpgradeStatusInfo = new PostUpgradeStatusInfo();
      // kishan starts
      // check password same or not
      // 1. merge user using fc user
      // 2. update in oc db
      // 3. populate post upgrade status
      if (Merchant.FREECHARGE == originatingSource) {
         // Account exists in FC and user logged in via FC.
         // User doesn't exist in OC
         // Create user in OC using password of FC.
         UpgradeDto mergedUserDetails = fcAccountUtil.getMergedUserDetails(request.getEmailId(),
                                                                           upgradeInfo.getState(),
                                                                           request.getVerifiedType(),
                                                                           request.getMobileNumber(),
                                                                           originatingSource,
                                                                           request.getUpgradeSource());
         // SD account doesn't exist, create OC account and mark upgrade status
         // as complete.
         postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
         postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         // Create user in OC using password of FC.
         // fcAccountUtil.createUser(mergedUserDetails);
         postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.CREATE);
         postUpgradeStatusInfo.setUpgradeDto(mergedUserDetails);
		} else if (Merchant.SNAPDEAL == originatingSource) {
			// sign up from SD
			UpgradeDto mergedUserDetails = sdUserAccount.getMergedUserDetails(
					request.getEmailId(), upgradeInfo.getState(),
					request.getVerifiedType(), request.getMobileNumber(),
					originatingSource, request.getUpgradeSource());
         // imsService.updateUserByEmail(mergedUserDetails);
         postUpgradeStatusInfo.setUpgradeDto(mergedUserDetails);
         postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.UPDATE);
			boolean isPasswordMatched = sdUserAccount.isPasswordMatched(request
					.getEmailId());
			if (isPasswordMatched) {
				log.info(MigrationConstants.MERGING_USER + request.getEmailId());
				postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
				postUpgradeStatusInfo
						.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
			} else {
				log.info(MigrationConstants.SKIPPING_MERGE
						+ request.getEmailId());
				postUpgradeStatusInfo
						.setCurrentState(State.SD_ACCOUNT_MIGRATED);
				postUpgradeStatusInfo.setUpgradeStatus(Upgrade.LINK_FC_ACCOUNT);
			}
		} else if(Merchant.ONECHECK == originatingSource){
         UpgradeDto mergedUserDetails = ocUserAccount.getMergedUserDetails(request.getEmailId(),
                  upgradeInfo.getState(),
                  request.getVerifiedType(),
                  request.getMobileNumber(),
                  originatingSource,
                  request.getUpgradeSource());
         //Updating user emailId/mobile as verified
         if(ocUserAccount.isSocialLogin(mergedUserDetails.getVerifiedType())){
            // ocUserAccount.createUser(mergedUserDetails);
            postUpgradeStatusInfo.setCreateOrUpdate(CreateOrUpdate.CREATE);
            postUpgradeStatusInfo.setUpgradeDto(mergedUserDetails);
            postUpgradeStatusInfo.setCurrentState(State.OC_ACCOUNT_EXISTS);
            postUpgradeStatusInfo.setUpgradeStatus(Upgrade.UPGRADE_COMPLETED);
         }
      }
		/*sendEmail(request.getEmailId(),
				ConfigurationConstants.UPGRADE_USER_EMAIL_TEMPLATE, false,
				imsUtillity.getEmailSubject(ServiceCommonConstants.FREECHARGE,
						ConfigurationConstants.SEND_EMAIL_UPGRADE_ACCOUNT));*/
      setEmailData(postUpgradeStatusInfo, ConfigurationConstants.UPGRADE_USER_EMAIL_TEMPLATE,
               null);
      return postUpgradeStatusInfo;
   }

}
