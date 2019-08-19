package com.snapdeal.payments.view.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

public @Data class TransactionStateDetailsEntity implements Serializable{
		
	private static final long serialVersionUID = 1L;
			private String txnId ;
			private String txnState ;
			private Date tsmTimeStamp ;
			private Date createdOn;
			private Date updatedOn;
}
