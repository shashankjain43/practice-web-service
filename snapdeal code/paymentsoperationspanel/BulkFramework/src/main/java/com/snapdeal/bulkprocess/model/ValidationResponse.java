package com.snapdeal.bulkprocess.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ValidationResponse {

	private String errorCode;
	
	private String errorMessage;
	
	@JsonProperty
	private boolean isValid;

	public ValidationResponse(String errorCode, String errorMessage,
			boolean isValid) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.isValid = isValid;
	}
	
	
}
