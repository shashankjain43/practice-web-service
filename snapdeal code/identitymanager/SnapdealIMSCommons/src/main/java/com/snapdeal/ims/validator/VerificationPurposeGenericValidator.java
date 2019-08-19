package com.snapdeal.ims.validator;

import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.VerificationPurposeGenericValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class VerificationPurposeGenericValidator implements
         ConstraintValidator<VerificationPurposeGenericValidation, VerificationPurpose> {

   private static final EnumSet<VerificationPurpose> ALLOWED_VERIFICATION_PURPOSE = EnumSet
            .of(VerificationPurpose.PARKING_INTO_WALLET,VerificationPurpose.VERIFY_NEW_USER);

   @Override
   public void initialize(VerificationPurposeGenericValidation constraintAnnotation) {
   }

   @Override
   public boolean isValid(VerificationPurpose name, ConstraintValidatorContext context) {

      if (ALLOWED_VERIFICATION_PURPOSE.contains(name)) {
         return true;
      }
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
               IMSRequestExceptionCodes.INVALID_VERIFICATION_PURPOSE.toString())
               .addConstraintViolation();
      return false;
   }

}