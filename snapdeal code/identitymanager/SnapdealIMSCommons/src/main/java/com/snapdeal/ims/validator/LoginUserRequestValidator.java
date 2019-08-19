package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.commons.validator.routines.EmailValidator;

import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.validator.annotation.LoginUserRequestValidation;

public class LoginUserRequestValidator implements
         ConstraintValidator<LoginUserRequestValidation, LoginUserRequest> {

   @Override
   public void initialize(LoginUserRequestValidation constraintAnnotation) {
   }

	@Override
	public boolean isValid(LoginUserRequest request,
			ConstraintValidatorContext context) {
		// 1. validate if both emailid and mobile no are blank.
		if (StringUtils.isBlank(request.getEmailId())
				&& StringUtils.isBlank(request.getMobileNumber())) {
			addConstraintViolation(context, IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY);
			return false;
		}


      // 3. if email is blank and mobile number is present
      // - phone number should be 10 digit
      // - phone number follow reg exp: ^[6-9]{1}[0-9]{9}$
      if (StringUtils.isBlank(request.getEmailId())) {
         if (StringUtils.isBlank(request.getMobileNumber())) {
            addConstraintViolation(context, IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK);
            return false;
         } else if (StringUtils.length(request.getMobileNumber()) != CommonConstants.MOBILE_NUMBER_DIGIT
                  && !request.getMobileNumber().matches(CommonConstants.MOBILE_NUMBER_REGEX)) {
            addConstraintViolation(context, IMSRequestExceptionCodes.INVALID_MOBILE_NUMBER);
            return false;
         }
      }
      // 4. Verify password.
      if (StringUtils.isBlank(request.getPassword())) {
         addConstraintViolation(context, IMSRequestExceptionCodes.PASSWORD_IS_BLANK);
         return false;
      } else if (StringUtils.length(request.getPassword()) < CommonConstants.MIN_PASSWORD_LENGTH
               || StringUtils.length(request.getPassword()) > CommonConstants.MAX_PASSWORD_LENGTH) {
         addConstraintViolation(context, IMSRequestExceptionCodes.PASSWORD_MUST_CONTAIN_SIX_LETTER);
         return false;
      }
      
      //5. If both email id and mobile number is provided
      if (StringUtils.isNotBlank(request.getEmailId())
				&& StringUtils.isNotBlank(request.getMobileNumber())) {
			addConstraintViolation(context, IMSRequestExceptionCodes.ONLY_EMAIL_ID_OR_MOBILE_NUMBER_REQUIRED);
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
