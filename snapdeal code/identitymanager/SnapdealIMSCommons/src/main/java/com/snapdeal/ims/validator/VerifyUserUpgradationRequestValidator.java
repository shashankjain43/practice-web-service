package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.enums.LinkUserVerifiedThrough;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.validator.annotation.VerifyUserUpgradationRequestValidation;

public class VerifyUserUpgradationRequestValidator implements
	ConstraintValidator<VerifyUserUpgradationRequestValidation, VerifyUserUpgradeRequest> {

	@Override
	public void initialize(VerifyUserUpgradationRequestValidation constraintAnnotation) {
	}

	@Override
	public boolean isValid(VerifyUserUpgradeRequest request, 
						  ConstraintValidatorContext context) {
		// email/token is required parameter.
		if(StringUtils.isBlank(request.getEmailId()) && StringUtils.isBlank(request.getToken())) {
			addConstraintViolation(context, IMSRequestExceptionCodes.EITHER_EMAIL_OR_TOKEN_MANDATORY);
			return false;
		}
		
		//IF purpose is verification using password than password must not be null
		if(LinkUserVerifiedThrough.LINK_ACCOUNT_VIA_PASSWORD == request.getVerifiedType()
				&& StringUtils.isBlank(request.getPassword())){
			addConstraintViolation(context, IMSRequestExceptionCodes.PASSWORD_IS_BLANK);
			return false;
			
		} else if(LinkUserVerifiedThrough.LINK_ACCOUNT_VIA_MOBILE_OTP == request.getVerifiedType()
				&& (StringUtils.isBlank(request.getOtp()) || StringUtils.isBlank(request.getOtpId()))){

			//IF purpose is verification using  mobile OTP than OTP must not be null
			addConstraintViolation(context, IMSRequestExceptionCodes.OTP_IS_BLANK);
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