package com.snapdeal.opspanel.bulk.response;

import com.snapdeal.opspanel.batch.error.GenericError;

import lombok.Data;

@Data
public class GenericResponse {
	
	private Object data;
	private GenericError error;
	
}
