package com.snapdeal.payments.view.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.payments.view.entity.TransactionDetailsEntity;
import com.snapdeal.payments.view.entity.TransactionDetailsForDisbursment;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionDetailsDTO;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionPaybleDto;

public interface ITransactionDetailsMapper {

   public void saveTransactionDetails(TransactionDetailsEntity entity);

   public TransactionDetailsDTO getTransactionDetails(Map<String, String> txnIdDetails);

   public TransactionDetailsDTO getTransactionDetails(@Param("txnId") String txnId);

   public int updateTxnDetailsOFDirectSystem(TransactionDetailsEntity entity);

   public int updateTxnDeatilsPayableSystem(TransactionPaybleDto dto);

   public int updateTxnDeatilsDisbursmentSystem(TransactionDetailsForDisbursment deEntity);

   public String getTransactionByTxnBtsRef(@Param("txnRef") String txnRef);
   
   public TransactionDetailsDTO healthCheckForMerchant() ;
}
