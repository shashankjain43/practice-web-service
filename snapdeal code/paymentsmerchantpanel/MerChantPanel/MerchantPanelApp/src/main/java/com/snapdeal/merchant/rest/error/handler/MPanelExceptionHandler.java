package com.snapdeal.merchant.rest.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.merchant.entity.response.GenericMerchantError;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;

@ControllerAdvice
public class MPanelExceptionHandler {

	@ExceptionHandler({ MerchantException.class })
	public @ResponseBody ResponseEntity<GenericMerchantResponse> handleMerchantException(
			MerchantException ex) {

		GenericMerchantResponse response = new GenericMerchantResponse();
		response.setData(null);

		GenericMerchantError error = new GenericMerchantError(ex.getErrCode(),
				ex.getErrMessage());
		response.setError(error);
		return new ResponseEntity<GenericMerchantResponse>(response,
				HttpStatus.OK);
	}
	
	@ExceptionHandler({ RoleMgmtException.class })
	public @ResponseBody ResponseEntity<GenericMerchantResponse> handleRoleMgmtException(
			RoleMgmtException ex) {

		GenericMerchantResponse response = new GenericMerchantResponse();
		response.setData(null);

		GenericMerchantError error = new GenericMerchantError(
				ex.getMessage());
		response.setError(error);
		return new ResponseEntity<GenericMerchantResponse>(response,
				HttpStatus.OK);
	}

	@ExceptionHandler({ Exception.class })
	public @ResponseBody ResponseEntity<GenericMerchantResponse> handleException(
			Exception ex) {
		GenericMerchantResponse response = new GenericMerchantResponse();
		response.setData(null);

		GenericMerchantError error = new GenericMerchantError(
				RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrCode(),
				RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrMsg()); 
		response.setError(error);
		return new ResponseEntity<GenericMerchantResponse>(response,
				HttpStatus.OK);
	}

}
