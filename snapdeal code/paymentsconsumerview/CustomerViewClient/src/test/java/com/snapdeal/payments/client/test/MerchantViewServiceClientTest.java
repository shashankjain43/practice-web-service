package com.snapdeal.payments.client.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;

import com.snapdeal.payments.view.client.impl.MerchantViewClient;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.commons.utils.JsonUtils;
import com.snapdeal.payments.view.merchant.commons.dto.MVFilterCriteria;
import com.snapdeal.payments.view.merchant.commons.dto.MVSearchCriteria;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantSettledTransactionsRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTransactionsHistoryRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnStatusHistoryByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsByOrderIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsByTxnIdRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsSearchFilterWithMetaDataRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantUnsettledAmount;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetTotalRefundedAmountForTxnRequest;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewSearch;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantSettledTransactionsResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTransactionsHistoryResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnStatusHistoryResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnsSearchFilterWithMetaDataResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetMerchantTxnsWithMetaDataResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalRefundedAmountForTxnResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalUnsettledAmountRespnse;
import com.snapdeal.payments.view.merchant.commons.response.GetTransactionsResponse;
import com.snapdeal.payments.view.utils.ClientDetails;
	
@Slf4j
public class MerchantViewServiceClientTest {

	MerchantViewClient mvClient = new MerchantViewClient() ;

	@Before
	public void setup() throws Exception {
		//staging
		//ClientDetails.init("https://views-stg.paywithfreecharge.com", "443", "49c9d21c-ed41-4e73-88ed-ebfc1c102304", "Fd1qV0jLp0lCCS",12000);

		//production
		//ClientDetails.init("https://views.paywithfreecharge.com", "443", "20ef18a9-7529-48a1-99d9-e7523d621845", "CustomerService",12000);

		//local
		//ClientDetails.init("http://localhost", "8080", "d866jdc06fd&", "test_auth_client",120000);
		//ClientDetails.init("http://localhost", "8080", "b1736f66-705c-4966-bc9a-d7fa9393ffdb", "GJvwsW82414zeI",120000);
		ClientDetails.init("http://localhost", "8080", "b1736f66-705c-4966-bc9a-d7fa9393ffdb", "GJvwsW82414zeI",120000);
	}

	
	@Test
	public void getTxnsBySearchWithFilter() {
		GetMerchantViewSearchWithFilterRequest request = new GetMerchantViewSearchWithFilterRequest() ;
		request.setMerchantId("K6971Hb9mY94Ml");
		request.setLimit(5);
		MerchantViewSearch criteria = new MerchantViewSearch();
		/*criteria.setCustomerId("13$#");
		criteria.setMerchantTag("@abhise");
		criteria.setMerchantTxnId("2343245354");
		criteria.setOrderId("#212354");
		criteria.setProductId("23134254");
		criteria.setSettlementId("233245465");
		criteria.setStoreId("#2345465");*/
		criteria.setTransactionId("K6971Hb9mY94Ml_160424373220355_5");
		request.setSearchCriteria(criteria);
		
		/*MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionStatus> txnStatusList = new LinkedList<MVTransactionStatus>();
		txnStatusList.add(MVTransactionStatus.SETTLED);
		List<MVTransactionType> txnType = new LinkedList<MVTransactionType>() ;
		txnType.add(MVTransactionType.DEBIT) ;
		filter.setTxnStatusList(txnStatusList);
		filter.setStartDate(new Date());
		filter.setEndDate(new Date());
		filter.setFromAmount(new BigDecimal(100));
		filter.setToAmount(new BigDecimal(100));
		filter.setMerchantTag("@Abhissehgkj");
		filter.setTxnTypeList(txnType);
		
		//request.setFilters(filter);
		request.setPage(1);
		request.setOrderby(1);
		
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setClientName("Fd1qV0jLp0lCCS");
		clientConfig.setClientKey("49c9d21c-ed41-4e73-88ed-ebfc1c102304");
		clientConfig.setAppRequestId(UUID.randomUUID().toString());
		clientConfig.setApiTimeOut(5000);
		request.setClientConfig(clientConfig);
		request.setUserAgent("22uuyty3278t83");
		request.setUserMachineIdentifier("q2yu21tu21huydsbajyg326327");
		//request.setClientConfig(clientConfig);
		request.setFilters(filter);*/
		/*request.setToken("B8Ot0xIdkamHpickVj9dUEhBtWC3yMlmK7-pV1sNfGay2emr3INnaUAoJiweoOiJ-ALirQHcKGB8JGt92iDwpRIDRNcXy0VDZ3rA0bd3R37EtZEftACZhHKlH6CR2ul7");
		request.setPage(5);
		request.setOrderby(1);*/
		
		//request.setToken("djEjY2hpdHJhaW50IzIwMTYtMDItMTAgMTI6MzA6Mzk=");
		try{
			GetTransactionsResponse response =   mvClient.getMerchantViewSearchWithFilter(request);
			System.out.println(response);
			log.info(response.toString());
		}catch(PaymentsViewGenericException e){
			System.out.println("error code: "+e.getErrCode());
			System.out.println("error msg: "+e.getErrMsg());
		}
		
	}
	
