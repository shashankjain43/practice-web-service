package com.snapdeal.opspanel.clientkeymanagement.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class GenericResponse {

	private GenericError error;
	private Object data;
	public GenericError getError() {
		return error;
	}
	public void setError(GenericError error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public GenericResponse(Object data) {
		super();
		this.error = null;
		this.data = data;
	}
	
	
	
}
