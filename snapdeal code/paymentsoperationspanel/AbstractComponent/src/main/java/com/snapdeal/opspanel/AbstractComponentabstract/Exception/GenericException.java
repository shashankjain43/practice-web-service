package com.snapdeal.opspanel.AbstractComponentabstract.Exception;

public abstract class GenericException extends Exception{

	String source;
	static final long serialVersionUID = 2;
	
	protected GenericException(String source){
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
