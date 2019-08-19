package com.snapdeal.ims.validator;

import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.enums.SocialSource;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocialUserDetailsDtoValidator {

	public void validate(CreateSocialUserRequest request) {
		SocialUserRequestDto dto = request.getSocialUserDto();
      boolean validateSocialSource = Boolean.valueOf(Configuration
               .getGlobalProperty(ConfigurationConstants.VALIDATE_SOCIAL_SOURCE));
      if (validateSocialSource) {
         String socialSrc = dto.getSocialSrc();
         if (StringUtils.isNotBlank(socialSrc)) {
            try {
               SocialSource src = SocialSource.forName(socialSrc);
               if (src == null)
                  throw new IMSServiceException(
                           IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errCode(),
                           IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errMsg());
            } catch (Exception e) {
               log.error("Invalid Social source: " + socialSrc);
               throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errCode(),
                        IMSServiceExceptionCodes.INVALID_SOCIAL_SRC.errMsg());
            }
         }
      }
		 if(dto.getSocialId()!=null && !StringUtils.isNumeric(dto.getSocialId())){
          throw new IMSServiceException(
                   IMSServiceExceptionCodes.INVALID_SOCIAL_ID.errCode(),
                   IMSServiceExceptionCodes.INVALID_SOCIAL_ID.errMsg());
       }
		// if flag == false all parameters are necessary
		boolean flag = new Boolean(
				Configuration.getGlobalProperty(ConfigurationConstants.LOGIN_SOCIALUSER_VALIDATE_ENABLE));
		if (!flag) {
			validateNotBlank(dto);
			validateLength(dto);
		} else {
			validateLength(dto);
		}

	}

	private void validateNotBlank(SocialUserRequestDto dto) {
		if (StringUtils.isBlank(dto.getSocialSrc())) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.SOCIAL_SOURCE_IS_BLANK.errCode(),
					IMSRequestExceptionCodes.SOCIAL_SOURCE_IS_BLANK.errMsg());
		}
		if (StringUtils.isBlank(dto.getSocialId())) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.SOCIAL_ID_IS_BLANK.errCode(),
					IMSRequestExceptionCodes.SOCIAL_ID_IS_BLANK.errMsg());
		}
      /*
       * if (StringUtils.isBlank(dto.getSocialSecret())) {
       * throw new RequestParameterException(
       * IMSRequestExceptionCodes.SOCIAL_SECRET_IS_BLANK.errCode(),
       * IMSRequestExceptionCodes.SOCIAL_SECRET_IS_BLANK.errMsg());
       * }
       * if (StringUtils.isBlank(dto.getSocialExpiry())) {
       * throw new RequestParameterException(
       * IMSRequestExceptionCodes.SOCIAL_EXPIRY_IS_BLANK.errCode(),
       * IMSRequestExceptionCodes.SOCIAL_EXPIRY_IS_BLANK.errMsg());
       * }
       * if (StringUtils.isBlank(dto.getSocialToken())) {
       * throw new RequestParameterException(
       * IMSRequestExceptionCodes.SOCIAL_TOKEN_IS_BLANK.errCode(),
       * IMSRequestExceptionCodes.SOCIAL_TOKEN_IS_BLANK.errMsg());
       * }
       */
	}

	private void validateLength(SocialUserRequestDto dto) {
		int length = 255;
		if (StringUtils.length(dto.getSocialSrc()) > length) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.SOCIAL_SRC_MAX_LENGTH.errCode(),
					IMSRequestExceptionCodes.SOCIAL_SRC_MAX_LENGTH.errMsg());
		}
		if (StringUtils.length(dto.getSocialId()) > length) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.SOCIAL_ID_MAX_LENGTH.errCode(),
					IMSRequestExceptionCodes.SOCIAL_ID_MAX_LENGTH.errMsg());
		}
		if (StringUtils.length(dto.getSocialSecret()) > length) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.SOCIAL_SECRET_MAX_LENGTH.errCode(),
					IMSRequestExceptionCodes.SOCIAL_SECRET_MAX_LENGTH.errMsg());
		}
		if (StringUtils.length(dto.getSocialExpiry()) > 20) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.SOCIAL_EXPIRY_MAX_LENGTH.errCode(),
					IMSRequestExceptionCodes.SOCIAL_EXPIRY_MAX_LENGTH.errMsg());
		}
      if (StringUtils.length(dto.getSocialToken()) > 512) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.SOCIAL_TOKEN_MAX_LENGTH.errCode(),
					IMSRequestExceptionCodes.SOCIAL_TOKEN_MAX_LENGTH.errMsg());
		}
		if (StringUtils.length(dto.getAboutMe()) > length) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.ABOUT_ME_MAX_LENGTH.errCode(),
					IMSRequestExceptionCodes.ABOUT_ME_MAX_LENGTH.errMsg());
		}
		if (StringUtils.length(dto.getPhotoURL()) > 400) {
			throw new RequestParameterException(
					IMSRequestExceptionCodes.PHOTO_URL_MAX_LENGTH.errCode(),
					IMSRequestExceptionCodes.PHOTO_URL_MAX_LENGTH.errMsg());
		}
	}

}
