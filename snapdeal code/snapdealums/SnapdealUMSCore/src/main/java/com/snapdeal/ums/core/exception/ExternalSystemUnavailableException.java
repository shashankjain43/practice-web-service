package com.snapdeal.ums.core.exception;

/**
 * Exception which denotes that external system is unavailable
 * 
 * @author ashish
 *
 */
public class ExternalSystemUnavailableException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 83644671122079054L;
	private String msg;
	

	public ExternalSystemUnavailableException(String msg, Throwable cause) {
		super();
		this.msg = msg;
		this.initCause(cause);
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	
}
