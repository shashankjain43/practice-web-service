package com.snapdeal.payments.view.merchant.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;

import lombok.Data;

@Data
public class MVTxnDTO implements Serializable {

	private static final long serialVersionUID = 306726482941958759L;
	
	private String txnId;
	private String instrumentType;
	private Date txnDate;
	private MVTransactionType txnType;
	private MVTransactionStatus txnStatus;
	private BigDecimal txnAmount;
	private String orderId;
	private String userId;
	private String txnRefId;
	private String txnRefType;
	private String merchantTag;
	private String merchantId;
	private String settlementId;
	private Date settlementDate;
	private String txnMetaData;
	private String payableMetaData;
	private String disbursementMetaData;
}
