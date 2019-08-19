package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.validator.annotation.GenrateOTPRequestValidation;

public class GenrateOTPRequestValidator implements
ConstraintValidator<GenrateOTPRequestValidation, GenerateOTPServiceRequest> {

	@Override
	public void initialize(GenrateOTPRequestValidation constraintAnnotation) {
	}

	@Override
	public boolean isValid(GenerateOTPServiceRequest request, ConstraintValidatorContext context) {
		OTPPurpose type = request.getOtpType();
		switch (type) {
		case FORGOT_PASSWORD:
			return bothMobileAndEmailCheck(request, context);
		case LOGIN:
			return bothMobileAndEmailCheck(request, context) && tokenMandatoryCheck(request, context);
		case MOBILE_VERIFICATION:
		case UPDATE_MOBILE:
			return onlyMobileMandatoryCheck(request, context) && tokenMandatoryCheck(request, context);
		case UPGRADE_USER:
			return onlyMobileMandatoryCheck(request, context) && eitherEmailAndTokenMandatoryCheck(request, context);
		case LINK_ACCOUNT:
			return tokenMandatoryCheck(request, context) && onlyEmailMandatoryCheck(request, context);
		case USER_SIGNUP:
			return onlyMobileMandatoryCheck(request, context) && onlyEmailMandatoryCheck(request, context);
		case ONECHECK_SOCIAL_SIGNUP:
			return  onlyMobileMandatoryCheck(request, context) && onlyEmailMandatoryCheck(request, context);
		case MONEY_OUT:
			return tokenMandatoryCheck(request, context) &&  bothMobileAndEmailCheck(request, context);
		case WALLET_PAY :
		case WALLET_LOAD :
		case WALLET_ENQUIRY :
			return onlyMobileMandatoryCheck(request, context);
		case LOGIN_WITH_MOBILE_OTP:
			return onlyMobileMandatoryCheck(request, context);
		case LOGIN_WITH_EMAIL_OTP:
			return onlyEmailMandatoryCheck(request, context);
		case SIGNUP_WITH_OTP:
			return onlyMobileMandatoryCheck(request, context);
		default:
			addConstraintViolation(context, IMSRequestExceptionCodes.INVALID_PURPOSE);
			return false;
		}
	}

	private boolean eitherEmailAndTokenMandatoryCheck(
			GenerateOTPServiceRequest request,
			ConstraintValidatorContext context) {
		if (StringUtils.isBlank(request.getEmailId())
				&& StringUtils.isBlank(request.getToken())) {
			addConstraintViolation(context,
					IMSRequestExceptionCodes.EITHER_EMAIL_OR_TOKEN_MANDATORY);
			return false;
		}
		return true;
	}

	private boolean onlyEmailMandatoryCheck(GenerateOTPServiceRequest request,
			ConstraintValidatorContext context) {
		if (StringUtils.isBlank(request.getEmailId())) {
			addConstraintViolation(context,
					IMSRequestExceptionCodes.EMAIL_IS_BLANK);
			return false;
		}
		return true;
	}

	private boolean onlyMobileMandatoryCheck(GenerateOTPServiceRequest request,
			ConstraintValidatorContext context) {
		if (StringUtils.isBlank(request.getMobileNumber())) {
			addConstraintViolation(context,
					IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK);
			return false;
		}
		return true;
	}

	private boolean tokenMandatoryCheck(GenerateOTPServiceRequest request,
			ConstraintValidatorContext context) {
		if (StringUtils.isBlank(request.getToken())) {
			addConstraintViolation(context,
					IMSRequestExceptionCodes.TOKEN_IS_BLANK);
			return false;
		}
		return true;
	}

	private boolean bothMobileAndEmailCheck(GenerateOTPServiceRequest request,
			ConstraintValidatorContext context) {
		if (StringUtils.isBlank(request.getEmailId())
				&& StringUtils.isBlank(request.getMobileNumber())) {
			addConstraintViolation(context,
					IMSRequestExceptionCodes.MOBILE_AND_EMAIL_BOTH_EMPTY);
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