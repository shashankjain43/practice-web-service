package com.snapdeal.payments.view.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.payments.authorize.core.model.ServiceResponse;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.commons.enums.ActionType;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.request.commons.request.GetPendingActionsBetweenPartyRequest;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchRequest;
import com.snapdeal.payments.view.request.commons.request.GetSplitViewTransactionsRequest;
import com.snapdeal.payments.view.request.commons.request.GetUserActionsHistoryRequest;
import com.snapdeal.payments.view.request.commons.request.GetUserPendingActionsRequest;
import com.snapdeal.payments.view.request.commons.response.GetPendingActionsBetweenPartiesResponse;
import com.snapdeal.payments.view.request.commons.response.GetRequestViewResponse;
import com.snapdeal.payments.view.request.commons.response.GetSplitViewTransactionsResponse;
import com.snapdeal.payments.view.request.commons.response.GetUserActionsHistoryResponse;
import com.snapdeal.payments.view.request.commons.response.GetUserPendingActionsResponse;
import com.snapdeal.payments.view.request.commons.response.UpdateTsmNotificationResponse;
import com.snapdeal.payments.view.request.commons.service.IRequestViewService;
import com.snapdeal.payments.view.service.impl.TSMNotifcationService;
import com.snapdeal.payments.view.utils.convertor.ActionTypeConvertor;
import com.snapdeal.payments.view.utils.convertor.DateConverter;

@RestController
@RequestMapping(RestURIConstants.VIEW)
public class RequestViewController extends AbstractViewController {

	@Qualifier("requestViewServiceImpl")	
	@Autowired
	private IRequestViewService requestViewService;

	@Autowired
	private TSMNotifcationService tsmNotifcationService;

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.REQUEST_VIEW
			+ RestURIConstants.SEARCH, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public ServiceResponse<GetRequestViewResponse> getRequestViewSearch(
			@RequestBody GetRequestViewSearchRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {

		ServiceResponse<GetRequestViewResponse> response = new ServiceResponse<GetRequestViewResponse>();
		@SuppressWarnings("deprecation")
		GetRequestViewResponse serviceResponse = requestViewService
				.getRequestViewSearch(request);
		response.setResponse(serviceResponse);
		return response;
	}

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.REQUEST_VIEW + RestURIConstants.PENDING_ACTIONS_BW_PARTY, 
					produces = RestURIConstants.APPLICATION_JSON, 
					method = RequestMethod.POST)
	public ServiceResponse<GetPendingActionsBetweenPartiesResponse> getPendingActionsBetweenParties(
			@RequestBody GetPendingActionsBetweenPartyRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {

		ServiceResponse<GetPendingActionsBetweenPartiesResponse> response = new ServiceResponse<GetPendingActionsBetweenPartiesResponse>();
		GetPendingActionsBetweenPartiesResponse serviceResponse = requestViewService
				.getPendingActionsBetweenParties(request);
		response.setResponse(serviceResponse);
		return response;
	}

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.REQUEST_VIEW+ RestURIConstants.PENDING_ACTIONS_FOR_PARTY,
					produces = RestURIConstants.APPLICATION_JSON, 
					method = RequestMethod.POST)
	public ServiceResponse<GetUserPendingActionsResponse> getUserPendingActions(
			@RequestBody GetUserPendingActionsRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {

		ServiceResponse<GetUserPendingActionsResponse> response = new ServiceResponse<GetUserPendingActionsResponse>();
		GetUserPendingActionsResponse serviceResponse = requestViewService
				.getUserPendingActions(request);
		response.setResponse(serviceResponse);
		return response;
	}

	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.REQUEST_VIEW + RestURIConstants.USER_ACTION_HISTORY, 
					produces = RestURIConstants.APPLICATION_JSON,
					method = RequestMethod.POST)
	public ServiceResponse<GetUserActionsHistoryResponse> getUserActionsHistory(
			@RequestBody GetUserActionsHistoryRequest request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {

		ServiceResponse<GetUserActionsHistoryResponse> response = new ServiceResponse<GetUserActionsHistoryResponse>();
		GetUserActionsHistoryResponse serviceResponse = requestViewService
				.getUserActionsHistory(request);
		response.setResponse(serviceResponse);
		return response;
	}


	@AuthorizeRequest
	@Timed
	@Logged
	@Marked
	@ExceptionMetered
	@RequestAware
	@RequestMapping(value = RestURIConstants.TSM_SYNC_NOTIFICATION, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public ServiceResponse<UpdateTsmNotificationResponse> syncTSMNotification(
			@RequestBody NotificationMessage request,
			HttpServletRequest httpServletRequest)
			throws PaymentsViewServiceException {
		ServiceResponse<UpdateTsmNotificationResponse> response = new ServiceResponse<UpdateTsmNotificationResponse>();
		tsmNotifcationService.updateTSMNotifcation(request);
		UpdateTsmNotificationResponse serviceResponse = new UpdateTsmNotificationResponse();
		serviceResponse.setResponse("OK");
		response.setResponse(serviceResponse);
		return response;
	}

	@RequestMapping(value = "/testdata", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public void test() {

		GetUserPendingActionsRequest request = new GetUserPendingActionsRequest();
		request.setStartDate(new Date(0));
		request.setEndDate(new Date());
		requestViewService.getUserPendingActions(request);
	}
	@InitBinder
	public void initBinderForDate(WebDataBinder binder) {
	      binder.registerCustomEditor(Date.class, new DateConverter());
	}
	@InitBinder
	public void initBinderForActionType(WebDataBinder binder) {
		List<ActionType> actionType = new LinkedList<ActionType>();
		binder.registerCustomEditor(actionType.getClass(), new ActionTypeConvertor());
	}
	

}
