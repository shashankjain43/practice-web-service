package com.snapdeal.bulkprocess.model;

import com.snapdeal.bulkprocess.exception.BulkProcessorException;

import lombok.Data;

@Data
public class BulkFrameworkResponse {

	public Object data;
	public String error;
	
	public BulkFrameworkResponse(Object data, String error) {
		super();
		this.data = data;
		this.error = error;
	}
	public BulkFrameworkResponse(){
		
	}
	public BulkFrameworkResponse(Object data) {
		super();
		this.data = data;
		this.error = null; 
	}
	
	
	
}
