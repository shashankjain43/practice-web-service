package com.snapdeal.merchant.rest.stub.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.snapdeal.merchant.dto.MPTransactionDTO;
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
import com.snapdeal.merchant.rest.service.ITransactionService;

@Service
public class StubTransactionServiceImpl implements ITransactionService{


	
	@Override
	public MerchantGetTransactionResponse getTxnsOfMerchant(
			MerchantGetFilterTransactionRequest request)
			throws MerchantException {
		
		MerchantGetTransactionResponse response = new MerchantGetTransactionResponse();
		
		List<MPTransactionDTO> mpTransactionList = new ArrayList<MPTransactionDTO>();
		
		// creating 1st txn 
		
		MPTransactionDTO  mpTxnDto = new MPTransactionDTO();
		mpTxnDto.setCustId("1234");
		mpTxnDto.setCustIP("custIP");
		mpTxnDto.setCustName("custName");
		mpTxnDto.setFcTxnId("fcTxnId");
		mpTxnDto.setLocation("location");
		mpTxnDto.setMerchantFee(new BigDecimal(1000));
		mpTxnDto.setMerchantId("merchantId");
		mpTxnDto.setMerchantName("merchantName");
		mpTxnDto.setMerchantTxnId("merchantTxnId");
		mpTxnDto.setNetDeduction(new BigDecimal(10));
		mpTxnDto.setOrderId("orderId");
		mpTxnDto.setPayableAmount(new BigDecimal(100));
		mpTxnDto.setProductId("productId");
		mpTxnDto.setServiceTax(new BigDecimal(5.2));
		mpTxnDto.setSettlementId("settlementId");
		mpTxnDto.setShippingCity("shippingCity");
		mpTxnDto.setStoreId("storeId");
		mpTxnDto.setStoreName("storeName");
		mpTxnDto.setSwachBharatCess(new BigDecimal(2.2));
		mpTxnDto.setTerminalId("terminalId");
		mpTxnDto.setTotalTxnAmount(new BigDecimal(100000));
		mpTxnDto.setTxnDate(new Date());
		mpTxnDto.setTxnStatus(MPTransactionStatus.PENDING);
		mpTxnDto.setTxnType(MPTransactionType.DEBIT);
		
		mpTransactionList.add(mpTxnDto);
		
		// creating 2nd txn 
		MPTransactionDTO  mpTxnDto1 = new MPTransactionDTO();
		mpTxnDto1.setCustId("1234");
		mpTxnDto1.setCustIP("custIP");
		mpTxnDto1.setCustName("custName");
		mpTxnDto1.setFcTxnId("fcTxnId");
		mpTxnDto1.setLocation("location");
		mpTxnDto1.setMerchantFee(new BigDecimal(1000));
		mpTxnDto1.setMerchantId("merchantId");
		mpTxnDto1.setMerchantName("merchantName");
		mpTxnDto1.setMerchantTxnId("merchantTxnId");
		mpTxnDto1.setNetDeduction(new BigDecimal(10));
		mpTxnDto1.setOrderId("orderId");
		mpTxnDto1.setPayableAmount(new BigDecimal(100));
		mpTxnDto1.setProductId("productId");
		mpTxnDto1.setServiceTax(new BigDecimal(5.2));
		mpTxnDto1.setSettlementId("settlementId");
		mpTxnDto1.setShippingCity("shippingCity");
		mpTxnDto1.setStoreId("storeId");
		mpTxnDto1.setStoreName("storeName");
		mpTxnDto1.setSwachBharatCess(new BigDecimal(2.2));
		mpTxnDto1.setTerminalId("terminalId");
		mpTxnDto1.setTotalTxnAmount(new BigDecimal(100000));
		mpTxnDto1.setTxnDate(new Date());
		mpTxnDto1.setTxnStatus(MPTransactionStatus.FAILED);
		mpTxnDto1.setTxnType(MPTransactionType.REFUND);
		
		mpTransactionList.add(mpTxnDto1);
		
		response.setMpTransactions(mpTransactionList);
		return response;
	}


