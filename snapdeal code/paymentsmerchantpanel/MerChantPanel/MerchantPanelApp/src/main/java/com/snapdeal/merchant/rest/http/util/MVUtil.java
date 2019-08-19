package com.snapdeal.merchant.rest.http.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantGetFilterTransactionRequest;
import com.snapdeal.merchant.request.MerchantGetSearchTransactionRequest;
import com.snapdeal.merchant.request.MerchantGetTransactionRequest;
import com.snapdeal.merchant.request.MerchantRefundAmountForTxnRequest;
import com.snapdeal.merchant.util.MVMapper;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.view.commons.exception.client.HttpTransportException;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetTotalRefundedAmountForTxnRequest;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewFilters;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewSearch;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalRefundedAmountForTxnResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetTransactionsResponse;
import com.snapdeal.payments.view.merchant.commons.service.IMerchantViewService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MVUtil {

	@Autowired
	IMerchantViewService mvClient;

	@Logged
	public GetTransactionsResponse getTxnsOfMerchant(MerchantGetFilterTransactionRequest request)
			throws MerchantException {

		GetTransactionsResponse mvResponse;

		GetMerchantViewFilterRequest mvRequest = new GetMerchantViewFilterRequest();

		mvRequest.setMerchantId(request.getMerchantId());

		mvRequest.setToken(request.getToken());

		MerchantViewFilters mvFilter = (MerchantViewFilters) MVMapper.MPToMVFilterMapping(request.getFilters());

		mvRequest.setFilters(mvFilter);
		mvRequest.setLimit(request.getLimit());
		mvRequest.setPage(request.getPage());
		mvRequest.setOrderby(request.getOrderby());

		try {

			mvResponse = mvClient.getMerchantViewFilter(mvRequest);

		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MView: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (PaymentsViewGenericException e) {
			log.error("Exception From MView  While getting txn : {} ", e);
			throw new MerchantException(e.getMessage());
		}

		return mvResponse;

	}
	
	@Logged
	public GetTransactionsResponse getTxnsOfMerchantBySearch(MerchantGetSearchTransactionRequest request)
			throws MerchantException {

		GetMerchantViewSearchRequest mvRequest = new GetMerchantViewSearchRequest();

		mvRequest.setMerchantId(request.getMerchantId());
		mvRequest.setToken(request.getToken());

		mvRequest.setOrderby(request.getOrderby());

		MerchantViewSearch mvSearch = (MerchantViewSearch) MVMapper.MPToMVSearchMapping(request.getSearchCriteria());

		mvRequest.setSearchCriteria(mvSearch);
		mvRequest.setLimit(request.getLimit());
		mvRequest.setPage(request.getPage());

		GetTransactionsResponse response = null;

		try {
			response = mvClient.getMerchantViewSearch(mvRequest);
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MView: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (PaymentsViewGenericException e) {
			log.error("Exception from MView  while searching Txn: {}", e);
			throw new MerchantException(e.getMessage());
		}

		return response;

	}

	public GetTransactionsResponse getMerchantTxns(MerchantGetTransactionRequest request) throws MerchantException {

		GetTransactionsResponse mvResponse=null;

		GetMerchantViewSearchWithFilterRequest mvRequest = new GetMerchantViewSearchWithFilterRequest();

		mvRequest.setMerchantId(request.getMerchantId());

		mvRequest.setToken(request.getToken());

		MerchantViewFilters mvFilter = (MerchantViewFilters) MVMapper.MPToMVFilterMapping(request.getFilters());

		mvRequest.setFilters(mvFilter);

		MerchantViewSearch mvSearch = (MerchantViewSearch) MVMapper.MPToMVSearchMapping(request.getSearchCriteria());

		mvRequest.setSearchCriteria(mvSearch);

		mvRequest.setFilters(mvFilter);
		mvRequest.setLimit(request.getLimit());
		mvRequest.setPage(request.getPage());
		mvRequest.setOrderby(request.getOrderby());

		try {
			
			log.info("MV Request For getMerchantTxns : {}",mvRequest);
			mvResponse = mvClient.getMerchantViewSearchWithFilter(mvRequest);
			log.info("MV Response For getMerchantTxns: {}",mvResponse);

		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MView: {}", httpException);
			throw new MerchantException(ErrorConstants.SESSION_INVALID_CODE, ErrorConstants.SESSION_INVALID_MSG);
		} catch (PaymentsViewGenericException e) {
			log.error("Exception From MView  While getting txn : {} ", e);
			throw new MerchantException(e.getMessage());
		}

		return mvResponse;

	}

	
	public GetTotalRefundedAmountForTxnResponse getRefundAmountForTxn(MerchantRefundAmountForTxnRequest request)
			throws MerchantException {

		GetTotalRefundedAmountForTxnRequest mvRequest = new GetTotalRefundedAmountForTxnRequest();

		mvRequest.setFcTxnId(request.getTxnRefId());
		mvRequest.setFcTxnRefType(request.getTxnRefType());
		mvRequest.setOrderId(request.getOrderId());
		mvRequest.setMerchantId(request.getMerchantId());
		mvRequest.setToken(request.getToken());
		GetTotalRefundedAmountForTxnResponse mvResponse = null;
		try {
			
			log.info("MV Request For getRefundAmountForTxn: {}",mvRequest);
			mvResponse = mvClient.getTotalRefundedAmountForTxn(mvRequest);
			log.info("MV Response For getRefundAmountForTxn: {}",mvResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MView: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (PaymentsViewGenericException e) {
			log.error("Exception From MView  While getting total Refund of Txn :{} ,  {} ", e.getMessage(), e);
			throw new MerchantException(e.getMessage());
		}

		return mvResponse;
	}

	public void setMvClient(IMerchantViewService mvClient) {
		this.mvClient = mvClient;
	}

}
