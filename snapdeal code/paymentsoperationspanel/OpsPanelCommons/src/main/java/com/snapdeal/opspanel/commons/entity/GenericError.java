package com.snapdeal.opspanel.commons.entity;

import lombok.Data;

@Data
public class GenericError {

	private String errorCode;
	private String errorMessage;
	private String errorSource;

	public GenericError(String errCode, String errMsg){
		this.errorCode = errCode;
		this.errorMessage = errMsg;
		this.errorSource = "";
	}

	public GenericError(String errCode, String errMsg, String errSoc){
		this.errorCode = errCode;
		this.errorMessage = errMsg;
		this.errorSource = errSoc;
	}

}
