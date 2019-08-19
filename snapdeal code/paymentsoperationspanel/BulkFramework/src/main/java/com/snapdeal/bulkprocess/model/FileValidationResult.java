package com.snapdeal.bulkprocess.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FileValidationResult {
	
	@JsonProperty
	private boolean validationSuccess;
	
	private String errorMessage;
	
	private long lineNumber;

}
