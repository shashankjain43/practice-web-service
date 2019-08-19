package com.snapdeal.opspanel.promotion.Response;

import java.util.List;

import lombok.Data;

@Data
public class TemplateResponseModel {
	
	private String templateName;
	
	private List<String> templateParamsValue;
	
	private List<String> templateParamsName;
	
	private String templateUrl;
	
	private String templateData;
	
	private String messageString;

	
	
}
