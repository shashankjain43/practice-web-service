package com.snapdeal.payments.view.merchant.commons.service;

import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantSettledTransactionsRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTransactionsHistoryRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnStatusHistoryByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsByOrderIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsSearchFilterWithMetaDataRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsWithFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantUnsettledAmount;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterCursorRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetTotalRefundedAmountForTxnRequest;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantSettledTransactionsResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTransactionsHistoryResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnStatusHistoryResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnsSearchFilterWithMetaDataResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnsWithMetaDataResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalRefundedAmountForTxnResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalUnsettledAmountRespnse;
import com.snapdeal.payments.view.merchant.commons.response.GetTransactionsResponse;

public interface IMerchantViewService {

	/**
	 * This will fetch all transactions done by users with given merchant for
	 * few filter criteria. Default is last two day's txns. This is paginated
	 * for 50 records per page.
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewServiceException
	 */
	/*public GetTransactionsResponse getMerchantViewFilter(
			GetMerchantViewFilterRequest request)
			throws PaymentsViewServiceException;*/

	/**
	 * This will fetch all transactions done by users with given merchant for a
	 * search criteria(having either one of them
	 * txnId,orderId,mcntxnId,settleId,terminalId, storeId).
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewServiceException
	 */
	/*public GetTransactionsResponse getMerchantViewSearch(
			GetMerchantViewSearchRequest request)
			throws PaymentsViewServiceException;*/

	/**
	 * This will fetch all transactions done by users with given merchant for a
	 * search criteria(having either one of them
	 * txnId,orderId,mcntxnId,settleId,terminalId, storeId). And combine with
	 * filters
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewServiceException
	 */
	@Deprecated
	public GetTransactionsResponse getMerchantViewSearchWithFilter(
			GetMerchantViewSearchWithFilterRequest request)
			throws PaymentsViewServiceException;

	/**
	 * This will fetch all transactions done by users with given merchant for a
	 * search criteria(having either one of them
	 * txnId,orderId,mcntxnId,settleId,terminalId, storeId). and combine with
	 * filters. Order of the result set will be latest to oldest transaction.
	 * This api is pagenated on the basis of a cursorKey which is txn timestamp.
	 * Txns can be fetch in forward/backward direction on the basis of this
	 * cursorKey. Each page contains a fix limit of 50 records.
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewServiceException
	 */
	/*public GetMerchantTransactionsHistoryResponse getMerchantViewSearchWithFilterCursor(
			GetMerchantViewSearchWithFilterCursorRequest request)
			throws PaymentsViewServiceException;*/

	/**
	 * 
	 * Gives available amount for refund for a particular transaction.
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewServiceException
	 * 
	 */
	public GetTotalRefundedAmountForTxnResponse getTotalRefundedAmountForTxn(
			GetTotalRefundedAmountForTxnRequest request)
			throws PaymentsViewServiceException;

	public GetTotalUnsettledAmountRespnse getTotalUnsettledAmount(
			GetMerchantUnsettledAmount request)
			throws PaymentsViewServiceException;

	/**
	 * Gives Unsettled amount of user that staill in wallet.
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewServiceException
	 * 
	 */
	public GetMerchantSettledTransactionsResponse getMerchantSettledTransactions(
			GetMerchantSettledTransactionsRequest request)
			throws PaymentsViewServiceException;

	public GetMerchantTransactionsHistoryResponse getMerchantTransactionsHistory(
			GetMerchantTransactionsHistoryRequest request)
			throws PaymentsViewServiceException;

	/*public GetMerchantTransactionsHistoryResponse getMerchantTxnsWithFilter(
			GetMerchantTxnsWithFilterRequest request)
			throws PaymentsViewServiceException;*/

	public GetMerchantTxnsWithMetaDataResponse getMerchantTransactionsByOrderId(
			GetMerchantTxnsByOrderIdRequest request)
			throws PaymentsViewServiceException;
	
	public GetMerchantTxnsWithMetaDataResponse getMerchantTransactionsByTxnId(
			GetMerchantTxnsByTxnIdRequest request)
			throws PaymentsViewServiceException;
	
	
	public GetMerchantTxnStatusHistoryResponse getMerchantTxnStatusHistoryByTxnId(
			GetMerchantTxnStatusHistoryByTxnIdRequest request) ;
	
	public GetMerchantTxnsSearchFilterWithMetaDataResponse getMerchantTxnsSearchFilterWithMetaData(
			GetMerchantTxnsSearchFilterWithMetaDataRequest request)
			throws PaymentsViewServiceException;

}
