package com.snapdeal.payments.view.client.impl;

import org.apache.mina.http.api.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.commons.response.ServiceResponse;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsByUserIdRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsByTxnIdRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsResponse;
import com.snapdeal.payments.view.load.service.ILoadCashService;

public class LoadCashClient extends AbstractPaymentsViewClient implements
		ILoadCashService {

	public GetLoadCashTxnsResponse getLoadCashTxnsByUserId(
			GetLoadCashTxnsByUserIdRequest request) throws PaymentsViewGenericException {
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetLoadCashTxnsResponse>>() {
				}, HttpMethod.POST, RestURIConstants.LOAD_CASH
						+ RestURIConstants.LOAD_CASH_BY_USER_ID);
	}
	
	public GetLoadCashTxnsResponse getLoadCashTxnsByTxnId(
			GetLoadCashTxnsByTxnIdRequest request) throws PaymentsViewGenericException {
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetLoadCashTxnsResponse>>() {
				}, HttpMethod.POST, RestURIConstants.LOAD_CASH
						+ RestURIConstants.LOAD_CASH_BY_TXN_ID);
	}

}
