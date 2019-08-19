package com.snapdeal.payments.view.merchant.commons.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MVSearchCriteria implements Serializable{
	
	private static final long serialVersionUID = 5709890739104681074L;
	
	private String txnId;
	private String orderId;
	private String userId;
	private String settlementId;

}
