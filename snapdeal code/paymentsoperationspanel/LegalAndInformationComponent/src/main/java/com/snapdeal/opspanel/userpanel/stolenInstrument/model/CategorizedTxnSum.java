package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CategorizedTxnSum {
	
	private BigDecimal fraudAmount;
	
	private BigDecimal generalBalance;
	
	private BigDecimal voucherBalance;
	
	private BigDecimal sendMoneyTxnsSum;
	
	private BigDecimal rechargeTxnsSum;
	
	private BigDecimal withdrawTxnsSum;
	
	private BigDecimal onlineOfflineMerchantsTxnsSum;
	
	private BigDecimal virtualCardTxnsSum;
	
	private BigDecimal otherTxnsSum;

}
