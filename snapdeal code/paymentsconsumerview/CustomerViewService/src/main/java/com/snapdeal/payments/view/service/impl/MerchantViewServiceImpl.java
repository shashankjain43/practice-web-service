package com.snapdeal.payments.view.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.metadata.DisbursementMetadata;
import com.snapdeal.payments.metadata.aggregator.CustomerPaymentsMetadata;
import com.snapdeal.payments.metadata.aggregator.PayablesGlobalMetaData;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.commons.utils.TSMMetaDataParser;
import com.snapdeal.payments.view.dao.IPersistanceManager;
import com.snapdeal.payments.view.merchant.commons.dto.MVFilterCriteria;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnStatusDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnWithMetaDataDTO;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantSettledTransactionsRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTransactionsHistoryRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnStatusHistoryByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsByOrderIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsSearchFilterWithMetaDataRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantUnsettledAmount;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetTotalRefundedAmountForTxnRequest;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewAbstractRequest;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewFilters;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewSearch;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantSettledTransactionsResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTransactionsHistoryResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnStatusHistoryResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnsSearchFilterWithMetaDataResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnsWithMetaDataResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalRefundedAmountForTxnResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalUnsettledAmountRespnse;
import com.snapdeal.payments.view.merchant.commons.response.GetTransactionsResponse;
import com.snapdeal.payments.view.merchant.commons.service.IMerchantViewService;
import com.snapdeal.payments.view.service.request.GetMerchantTransactionWithCursor;
import com.snapdeal.payments.view.utils.PaymentsViewShardContextHolder;
import com.snapdeal.payments.view.utils.PaymentsViewShardUtil;
import com.snapdeal.payments.view.utils.clients.SDmoneyClientUtil;
import com.snapdeal.payments.view.utils.validator.GenericValidator;

@Slf4j
@Service("merchantViewServiceImpl")
public class MerchantViewServiceImpl implements IMerchantViewService {

	@Autowired
	private IPersistanceManager persistManager;

	@Autowired
	GenericValidator<AbstractRequest> validator;

	@Autowired
	private PaymentsViewShardUtil paymentsViewUtil;

	@Autowired
	private SDmoneyClientUtil sdmoneyClientUtil;
	
	/*@RequestAware
	@ExceptionMetered
	@Marked
	@Override
	public GetTransactionsResponse getMerchantViewFilter(
			GetMerchantViewFilterRequest request)
			throws PaymentsViewServiceException {

		validator.validate(request);
		paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);
		MapRollbackWithFailed(request.getFilters());

		List<MVTransactionDTO> transactions = persistManager
				.getMerchantViewFilter(request);
		PaymentsViewShardContextHolder.clearShardKey();
		GetTransactionsResponse response = new GetTransactionsResponse();
		response.setMvTransactions(transactions);
		return response;
	}*/

	private void MapRollbackWithFailed(MerchantViewFilters request) {
		// inducing rollback along with failed status
		if (request != null
				&& request.getTxnStatusList() != null
				&& !request.getTxnStatusList().isEmpty()
				&& request.getTxnStatusList().contains(
						MVTransactionStatus.FAILED)) {
			request.getTxnStatusList().add(MVTransactionStatus.ROLLBACK);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.snapdeal.payments.merchantView.service.IMerchantViewService#
	 * getMerchantViewSearchWithFilter
	 * (com.snapdeal.payments.merchantView.commons
	 * .request.GetMerchantViewSearchWithFilterRequest)
	 */
	@Override
	public GetTransactionsResponse getMerchantViewSearchWithFilter(
			GetMerchantViewSearchWithFilterRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);

		if (request != null
				&& request.getSearchCriteria() != null
				&& (request.getSearchCriteria().getCustomerId() == null
						&& request.getSearchCriteria().getMerchantTxnId() == null
						&& request.getSearchCriteria().getOrderId() == null
						&& request.getSearchCriteria().getProductId() == null
						&& request.getSearchCriteria().getSettlementId() == null
						&& request.getSearchCriteria().getStoreId() == null
						&& request.getSearchCriteria().getTerminalId() == null
						&& request.getSearchCriteria().getTransactionId() == null && request
						.getSearchCriteria().getMerchantTag() == null))

			request.setSearchCriteria(null);

		if (request != null) {
			MapRollbackWithFailed(request.getFilters());

			paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
					ViewTypeEnum.MERCHANTVIEW);
		}

		List<MVTransactionDTO> transactions = persistManager
				.getMerchantViewSearchWithFilter(request);
		for(MVTransactionDTO dto : transactions){
			processMVDTO(dto);
		}
		PaymentsViewShardContextHolder.clearShardKey();
		GetTransactionsResponse response = new GetTransactionsResponse();
		response.setMvTransactions(transactions);
		return response;
	}

