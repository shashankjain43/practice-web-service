package com.snapdeal.payments.view.load.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.snapdeal.payments.view.load.enums.LoadCashTxnStatus;
import com.snapdeal.payments.view.load.enums.LoadCashTxnType;

@Data
public class LCFilterCriteria implements Serializable {
	
	private static final long serialVersionUID = 2855083299948348556L;
	
	private List<LoadCashTxnType> txnTypeList;
	private List<LoadCashTxnStatus> txnStatusList;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private Date startDate;
	private Date endDate;
}
