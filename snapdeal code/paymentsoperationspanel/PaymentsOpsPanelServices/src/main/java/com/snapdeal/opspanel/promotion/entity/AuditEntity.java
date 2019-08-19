package com.snapdeal.opspanel.promotion.entity;
import lombok.Data;

@Data
public class AuditEntity {
	
	private String emailId;
	
	private String fileName;
	
	private String ip;
	
	private String timestamp;
	
	private String request;
	
	private String response;
	
	private String apiName;
	
	private String idempotencyId;
	
	
}
