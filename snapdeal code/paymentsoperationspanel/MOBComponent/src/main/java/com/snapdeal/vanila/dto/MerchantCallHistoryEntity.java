package com.snapdeal.vanila.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MerchantCallHistoryEntity {

	private String merchantId;

	private String contactType;

	private String callStatus;
	
	private int attemptsCount;

	private String createdBy;

	private String callTime;

	private String callpath;

	private String description;

}
