package com.snapdeal.merchant.client.impl;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import org.apache.mina.http.api.HttpMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.reflect.TypeToken;
import com.snapdeal.merchant.client.ITransactionServiceClient;
import com.snapdeal.merchant.client.exception.HttpTransportException;
import com.snapdeal.merchant.client.util.RestURIConstants;
import com.snapdeal.merchant.dto.MPSearch;
import com.snapdeal.merchant.dto.MPViewFilters;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.enums.FileType;
import com.snapdeal.merchant.enums.MPTransactionStatus;
import com.snapdeal.merchant.enums.MPTransactionType;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantBulkRefundRequest;
import com.snapdeal.merchant.request.MerchantGetFilterTransactionRequest;
import com.snapdeal.merchant.request.MerchantGetSearchTransactionRequest;
import com.snapdeal.merchant.request.MerchantGetTransactionRequest;
import com.snapdeal.merchant.request.MerchantRefundAmountForTxnRequest;
import com.snapdeal.merchant.request.MerchantRefundAmountRequest;
import com.snapdeal.merchant.response.MerchantBulkRefundResponse;
import com.snapdeal.merchant.response.MerchantExportTxnResponse;
import com.snapdeal.merchant.response.MerchantGetTransactionResponse;
import com.snapdeal.merchant.response.MerchantRefundAmountForTxnResponse;
import com.snapdeal.merchant.response.MerchantRefundAmountResponse;
import com.snapdeal.merchant.response.MerchantVerifyUserResponse;

public class TransactionServiceClientImpl extends AbstractClientImpl implements ITransactionServiceClient {

