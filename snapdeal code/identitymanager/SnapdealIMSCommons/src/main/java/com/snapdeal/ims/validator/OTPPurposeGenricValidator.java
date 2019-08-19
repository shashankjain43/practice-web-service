package com.snapdeal.ims.validator;

import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.OTPPurposeGenricValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class OTPPurposeGenricValidator implements
        ConstraintValidator<OTPPurposeGenricValidation, OTPPurpose> {

   // List of OTP purpose which are allowed to call Generate and send otp.
   private static final EnumSet<OTPPurpose> ALLOWED_OTP_PURPOSE_TO_GENERATE_OTP = EnumSet.of(
                                                                        OTPPurpose.MOBILE_VERIFICATION, 
                                                                        OTPPurpose.UPDATE_MOBILE, 
                                                                        OTPPurpose.UPGRADE_USER,
                                                                        OTPPurpose.LINK_ACCOUNT,
                                                                        OTPPurpose.MONEY_OUT,
                                                                        OTPPurpose.WALLET_PAY,
                                                            			OTPPurpose.WALLET_LOAD,
                                                            			OTPPurpose.WALLET_ENQUIRY,
                                                            			OTPPurpose.LOGIN_WITH_EMAIL_OTP,
                                                            			OTPPurpose.LOGIN_WITH_MOBILE_OTP,
                                                            			OTPPurpose.SIGNUP_WITH_OTP);
   @Override
   public void initialize(OTPPurposeGenricValidation constraintAnnotation) {
   }

   @Override
   public boolean isValid(OTPPurpose name, ConstraintValidatorContext context) {

      if (ALLOWED_OTP_PURPOSE_TO_GENERATE_OTP.contains(name)) {
         return true;
      }
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
               IMSRequestExceptionCodes.INVALID_PURPOSE.toString()).addConstraintViolation();
      return false;
   }

}