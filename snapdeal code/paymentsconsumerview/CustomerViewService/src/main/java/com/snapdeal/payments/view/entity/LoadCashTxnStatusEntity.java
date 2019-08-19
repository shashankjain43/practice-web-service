package com.snapdeal.payments.view.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LoadCashTxnStatusEntity implements Serializable {

	private static final long serialVersionUID = 4456839649221538778L;
	private String id;
	private String txnStatus;
	private Date tsmTimeStamp;
	private Date createdOn;
	private Date updatedOn;
}
