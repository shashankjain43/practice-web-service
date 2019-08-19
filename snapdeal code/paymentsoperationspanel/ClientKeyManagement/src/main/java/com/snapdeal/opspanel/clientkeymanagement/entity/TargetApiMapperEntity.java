package com.snapdeal.opspanel.clientkeymanagement.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class TargetApiMapperEntity {
	
	@NotBlank(message="Target Application can not be empty!")
	@Size(max=255, message="Target Application can not be more than 255 characters long!")
	private String targetApplication;
	
	@NotBlank(message="API Id can not be empty!")
	@Size(max=255, message="API Id can not be more than 255 characters long!")
	private String apiId;
	
	@Size(max=255, message="API Name can not be more than 255 characters long!")
	private String apiName;
	
	private boolean activeStatus;

	private String oldApiId;
}
