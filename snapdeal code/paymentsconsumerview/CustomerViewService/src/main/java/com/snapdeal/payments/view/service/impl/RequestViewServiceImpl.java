package com.snapdeal.payments.view.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.view.commons.enums.ActionType;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.dao.IPersistanceManager;
import com.snapdeal.payments.view.entity.ActionDetailsEntity;
import com.snapdeal.payments.view.request.commons.dto.ActionDto;
import com.snapdeal.payments.view.request.commons.dto.ActionFilter;
import com.snapdeal.payments.view.request.commons.dto.RVTransactionDto;
import com.snapdeal.payments.view.request.commons.enums.TxnStatus;
import com.snapdeal.payments.view.request.commons.request.GetPendingActionsBetweenPartyRequest;
import com.snapdeal.payments.view.request.commons.request.GetRequestViewSearchRequest;
import com.snapdeal.payments.view.request.commons.request.GetSplitViewTransactionsRequest;
import com.snapdeal.payments.view.request.commons.request.GetUserActionsHistoryRequest;
import com.snapdeal.payments.view.request.commons.request.GetUserPendingActionsRequest;
import com.snapdeal.payments.view.request.commons.request.RequestViewAbstractRequest;
import com.snapdeal.payments.view.request.commons.response.GetActionsWithFilterResponse;
import com.snapdeal.payments.view.request.commons.response.GetPendingActionsBetweenPartiesResponse;
import com.snapdeal.payments.view.request.commons.response.GetRequestViewResponse;
import com.snapdeal.payments.view.request.commons.response.GetSplitViewTransactionsResponse;
import com.snapdeal.payments.view.request.commons.response.GetUserActionsHistoryResponse;
import com.snapdeal.payments.view.request.commons.response.GetUserPendingActionsResponse;
import com.snapdeal.payments.view.request.commons.service.IRequestViewService;
import com.snapdeal.payments.view.transformers.ObjectTransformer;
import com.snapdeal.payments.view.utils.PaymentsViewShardContextHolder;
import com.snapdeal.payments.view.utils.PaymentsViewShardUtil;
import com.snapdeal.payments.view.utils.validator.GenericValidator;

@Service("requestViewServiceImpl")
public class RequestViewServiceImpl implements IRequestViewService {

	@Autowired
	private IPersistanceManager persistManager;

	@Autowired
	GenericValidator<AbstractRequest> validator;

	@Autowired
	private PaymentsViewShardUtil paymentsViewUtil;

	@Autowired
	@Qualifier("ActionToRequestViewTransaformer")
	private ObjectTransformer<ActionDetailsEntity, ActionDto> transformer;

	@Override
	public GetRequestViewResponse getRequestViewSearch(
			GetRequestViewSearchRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);
		setShardKey();
		if (request.getStatus() == TxnStatus.ALL) {
			request.setStatus(null);
		}
		GetRequestViewResponse response = new GetRequestViewResponse();
		List<RVTransactionDto> rvTransaction = persistManager
				.getRVTransactionDetails(request);
		PaymentsViewShardContextHolder.clearShardKey();
		response.setRequestViewTransactionDto(rvTransaction);

