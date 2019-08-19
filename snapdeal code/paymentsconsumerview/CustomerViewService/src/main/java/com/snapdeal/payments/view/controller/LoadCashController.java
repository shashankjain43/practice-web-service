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
import com.snapdeal.payments.view.commons.response.ServiceResponse;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsByUserIdRequest;
import com.snapdeal.payments.view.load.request.GetLoadCashTxnsRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsByTxnIdRequest;
import com.snapdeal.payments.view.load.response.GetLoadCashTxnsResponse;
import com.snapdeal.payments.view.load.service.ILoadCashService;

@RestController
@RequestMapping(RestURIConstants.VIEW)
public class LoadCashController extends AbstractViewController {

	@Autowired
	private ILoadCashService loadCashService;

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.LOAD_CASH
			+ RestURIConstants.LOAD_CASH_WITH_FILTER,
			produces = RestURIConstants.APPLICATION_JSON, 
			method = RequestMethod.POST)
	public ServiceResponse<GetLoadCashTxnsResponse> getLoadCashTransactions(
			@RequestBody GetLoadCashTxnsRequest request,
			HttpServletRequest httpServletRequest) {

		ServiceResponse<GetLoadCashTxnsResponse> response = new ServiceResponse<GetLoadCashTxnsResponse>();
		/*GetLoadCashTxnsResponse serviceResponse = loadCashService.getLoadCashTransactions(request);
		response.setResponse(serviceResponse);*/
		return response;
	}
	
	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.LOAD_CASH
			+ RestURIConstants.LOAD_CASH_BY_USER_ID,
			produces = RestURIConstants.APPLICATION_JSON, 
			method = RequestMethod.POST)
	public ServiceResponse<GetLoadCashTxnsResponse> getLoadCashTxnsByUserId(
			@RequestBody GetLoadCashTxnsByUserIdRequest request,
			HttpServletRequest httpServletRequest) {

		ServiceResponse<GetLoadCashTxnsResponse> response = new ServiceResponse<GetLoadCashTxnsResponse>();
		GetLoadCashTxnsResponse serviceResponse = loadCashService.getLoadCashTxnsByUserId(request);
		response.setResponse(serviceResponse);
		return response;
	}
	
	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.LOAD_CASH
			+ RestURIConstants.LOAD_CASH_BY_TXN_ID,
			produces = RestURIConstants.APPLICATION_JSON, 
			method = RequestMethod.POST)
	public ServiceResponse<GetLoadCashTxnsResponse> getLoadCashTxnsByTxnId(
			@RequestBody GetLoadCashTxnsByTxnIdRequest request,
			HttpServletRequest httpServletRequest) {

		ServiceResponse<GetLoadCashTxnsResponse> response = new ServiceResponse<GetLoadCashTxnsResponse>();
		GetLoadCashTxnsResponse serviceResponse = loadCashService.getLoadCashTxnsByTxnId(request);
		response.setResponse(serviceResponse);
		return response;
	}

	

}
