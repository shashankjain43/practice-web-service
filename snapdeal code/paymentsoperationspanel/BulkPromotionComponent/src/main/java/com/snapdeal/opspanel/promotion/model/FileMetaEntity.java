package com.snapdeal.opspanel.promotion.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FileMetaEntity {

	private String userId;
	
	private String fileName;

	private String status;

	private String idempotencyId;

	private Long totalRows;
	
	private BigDecimal totalMoney;
	
	private Long successRowsNum;

	private BigDecimal totalSuccessMoney;

	private Long processingRow;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm, dd-MM-yyyy", timezone="IST")
	private Date uploadTime;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm, dd-MM-yyyy", timezone="IST")
	private Date completionTime;

	private String smsTemplateId;
	
	private String mailTemplateId;

	private String corporateAccountName;

	private String message;

}
