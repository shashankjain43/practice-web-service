package com.snapdeal.opspanel.clientintegrationscomponent.exception;

import com.snapdeal.payments.disbursement.model.ExceptionErrorCode;

import lombok.Data;

@Data
public class ClientIntegrationException extends Exception {

	ExceptionErrorCode errorCode;
	String errorMessage;
	
	public ClientIntegrationException(ExceptionErrorCode exceptionErrorCode, String errorMessage) {
		super();
		this.errorCode = exceptionErrorCode;
		this.errorMessage = errorMessage;
	}
	
	
}
