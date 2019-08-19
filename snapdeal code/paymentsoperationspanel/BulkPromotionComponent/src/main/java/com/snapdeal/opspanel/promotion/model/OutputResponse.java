package com.snapdeal.opspanel.promotion.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OutputResponse {
	private Timestamp uploadTimeStamp;
	private Timestamp transactionTimeStamp;
	private String responseCode;
	private String responseMessage;
	private String responseStatus;
}
