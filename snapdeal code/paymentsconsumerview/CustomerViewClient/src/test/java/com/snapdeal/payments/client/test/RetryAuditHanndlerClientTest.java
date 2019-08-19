package com.snapdeal.payments.client.test;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.payments.view.client.impl.RetryAuditHandlerClient;
import com.snapdeal.payments.view.commons.enums.RetryTaskStatus;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.merchant.commons.dto.RetryTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.request.RetryPaymentsViewAuditRequest;
import com.snapdeal.payments.view.merchant.commons.request.RetryTransactionListRequest;
import com.snapdeal.payments.view.utils.ClientDetails;

public class RetryAuditHanndlerClientTest {

	RetryAuditHandlerClient retryClient = new RetryAuditHandlerClient() ;
	@Before
	public void setup() throws Exception {

	 ClientDetails.init("http://localhost", "8080", "snapdeal", "1",1200000000*3600);
		ClientDetails.init("https://views.paywithfreecharge.com", "443", "20ef18a9-7529-48a1-99d9-e7523d621845", "CustomerService",12000);

	}
	
	@Test
	public void retryMerchantAudit()
			throws PaymentsViewServiceException {
		RetryPaymentsViewAuditRequest request = new RetryPaymentsViewAuditRequest() ;
		//List<TransactionType> txnTypeList = new LinkedList<TransactionType>() ;
		//txnTypeList.add(TransactionType.OPS_WALLET_CREDIT) ;
		//List<String> errorCodeList = new LinkedList<String>() ;
			//errorCodeList.add("ER-5108") ;
			//errorCodeList.add("ER-5111");
			//errorCodeList.add("ER-5114");
			//errorCodeList.add("ER-5111") ;
			//request.setTxnTypeList(txnTypeList);
			//request.setErrorCode(errorCodeList);
		request.setRetryCount(3);
		request.setViewType(ViewTypeEnum.REQUESTVIEW);
			request.setStatus(RetryTaskStatus.PENDING);
		String status = retryClient.retryMerchantviewAudit(request) ;
		System.out.println(status);
	}
	
	@Test
	public void retryTxnList()
			throws PaymentsViewServiceException {
		RetryTransactionListRequest request = new RetryTransactionListRequest() ;
		List<RetryTransactionDTO> dtoList = new LinkedList<RetryTransactionDTO>();
		RetryTransactionDTO dto = new RetryTransactionDTO();
		dto.setTxnId("YYTnxfVWSk17OGgy_fOIcAiAnQ0");
		dto.setTxnType(TransactionType.ESCROW_PAYMENT.name());
		dtoList.add(dto)  ;
		request.setTransactionList(dtoList);
		String status = retryClient.retryTransactionsList(request) ;
		System.out.println(status);
	}
	
}
