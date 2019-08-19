package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.validator.annotation.CreateSocialUserRequestValidation;

public class CreateSocialUserRequestValidator implements
         ConstraintValidator<CreateSocialUserRequestValidation, CreateSocialUserRequest> {

   @Override
   public void initialize(CreateSocialUserRequestValidation constraintAnnotation) {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean isValid(CreateSocialUserRequest request, ConstraintValidatorContext context) {
      if (StringUtils.length(request.getSocialUserDto().getMobileNumber()) != CommonConstants.MOBILE_NUMBER_DIGIT) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(
                  IMSRequestExceptionCodes.INVALID_MOBILE_NUMBER.name()).addConstraintViolation();
         return false;
      }

      return true;
   }
}
