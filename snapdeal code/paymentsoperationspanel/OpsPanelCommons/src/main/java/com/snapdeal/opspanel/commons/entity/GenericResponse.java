package com.snapdeal.opspanel.commons.entity;

import lombok.Data;

@Data
public class GenericResponse {
	
	private Object data;
	private GenericError error;
	
}
