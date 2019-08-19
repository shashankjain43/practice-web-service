package com.snapdeal.ims.validator;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.Password;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

   private static final String PASSWORD_PATTERN = "^[A-Za-z0-9@%+'!~`#\\$?.\\^\\{\\}\\[\\]\\(\\)_\\-:, \\/\\\\]*$";

   private Password password;
   @Override
   public void initialize(Password constraintAnnotation) {
	   this.password = constraintAnnotation;
   }

   @Override
   public boolean isValid(String password, ConstraintValidatorContext context) {

      if (!StringUtils.isBlank(password)) {
         String passwordTrimed = password.trim();
         if (passwordTrimed.length() < 6 || passwordTrimed.length() > 127) {
            addConstraintViolation(context,
                     IMSRequestExceptionCodes.PASSWORD_MUST_CONTAIN_SIX_LETTER);
            return false;
         }
         if (!passwordTrimed.matches(PASSWORD_PATTERN)) {
            addConstraintViolation(context, IMSRequestExceptionCodes.INVALID_CHARACTER_PASSWORD);
            return false;
         }
      } else if(this.password.mandatory()){
         addConstraintViolation(context, IMSRequestExceptionCodes.PASSWORD_IS_BLANK);
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