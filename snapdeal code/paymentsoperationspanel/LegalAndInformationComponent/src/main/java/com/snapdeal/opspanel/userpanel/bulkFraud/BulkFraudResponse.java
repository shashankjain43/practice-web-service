package com.snapdeal.opspanel.userpanel.bulkFraud;

import lombok.Data;

@Data
public class BulkFraudResponse {
	
	private String entityType;
	
	private String txnType;
	
	private String entityStatus;
	
	private String updateReason;
	
	private String updateCode;
	
	private String jiraID;
	
	private String updatedBy;
	
	private String status;
	
	private String error;

}
