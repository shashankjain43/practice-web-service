package com.snapdeal.merchant.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import com.snapdeal.merchant.annotation.MobileNumber;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;

public class MobileNumberValidator implements ConstraintValidator<MobileNumber, String> {
	private boolean mandatory;

	public void initialize(MobileNumber constraintAnnotation) {
		mandatory = constraintAnnotation.mandatory();
	}

	public boolean isValid(String mobileNumber, ConstraintValidatorContext context) {
		if ((!mandatory) && (mobileNumber == null)) {
			return true;
		}

		if (StringUtils.isBlank(mobileNumber)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(RequestExceptionCodes.MOBILE_IS_BLANK.getErrCode()).addConstraintViolation();

			return false;
		}

		if (StringUtils.length(mobileNumber) != 10) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(RequestExceptionCodes.MOBILE_INVALID_FORMAT.getErrCode())
					.addConstraintViolation();

			return false;
		}

		if (!mobileNumber.matches("[6-9]{1}[0-9]{9}")) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(RequestExceptionCodes.MOBILE_INVALID_FORMAT.getErrCode())
					.addConstraintViolation();

			return false;
		}

		return true;
	}
}