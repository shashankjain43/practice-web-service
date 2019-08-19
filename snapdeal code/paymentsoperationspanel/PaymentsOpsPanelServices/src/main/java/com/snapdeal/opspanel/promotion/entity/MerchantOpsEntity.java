package com.snapdeal.opspanel.promotion.entity;

import java.util.Date;

import lombok.Data;

@Data
public class MerchantOpsEntity {

	private String merchantId;

	private String merchantEmail;

	private String merchantType;

	private String kycStatus;

	private int mailCount;

	private int callCount;
	
	private String lastCallStatus;
	
	private Date lastCallTime;

}
