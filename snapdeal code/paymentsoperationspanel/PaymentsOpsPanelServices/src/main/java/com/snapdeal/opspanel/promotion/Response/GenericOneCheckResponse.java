package com.snapdeal.opspanel.promotion.Response;

import lombok.Data;

@Data
public class GenericOneCheckResponse {
	
	private GenericOneCheckError error;
    private Object data;

}
