package com.snapdeal.ims.activity.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ActivityGenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errCode;
	private String errMsg;
	private boolean retryable = false;

	public ActivityGenericException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
	}

	public ActivityGenericException(Throwable e) {
		super(e);
	}

	public ActivityGenericException(String errCode, String message, Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
	}

	private Class<? extends ActivityGenericException> exceptionCause;

	{
		this.setExceptionCause(this.getClass());
	}

}
