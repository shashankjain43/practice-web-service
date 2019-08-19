package com.snapdeal.ims.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.validator.annotation.MobileNumber;
public class MobileNumberValidation implements ConstraintValidator<MobileNumber, String> {
  
   private boolean mandatory;
   @Override
   public void initialize(MobileNumber constraintAnnotation) {
	   mandatory = constraintAnnotation.mandatory();
   }
   @Override
   public boolean isValid(String mobileNumber, ConstraintValidatorContext context) {
	   
	   if(mandatory == false ){
		   if(StringUtils.isBlank(mobileNumber)){
			   return true;
		   }
	   }else {
		   if(StringUtils.isBlank(mobileNumber)){
			   context.disableDefaultConstraintViolation();
	           context.buildConstraintViolationWithTemplate(
	                    IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK.name())
	                    .addConstraintViolation();
	           return false;
		   }
	   }
	   
	   if(StringUtils.length(mobileNumber) != CommonConstants.MOBILE_NUMBER_DIGIT){
		   context.disableDefaultConstraintViolation();
           context.buildConstraintViolationWithTemplate(
                    IMSRequestExceptionCodes.INVALID_MOBILE_NUMBER.name())
                    .addConstraintViolation();
           return false;
           
	   }
	   if(!mobileNumber.matches(CommonConstants.MOBILE_NUMBER_REGEX)){
		   context.disableDefaultConstraintViolation();
           context.buildConstraintViolationWithTemplate(
                    IMSRequestExceptionCodes.INVALID_MOBILE_NUMBER.name())
                    .addConstraintViolation();
           return false;
	   }
	   
      return true;
   }
}