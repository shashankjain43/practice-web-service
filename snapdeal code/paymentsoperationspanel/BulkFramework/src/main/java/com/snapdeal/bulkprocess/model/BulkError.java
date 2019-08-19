package com.snapdeal.bulkprocess.model;

import lombok.Data;

@Data
public class BulkError {

	public String Error;

	public BulkError(String error) {
	
		Error = error;
	}
	
	
	
}
