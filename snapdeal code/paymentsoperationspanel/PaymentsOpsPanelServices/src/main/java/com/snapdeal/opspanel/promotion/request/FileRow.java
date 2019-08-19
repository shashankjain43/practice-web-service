package com.snapdeal.opspanel.promotion.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FileRow {
	private String userId;
	private BigDecimal amount; 
	private String eventContex;
	private long rowNum;
}