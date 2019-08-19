package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.validator.annotation.UserUpgradeByEmailRequestValidation;

public class UserUpgradeByEmailRequestValidator implements
	ConstraintValidator<UserUpgradeByEmailRequestValidation, UserUpgradeByEmailRequest> {

	@Override
	public void initialize(UserUpgradeByEmailRequestValidation constraintAnnotation) {
	}

	@Override
	public boolean isValid(UserUpgradeByEmailRequest request, 
						  ConstraintValidatorContext context) {
		// email/token is required parameter.
		if(StringUtils.isBlank(request.getEmailId()) && StringUtils.isBlank(request.getToken())) {
			addConstraintViolation(context, IMSRequestExceptionCodes.EITHER_EMAIL_OR_TOKEN_MANDATORY);
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