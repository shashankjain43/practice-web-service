package com.snapdeal.payments.view.merchant.commons.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;

import lombok.Data;

@Data
public class MVFilterCriteria implements Serializable {
	
	private static final long serialVersionUID = 2855083299948348556L;
	
	private List<MVTransactionType> txnTypeList;
	private List<MVTransactionStatus> txnStatusList;
	private Date startDate;
	private Date endDate;
}
