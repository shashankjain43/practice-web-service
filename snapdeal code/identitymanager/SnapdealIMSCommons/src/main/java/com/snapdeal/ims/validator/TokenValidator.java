package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.Token;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

public class TokenValidator implements ConstraintValidator<Token, String> {
	
	private Token token;
	@Override
	public void initialize(Token constraintAnnotation) {
		this.token = constraintAnnotation;
		
	}

	@Override
	public boolean isValid(String token, ConstraintValidatorContext context) {
		if(!StringUtils.isBlank(token) && !token.matches("[a-zA-Z0-9-_]+")){
			addConstraintViolation(context, IMSRequestExceptionCodes.INVALID_TOKEN);
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
