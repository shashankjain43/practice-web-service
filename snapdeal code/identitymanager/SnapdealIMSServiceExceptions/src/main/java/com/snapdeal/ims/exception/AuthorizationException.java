package com.snapdeal.ims.exception;

import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;

public class AuthorizationException extends IMSGenericException {

	private static final long serialVersionUID = 2098153784319255395L;

	public AuthorizationException(String errCode,String errMsg) {
		super(errCode,errMsg);
	}

	public AuthorizationException(Throwable cause) {
		super(IMSDefaultExceptionCodes.AUTHORIZATION.errCode(),
				IMSDefaultExceptionCodes.AUTHORIZATION.errMsg(), cause);
	}

	public AuthorizationException() {
		super(IMSDefaultExceptionCodes.AUTHORIZATION.errCode(), 
				IMSDefaultExceptionCodes.AUTHORIZATION.errMsg());
	}

	public AuthorizationException(String errCode, String message, 
				Throwable cause) {
		super(errCode,message, cause);
	}
}
