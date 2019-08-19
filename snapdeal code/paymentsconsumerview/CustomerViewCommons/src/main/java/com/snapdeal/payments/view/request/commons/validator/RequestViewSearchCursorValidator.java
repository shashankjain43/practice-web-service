package com.snapdeal.payments.view.request.commons.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchCursorRequest;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchRequest;
import com.snapdeal.payments.view.request.commons.validator.annotation.RequestViewSearchCursorRequestValidation;
import com.snapdeal.payments.view.request.commons.validator.annotation.RequestViewSearchRequestValidation;

public class RequestViewSearchCursorValidator implements
         ConstraintValidator<RequestViewSearchCursorRequestValidation, GetRequestViewSearchCursorRequest> {

   @Override
   public void initialize(RequestViewSearchCursorRequestValidation constraintAnnotation) {
   }

   @Override
   public boolean isValid(GetRequestViewSearchCursorRequest request, ConstraintValidatorContext context) {
	   String srcpartyId =  request.getSrcPartyId();
	   String destPartyTxnId  = request.getDestPartyId() ;
	   String srcEmaild = request.getSrcEmailId() ;
	   String destEmailId = request.getDestEmailId();
	   String srcMobileNumber = request.getSrcMobileNumber();
	   String destMobileNumber = request.getDestMobileNumber();
	   String fcTxnId = request.getFcTxnId() ;
	   if(StringUtils.isBlank(srcpartyId) && StringUtils.isBlank(destPartyTxnId) &&
			   StringUtils.isBlank(srcEmaild) && StringUtils.isBlank(destEmailId) &&
			   StringUtils.isBlank(srcMobileNumber) && StringUtils.isBlank(destMobileNumber) && StringUtils.isBlank(fcTxnId)){
				addConstraintViolation(context, PaymentsViewServiceExceptionCodes.REQUEST_VIEW_SEARCH_CRITERA_MISSING);
		        return false;
		}
   		return true;
   }


   private void addConstraintViolation(ConstraintValidatorContext context,
		   PaymentsViewServiceExceptionCodes requestExceptionCodes) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(requestExceptionCodes.name())
               .addConstraintViolation();
   }

}

