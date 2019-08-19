package com.snapdeal.opspanel.promotion.request;

import lombok.Data;

@Data
public class GenericClientRequest {

	private String methodName;
	
	private Object request;
	
	private Boolean onlyGetRequest; 
	
}