	//@Test(expected=PaymentsViewServiceException.class)
	public void getTotalRefundedAmountForTxn()
			 {
		GetTotalRefundedAmountForTxnRequest request = new GetTotalRefundedAmountForTxnRequest() ;
		request.setMerchantId("kX9WzdLXiCxacb");
		request.setFcTxnId("100000000021114");
		request.setFcTxnRefType("OPS_WALLET_DEBIT");
		request.setOrderId("30001605000064");
			GetTotalRefundedAmountForTxnResponse response =   mvClient.getTotalRefundedAmountForTxn(request);
			System.out.println("Total refunded amount is: "+response.getTotalRefundedAmount());
			log.info(response.toString());
		
	}
	
	/**
	 * 
	 */
//	@Test
	public void getTotalUnsettledAmountForMerchant()
			 {
		GetMerchantUnsettledAmount request = new GetMerchantUnsettledAmount() ;
		request.setMerchantId("VdmdCD2FTKejaQ");
		//request.setFcTxnId("100000000021114");
		//request.setFcTxnRefType("OPS_WALLET_DEBIT");
		//request.setOrderId("30001605000064");
		GetTotalUnsettledAmountRespnse response =   mvClient.getTotalUnsettledAmount(request);
		System.out.println("Total refunded amount is: "+response.getTotalUnsettledAmount());
		log.info(response.toString());
		
	}
	@Test
	public void getMerchantSettledTransactions()
			 {
		GetMerchantSettledTransactionsRequest request = new GetMerchantSettledTransactionsRequest() ;
		//100 days before
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -365);
		Date pastDate = new Date(System.currentTimeMillis()-7*24*60*60*1000);
		request.setStartDate(cal.getTime());
		request.setMerchantId("sWrcbTRfArSrzD");
		request.setEndDate(new Date());
		try{
			GetMerchantSettledTransactionsResponse response =   mvClient.getMerchantSettledTransactions(request);
			System.out.println("response "+response.getSettledMerchantTxnsList());
			log.info(response.toString());
		}catch(PaymentsViewServiceException e){
			
		}
		
		
		
	}
	/*//@Test
	public void getMerchantTxnsWithFilter()
			 {
		GetMerchantTxnsWithFilterRequest request = new GetMerchantTxnsWithFilterRequest() ;
		//request.setEndDate(new da);
		//request.setMerchantId("123245556");
		request.setMerchantId("VdmdCD2FTKejaQ");
		List<MVTransactionType> txnTypeList = new LinkedList<MVTransactionType>();
		txnTypeList.add(MVTransactionType.PAYMENT) ;
		request.setTxnTypeList(txnTypeList);
		//request.setFcTxnId("100000000021114");
		//request.setFcTxnRefType("OPS_WALLET_DEBIT");
		//request.setOrderId("30001605000064");
		GetMerchantTransactionsHistoryResponse response =   mvClient.getMerchantTxnsWithFilter(request);
		System.out.println("response "+response.getMerchantTxnsHistoryList());
		log.info(response.toString());
		
	}*/
	//@Test
	public void getMerchantTxnHistory()
			 {
		GetMerchantTransactionsHistoryRequest request = new GetMerchantTransactionsHistoryRequest() ;
		//request.setMerchantId("l2Paj4COuVZbgU");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/*try {
			request.setEndDate(sdf.parse("2016-04-12"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*try {
			request.setStartDate(sdf.parse("2015-04-12"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		request.setLimit(10);
		request.setLastEvaluatedkey(1451983242000L);
		//request.setPrevious(true);
		GetMerchantTransactionsHistoryResponse response =   mvClient.getMerchantTransactionsHistory(request);
		System.out.println("response "+response);
		log.info(response.toString());
		
	}
	
	//@Test
	public void getMerchantTransactionsByOrderIdTest() {
		GetMerchantTxnsByOrderIdRequest request = new GetMerchantTxnsByOrderIdRequest();
		request.setMerchantId("uLn866WsnczHGg");
		request.setOrderId("1602290248361691595");
		GetMerchantTxnsWithMetaDataResponse response = mvClient.getMerchantTransactionsByOrderId(request);
		System.out.println(JsonUtils.serialize(response));
	}
	
	//@Test
	public void getMerchantTransactionsByTxnIdTest() {
		GetMerchantTxnsByTxnIdRequest request = new GetMerchantTxnsByTxnIdRequest();
		request.setMerchantId("uLn866WsnczHGg");
		request.setFcTxnRefId("uLn866WsnczHGg_1602290248361691595_1");
		GetMerchantTxnsWithMetaDataResponse response = mvClient.getMerchantTransactionsByTxnId(request);
		System.out.println(response);
	}
	//@Test
	public void getMerchantTxnStatusHistoryByTxnId(){
		GetMerchantTxnStatusHistoryByTxnIdRequest request = 
				new GetMerchantTxnStatusHistoryByTxnIdRequest();
		request.setMerchantId("VjAxIzlmYmJlODE2LTQ0MGUtNGM4Yy1iOTk5LTI1MDA4YjQwZDQyYg");
		request.setTxnRefId("3EkRbNXLBUEn84_Pnp1cL1lQY");
		request.setTxnRefType(TransactionType.ESCROW_PAYMENT);
		GetMerchantTxnStatusHistoryResponse response  = 
				mvClient.getMerchantTxnStatusHistoryByTxnId(request);
		System.out.println(JsonUtils.serialize(response));
	}
	
	@Test
	public void getMerchantTxnsSearchFilterWithMetaData(){
		
		GetMerchantTxnsSearchFilterWithMetaDataRequest request = new GetMerchantTxnsSearchFilterWithMetaDataRequest();
		request.setMerchantId("l2Paj4COuVZbgU");
		request.setLimit(2);
		MVFilterCriteria filters=new MVFilterCriteria();
		List<MVTransactionType> txnType = new LinkedList<MVTransactionType>() ;
		txnType.add(MVTransactionType.PAYMENT) ;
		filters.setTxnTypeList(txnType);
		
		List<MVTransactionStatus> txnSt = new LinkedList<MVTransactionStatus>() ;
		txnSt.add(MVTransactionStatus.SUCCESS);
		
		request.setFilters(filters);
		
		MVSearchCriteria sc = new MVSearchCriteria();
		//sc.setOrderId("30005307000187");
		request.setSearchCriteria(sc);
		
		GetMerchantTxnsSearchFilterWithMetaDataResponse res= mvClient.getMerchantTxnsSearchFilterWithMetaData(request);
		System.out.println(res);
	}


}