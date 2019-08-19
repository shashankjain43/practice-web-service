package com.snapdeal.payments.view.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnStatusDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnWithMetaDataDTO;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnStatusHistoryByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetTotalRefundedAmountForTxnRequest;
import com.snapdeal.payments.view.service.request.GetMVSettledTxnsMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantTransactionWithCursor;
import com.snapdeal.payments.view.service.request.GetMerchantTxnsSearchFilterWithMetaDataMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantViewFilterMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantViewSearchMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantViewSearchWithFilterCursorMapperRequest;
import com.snapdeal.payments.view.service.request.GetMerchantViewSearchWithFilterMapperRequest;

public interface IMerchantViewMapper {

	public List<MVTransactionDTO> getTxnsForFilters(
			GetMerchantViewFilterMapperRequest request);

	public List<MVTransactionDTO> getTxnsForSearchCriteria(
			GetMerchantViewSearchMapperRequest request);

	public List<MVTxnWithMetaDataDTO> getTxnsMetaDataForSearchCriteria(
			GetMerchantViewSearchRequest request);

	public List<MVTransactionDTO> getTxnsForSearchWithFilter(
			GetMerchantViewSearchWithFilterMapperRequest request);

	public List<MVTransactionDTO> getTxnsForSearchWithFilterCursor(
			GetMerchantViewSearchWithFilterCursorMapperRequest request);

	public BigDecimal getTotalRefundedAmountForTxn(
			GetTotalRefundedAmountForTxnRequest request);

	public List<MVTransactionDTO> getMerchantVewTransactionsWithCursor(
			GetMerchantTransactionWithCursor request);
	
	public  List<MVTxnStatusDTO>  getMerchantTxnStatusHistoryByTxnId(
			GetMerchantTxnStatusHistoryByTxnIdRequest request) ;
	
	public List<MVTxnDTO> getTxnsForSearchFilterWithMetaData(
			GetMerchantTxnsSearchFilterWithMetaDataMapperRequest request);
	
	List<MVTxnDTO> getMVSettledTxns(
			GetMVSettledTxnsMapperRequest mapperReq);

}
