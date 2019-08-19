package com.snapdeal.payments.view.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.payments.view.entity.TransactionDetailsEntity;
import com.snapdeal.payments.view.entity.TransactionDetailsForDisbursment;
import com.snapdeal.payments.view.entity.TransactionStateDetailsEntity;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnStatusDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnWithMetaDataDTO;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionDetailsDTO;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionPaybleDto;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantSettledTransactionsRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnStatusHistoryByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsSearchFilterWithMetaDataRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterCursorRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetTotalRefundedAmountForTxnRequest;
import com.snapdeal.payments.view.service.request.GetMerchantTransactionWithCursor;

public interface IMerchantTransactionDetailsDao {

   public void saveTransactionDetails(TransactionDetailsEntity entity);

   public void saveTransactionStateDetails(TransactionStateDetailsEntity entity);

   public boolean verifyForTxnStatusValid(TransactionStateDetailsEntity entity);

   public void updateForAlreadyExistState(TransactionStateDetailsEntity entity);

   public TransactionDetailsDTO getTransactionDetails(Map<String, String> txnDetails);

   public TransactionDetailsDTO getTransactionDetails(String txnId);



   public List<MVTransactionDTO> getMerchantViewFilter(GetMerchantViewFilterRequest request);

   public List<MVTransactionDTO> getMerchantViewSearch(GetMerchantViewSearchRequest request);

   public List<MVTxnWithMetaDataDTO> getTxnsMetaDataForSearch(GetMerchantViewSearchRequest request);

   public List<MVTransactionDTO> getMerchantViewSearchWithFilter(GetMerchantViewSearchWithFilterRequest request);
   
   public List<MVTransactionDTO> getMerchantViewSearchWithFilterCursor(
			GetMerchantViewSearchWithFilterCursorRequest request);
   
   public BigDecimal getTotalRefundedAmountForTxn(GetTotalRefundedAmountForTxnRequest request);
   
   public int updateTxnDetailsOFDirectSystem(TransactionDetailsEntity entity);

   public int updateTxnDeatilsPayableSystem(TransactionPaybleDto dto);

   public int updateTxnDeatilsDisbursmentSystem(TransactionDetailsForDisbursment deEntity);

   public String getTransactionByTxnBtsRef(@Param("txnRef") String txnRef);
   
   public List<MVTransactionDTO> getMerchantVewTransactionsWithCursor(GetMerchantTransactionWithCursor request);
   
   public void healthCheckForMerchant() ;
   
   public  List<MVTxnStatusDTO> getMerchantTxnStatusHistoryByTxnId(
			GetMerchantTxnStatusHistoryByTxnIdRequest request) ;
   
   public List<MVTxnDTO> getMerchantTxnsSearchFilterWithMetaData(
			GetMerchantTxnsSearchFilterWithMetaDataRequest request);
   
   List<MVTxnDTO> getMVSettledTxns(
		   GetMerchantSettledTransactionsRequest cursorRequest);
}
