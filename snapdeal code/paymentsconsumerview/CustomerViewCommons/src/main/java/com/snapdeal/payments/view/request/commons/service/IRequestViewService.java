package com.snapdeal.payments.view.request.commons.service;

import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
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

public interface IRequestViewService {

	@Deprecated
	public GetRequestViewResponse getRequestViewSearch(
			GetRequestViewSearchRequest request)
			throws PaymentsViewServiceException;

	/**
	 * Gives transactions between 2 parties those yet to complete.
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewServiceException
	 * 
	 */
	public GetPendingActionsBetweenPartiesResponse getPendingActionsBetweenParties(
			GetPendingActionsBetweenPartyRequest request)
			throws PaymentsViewServiceException;

	/**
	 * 
	 * Gives pending transactions for user that has not be completed yet
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewServiceException
	 */
	public GetUserPendingActionsResponse getUserPendingActions(
			GetUserPendingActionsRequest request)
			throws PaymentsViewServiceException;

	/**
	 * Gives all transactions for user with filter selection.
	 * 
	 * @param request
	 * @return
	 * @throws PaymentsViewServiceException
	 * 
	 */
	public GetUserActionsHistoryResponse getUserActionsHistory(
			GetUserActionsHistoryRequest request)
			throws PaymentsViewServiceException;

	/**
	 * Gives Transactions for a given split.
	 * 
	 * @param request
	 * @return
	 * 
	 */
	/*public GetSplitViewTransactionsResponse getSplitViewTransactions(
			GetSplitViewTransactionsRequest request);*/

}
