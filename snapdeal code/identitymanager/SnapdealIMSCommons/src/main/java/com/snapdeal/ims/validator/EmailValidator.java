package com.snapdeal.ims.validator;

import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.MobileNumber;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

	private Email email;
   @Override
   public void initialize(Email constraintAnnotation) {
	   this.email = constraintAnnotation;
   }

   @Override
   public boolean isValid(String email, ConstraintValidatorContext context) {
	  if(this.email.mandatory() == false){
		  if(StringUtils.isBlank(email))
			  return true ;
	  }
      if (StringUtils.isNotBlank(email)) {
         if (email.length() > 128) {
            addConstraintViolation(context, IMSRequestExceptionCodes.EMAIL_MAX_LENGTH);
            return false;
         }
         if (!email.matches(CommonConstants.EMAIL_REGEX)) {
            addConstraintViolation(context, IMSRequestExceptionCodes.EMAIL_FORMAT_INCORRECT);
            return false;
         }
      } else if(this.email.mandatory()){
         addConstraintViolation(context, IMSRequestExceptionCodes.EMAIL_IS_BLANK);
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