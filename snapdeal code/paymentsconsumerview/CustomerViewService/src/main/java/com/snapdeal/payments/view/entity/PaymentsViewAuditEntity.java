package com.snapdeal.payments.view.entity;

import java.util.Date;

import com.snapdeal.payments.view.commons.enums.RetryTaskStatus;

import lombok.Data;

public @Data class PaymentsViewAuditEntity {

	public String fcTxnId ;
	public String txnType ;
	public String exceptionType ;
	public String exceptionMsg;
	public String exceptionCode ;
	public int retryCount ;
	public Date createdDate ;
	public Date updatedDate ;
	public Date tsmTimeStamp ;
	public RetryTaskStatus status ;
	
}