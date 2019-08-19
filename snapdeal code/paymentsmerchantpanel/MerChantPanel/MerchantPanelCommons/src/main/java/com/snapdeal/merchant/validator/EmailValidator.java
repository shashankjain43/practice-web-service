package com.snapdeal.merchant.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.snapdeal.merchant.annotation.Email;
import com.snapdeal.merchant.common.constant.CommonConstants;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;

public class EmailValidator implements ConstraintValidator<Email, String> {

   private Email email;

   @Override
   public void initialize(Email constraintAnnotation) {
      this.email = constraintAnnotation;
   }

   @Override
   public boolean isValid(String email, ConstraintValidatorContext context) {
      if (this.email.mandatory() == false) {
         if (StringUtils.isBlank(email))
            return true;
      }
      if (StringUtils.isNotBlank(email)) {

         if (!email.matches(CommonConstants.EMAIL_REGEX)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                     RequestExceptionCodes.EMAIL_FORMAT_INCORRECT.getErrCode())
                     .addConstraintViolation();
            return false;
         }
      } else if (this.email.mandatory()) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(
                  RequestExceptionCodes.EMAIL_IS_BLANK.getErrCode()).addConstraintViolation();
         return false;
      }
      return true;
   }
}
