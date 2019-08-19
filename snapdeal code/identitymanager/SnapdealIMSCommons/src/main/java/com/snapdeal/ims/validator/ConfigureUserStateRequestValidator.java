package com.snapdeal.ims.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.enums.ConfigureUserBasedOn;
import com.snapdeal.ims.enums.Reason;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.validator.annotation.ConfigureUserStateRequestValidation;

public class ConfigureUserStateRequestValidator implements
         ConstraintValidator<ConfigureUserStateRequestValidation, ConfigureUserStateRequest> {

   @Override
   public void initialize(ConfigureUserStateRequestValidation constraintAnnotation) {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean isValid(ConfigureUserStateRequest request, ConstraintValidatorContext context) {
      
	   ConfigureUserBasedOn configureUserBasedOn = request.getConfigureUserBasedOn() ;
	   
	 //to do enum check
	   if(request.getReason() == Reason.OTHERS){
		   if(StringUtils.isBlank(request.getDescription())){
			   return addConstraintViolation(context,IMSRequestExceptionCodes.DESCRIPTION_IS_BLANK) ;
		   }
	   }
	   switch(configureUserBasedOn){
		   case EMAIL:
			   if(StringUtils.isBlank(request.getEmailId())){
				   return addConstraintViolation(context,IMSRequestExceptionCodes.EMAIL_IS_BLANK) ;  
			   }
			   return true;
		   case MOBILE:
			   if(StringUtils.isBlank(request.getMobileNumber())){
				   return addConstraintViolation(context,IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK) ;
			   } 
			   return true;
		   case TOKEN :
			   if(StringUtils.isBlank(request.getToken())){
				   return addConstraintViolation(context,IMSRequestExceptionCodes.TOKEN_IS_BLANK) ;
			   }
			   return true;
		   case USER_ID:
			   if(StringUtils.isBlank(request.getUserId())){
				   return addConstraintViolation(context, IMSRequestExceptionCodes.USER_ID_IS_BLANK);
			   }
			   return true;
		   default :
			   return addConstraintViolation(context, IMSRequestExceptionCodes.INVALID_CONFIGURE_USER_STATE_BASED_ON);
	   }
	 
   }
   
   private boolean addConstraintViolation(ConstraintValidatorContext context,
           IMSRequestExceptionCodes requestExceptionCodes) {
     context.disableDefaultConstraintViolation();
     context.buildConstraintViolationWithTemplate(requestExceptionCodes.name())
              .addConstraintViolation();
     return false ;
  }
}