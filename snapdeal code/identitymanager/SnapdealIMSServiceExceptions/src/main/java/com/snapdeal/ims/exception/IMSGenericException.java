package com.snapdeal.ims.exception;

import com.snapdeal.ims.exception.IMSGenericException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IMSGenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errCode;
	private String errMsg;
	private boolean retryable = false;

	public IMSGenericException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public IMSGenericException(String errCode, String message, Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
		this.errMsg = message;
	}

	private Class<? extends IMSGenericException> exceptionCause;

	{
		this.setExceptionCause(this.getClass());
	}

}