	@Override
	public GetTotalRefundedAmountForTxnResponse getTotalRefundedAmountForTxn(
			GetTotalRefundedAmountForTxnRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);
		paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);
		BigDecimal totalRefundedAmount = persistManager
				.getTotalRefundedAmountForTxn(request);
		GetTotalRefundedAmountForTxnResponse response = new GetTotalRefundedAmountForTxnResponse();
		response.setTotalRefundedAmount(totalRefundedAmount);
		return response;
	}

	@Override
	public GetTotalUnsettledAmountRespnse getTotalUnsettledAmount(
			GetMerchantUnsettledAmount request) {

		validator.validate(request);
		// TODO Auto-generated method stub
		return sdmoneyClientUtil.getUnsettleAmount(request);
	}


	@Override
	public GetMerchantSettledTransactionsResponse getMerchantSettledTransactions(
			GetMerchantSettledTransactionsRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);
		paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);
		
		List<MVTxnDTO> transactions =
				persistManager.getMVSettledTxns(request);
		GetMerchantSettledTransactionsResponse response = new GetMerchantSettledTransactionsResponse();
		response.setSettledMerchantTxnsList(transactions);
		PaymentsViewShardContextHolder.clearShardKey();
		return response;
	}

	private void sortIfPrevOpen(List<MVTransactionDTO> transactions) {
		if(null==transactions){
			return;
		}
		Collections.sort(transactions, new Comparator<MVTransactionDTO>() {
            public int compare(MVTransactionDTO t1,MVTransactionDTO t2) {
                return t2.getTxnDate().compareTo(t1.getTxnDate());}}
           );
	}
	
	@Override
	public GetMerchantTransactionsHistoryResponse getMerchantTransactionsHistory(
			GetMerchantTransactionsHistoryRequest request) throws PaymentsViewServiceException {
		validator.validate(request);
		paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);

		List<MVTransactionDTO> transactions = 
				persistManager.getMerchantVewTransactionsWithCursor(
							mapRequestToCursorRequest(request));
							
		if(request.getLastEvaluatedkey()!=null && request.isPrevious()){
			sortIfPrevOpen(transactions);
		}
		GetMerchantTransactionsHistoryResponse response = new GetMerchantTransactionsHistoryResponse();
		response.setMerchantTxnsHistoryList(transactions);
		if(transactions!=null && !transactions.isEmpty()){
			response.setLastEvaluatedkey(transactions.get(transactions.size()-1).getTxnDate().getTime());
		}

		PaymentsViewShardContextHolder.clearShardKey();
		return response;
	}

	/*@Override
	public GetMerchantTransactionsHistoryResponse getMerchantTxnsWithFilter(
			GetMerchantTxnsWithFilterRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);
		paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);
		GetMerchantTransactionWithCursor cursorRequest = mapRequestToCursorRequest(request) ;
		cursorRequest.setTxnTypeList(request.getTxnTypeList());
		List<MVTransactionDTO> transactions =
				persistManager.getMerchantVewTransactionsWithCursor(
						cursorRequest);
		if(request.getLastEvaluatedkey()!=null && request.isPrevious()){
			sortIfPrevOpen(transactions);
		}
		GetMerchantTransactionsHistoryResponse response = new GetMerchantTransactionsHistoryResponse();
		response.setMerchantTxnsHistoryList(transactions);
		if(transactions!=null && !transactions.isEmpty()){
			response.setLastEvaluatedkey(transactions.get(transactions.size()-1).getTxnDate().getTime());
		}

		PaymentsViewShardContextHolder.clearShardKey();
		return response;
	}*/

	private GetMerchantTransactionWithCursor mapRequestToCursorRequest(
			MerchantViewAbstractRequest request) {
		GetMerchantTransactionWithCursor cursorRequest = new GetMerchantTransactionWithCursor();
		cursorRequest.setStartDate(request.getStartDate());
		cursorRequest.setEndDate(request.getEndDate());
		cursorRequest.setLastEvaluatedkey(request.getLastEvaluatedkey());
		if(request.getLastEvaluatedkey()!=null){
			cursorRequest.setCursorKey(new Date(request.getLastEvaluatedkey()));
		}
		cursorRequest.setLimit(request.getLimit());
		cursorRequest.setMerchantId(request.getMerchantId());
		cursorRequest.setPrevious(request.isPrevious());
	
		return cursorRequest ;

	}
	
	@Override
	public GetMerchantTxnsWithMetaDataResponse getMerchantTransactionsByOrderId(
			GetMerchantTxnsByOrderIdRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);
		paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);
		GetMerchantViewSearchRequest searchRequest = new GetMerchantViewSearchRequest();
		MerchantViewSearch search = new MerchantViewSearch();
		search.setOrderId(request.getOrderId());
		searchRequest.setMerchantId(request.getMerchantId());
		searchRequest.setSearchCriteria(search);
		List<MVTxnWithMetaDataDTO> transactions = persistManager
				.getTxnsMetaDataForSearch(searchRequest);
		PaymentsViewShardContextHolder.clearShardKey();
		GetMerchantTxnsWithMetaDataResponse response = new GetMerchantTxnsWithMetaDataResponse();
		response.setMvTransactions(transactions);
		return response;
	}

	@Override
	public GetMerchantTxnsWithMetaDataResponse getMerchantTransactionsByTxnId(
			GetMerchantTxnsByTxnIdRequest request)
			throws PaymentsViewServiceException {
		validator.validate(request);
		paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);
		GetMerchantViewSearchRequest searchRequest = new GetMerchantViewSearchRequest();
		MerchantViewSearch search = new MerchantViewSearch();
		search.setTransactionId(request.getFcTxnRefId());
		searchRequest.setMerchantId(request.getMerchantId());
		searchRequest.setSearchCriteria(search);
		List<MVTxnWithMetaDataDTO> transactions = persistManager
				.getTxnsMetaDataForSearch(searchRequest);
		PaymentsViewShardContextHolder.clearShardKey();
		GetMerchantTxnsWithMetaDataResponse response = new GetMerchantTxnsWithMetaDataResponse();
		response.setMvTransactions(transactions);
		return response;
		
	}

	@Override
	public GetMerchantTxnStatusHistoryResponse getMerchantTxnStatusHistoryByTxnId(
			GetMerchantTxnStatusHistoryByTxnIdRequest request) {
		validator.validate(request);
		paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
				ViewTypeEnum.MERCHANTVIEW);
		 List<MVTxnStatusDTO> mvTxnStatusDTO =  
				 	persistManager.getMerchantTxnStatusHistoryByTxnId(request);
		GetMerchantTxnStatusHistoryResponse response = new GetMerchantTxnStatusHistoryResponse();
		response.setMvTxnStatusDTO(mvTxnStatusDTO) ;
		PaymentsViewShardContextHolder.clearShardKey();
		return response ;
	}

	@Override
	public GetMerchantTxnsSearchFilterWithMetaDataResponse getMerchantTxnsSearchFilterWithMetaData(
			GetMerchantTxnsSearchFilterWithMetaDataRequest request)
			throws PaymentsViewServiceException {
		
		validator.validate(request);
		validateSearchCriteria(request);
		if (request != null) {
			MapRollbackWithFailedNew(request.getFilters());
			paymentsViewUtil.setDataBaseSource(request.getMerchantId(),
					ViewTypeEnum.MERCHANTVIEW);
		}
		List<MVTxnDTO> transactions = persistManager
				.getMerchantTxnsSearchFilterWithMetaData(request);
		PaymentsViewShardContextHolder.clearShardKey();
		GetMerchantTxnsSearchFilterWithMetaDataResponse response = new GetMerchantTxnsSearchFilterWithMetaDataResponse();
		response.setMvTransactions(transactions);
		return response;
	}

	private void validateSearchCriteria(
			GetMerchantTxnsSearchFilterWithMetaDataRequest request) {
		if (request != null
				&& request.getSearchCriteria() != null
				&& (request.getSearchCriteria().getOrderId() == null
						&& request.getSearchCriteria().getUserId() == null
						&& request.getSearchCriteria().getSettlementId() == null
						&& request.getSearchCriteria().getTxnId() == null )){
			request.setSearchCriteria(null);
		}
	}

	private void MapRollbackWithFailedNew(
			MVFilterCriteria filters) {
		// inducing rollback along with failed status
		if (filters != null
				&& filters.getTxnStatusList() != null
				&& !filters.getTxnStatusList().isEmpty()
				&& filters.getTxnStatusList().contains(
						MVTransactionStatus.FAILED)) {
			filters.getTxnStatusList().add(MVTransactionStatus.ROLLBACK);
		}

	}

	private void processMVDTO(MVTransactionDTO dto){
		TransactionType txnType  = TransactionType.valueOf(dto.getFcTxnType());
		PayablesGlobalMetaData payableMetaData = null ;
		if((dto.getTxnMetaData() ==  null || dto.getTxnMetaData().isEmpty())&&
			(dto.getPayableMetaData() == null ||	dto.getPayableMetaData().isEmpty()))
			return ;
		else if(!(dto.getPayableMetaData() == null ||	dto.getPayableMetaData().isEmpty())){
			payableMetaData = TSMMetaDataParser.getPayableMetaData(dto.getPayableMetaData());
			dto.setKrishiKalyanCess(payableMetaData.getKrishiKalyanCess());
		}
		
		switch(txnType){
		case CUSTOMER_PAYMENT:
		case CANCELLATION_REFUND:
			if (!(dto.getTxnMetaData() == null || dto.getTxnMetaData()
					.isEmpty())) {
				CustomerPaymentsMetadata customerMetaData = null;
				try {
					customerMetaData = TSMMetaDataParser
							.getCustomerPaymentMetaData(dto.getTxnMetaData());

				} catch (Exception e) {
					log.error("ERROR occured while parsing : "  + e.getMessage());
				}
				if (customerMetaData != null) {
					dto.setPlatformId(customerMetaData.getPlatformId());
					dto.setPartnerId(customerMetaData.getPartnerId());
					dto.setDealerId(customerMetaData.getDealerId());
				}
			}
			break;
		case OPS_WALLET_DEBIT:
		case OPS_WALLET_DEBIT_REVERSE:
		case OPS_WALLET_DEBIT_VOID:
		case OPS_WALLET_DEBIT_VOID_REVERSE:
			default:
				break ;
		}
		dto.setPayableMetaData(null);
		dto.setTxnMetaData(null);
		if(dto.getDisbursementMetaData()!=null && !dto.getDisbursementMetaData().isEmpty()){
			DisbursementMetadata deMetaData=null;
			try {
				deMetaData = TSMMetaDataParser
						.getDisburseMetaData(dto.getDisbursementMetaData());
			} catch (IOException e) {
				log.error("ERROR occured while parsing : "  + e.getMessage());
			}
			if(deMetaData!=null){
				dto.setNeftId(deMetaData.getBankTransactionId());
			}
		}
		dto.setDisbursementMetaData(null);
	}
	
}