package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.validator.annotation.ForgotPasswordRequestValidation;

public class ForgotPasswordRequestValidator
		implements
		ConstraintValidator<ForgotPasswordRequestValidation, ForgotPasswordRequest> {

	@Override
	public void initialize(ForgotPasswordRequestValidation constraintAnnotation) {

	}

	@Override
	public boolean isValid(ForgotPasswordRequest request,
			ConstraintValidatorContext context) {
		if (StringUtils.isNotBlank(request.getEmailId())
				|| StringUtils.isNotBlank(request.getMobileNumber())) {
			return true;
		}
		addConstraintViolation(context,
				IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY);
		return false;
	}
	
   private void addConstraintViolation(ConstraintValidatorContext context,
            IMSRequestExceptionCodes requestExceptionCodes) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(requestExceptionCodes.name())
               .addConstraintViolation();
   }
}