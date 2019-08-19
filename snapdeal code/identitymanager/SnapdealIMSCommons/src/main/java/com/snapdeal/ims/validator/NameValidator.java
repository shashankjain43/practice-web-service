package com.snapdeal.ims.validator;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.Name;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

	private static final String NAME_PATTERN = "^(([a-zA-Z0-9_-][a-zA-Z0-9 _-]*[a-zA-Z0-9_-])|([a-zA-Z0-9_-]))$";

	private Name.NameType nameType;

	@Override
	public void initialize(Name constraintAnnotation) {
		nameType = constraintAnnotation.type();
	}

	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {

		if (StringUtils.isBlank(name)) {
			return true;
		} else {
			if (name.length() > 50) {
				String errorMsg = null;
				switch (nameType) {
				case FIRST:
					errorMsg = IMSRequestExceptionCodes.NAME_MAX_LENGTH.name();
					break;
				case MIDDLE:
					errorMsg = IMSRequestExceptionCodes.MIDDLE_NAME_MAX_LENGTH
							.name();
					break;
				case LAST:
					errorMsg = IMSRequestExceptionCodes.LAST_NAME_MAX_LENGTH
							.name();
					break;
				case DISPLAY:
					errorMsg = IMSRequestExceptionCodes.DISPLAY_NAME_MAX_LENGTH
							.name();
					break;
				}
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(errorMsg)
						.addConstraintViolation();
				return false;
			}
			if (!name.matches(NAME_PATTERN)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(
						IMSRequestExceptionCodes.NAME_INVALID_CHARACTERS.name())
						.addConstraintViolation();
				return false;
			}
		}
		return true;
	}
}