package com.snapdeal.merchant.dto;

import lombok.Data;

@Data
public class MerchantUploadedDocsDTO {
	
	private String name;
	private String id;
	private String contentType;
	private boolean approvalStatus;
	private String documentType;
	private String documentCategory;
	private long documentSize;
	private String docIdentityValue;
	private String docStatus;
	
}
