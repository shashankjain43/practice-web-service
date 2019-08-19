package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.ResourceValidation;

/*
 * Author rajesh.verma
 * 
 */

public class ResourceValidator implements
		ConstraintValidator<ResourceValidation, String> {

	private ResourceValidation resource;

	@Override
	public void initialize(ResourceValidation constraintAnnotation) {
		this.resource = constraintAnnotation;
	}

	@Override
	public boolean isValid(String resource, ConstraintValidatorContext context) {

		if (StringUtils.isNotBlank(resource)) {
			if (resource.length() > 128) {
				addConstraintViolation(context,
						IMSRequestExceptionCodes.RESOURCE_MAX_LENGTH);
				return false;
			}
		}

		if (this.resource.mandatory() == false) {
			return true;
		} else {
			addConstraintViolation(context,
					IMSRequestExceptionCodes.RESOURCE_IS_NULL);
			return false;
		}

	}

	private void addConstraintViolation(ConstraintValidatorContext context,
			IMSRequestExceptionCodes requestExceptionCodes) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(
				requestExceptionCodes.name()).addConstraintViolation();
	}

}
