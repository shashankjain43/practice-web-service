package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import lombok.Data;

import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdResponse;

@Data
public class TransactionDetailsInfo {
	
	GetTransactionByIdResponse transactionDetails;
	
	String error;

}
