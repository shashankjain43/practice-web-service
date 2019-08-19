package com.snapdeal.merchant.client;

import java.io.InputStream;

import com.snapdeal.merchant.client.exception.HttpTransportException;
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


public interface ITransactionServiceClient {

	@Deprecated
   public MerchantGetTransactionResponse getTxnsOfMerchant(
            MerchantGetFilterTransactionRequest request)
                     throws MerchantException, HttpTransportException;

	@Deprecated
   public MerchantGetTransactionResponse getTxnsOfMerchantBySearch(
            MerchantGetSearchTransactionRequest request)
                     throws MerchantException, HttpTransportException;
   
   public MerchantRefundAmountResponse refundMoney(MerchantRefundAmountRequest request) throws MerchantException ,HttpTransportException;

	public MerchantGetTransactionResponse getMerchantTxns(MerchantGetTransactionRequest request) throws MerchantException ,HttpTransportException;

	public MerchantRefundAmountForTxnResponse getRefundAmountForTxn(MerchantRefundAmountForTxnRequest request) throws MerchantException ,HttpTransportException;

}
