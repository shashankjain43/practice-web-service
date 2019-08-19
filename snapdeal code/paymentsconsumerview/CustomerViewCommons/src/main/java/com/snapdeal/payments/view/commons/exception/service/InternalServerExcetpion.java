package com.snapdeal.payments.view.commons.exception.service;

import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewDefaultExceptionCodes;


public class InternalServerExcetpion extends PaymentsViewGenericException{

	private static final long serialVersionUID = 6076890854175525661L;

	public InternalServerExcetpion(String errCode,String errMsg) {
		super(errCode,errMsg);
	}

	public InternalServerExcetpion(Throwable cause) {
		super(PaymentsViewDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
			  PaymentsViewDefaultExceptionCodes.INTERNAL_SERVER.errMsg(), cause);
		
	}

	public InternalServerExcetpion() {
		super(PaymentsViewDefaultExceptionCodes.INTERNAL_SERVER.errCode(), 
				PaymentsViewDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
	}

	public InternalServerExcetpion(String errCode, String message, 
				Throwable cause) {
		super(errCode,message, cause);
	}
}
