package com.snapdeal.payments.view.client.impl;

import org.apache.mina.http.api.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.response.ServiceResponse;
import com.snapdeal.payments.view.request.commons.request.GetUserActionsHistoryRequest;
import com.snapdeal.payments.view.request.commons.request.GetPendingActionsBetweenPartyRequest;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchRequest;
import com.snapdeal.payments.view.request.commons.request.GetSplitViewTransactionsRequest;
import com.snapdeal.payments.view.request.commons.request.GetUserPendingActionsRequest;
import com.snapdeal.payments.view.request.commons.response.GetActionsWithFilterResponse;
import com.snapdeal.payments.view.request.commons.response.GetPendingActionsBetweenPartiesResponse;
import com.snapdeal.payments.view.request.commons.response.GetRequestViewResponse;
import com.snapdeal.payments.view.request.commons.response.GetSplitViewTransactionsResponse;
import com.snapdeal.payments.view.request.commons.response.GetUserActionsHistoryResponse;
import com.snapdeal.payments.view.request.commons.response.GetUserPendingActionsResponse;
import com.snapdeal.payments.view.request.commons.service.IRequestViewService;

public class RequestViewClient extends AbstractPaymentsViewClient implements IRequestViewService{

	public GetRequestViewResponse getRequestViewSearch(GetRequestViewSearchRequest request) 
			throws PaymentsViewServiceException {
		
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetRequestViewResponse>>(){},
								HttpMethod.POST,
								RestURIConstants.REQUEST_VIEW + RestURIConstants.SEARCH); 
	}

	public GetUserActionsHistoryResponse getUserActionsHistory(
			GetUserActionsHistoryRequest request)
			throws PaymentsViewServiceException {
		
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetUserActionsHistoryResponse>>(){},
								HttpMethod.POST,
								RestURIConstants.REQUEST_VIEW + RestURIConstants.USER_ACTION_HISTORY);
	}

	public GetPendingActionsBetweenPartiesResponse getPendingActionsBetweenParties(
			GetPendingActionsBetweenPartyRequest request)
			throws PaymentsViewServiceException {
		
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetPendingActionsBetweenPartiesResponse>>(){},
								HttpMethod.POST,
								RestURIConstants.REQUEST_VIEW + RestURIConstants.PENDING_ACTIONS_BW_PARTY);
		
	}

	public GetUserPendingActionsResponse getUserPendingActions(
			GetUserPendingActionsRequest request)
			throws PaymentsViewServiceException {
		
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetUserPendingActionsResponse>>(){},
								HttpMethod.POST,
								RestURIConstants.REQUEST_VIEW + RestURIConstants.PENDING_ACTIONS_FOR_PARTY);
	}

	/*public GetSplitViewTransactionsResponse getSplitViewTransactions(
			GetSplitViewTransactionsRequest request) {
		
		return prepareResponseNew(request,
				new TypeReference<ServiceResponse<GetSplitViewTransactionsResponse>>(){},
								HttpMethod.GET,
								RestURIConstants.REQUEST_VIEW+ RestURIConstants.SPLIT_TRANSACTIONS);
	}*/

}
