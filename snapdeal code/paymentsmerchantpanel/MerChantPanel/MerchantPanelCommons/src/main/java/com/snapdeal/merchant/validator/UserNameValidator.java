package com.snapdeal.merchant.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.snapdeal.merchant.annotation.UserName;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;

public class UserNameValidator implements ConstraintValidator<UserName, String> {

   private UserName userName;

   @Override
   public void initialize(UserName constraintAnnotation) {
      this.userName = constraintAnnotation;
   }

   @Override
   public boolean isValid(String userName, ConstraintValidatorContext context) {
      if (this.userName.mandatory() == false) {
         if (StringUtils.isBlank(userName))
            return true;
      }

      if (this.userName.mandatory()) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(
                  RequestExceptionCodes.USERNAME_IS_BLANK.getErrCode()).addConstraintViolation();
         return false;
      }
      return true;

   }

}