   @Override
   public MerchantGetTransactionResponse getTxnsOfMerchantBySearch(
            MerchantGetSearchTransactionRequest request) {

      MerchantGetTransactionResponse response = new MerchantGetTransactionResponse();
      List<MPTransactionDTO> mpTransactionList = new ArrayList<MPTransactionDTO>();

      // creating 1st txn

      MPTransactionDTO mpTxnDto = new MPTransactionDTO();
      mpTxnDto.setCustId("1235");
      mpTxnDto.setCustIP("custIP");
      mpTxnDto.setCustName("custName");
      mpTxnDto.setFcTxnId("fcTxnId");
      mpTxnDto.setLocation("location");
      mpTxnDto.setMerchantFee(new BigDecimal(1000));
      mpTxnDto.setMerchantId("merchantId");
      mpTxnDto.setMerchantName("merchantName");
      mpTxnDto.setMerchantTxnId("merchantTxnId");
      mpTxnDto.setNetDeduction(new BigDecimal(10));
      mpTxnDto.setOrderId("orderId");
      mpTxnDto.setPayableAmount(new BigDecimal(100));
      mpTxnDto.setProductId("productId");
      mpTxnDto.setServiceTax(new BigDecimal(5.2));
      mpTxnDto.setSettlementId("settlementId1");
      mpTxnDto.setShippingCity("shippingCity");
      mpTxnDto.setStoreId("storeId");
      mpTxnDto.setStoreName("storeName");
      mpTxnDto.setSwachBharatCess(new BigDecimal(2.2));
      mpTxnDto.setTerminalId("terminalId");
      mpTxnDto.setTotalTxnAmount(new BigDecimal(100000));
      mpTxnDto.setTxnDate(new Date());
      mpTxnDto.setTxnStatus(MPTransactionStatus.SETTLED);
      mpTxnDto.setTxnType(MPTransactionType.REFUND);

      mpTransactionList.add(mpTxnDto);

      // creating 2nd txn
      MPTransactionDTO mpTxnDto1 = new MPTransactionDTO();
      mpTxnDto1.setCustId("1236");
      mpTxnDto1.setCustIP("custIP");
      mpTxnDto1.setCustName("custName");
      mpTxnDto1.setFcTxnId("fcTxnId");
      mpTxnDto1.setLocation("location");
      mpTxnDto1.setMerchantFee(new BigDecimal(1000));
      mpTxnDto1.setMerchantId("merchantId");
      mpTxnDto1.setMerchantName("merchantName");
      mpTxnDto1.setMerchantTxnId("merchantTxnId");
      mpTxnDto1.setNetDeduction(new BigDecimal(10));
      mpTxnDto1.setOrderId("orderId");
      mpTxnDto1.setPayableAmount(new BigDecimal(100));
      mpTxnDto1.setProductId("productId");
      mpTxnDto1.setServiceTax(new BigDecimal(5.2));
      mpTxnDto1.setSettlementId("settlementId2");
      mpTxnDto1.setShippingCity("shippingCity");
      mpTxnDto1.setStoreId("storeId");
      mpTxnDto1.setStoreName("storeName");
      mpTxnDto1.setSwachBharatCess(new BigDecimal(2.2));
      mpTxnDto1.setTerminalId("terminalId");
      mpTxnDto1.setTotalTxnAmount(new BigDecimal(100000));
      mpTxnDto1.setTxnDate(new Date());
      mpTxnDto1.setTxnStatus(MPTransactionStatus.FAILED);
      mpTxnDto1.setTxnType(MPTransactionType.DEBIT);

      mpTransactionList.add(mpTxnDto1);

      response.setMpTransactions(mpTransactionList);
      return response;
   }
   
   
    @Override
	public MerchantRefundAmountResponse refundMoney(MerchantRefundAmountRequest request) throws MerchantException {
		MerchantRefundAmountResponse response = new MerchantRefundAmountResponse();
		response.setStatus(true);
		return response;
	}


	@Override
	public MerchantBulkRefundResponse bulkRefund(MerchantBulkRefundRequest request, InputStream ioStream)
			throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public MerchantExportTxnResponse exportTxn(MerchantGetTransactionRequest request, FileType fileType, String userId)
			throws MerchantException, Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public MerchantGetTransactionResponse getMerchantTxns(MerchantGetTransactionRequest request)
			throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public MerchantRefundAmountForTxnResponse getRefundAmountForTxn(MerchantRefundAmountForTxnRequest request) {
		
		MerchantRefundAmountForTxnResponse response = new MerchantRefundAmountForTxnResponse(); 
		response.setTotalRefundAmount(new BigDecimal(123.986));
		return response;
	}

}
