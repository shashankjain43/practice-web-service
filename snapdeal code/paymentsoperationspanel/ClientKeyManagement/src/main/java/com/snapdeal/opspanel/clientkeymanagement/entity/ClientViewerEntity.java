package com.snapdeal.opspanel.clientkeymanagement.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class ClientViewerEntity {
	
	@NotNull(message="UserId can not be empty!")
	@Size(max = 255, message="UserId can not be more than 255 characters long!")
	private String userId;
	
	@NotNull(message="Client Name can not be empty!")
	@Size(max = 255, message="Client Name can not be more than 255 characters long!")
	private String clientName;
	
	@Size(max = 255, message="Source Application can not be more than 255 characters long!")
	private String sourceApplication;
	
	@NotNull(message="Target Application can not be empty!")
	@Size(max = 255, message="Target Application can not be more than 255 characters long!")
	private String targetApplication;
	
	@Size(max = 255, message="Client Context can not be more than 255 characters long!")
	private String clientContext;
	
	private String createdBy;
	
	private String createdOn;
	
	@Size(max = 255, message="Remarks can not be more than 255 characters long!")
	private String remarks;
}
