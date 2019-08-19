package com.snapdeal.opspanel.promotion.service;


import com.snapdeal.opspanel.promotion.exception.OneCheckServiceException;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseRefundTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseRefundTransactionResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionResponse;


public interface LegalService {

	
	public ReverseLoadMoneyResponse reverseLoadMoney(ReverseLoadMoneyRequest reverseLoadMoneyRequest) throws WalletServiceException;
	
	public ReverseTransactionResponse reverseTransaction(ReverseTransactionRequest reverseTransactionRequest) throws WalletServiceException;

	public ReverseRefundTransactionResponse reverseRefundTransaction(
			ReverseRefundTransactionRequest reverseRefundTransactionRequest) throws WalletServiceException; 
	
	public com.snapdeal.opspanel.promotion.Response.ReverseLoadMoneyResponse reverseLoadMoneyViaOneCheck(
			com.snapdeal.opspanel.promotion.request.ReverseLoadMoneyRequest reverseLoadMoneyRequest) throws OneCheckServiceException;

	
	
}