	@Override
	@Deprecated
	public MerchantGetTransactionResponse getTxnsOfMerchant(MerchantGetFilterTransactionRequest request)
			throws MerchantException, HttpTransportException {
		Type type = new TypeToken<GenericMerchantResponse<MerchantGetTransactionResponse>>() {
		}.getType();
		
		StringBuilder url = new StringBuilder();
		url.append(RestURIConstants.TRANSACTION).append(RestURIConstants.VIEW_TRANSACTION).append("?");
		
		MPViewFilters filters = request.getFilters();
		
		url.append(RestURIConstants.orderBy);
		url.append(request.getOrderby());
		
		url.append(RestURIConstants.and);
		url.append(RestURIConstants.limit);
		url.append(request.getLimit());
		
		url.append(RestURIConstants.and);
		url.append(RestURIConstants.page);
		url.append(request.getPage());
		
		if(filters != null) {
		
			if (filters.getStartDate() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.startDate);
				url.append(filters.getStartDate().toString());
			}
				
			 
			if (filters.getEndDate() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.endDate);
				url.append(filters.getEndDate().toString());
			}
				
			
			if(filters.getFromAmount() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.fromAmount);
				url.append(filters.getFromAmount().toString());
			}
			
			if(filters.getToAmount() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.toAmount);
				url.append(filters.getToAmount().toString());
			}
				
			
			if(filters.getTxnStatusList() != null) {
				List<MPTransactionStatus> txnstatus = filters.getTxnStatusList();
				if(txnstatus.size() != 0) {
					url.append(RestURIConstants.and);
					StringBuilder txnvalues = new StringBuilder();
					for(int i = 0 ; i < txnstatus.size() ; i++) {
						MPTransactionStatus status = txnstatus.get(i);
						if(i != txnstatus.size()-1){
							txnvalues.append(status.getTxnStatusValue()).append(",");
						} else {
							txnvalues.append(status.getTxnStatusValue());
						}
						
					}
					url.append(RestURIConstants.txnStatusList);
					url.append(txnvalues.toString());
					
				}
				
			}
			
			if(filters.getTxnTypeList() != null) {
				List<MPTransactionType> txnstatus = filters.getTxnTypeList();
				if(txnstatus.size() != 0) {
					url.append(RestURIConstants.and);
					StringBuilder txnvalues = new StringBuilder();
					for(int i = 0 ; i < txnstatus.size() ; i++) {
						MPTransactionType status = txnstatus.get(i);
						if(i != txnstatus.size()-1){
							txnvalues.append(status.getTxnTypeValue()).append(",");
						} else {
							txnvalues.append(status.getTxnTypeValue());
						}
						
					}
					url.append(RestURIConstants.txnTypeList);
					url.append(txnvalues.toString());
					
				}
				
			}
		}
		
		

		
		return prepareResponse(request, MerchantGetTransactionResponse.class, type, HttpMethod.GET,
				  url.toString());
	}

	@Override
	@Deprecated
	public MerchantGetTransactionResponse getTxnsOfMerchantBySearch(MerchantGetSearchTransactionRequest request)
			throws MerchantException, HttpTransportException {
		Type type = new TypeToken<GenericMerchantResponse<MerchantGetTransactionResponse>>() {
		}.getType();
		
		StringBuilder url = new StringBuilder();
		url.append(RestURIConstants.TRANSACTION).append(RestURIConstants.SEARCH_TRANSACTION).append("?");
		
		url.append(RestURIConstants.orderBy);
		url.append(request.getOrderby());
		
		url.append(RestURIConstants.and);
		url.append(RestURIConstants.limit);
		url.append(request.getLimit());
		
		url.append(RestURIConstants.and);
		url.append(RestURIConstants.page);
		url.append(request.getPage());

		MPSearch mpSearch = request.getSearchCriteria();
		
		if(mpSearch != null){
			
			if(mpSearch.getMerchantTxnId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.merchantTxnId);
				url.append(mpSearch.getMerchantTxnId().toString());
			}
			if(mpSearch.getCustomerId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.customerId);
				url.append(mpSearch.getCustomerId().toString());
			}
			if(mpSearch.getOrderId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.orderId);
				url.append(mpSearch.getOrderId().toString());
			}
			if(mpSearch.getProductId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.productId);
				url.append(mpSearch.getProductId().toString());
			}
			if(mpSearch.getSettlementId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.settlementId);
				url.append(mpSearch.getSettlementId().toString());
			}
			if(mpSearch.getStoreId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.storeId);
				url.append(mpSearch.getStoreId().toString());
			}
			if(mpSearch.getTerminalId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.terminalId);
				url.append(mpSearch.getTerminalId().toString());
			}
			if(mpSearch.getTransactionId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.transactionId);
				url.append(mpSearch.getTransactionId().toString());
			}
			
		}
		
		
		return prepareResponse(request, MerchantGetTransactionResponse.class, type, HttpMethod.GET,
				url.toString());
	}

	@Override
	public MerchantRefundAmountResponse refundMoney(MerchantRefundAmountRequest request)
			throws MerchantException, HttpTransportException {
		
		Type type = new TypeToken<GenericMerchantResponse<MerchantRefundAmountResponse>>() {
		}.getType();
		return prepareResponse(request, MerchantRefundAmountResponse.class, type, HttpMethod.POST,
				RestURIConstants.TRANSACTION + RestURIConstants.REFUND_TRANSACTION);
		
	}

	@Override
	public MerchantGetTransactionResponse getMerchantTxns(MerchantGetTransactionRequest request)
			throws MerchantException, HttpTransportException {
		   
		Type type = new TypeToken<GenericMerchantResponse<MerchantGetTransactionResponse>>() {
			}.getType();
			
		StringBuilder url = new StringBuilder();
		url.append(RestURIConstants.TRANSACTION).append(RestURIConstants.GET_TXNS).append("?");

		url.append(RestURIConstants.orderBy);
		url.append(request.getOrderby());
		
		url.append(RestURIConstants.and);
		url.append(RestURIConstants.limit);
		url.append(request.getLimit());
		
		url.append(RestURIConstants.and);
		url.append(RestURIConstants.page);
		url.append(request.getPage());
		
		MPViewFilters filters = request.getFilters();
		MPSearch mpSearch = request.getSearchCriteria();
		

		if(filters != null) {
			
			if (filters.getStartDate() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.startDate);
				url.append(filters.getStartDate().toString());
			}
				
			 
			if (filters.getEndDate() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.endDate);
				url.append(filters.getEndDate().toString());
			}
				
			
			if(filters.getFromAmount() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.fromAmount);
				url.append(filters.getFromAmount().toString());
			}
			
			if(filters.getToAmount() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.toAmount);
				url.append(filters.getToAmount().toString());
			}
				
			
			if(filters.getTxnStatusList() != null) {
				List<MPTransactionStatus> txnstatus = filters.getTxnStatusList();
				if(txnstatus.size() != 0) {
					url.append(RestURIConstants.and);
					StringBuilder txnvalues = new StringBuilder();
					for(int i = 0 ; i < txnstatus.size() ; i++) {
						MPTransactionStatus status = txnstatus.get(i);
						if(i != txnstatus.size()-1){
							txnvalues.append(status.getTxnStatusValue()).append(",");
						} else {
							txnvalues.append(status.getTxnStatusValue());
						}
						
					}
					url.append(RestURIConstants.txnStatusList);
					url.append(txnvalues.toString());
					
				}
				
			}
			
			if(filters.getTxnTypeList() != null) {
				List<MPTransactionType> txnstatus = filters.getTxnTypeList();
				if(txnstatus.size() != 0) {
					url.append(RestURIConstants.and);
					StringBuilder txnvalues = new StringBuilder();
					for(int i = 0 ; i < txnstatus.size() ; i++) {
						MPTransactionType status = txnstatus.get(i);
						if(i != txnstatus.size()-1){
							txnvalues.append(status.getTxnTypeValue()).append(",");
						} else {
							txnvalues.append(status.getTxnTypeValue());
						}
						
					}
					url.append(RestURIConstants.txnTypeList);
					url.append(txnvalues.toString());
					
				}
				
			}
		}
		
	if(mpSearch != null){
			
			if(mpSearch.getMerchantTxnId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.merchantTxnId);
				url.append(mpSearch.getMerchantTxnId().toString());
			}
			if(mpSearch.getCustomerId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.customerId);
				url.append(mpSearch.getCustomerId().toString());
			}
			if(mpSearch.getOrderId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.orderId);
				url.append(mpSearch.getOrderId().toString());
			}
			if(mpSearch.getProductId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.productId);
				url.append(mpSearch.getProductId().toString());
			}
			if(mpSearch.getSettlementId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.settlementId);
				url.append(mpSearch.getSettlementId().toString());
			}
			if(mpSearch.getStoreId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.storeId);
				url.append(mpSearch.getStoreId().toString());
			}
			if(mpSearch.getTerminalId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.terminalId);
				url.append(mpSearch.getTerminalId().toString());
			}
			if(mpSearch.getTransactionId() != null){
				url.append(RestURIConstants.and);
				url.append(RestURIConstants.transactionId);
				url.append(mpSearch.getTransactionId().toString());
			}
			
		}
		
		
			
	    return prepareResponse(request, MerchantGetTransactionResponse.class, type, HttpMethod.GET,
	               url.toString());
		
	}

	@Override
	public MerchantRefundAmountForTxnResponse getRefundAmountForTxn(MerchantRefundAmountForTxnRequest request)
			throws MerchantException, HttpTransportException {
		
		Type type = new TypeToken<GenericMerchantResponse<MerchantGetTransactionResponse>>() {
		}.getType();
		
		
		 // ?txnRefId=ICRL7Lx44VXomD_UUgVsmQHJA_1&txnRefType=CUSTOMER_PAYMENT&orderId=UUgVsmQHJA
				
				
		StringBuilder url = new StringBuilder();
		url.append(RestURIConstants.TRANSACTION).append(RestURIConstants.REFUND_AMOUNT).append("?");
		
		url.append(RestURIConstants.txnRefId);
		url.append(request.getTxnRefId());
		url.append(RestURIConstants.and);
		
		url.append(RestURIConstants.txnRefType);
		url.append(request.getTxnRefType());
		url.append(RestURIConstants.and);
		
		url.append(RestURIConstants.orderId);
		url.append(request.getOrderId());
		
		
      return prepareResponse(request, MerchantRefundAmountForTxnResponse.class, type, HttpMethod.GET, url.toString());
      
	}

}
