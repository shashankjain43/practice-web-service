package com.snapdeal.bulkprocess.model;

import com.snapdeal.payments.ts.registration.TaskRequest;

import lombok.Data;

@Data
public class GenericRequestResponseValues {

	Object responseValues;
	
	String[] requestValues;
	

}
