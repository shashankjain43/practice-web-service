package com.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String errCode;
	private String errMsg;
	
	public ServiceResponse() {
		super();
	}

	public ServiceResponse(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
}
