package com.snapdeal.merchant.rest.service;

import java.io.InputStream;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.merchant.enums.FileType;
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

  
public interface ITransactionService {

	@Deprecated
	public MerchantGetTransactionResponse getTxnsOfMerchant(
            MerchantGetFilterTransactionRequest request) throws MerchantException;

	@Deprecated
	public MerchantGetTransactionResponse getTxnsOfMerchantBySearch(
            MerchantGetSearchTransactionRequest request) throws MerchantException;

	
	public MerchantRefundAmountResponse refundMoney(MerchantRefundAmountRequest request) throws MerchantException;

	public MerchantExportTxnResponse exportTxn(MerchantGetTransactionRequest request,FileType fileType,String userId)throws MerchantException;
	
	public MerchantGetTransactionResponse getMerchantTxns(MerchantGetTransactionRequest request) throws MerchantException;

	public MerchantRefundAmountForTxnResponse getRefundAmountForTxn(MerchantRefundAmountForTxnRequest request) throws MerchantException;

	


}