		return response;
	}



	@Override
	public GetUserActionsHistoryResponse getUserActionsHistory(
			GetUserActionsHistoryRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);
		setShardKey();
		GetUserActionsHistoryResponse response = new GetUserActionsHistoryResponse();
		List<ActionDto> rvTransaction = new ArrayList<ActionDto>();

		List<ActionDetailsEntity> pendingActions = persistManager
				.getActionsByFilter(getActionsFilter(request));

		transformOutput(rvTransaction, pendingActions, request);
		PaymentsViewShardContextHolder.clearShardKey();
		response.setActionsList(rvTransaction);
		if (rvTransaction != null && !rvTransaction.isEmpty()) {
			response.setLastEvaluatedkey(rvTransaction
					.get(rvTransaction.size() - 1)
					.getActionInitiationTimestamp().getTime());
		}
		return response;
	}


	@Override
	public GetPendingActionsBetweenPartiesResponse getPendingActionsBetweenParties(
			GetPendingActionsBetweenPartyRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);
		setShardKey();
		GetPendingActionsBetweenPartiesResponse response = new GetPendingActionsBetweenPartiesResponse();
		List<ActionDto> rvTransaction = new ArrayList<ActionDto>();

		List<ActionDetailsEntity> pendingActions = persistManager
				.getActionsByFilter(getMutualPendingActionsFilter(request));

		PaymentsViewShardContextHolder.clearShardKey();
		transformOutput(rvTransaction, pendingActions, request);
		response.setActionsList(rvTransaction);
		if (rvTransaction != null && !rvTransaction.isEmpty()) {
			response.setLastEvaluatedkey(rvTransaction
					.get(rvTransaction.size() - 1)
					.getActionInitiationTimestamp().getTime());
		}
		return response;
	}

	@Override
	public GetUserPendingActionsResponse getUserPendingActions(
			GetUserPendingActionsRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);
		setShardKey();
		GetUserPendingActionsResponse response = new GetUserPendingActionsResponse();
		List<ActionDto> rvTransaction = new ArrayList<ActionDto>();

		List<ActionDetailsEntity> pendingActions = persistManager
				.getActionsByFilter(getPendingActionsForUserFilter(request));

		PaymentsViewShardContextHolder.clearShardKey();
		transformOutput(rvTransaction, pendingActions, request);

		response.setActionsList(rvTransaction);
		if (rvTransaction != null && !rvTransaction.isEmpty()) {
			response.setLastEvaluatedkey(rvTransaction
					.get(rvTransaction.size() - 1)
					.getActionInitiationTimestamp().getTime());
		}

		return response;
	}

	private ActionFilter getPendingActionsForUserFilter(
			GetUserPendingActionsRequest request) {
		ActionFilter filter = new ActionFilter();

		filter.setEndDate(request.getEndDate());
		filter.setStartDate(request.getStartDate());
		if(request.getLastEvaluatedkey()!=null){
			filter.setLastEvaluatedkey(new Date(request.getLastEvaluatedkey()));
		}
		filter.setUserId(request.getUserId());
		filter.setActionTypes(request.getActionType());
		List<TxnStatus> status = new ArrayList<TxnStatus>();
		status.add(TxnStatus.PENDING);

		filter.setTxnStatus(status);
		filter.setPrevious(request.isPrevious());
		filter.setLimit(request.getLimit());

		return filter;
	}

	private ActionFilter getMutualPendingActionsFilter(
			GetPendingActionsBetweenPartyRequest request) {
		ActionFilter filter = new ActionFilter();
		filter.setEndDate(request.getEndDate());
		filter.setStartDate(request.getStartDate());
		if(request.getLastEvaluatedkey()!=null){
			filter.setLastEvaluatedkey(new Date(request.getLastEvaluatedkey()));
		}
		filter.setUserId(request.getUserId());
		filter.setActionTypes(request.getActionType());
		filter.setOtherPartyId(request.getOtherPartyId());
		List<TxnStatus> status = new ArrayList<TxnStatus>();
		status.add(TxnStatus.PENDING);
		filter.setTxnStatus(status);
		filter.setPrevious(request.isPrevious());
		filter.setLimit(request.getLimit());
		return filter;
	}

	private ActionFilter getActionsFilter(GetUserActionsHistoryRequest request) {
		ActionFilter filter = new ActionFilter();
		filter.setEndDate(request.getEndDate());
		filter.setStartDate(request.getStartDate());
		if(request.getLastEvaluatedkey()!=null){
			filter.setLastEvaluatedkey(new Date(request.getLastEvaluatedkey()));
		}
		filter.setUserId(request.getUserId());
		filter.setActionTypes(request.getActionType());
		filter.setPrevious(request.isPrevious());
		filter.setLimit(request.getLimit());
		List<String> referenceTypesToSearch = new LinkedList<String>();
		if (request.getReferenceType() != null
				&& !request.getReferenceType().isEmpty()) {
			List<ActionType> referenceTypes = request.getReferenceType();

			for (ActionType referenceType : referenceTypes)
				referenceTypesToSearch.add(referenceType.name());
		}
		filter.setReferenceTypes(referenceTypesToSearch);
		return filter;
	}

	/*private ActionFilter getSplitTransactionsFilter(
			GetSplitViewTransactionsRequest request) {
		ActionFilter filter = new ActionFilter();

		filter.setEndDate(request.getEndDate());
		filter.setStartDate(request.getStartDate());
		if(request.getLastEvaluatedkey()!=null){
			filter.setLastEvaluatedkey(new Date(request.getLastEvaluatedkey()));
		}
		filter.setReferenceId(request.getSplitId());
		List<String> referenceTypesToSearch = new LinkedList<String>();
		referenceTypesToSearch.add(TransactionType.SPLIT_MONEY.name());
		filter.setReferenceTypes(referenceTypesToSearch);
		filter.setLimit(request.getLimit());

		return filter;
	}*/

	/*@Override
	public GetSplitViewTransactionsResponse getSplitViewTransactions(
			GetSplitViewTransactionsRequest request) {
		String shardKey = paymentsViewUtil.getTaskShardKey(null,
				ViewTypeEnum.REQUESTVIEW);
		paymentsViewUtil.setDataBaseSource(shardKey, ViewTypeEnum.REQUESTVIEW);
		validator.validate(request);
		GetSplitViewTransactionsResponse response = new GetSplitViewTransactionsResponse();
		List<ActionDto> rvTransaction = new ArrayList<ActionDto>();
		List<ActionDetailsEntity> pendingActions = persistManager
				.getActionsByFilter(getSplitTransactionsFilter(request));

		PaymentsViewShardContextHolder.clearShardKey();
		transformOutput(rvTransaction, pendingActions, request);
		response.setTransactions(rvTransaction);
		if (rvTransaction != null && !rvTransaction.isEmpty()) {
			response.setLastEvaluatedkey(rvTransaction
					.get(rvTransaction.size() - 1)
					.getActionInitiationTimestamp().getTime());
		}
		return response;
	}*/
	
	/**
	 * Set Shard For RequestView
	 */
	private void setShardKey() {
		String shardKey = paymentsViewUtil.getTaskShardKey(null,
				ViewTypeEnum.REQUESTVIEW);
		paymentsViewUtil.setDataBaseSource(shardKey, ViewTypeEnum.REQUESTVIEW);

	}
	
	private void transformOutput(List<ActionDto> output,
			List<ActionDetailsEntity> inputList,
			RequestViewAbstractRequest request) {
		for (ActionDetailsEntity actionEntity : inputList) {
			output.add(transformer.transforme(actionEntity));
		}

		if (output != null && !output.isEmpty()) {
			// in case of prev page we need to reverse the order of dao output
			if (request.getLastEvaluatedkey() != null && request.isPrevious()) {
				Collections.sort(output);
			}
		}

	}

}
