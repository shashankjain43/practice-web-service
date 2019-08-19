package com.snapdeal.opspanel.promotion.entity;

import java.util.Date;

import lombok.Data;

@Data
public class CallHistoryEntity {

	private String merchantId;

	private String contactType;

	private String contactStatus;
	
	private int attempts;

	private String description;

	private String contactPath;

	private String createdBy;

	private Date createdOn;

}
