package com.snapdeal.opspanel.audit.response;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuditResponse {
	
	
	private String jsonRequest;
	private String jsonResponse;
	private String email;
	private String methodName;
	private String timeStamp;

}
