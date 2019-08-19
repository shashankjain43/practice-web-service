package com.snapdeal.payments.view.client.impl;

import org.apache.mina.http.api.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.response.ServiceResponse;
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
import com.snapdeal.payments.view.merchant.commons.service.IMerchantViewService;

public class MerchantViewClient extends AbstractPaymentsViewClient implements IMerchantViewService {

	public GetTransactionsResponse getMerchantViewSearchWithFilter(GetMerchantViewSearchWithFilterRequest request)
			throws PaymentsViewServiceException {
		return prepareResponseNew(request, new TypeReference<ServiceResponse<GetTransactionsResponse>>() {
		}, HttpMethod.POST, RestURIConstants.MERCHANT_VIEW + RestURIConstants.MERCHANT_VIEW_SEARCH_WITH_FILTER);

	}

	public GetTotalRefundedAmountForTxnResponse getTotalRefundedAmountForTxn(
			GetTotalRefundedAmountForTxnRequest request) throws PaymentsViewServiceException {
		return prepareResponseNew(request, new TypeReference<ServiceResponse<GetTotalRefundedAmountForTxnResponse>>() {
		}, HttpMethod.POST,
				RestURIConstants.MERCHANT_VIEW + RestURIConstants.MERCHANT_VIEW_FETCH_TOTAL_REFUNDED_AMOUNT);

	}

	public GetTotalUnsettledAmountRespnse getTotalUnsettledAmount(GetMerchantUnsettledAmount request)
			throws PaymentsViewServiceException {
		// TODO Auto-generated method stub
		return prepareResponseNew(request, new TypeReference<ServiceResponse<GetTotalUnsettledAmountRespnse>>() {
		}, HttpMethod.POST, RestURIConstants.MERCHANT_VIEW + RestURIConstants.UNSETTLED_AMOUNT);
	}

	public GetMerchantSettledTransactionsResponse getMerchantSettledTransactions(
			GetMerchantSettledTransactionsRequest request) throws PaymentsViewServiceException {

		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetMerchantSettledTransactionsResponse>>() {
				}, HttpMethod.GET, RestURIConstants.MERCHANT_VIEW + RestURIConstants.MV_SETTLED_TXNS);
	}

	public GetMerchantTransactionsHistoryResponse getMerchantTransactionsHistory(
			GetMerchantTransactionsHistoryRequest request) throws PaymentsViewServiceException {

		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetMerchantTransactionsHistoryResponse>>() {
				}, HttpMethod.GET, RestURIConstants.MERCHANT_VIEW + RestURIConstants.MV_TXN_HISTORY);
	}

	/*
	 * public GetMerchantTransactionsHistoryResponse getMerchantTxnsWithFilter(
	 * GetMerchantTxnsWithFilterRequest request) throws PaymentsViewServiceException
	 * {
	 * 
	 * return prepareResponseNew(request, new
	 * TypeReference<ServiceResponse<GetMerchantTransactionsHistoryResponse>>(){},
	 * HttpMethod.POST, RestURIConstants.MERCHANT_VIEW +
	 * RestURIConstants.MV_TXN_WITH_FILTERS); }
	 */

	public GetMerchantTxnsWithMetaDataResponse getMerchantTransactionsByOrderId(GetMerchantTxnsByOrderIdRequest request)
			throws PaymentsViewServiceException {
		return prepareResponseNew(request, new TypeReference<ServiceResponse<GetMerchantTxnsWithMetaDataResponse>>() {
		}, HttpMethod.GET,
				RestURIConstants.MERCHANT_VIEW + RestURIConstants.MV_TXN_HISTORY + RestURIConstants.ORDER_ID);
	}

	public GetMerchantTxnsWithMetaDataResponse getMerchantTransactionsByTxnId(GetMerchantTxnsByTxnIdRequest request)
			throws PaymentsViewServiceException {
		return prepareResponseNew(request, new TypeReference<ServiceResponse<GetMerchantTxnsWithMetaDataResponse>>() {
		}, HttpMethod.GET, RestURIConstants.MERCHANT_VIEW + RestURIConstants.MV_TXN_HISTORY + RestURIConstants.TXN_ID);
	}

	public GetMerchantTxnStatusHistoryResponse getMerchantTxnStatusHistoryByTxnId(
			GetMerchantTxnStatusHistoryByTxnIdRequest request) {
		return prepareResponseNew(request, new TypeReference<ServiceResponse<GetMerchantTxnStatusHistoryResponse>>() {
		}, HttpMethod.GET, RestURIConstants.MERCHANT_VIEW + RestURIConstants.SEARCH_STATS_HISTORY);
	}

	public GetMerchantTxnsSearchFilterWithMetaDataResponse getMerchantTxnsSearchFilterWithMetaData(
			GetMerchantTxnsSearchFilterWithMetaDataRequest request) throws PaymentsViewServiceException {
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetMerchantTxnsSearchFilterWithMetaDataResponse>>() {
				}, HttpMethod.POST, RestURIConstants.MERCHANT_VIEW + RestURIConstants.MV_SEARCH_FILTER_WITH_META_DATA);
	}

}
