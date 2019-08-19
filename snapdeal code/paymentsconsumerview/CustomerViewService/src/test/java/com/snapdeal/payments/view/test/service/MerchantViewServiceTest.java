package com.snapdeal.payments.view.test.service;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;

import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.dao.IPersistanceManager;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchRequest;
import com.snapdeal.payments.view.service.impl.MerchantViewServiceImpl;
import com.snapdeal.payments.view.utils.PaymentsViewShardUtil;
import com.snapdeal.payments.view.utils.validator.GenericValidator;

@ContextConfiguration(locations = { "classpath:*///spring/application-context.xml" })
public class MerchantViewServiceTest {

	@InjectMocks
	private MerchantViewServiceImpl merchantViewServiceImpl;

	@Mock
	private IPersistanceManager persistManager;
	
	@Spy
	GenericValidator<AbstractRequest> validator;
	
	@Mock
	private PaymentsViewShardUtil paymentsViewUtil;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		List<MVTransactionDTO> txnsList = new ArrayList<MVTransactionDTO>();

		Mockito.when(
				persistManager
						.getMerchantViewFilter(any(GetMerchantViewFilterRequest.class)))
				.thenReturn(txnsList);
		
		Mockito.when(
				persistManager
						.getMerchantViewSearch(any(GetMerchantViewSearchRequest.class)))
				.thenReturn(txnsList);
	}

	/*@Test
	public void testGetMerchantViewWithNoFilters() {
		
		 PaymentsViewShardContextHolder.setShardKey("DB1");
		GetMerchantViewFilterRequest req = new GetMerchantViewFilterRequest();
		req.setMerchantId("test_merchant_id");
		GetTransactionsResponse response= merchantViewServiceImpl
				.getMerchantViewFilter(req);
		Assert.assertNotNull("Expected a proper resonse from getMerchantViewFilter.", response);
		Assert.assertNotNull("Expected a not null list of txns.", response.getMvTransactions());
	}*/
	
	/*@Test
	public void testGetMerchantViewWithEmptyMerchantId() {
		 PaymentsViewShardContextHolder.setShardKey("DB1");

		
		Mockito.doThrow(new ValidationException(PaymentsViewDefaultExceptionCodes.VALIDATION.errCode(),""))
			.when(validator).validate(any(GetMerchantViewFilterRequest.class));

		GetMerchantViewFilterRequest req = new GetMerchantViewFilterRequest();
		GetTransactionsResponse response=null;
		try{
			response= merchantViewServiceImpl
					.getMerchantViewFilter(req);
		}catch(ValidationException vex){
			Assert.assertNotNull("Expected a validation exception.", vex);
		}
	}*/
	
	/*@Test
	public void testGetMerchantViewWithFiltersTxnType() {
		 PaymentsViewShardContextHolder.setShardKey("DB1");

		GetMerchantViewFilterRequest req = new GetMerchantViewFilterRequest();
		req.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionType> txnTypeList = new ArrayList<MVTransactionType>();
		txnTypeList.add(MVTransactionType.DEBIT);
		filter.setTxnTypeList(txnTypeList);
		req.setFilters(filter);
		GetTransactionsResponse response= merchantViewServiceImpl
				.getMerchantViewFilter(req);
		Assert.assertNotNull("Expected a proper resonse from getMerchantViewFilter.", response);
		Assert.assertNotNull("Expected a not null list of txns.", response.getMvTransactions());
	}*/
	
	/*@Test
	public void testGetMerchantViewSearch(){
		 PaymentsViewShardContextHolder.setShardKey("DB1");

		GetMerchantViewSearchRequest req = new GetMerchantViewSearchRequest();
		req.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setTransactionId("100000000002498");
		req.setSearchCriteria(criteria);
		GetTransactionsResponse response= merchantViewServiceImpl
				.getMerchantViewSearch(req);
		Assert.assertNotNull("Expected a proper resonse from getMerchantViewSearch.", response.getMvTransactions());
	}*/
	
	/*@Test
	public void testGetMerchantViewSearchWithNoMerchantId(){
		 PaymentsViewShardContextHolder.setShardKey("DB1");

		GetMerchantViewSearchRequest req = new GetMerchantViewSearchRequest();
		//req.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setTransactionId("100000000002498");
		req.setSearchCriteria(criteria);
		GetTransactionsResponse response;
		try{
			response= merchantViewServiceImpl
					.getMerchantViewSearch(req);
		}catch(ValidationException vex){
			Assert.assertNotNull("Expected a validation exception.", vex);
		}
	}*/
	
	/*@Test
	public void testGetMerchantViewSearchWithNullCriteria(){
		 PaymentsViewShardContextHolder.setShardKey("DB1");

		GetMerchantViewSearchRequest req = new GetMerchantViewSearchRequest();
		req.setMerchantId("test_merchant_id");
		//MerchantViewSearch criteria = new MerchantViewSearch();
		//criteria.setTransactionId("100000000002498");
		//req.setSearchCriteria(criteria);
		GetTransactionsResponse response;
		try{
			response= merchantViewServiceImpl
					.getMerchantViewSearch(req);
		}catch(PaymentsViewServiceException pex){
			Assert.assertNotNull("Expected a PaymentsViewService Exception.", pex);
		}
	}*/
	
	/*@Test
	public void testGetMerchantViewSearchWithNotNullButEmptyCriteriaMember(){
		
		 PaymentsViewShardContextHolder.setShardKey("DB1");

		GetMerchantViewSearchRequest req = new GetMerchantViewSearchRequest();
		req.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		//criteria.setTransactionId("100000000002498");
		req.setSearchCriteria(criteria);
		GetTransactionsResponse response;
		try{
			response= merchantViewServiceImpl
					.getMerchantViewSearch(req);
		}catch(PaymentsViewServiceException pex){
			Assert.assertNotNull("Expected a PaymentsViewService Exception.", pex);
		}
	}*/
	
}