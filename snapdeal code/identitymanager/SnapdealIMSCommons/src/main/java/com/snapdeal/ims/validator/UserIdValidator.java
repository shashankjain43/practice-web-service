package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.math.NumberUtils;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.UserId;

public class UserIdValidator implements ConstraintValidator<UserId, String> {

   @Override
   public void initialize(UserId constraintAnnotation) {
	  
   }

   @Override
   public boolean isValid(String userId, ConstraintValidatorContext context) {
	   try{
		   if(NumberUtils.isNumber(userId)){
			   int intUserId = Integer.parseInt(userId);
		   }
	   }catch(NumberFormatException e){
		   addConstraintViolation(context,IMSRequestExceptionCodes.INVALID_USER_ID) ;
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