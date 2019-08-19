package com.snapdeal.ims.exception;

import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;

public class RequestParameterException extends ValidationException {

	private static final long serialVersionUID = 1L;

	public RequestParameterException(String errCode,String errMsg) {
		super(errCode,errMsg);
	}
	
	public RequestParameterException() {

		super(IMSDefaultExceptionCodes.INVALID_REQUEST.errCode(),
				IMSDefaultExceptionCodes.INVALID_REQUEST.errMsg());
	}
	
}
