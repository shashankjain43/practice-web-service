package com.snapdeal.ims.exception;

import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;


public class ValidationException extends IMSGenericException {

	private static final long serialVersionUID = 1L;

	public ValidationException(String errCode,String errMsg) {
		super(errCode,errMsg);
	}

	public ValidationException() {
		this(IMSDefaultExceptionCodes.VALIDATION.errCode(),IMSDefaultExceptionCodes.VALIDATION.errMsg());
	}

}
