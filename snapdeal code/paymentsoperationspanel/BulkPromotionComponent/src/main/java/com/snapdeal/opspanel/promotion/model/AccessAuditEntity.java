package com.snapdeal.opspanel.promotion.model;

import java.util.Date;

import com.snapdeal.opspanel.promotion.enums.AuditOperationMode;

import lombok.Data;

@Data
public class AccessAuditEntity {
	
	private String clientEmail;
	
	private String clientRole;
	
	private AuditOperationMode operationMode;
	
	private String merchantName;
	
	private String corporateData;
	
	private String instrumentName;
	
	private Date operationTime;
	

}
