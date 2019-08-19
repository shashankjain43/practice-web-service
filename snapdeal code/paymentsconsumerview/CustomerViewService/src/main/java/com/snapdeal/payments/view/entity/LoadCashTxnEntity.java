package com.snapdeal.payments.view.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class LoadCashTxnEntity implements Serializable {

	private static final long serialVersionUID = 4947890456663597119L;
	private String id;
	private String globalTxnId;
	private String globalTxnType;
	private String refTxnId;
	private String refTxnType;
	private String txnType;
	private Date txnDate;
	private BigDecimal txnAmount;
	private String custMobile;
	private String merchantId;
	private String merchantName;
	private String merchantTxnId;
	private Date tsmTimeStamp;
	private String txnMetaData;
	private Date createdOn;
	private Date updatedOn;
	
}