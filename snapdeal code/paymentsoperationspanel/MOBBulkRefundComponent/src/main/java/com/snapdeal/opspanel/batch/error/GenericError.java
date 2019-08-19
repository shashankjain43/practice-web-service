package com.snapdeal.opspanel.batch.error;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericError{

	private String errorCode;
	private String errorMessage;
	private String errorSource;
	
	public GenericError(String errCode, String errMsg, String errSoc){
		this.errorCode = errCode;
		this.errorMessage = errMsg;
		this.errorSource = errSoc;
	}

	public GenericError(String errCode, String errMsg){
		this.errorCode = errCode;
		this.errorMessage = errMsg;
		this.errorSource = "";
	}

}
