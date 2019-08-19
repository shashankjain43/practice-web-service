package com.snapdeal.ims.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.DOB;

public class DOBValidator implements ConstraintValidator<DOB, String> {

   private boolean checkUnderAge;

   @Override
   public void initialize(DOB constraintAnnotation) {
      this.checkUnderAge = constraintAnnotation.checkUnderAge();
   }

   @Override
   public boolean isValid(String dob, ConstraintValidatorContext context) {
      SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
      sdf.setLenient(false);
      Date dateOfBirth = null;
      String errorMsg = null;

      if(StringUtils.isBlank(dob))  // If dob is null then we should return true
      {
    	  return true;
      }
      try {
         dateOfBirth = sdf.parse(dob);
      } catch (ParseException e) {
         errorMsg = IMSRequestExceptionCodes.INVALID_DOB.name();
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(errorMsg).addConstraintViolation();
         return false;
      }

      if (dateOfBirth == null) {
         setErrorMsgToContext(context, IMSRequestExceptionCodes.INVALID_DOB.name());
         return false;
      }

      if (dateOfBirth.getTime() > Calendar.getInstance().getTimeInMillis()) {
         setErrorMsgToContext(context, IMSRequestExceptionCodes.INVALID_DOB.name());
         return false;
      }

      if (this.checkUnderAge) {
         Calendar calendar18YrsBefore = Calendar.getInstance();
         calendar18YrsBefore.add(Calendar.YEAR, -18);

         if (dateOfBirth.after(calendar18YrsBefore.getTime())) {
            setErrorMsgToContext(context, IMSRequestExceptionCodes.UNDER_AGED.name());
            return false;
         }
      }

      return true;
   }
   
   private void setErrorMsgToContext(ConstraintValidatorContext context, String errorMsg){
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(errorMsg).addConstraintViolation();
   }

}
