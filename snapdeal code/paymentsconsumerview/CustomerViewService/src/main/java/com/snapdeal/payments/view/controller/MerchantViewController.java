package com.snapdeal.payments.view.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.response.ServiceResponse;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantSettledTransactionsRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTransactionsHistoryRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnStatusHistoryByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsByOrderIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsSearchFilterWithMetaDataRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantUnsettledAmount;
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
import com.snapdeal.payments.view.utils.convertor.DateConverter;

@RestController
@RequestMapping(RestURIConstants.VIEW)
public class MerchantViewController extends AbstractViewController {

	@Autowired
	private IMerchantViewService merchantViewService;



	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_VIEW
			+ RestURIConstants.MERCHANT_VIEW_SEARCH_WITH_FILTER,
			produces = RestURIConstants.APPLICATION_JSON, 
			method = RequestMethod.POST)
	public ServiceResponse<GetTransactionsResponse> getMerchantViewSearchWithFilter(
			@RequestBody GetMerchantViewSearchWithFilterRequest request,
			HttpServletRequest httpServletRequest) {

		ServiceResponse<GetTransactionsResponse> response = new ServiceResponse<GetTransactionsResponse>();
		@SuppressWarnings("deprecation")
		GetTransactionsResponse serviceResponse = merchantViewService
				.getMerchantViewSearchWithFilter(request);
		response.setResponse(serviceResponse);
		return response;
	}

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_VIEW + RestURIConstants.MV_SETTLED_TXNS, 
					produces = RestURIConstants.APPLICATION_JSON, 
					method = RequestMethod.GET)
	public ServiceResponse<GetMerchantSettledTransactionsResponse> getMerchantSettledTransactions(
			@ModelAttribute GetMerchantSettledTransactionsRequest request,
			HttpServletRequest httpServletRequest) {

		ServiceResponse<GetMerchantSettledTransactionsResponse> response = new ServiceResponse<GetMerchantSettledTransactionsResponse>();
		GetMerchantSettledTransactionsResponse serviceResponse = merchantViewService
				.getMerchantSettledTransactions(request);
		response.setResponse(serviceResponse);
		return response;
	}

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_VIEW + RestURIConstants.MV_TXN_HISTORY, 
			produces = RestURIConstants.APPLICATION_JSON, 
			method = RequestMethod.GET)
	public ServiceResponse<GetMerchantTransactionsHistoryResponse> getMerchantTransactionsHistory(
			@ModelAttribute GetMerchantTransactionsHistoryRequest request,
			HttpServletRequest httpServletRequest) {

		ServiceResponse<GetMerchantTransactionsHistoryResponse> response = new ServiceResponse<GetMerchantTransactionsHistoryResponse>();
		GetMerchantTransactionsHistoryResponse serviceResponse = merchantViewService
				.getMerchantTransactionsHistory(request);
		response.setResponse(serviceResponse);
		return response;
	}

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_VIEW
			+ RestURIConstants.MERCHANT_VIEW_FETCH_TOTAL_REFUNDED_AMOUNT, 
			produces = RestURIConstants.APPLICATION_JSON, 
			method = RequestMethod.POST)
	public ServiceResponse<GetTotalRefundedAmountForTxnResponse> getTotalRefundedAmountForTxn(
			@RequestBody GetTotalRefundedAmountForTxnRequest request,
			HttpServletRequest httpServletRequest) {

		ServiceResponse<GetTotalRefundedAmountForTxnResponse> response = new ServiceResponse<GetTotalRefundedAmountForTxnResponse>();
		GetTotalRefundedAmountForTxnResponse serviceResponse = merchantViewService
				.getTotalRefundedAmountForTxn(request);
		response.setResponse(serviceResponse);
		return response;
	}

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_VIEW
			+ RestURIConstants.UNSETTLED_AMOUNT, 
			produces = RestURIConstants.APPLICATION_JSON, 
			method = RequestMethod.POST)
	public ServiceResponse<GetTotalUnsettledAmountRespnse> getTotalUnsettledAmount(
			@RequestBody GetMerchantUnsettledAmount request,
			HttpServletRequest httpServletRequest) {

		ServiceResponse<GetTotalUnsettledAmountRespnse> response = new ServiceResponse<GetTotalUnsettledAmountRespnse>();
		GetTotalUnsettledAmountRespnse serviceResponse = merchantViewService
				.getTotalUnsettledAmount(request);
		response.setResponse(serviceResponse);
		return response;
	}
	
	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_VIEW +  RestURIConstants.MV_TXN_HISTORY 
							+ RestURIConstants.ORDER_ID, 
					produces = RestURIConstants.APPLICATION_JSON, 
					method = RequestMethod.GET)
	public ServiceResponse<GetMerchantTxnsWithMetaDataResponse> getMerchantTransactionsByOrderId(
			@ModelAttribute GetMerchantTxnsByOrderIdRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {

		ServiceResponse<GetMerchantTxnsWithMetaDataResponse> response = 
				new ServiceResponse<GetMerchantTxnsWithMetaDataResponse>();
		GetMerchantTxnsWithMetaDataResponse serviceResponse = merchantViewService
				.getMerchantTransactionsByOrderId(request);
		response.setResponse(serviceResponse);
		return response;
	}
	
	
	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_VIEW + RestURIConstants.MV_TXN_HISTORY+
					RestURIConstants.TXN_ID, 
					produces = RestURIConstants.APPLICATION_JSON, 
					method = RequestMethod.GET)
	public ServiceResponse<GetMerchantTxnsWithMetaDataResponse> getMerchantTransactionsByTxnId(
			@ModelAttribute GetMerchantTxnsByTxnIdRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {

		ServiceResponse<GetMerchantTxnsWithMetaDataResponse> response = 
				new ServiceResponse<GetMerchantTxnsWithMetaDataResponse>();
		GetMerchantTxnsWithMetaDataResponse serviceResponse = merchantViewService
				.getMerchantTransactionsByTxnId(request);
		response.setResponse(serviceResponse);
		return response;
	}
	
	
	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_VIEW + RestURIConstants.SEARCH_STATS_HISTORY, 
					produces = RestURIConstants.APPLICATION_JSON, 
					method = RequestMethod.GET)
	public ServiceResponse<GetMerchantTxnStatusHistoryResponse> getMerchantTxnStatusHistoryByTxnId(
			@ModelAttribute GetMerchantTxnStatusHistoryByTxnIdRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {

		ServiceResponse<GetMerchantTxnStatusHistoryResponse> response = 
				new ServiceResponse<GetMerchantTxnStatusHistoryResponse>();
		GetMerchantTxnStatusHistoryResponse serviceResponse = merchantViewService
				.getMerchantTxnStatusHistoryByTxnId(request);
		response.setResponse(serviceResponse);
		return response;
	}
	
	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_VIEW + RestURIConstants.MV_SEARCH_FILTER_WITH_META_DATA, 
					produces = RestURIConstants.APPLICATION_JSON, 
					method = RequestMethod.POST)
	public ServiceResponse<GetMerchantTxnsSearchFilterWithMetaDataResponse> getMerchantTxnsSearchFilterWithMetaData(
			@RequestBody GetMerchantTxnsSearchFilterWithMetaDataRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {

		ServiceResponse<GetMerchantTxnsSearchFilterWithMetaDataResponse> response = 
				new ServiceResponse<GetMerchantTxnsSearchFilterWithMetaDataResponse>();
		GetMerchantTxnsSearchFilterWithMetaDataResponse serviceResponse = merchantViewService
				.getMerchantTxnsSearchFilterWithMetaData(request);
		response.setResponse(serviceResponse);
		return response;
	}
	
	
	@InitBinder
	   public void initBinderForDate(WebDataBinder binder) {
	      binder.registerCustomEditor(Date.class, new DateConverter());
	   }

}
