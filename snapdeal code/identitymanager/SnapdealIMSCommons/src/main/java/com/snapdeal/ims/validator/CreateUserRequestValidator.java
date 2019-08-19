package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.request.CreateUserMobileGenerateRequest;
import com.snapdeal.ims.validator.annotation.CreateUserRequestValidation;

public class CreateUserRequestValidator implements
         ConstraintValidator<CreateUserRequestValidation, CreateUserMobileGenerateRequest> {

   @Override
   public void initialize(CreateUserRequestValidation constraintAnnotation) {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean isValid(CreateUserMobileGenerateRequest request,
            ConstraintValidatorContext context) {
      if (StringUtils.isBlank(request.getUserRequestDto().getEmailId())
               && StringUtils.isBlank(request.getUserRequestDto().getMobileNumber())) {
         addConstraintViolation(context, IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY);
         return false;
      }

      if (StringUtils.length(request.getUserRequestDto().getMobileNumber()) != CommonConstants.MOBILE_NUMBER_DIGIT) {

         addConstraintViolation(context, IMSRequestExceptionCodes.INVALID_MOBILE_NUMBER);
         return false;
      }

      if (StringUtils.isBlank(request.getUserRequestDto().getPassword())
               || (StringUtils.length(request.getUserRequestDto().getPassword()) < CommonConstants.MIN_PASSWORD_LENGTH)) {

         addConstraintViolation(context, IMSRequestExceptionCodes.PASSWORD_MUST_CONTAIN_SIX_LETTER);
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
