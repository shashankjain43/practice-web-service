package com.snapdeal.opspanel.bulk.request;

import com.snapdeal.payments.ts.registration.TaskRequest;

import lombok.Data;

@Data
public class BulkRefundTaskRequest implements TaskRequest {

	private  String fileName;

	private String s3Path;

	private String emailId;
	
	private String refundKey;

	private String merchantId;
	
	private  String merchantName;

	public String getTaskId() {

		return fileName;
	}

}
