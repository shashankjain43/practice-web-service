package com.snapdeal.opspanel.promotion.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericOneCheckError {

	private String errorCode;
	private String errorMessage;

	public GenericOneCheckError(String errCode, String errMsg) {
		this.errorCode = errCode;
		this.errorMessage = errMsg;
	}

}
