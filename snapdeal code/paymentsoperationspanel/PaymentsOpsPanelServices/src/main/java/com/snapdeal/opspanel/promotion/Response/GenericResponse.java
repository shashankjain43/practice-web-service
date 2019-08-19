package com.snapdeal.opspanel.promotion.Response;

import lombok.Data;

import com.snapdeal.opspanel.promotion.exception.GenericError;

@Data
public class GenericResponse {
	
	private Object data;
	private GenericError error;
	
}
