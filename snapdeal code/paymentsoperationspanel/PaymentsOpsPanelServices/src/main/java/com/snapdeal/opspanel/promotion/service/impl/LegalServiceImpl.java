package com.snapdeal.opspanel.promotion.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.exception.OneCheckServiceException;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.service.LegalService;
import com.snapdeal.opspanel.promotion.utils.OneCheckHttpUtil;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseRefundTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseRefundTransactionResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionResponse;

@Service("LegalService")
@Slf4j
public class LegalServiceImpl implements LegalService {

	@Autowired
	SDMoneyClient SDMoneyClient;
	
	@Autowired
	private OneCheckHttpUtil oneCheckUtil;
	
	@Override
	@Deprecated
	public ReverseLoadMoneyResponse reverseLoadMoney(ReverseLoadMoneyRequest reverseLoadMoneyRequest)
			throws WalletServiceException {
		try {
			return SDMoneyClient.reverseLoadMoney(reverseLoadMoneyRequest);
		} catch (SDMoneyException e) {
			log.info("Exception while reverse Load Money Request in Legal Service" + ExceptionUtils.getFullStackTrace(e));
			throw new WalletServiceException("Exception while reverse Load Money Request in Legal Service", e.getMessage());
		}
	}

	@Override
	public ReverseTransactionResponse reverseTransaction(ReverseTransactionRequest reverseTransactionRequest)
			throws WalletServiceException {
		try {
			return SDMoneyClient.reverseTransaction(reverseTransactionRequest);
		} catch (SDMoneyException e) {
			log.info("Exception while reverse Transaction Request in Legal Service" + ExceptionUtils.getFullStackTrace(e));
			throw new WalletServiceException("Exception while reverse Transaction Request in Legal Service", e.getMessage());
		}
	}
	
	@Override
	public ReverseRefundTransactionResponse reverseRefundTransaction(ReverseRefundTransactionRequest reverseRefundTransactionRequest)
			throws WalletServiceException {
		try {
			return SDMoneyClient.reverseRefundTransaction(reverseRefundTransactionRequest);
		} catch (SDMoneyException e) {
			log.info("Exception while reverse Refund Transaction Request in Legal Service" + ExceptionUtils.getFullStackTrace(e));
			throw new WalletServiceException("Exception while reverse refund Transaction Request in Legal Service", e.getMessage());
		}
	}

	@Override
	public com.snapdeal.opspanel.promotion.Response.ReverseLoadMoneyResponse reverseLoadMoneyViaOneCheck(
			com.snapdeal.opspanel.promotion.request.ReverseLoadMoneyRequest reverseLoadMoneyRequest)
			throws OneCheckServiceException {
		return oneCheckUtil.httpCallForReverseLoadMoneyOneCheck(reverseLoadMoneyRequest);
	}
	

	

}
