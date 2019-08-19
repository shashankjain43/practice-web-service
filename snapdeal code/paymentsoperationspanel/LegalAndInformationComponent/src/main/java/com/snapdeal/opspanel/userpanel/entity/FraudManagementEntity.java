package com.snapdeal.opspanel.userpanel.entity;

import java.util.Date;

import lombok.Data;

@Data
public class FraudManagementEntity {

	private String requestId;
	private String clientId;
	private String userIdInvolved;
	private String transactionId;
	private String originalAmount;
	private String amountRefunded;
	private String reason;
	private String action;
	private Date actionTime;
	private String manualReason;

}

