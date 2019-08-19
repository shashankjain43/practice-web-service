package com.snapdeal.ims.validator;

import com.snapdeal.ims.enums.UpgradeSource;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.validator.annotation.UserUpgradationRequestValidation;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserUpgradationRequestValidator implements
	ConstraintValidator<UserUpgradationRequestValidation, UserUpgradeRequest> {

	@Override
	public void initialize(UserUpgradationRequestValidation constraintAnnotation) {
	}

	@Override
	public boolean isValid(UserUpgradeRequest request, 
						  ConstraintValidatorContext context) {
		// email/token is required parameter.
		if(StringUtils.isBlank(request.getEmailId()) && StringUtils.isBlank(request.getToken())) {
			addConstraintViolation(context, IMSRequestExceptionCodes.EITHER_EMAIL_OR_TOKEN_MANDATORY);
			return false;
		}
		if(request.getVerifiedType() == null) {
		   addConstraintViolation(context, IMSRequestExceptionCodes.VERIFIED_TYPE_IS_BLANK);
		   return false;
		}
		if(request.getUpgradeSource() == null) {
		   addConstraintViolation(context, IMSRequestExceptionCodes.UPGRADE_SOURCE_MANDATORY);
         return false;
		}
      // Checking if UpgradeSource is sign-up.
      // user can upgrade after sign-in, so making token mandatory.
      if (request.getUpgradeSource() == UpgradeSource.SIGN_UP) {
         addConstraintViolation(context, IMSRequestExceptionCodes.INVALID_UPGRADE_SOURCE_SIGN_UP);
         return false;
      } else if (StringUtils.isBlank(request.getToken())) {
         addConstraintViolation(context, IMSRequestExceptionCodes.TOKEN_IS_BLANK);
         return false;
      }
		return true;
	}
	 private void addConstraintViolation(ConstraintValidatorContext context,
	            IMSRequestExceptionCodes requestExceptionCodes) {
	      context.disableDefaultConstraintViolation();
	      context.buildConstraintViolationWithTemplate(requestExceptionCodes.name())
	               .addConstraintViolation();
	   }
}