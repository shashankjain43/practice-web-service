package com.snapdeal.opspanel.clientkeymanagement.utils;

import lombok.Getter;
import lombok.Setter;

public class GenericError{

	private String errorCode;
	private String errorMessage;
	
	
	
	public String getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}



	public GenericError(String errCode, String errMsg){
		this.errorCode = errCode;
		this.errorMessage = errMsg;
	}
	
}
