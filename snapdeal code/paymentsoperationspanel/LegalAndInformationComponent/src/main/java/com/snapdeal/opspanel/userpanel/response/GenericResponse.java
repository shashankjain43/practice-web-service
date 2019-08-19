package com.snapdeal.opspanel.userpanel.response;

import com.snapdeal.opspanel.userpanel.exception.GenericError;

import lombok.Data;

@Data
public class GenericResponse {

	private GenericError error;
	private Object data;
	
}
