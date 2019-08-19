package com.snapdeal.merchant.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.snapdeal.merchant.annotation.Password;
import com.snapdeal.merchant.common.constant.CommonConstants;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;

public class PasswordValidator implements ConstraintValidator<Password, String> {

   private Password password;

   @Override
   public void initialize(Password constraintAnnotation) {
      this.password = constraintAnnotation;
   }

   @Override
   public boolean isValid(String password, ConstraintValidatorContext context) {

      if (!StringUtils.isBlank(password)) {
         String passwordTrimed = password.trim();

         if (!passwordTrimed.matches(CommonConstants.PASSWORD_PATTERN)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                     RequestExceptionCodes.INVALID_CHARACTER_PASSWORD.getErrCode())
                     .addConstraintViolation();
            return false;
         }
      } else if (this.password.mandatory()) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(
                  RequestExceptionCodes.PASSWORD_IS_BLANK.getErrCode()).addConstraintViolation();
         return false;
      }
      return true;
   }
}
