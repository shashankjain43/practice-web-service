package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.VerifyUserWithLinkedToken;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

public class VerifyUserWithLinkedTokenValidator implements ConstraintValidator<VerifyUserWithLinkedToken, String> {

   private VerifyUserWithLinkedToken token;

   @Override
   public void initialize(VerifyUserWithLinkedToken constraintAnnotation) {
      this.token = constraintAnnotation;
   }

   @Override
   public boolean isValid(String token, ConstraintValidatorContext context) {
      if (this.token.mandatory() == false) {
         if (StringUtils.isBlank(token))
            return true;
      }
      if (StringUtils.isNotBlank(token)) {
         if (token.length() > 154) {
            addConstraintViolation(context, IMSRequestExceptionCodes.TOKEN_MAX_LENGTH);
            return false;
         }
      } else if (this.token.mandatory()) {
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