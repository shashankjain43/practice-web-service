package com.snapdeal.opspanel.promotion.model;

import lombok.Data;

@Data
public class FileSpecificTemplateRequest {
	
	private String templateId;
	
	private String userId;
	
	private String templateName;
		
    private String[] templateParamsName;
	
	private String[] templateParamsValue;

}
