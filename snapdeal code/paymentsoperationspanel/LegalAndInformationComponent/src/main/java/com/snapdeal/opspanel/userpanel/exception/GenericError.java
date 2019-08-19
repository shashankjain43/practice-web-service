package com.snapdeal.opspanel.userpanel.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericError{

	private String errorCode;
	private String errorMessage;
	
	public GenericError(String errCode, String errMsg){
		this.errorCode = errCode;
		this.errorMessage = errMsg;
	}
	
}
