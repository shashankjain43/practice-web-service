package com.snapdeal.ims.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.enums.Skip;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.service.dto.UserDTO;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Slf4j
@Component("IMSServiceImplOCPasswordLogin")
public class IMSServiceImplOCPasswordLogin extends IMSServiceImpl {

   @Autowired
   private AuthorizationContext context;

   @Override
   @Timed
   @Marked
   public LoginUserResponse loginUser(LoginUserRequest imsRequest) {

      if (StringUtils.isEmpty(imsRequest.getEmailId())
               && StringUtils.isNotBlank(imsRequest.getMobileNumber())) {
         log.error("login user with mobile is not support currently");
         throw new RequestParameterException(IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errCode(),
                  IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errMsg());
      }
      loginUserSetActivitData(imsRequest);
      // Validate logic is different based on the merchant type.
      // validateUserCredential() method is used to validate credential and
      // fetch UserDTO.
      UserDTO userDTO = validateUserCredential(imsRequest);
      log.warn("User logged in with id: " + imsRequest.getEmailId()
               + ", using OC password and user is not migrated. This could be a fraud transaction.");
      TokenInformationDTO tokenInformation = createTokenOnLogin(userDTO.getUserId(), userDTO.getEmailId(), true);
      // set activity data
      activityDataService.setActivityDataByEmailId(imsRequest.getEmailId());

      LoginUserResponse loginUserResponse = createLoginUserResponse(userDTO, 
                                                                    tokenInformation,
                                                                    getUpgradationInformationDTO(imsRequest.getEmailId()));
      return loginUserResponse;

   }

   /**
    * This method is used to change make sure that user cannot skip upgrade when
    * using OC password.
    */
   @Override
   protected UpgradationInformationDTO getUpgradationInformationDTO(String emailId) {
      UpgradationInformationDTO upgradationInformationDTO = super
               .getUpgradationInformationDTO(emailId);
      if (upgradationInformationDTO.getUpgrade() != Upgrade.UPGRADE_COMPLETED) {
         upgradationInformationDTO.setSkip(Skip.SKIP_FALSE);
      }
      return upgradationInformationDTO;
   }
}