package com.snapdeal.opspanel.userpanel.response;

import lombok.Data;

@Data
public class ViewUserAccountResponse {
	
	private String agentId;
	
	private String action;
	
	private String actionKeyType;
	
	private String actionKey;
	
	private String reason;
	
	private String actionTime;
	
	private String comments;
	
	private String typeOfFraud;

}
