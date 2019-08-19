package com.snapdeal.ims.migration;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.utility.EmailSenderUtility;
import com.snapdeal.ims.utility.IMSUtility;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * Class hierarchy which defines the existence of user in the system. <br/>
 */
@Slf4j
public abstract class UserAccountExist {
	
	@Autowired
	EmailSenderUtility emailSenderUtility;
	
	@Autowired
	AuthorizationContext context ;
	
   public abstract PostUpgradeStatusInfo performTask(UserUpgradeRequest request,
                                                     Merchant originatingSource,
                                                     UpgradationInformationDTO upgradeInfo);

   public void sendEmail(String email, ConfigurationConstants emailTempelateKey,
            boolean blocking,ConfigurationConstants subjectKey, String verificationUrl) {
      try {
         emailSenderUtility.sendEmail(email, emailTempelateKey, blocking,
                 subjectKey, verificationUrl);
      } catch (Exception ex) {
         log.error(MessageFormat.format("Upgrade email send failed : {0}, {1}, {2}, {3}, {4}",
                  email, emailTempelateKey, blocking,subjectKey, verificationUrl));
      }
   }

   protected void setEmailData(final PostUpgradeStatusInfo postUpgradeStatusInfo,
                               ConfigurationConstants emailTemplate,
                               ConfigurationConstants subject) {
	  postUpgradeStatusInfo.setSubjectKey(subject);
      postUpgradeStatusInfo.setEmailTemplate(emailTemplate);
   }
}