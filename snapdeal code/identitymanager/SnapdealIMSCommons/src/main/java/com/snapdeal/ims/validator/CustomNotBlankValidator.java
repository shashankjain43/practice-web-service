package com.snapdeal.ims.validator;

import java.util.EnumSet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.request.GenerateOTPRequest;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

public class CustomNotBlankValidator implements ConstraintValidator<CustomNotBlank, GenerateOTPRequest> {

	private static final EnumSet<OTPPurpose> ALLOWED_OTP_PURPOSE_TO_NOT_BLANK = EnumSet.of(
			OTPPurpose.MOBILE_VERIFICATION, 
			OTPPurpose.UPDATE_MOBILE, 
			OTPPurpose.UPGRADE_USER,
			OTPPurpose.LINK_ACCOUNT,
			OTPPurpose.MONEY_OUT,
			OTPPurpose.WALLET_PAY,
			OTPPurpose.WALLET_LOAD,
			OTPPurpose.WALLET_ENQUIRY,
			OTPPurpose.SIGNUP_WITH_OTP);
	
	
	@Override
	public void initialize(CustomNotBlank constraintAnnotation) {
	}

	@Override
	public boolean isValid(GenerateOTPRequest value, ConstraintValidatorContext context) {
		if(ALLOWED_OTP_PURPOSE_TO_NOT_BLANK.contains(value.getPurpose())){
			if(StringUtils.isBlank(value.getToken())){
				return false;
			}
		}
		return true;
	}

	

	

}
