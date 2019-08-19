package com.snapdeal.opspanel.promotion.request;

import lombok.Data;

import com.snapdeal.payments.ts.registration.TaskRequest;

@Data
public class BulkTaskRequest implements TaskRequest{

	
	private  String fileName;
	
	private String s3Path;
	
	private String emailId;

	private FormData formData;
	
	@Override
	public String getTaskId() {
	
		return fileName;
	}

}
