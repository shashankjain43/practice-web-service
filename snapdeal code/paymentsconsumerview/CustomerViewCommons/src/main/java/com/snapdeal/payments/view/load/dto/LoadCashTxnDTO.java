package com.snapdeal.payments.view.load.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import com.snapdeal.payments.view.load.enums.LoadCashTxnStatus;
import com.snapdeal.payments.view.load.enums.LoadCashTxnType;
@Data
public class LoadCashTxnDTO implements Serializable {

	private static final long serialVersionUID = 1886520355445931840L;
	private String globalTxnId;
	private Date txnDate;
	private LoadCashTxnType txnType;
	private LoadCashTxnStatus txnStatus;
	private BigDecimal txnAmount;
	private String merchantId;
	private String merchantName;
	private String merchantTxnId;
	private String txnMetaData;

}
