package com.snapdeal.payments.view.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.snapdeal.payments.view.merchant.commons.request.RetryPaymentsViewAuditRequest;
import com.snapdeal.payments.view.merchant.commons.request.RetryTransactionListRequest;
import com.snapdeal.payments.view.service.impl.RetryAuditHandler;

@RestController
@RequestMapping(RestURIConstants.VIEW)
public class RetryAuditController {

	@Autowired
	private RetryAuditHandler retryHandler;

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.MERCHANT_AUDIT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public ServiceResponse<String> retryMerchantAudit(
			@RequestBody RetryPaymentsViewAuditRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {
		ServiceResponse<String> response = new ServiceResponse<String>();
		retryHandler.retryPaymentViewAudit(request);
		response.setResponse("OK");
		return response;
	}

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.TRANSACTION_LIST, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public ServiceResponse<String> retryTransactionsList(
			@RequestBody RetryTransactionListRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {
		ServiceResponse<String> response = new ServiceResponse<String>();
		retryHandler.retryTransactionList(request);
		response.setResponse("OK");
		return response;
	}
}
