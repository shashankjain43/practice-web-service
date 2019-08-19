package com.snapdeal.payments.view.load.service;

import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsByUserIdRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsByTxnIdRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsResponse;

public interface ILoadCashService {

	/**
	 * This will fetch all load cash transactions done by users with given merchant 
	 * This api is pagenated on the basis of a page numbering.
	 * Each page contains a default limit of 50 records.
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewGenericException
	 */
	public GetLoadCashTxnsResponse getLoadCashTxnsByUserId(
			GetLoadCashTxnsByUserIdRequest request) throws PaymentsViewGenericException;
	
	public GetLoadCashTxnsResponse getLoadCashTxnsByTxnId(
			GetLoadCashTxnsByTxnIdRequest request) throws PaymentsViewGenericException;

}
