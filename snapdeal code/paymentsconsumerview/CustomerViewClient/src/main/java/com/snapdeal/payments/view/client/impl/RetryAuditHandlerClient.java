package com.snapdeal.payments.view.client.impl;

import org.apache.mina.http.api.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.commons.response.ServiceResponse;
import com.snapdeal.payments.view.merchant.commons.request.RetryPaymentsViewAuditRequest;
import com.snapdeal.payments.view.merchant.commons.request.RetryTransactionListRequest;

public class RetryAuditHandlerClient extends AbstractPaymentsViewClient {

	public String retryMerchantviewAudit(RetryPaymentsViewAuditRequest request){
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<String>>(){},
								HttpMethod.POST,
								RestURIConstants.MERCHANT_AUDIT); 
	}
	
	public String retryTransactionsList(RetryTransactionListRequest request){
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<String>>(){},
								HttpMethod.POST,
								RestURIConstants.TRANSACTION_LIST); 
	}
}
