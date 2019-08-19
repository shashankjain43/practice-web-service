/*package com.snapdeal.payments.view.test.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.payments.merchantView.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.merchantView.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.merchantView.commons.enums.MVTransactionType;
import com.snapdeal.payments.merchantView.commons.request.GetMerchantViewFilterRequest;
import com.snapdeal.payments.merchantView.commons.request.GetMerchantViewSearchRequest;
import com.snapdeal.payments.merchantView.commons.request.MerchantViewFilters;
import com.snapdeal.payments.merchantView.commons.request.MerchantViewSearch;

@ContextConfiguration(locations = {"classpath:/spring/application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MerchantTransactionDetailsDaoTest {

	@Autowired
	private IMerchantTransactionDetailsDao dao;
	
	@Test
	public void testGetMerchantViewFilterBasic() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
	}
	
	@Test
	public void testGetMerchantViewFilterBasicLimit() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest();
		request.setLimit(2);
		request.setMerchantId("test_merchant_id");
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
	}
	
	@Test
	public void testGetMerchantViewFilterWithTxnTypeDebit() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionType> txnTypeList = new ArrayList<MVTransactionType>();
		txnTypeList.add(MVTransactionType.DEBIT);
		filter.setTxnTypeList(txnTypeList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnType().equals(MVTransactionType.DEBIT));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithTxnTypeRefund() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionType> txnTypeList = new ArrayList<MVTransactionType>();
		txnTypeList.add(MVTransactionType.REFUND);
		filter.setTxnTypeList(txnTypeList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnType().equals(MVTransactionType.REFUND));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithTxnTypeDebitAndRefund() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionType> txnTypeList = new ArrayList<MVTransactionType>();
		txnTypeList.add(MVTransactionType.DEBIT);
		txnTypeList.add(MVTransactionType.REFUND);
		filter.setTxnTypeList(txnTypeList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnType().equals(MVTransactionType.REFUND) ||
					txn.getTxnType().equals(MVTransactionType.DEBIT));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithTxnStatusPending() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionStatus> txnStatuseList = new ArrayList<MVTransactionStatus>();
		txnStatuseList.add(MVTransactionStatus.PENDING);
		filter.setTxnStatusList(txnStatuseList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnStatus().equals(MVTransactionStatus.PENDING));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithTxnStatusSucess() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionStatus> txnStatuseList = new ArrayList<MVTransactionStatus>();
		txnStatuseList.add(MVTransactionStatus.SUCCESS);
		filter.setTxnStatusList(txnStatuseList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnStatus().equals(MVTransactionStatus.SUCCESS));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithTxnStatusFailed() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionStatus> txnStatuseList = new ArrayList<MVTransactionStatus>();
		txnStatuseList.add(MVTransactionStatus.FAILED);
		filter.setTxnStatusList(txnStatuseList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnStatus().equals(MVTransactionStatus.FAILED));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithTxnStatusToBeSettled() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionStatus> txnStatuseList = new ArrayList<MVTransactionStatus>();
		txnStatuseList.add(MVTransactionStatus.TO_BE_SETTLLED);
		filter.setTxnStatusList(txnStatuseList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnStatus().equals(MVTransactionStatus.TO_BE_SETTLLED));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithTxnStatusSettled() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionStatus> txnStatuseList = new ArrayList<MVTransactionStatus>();
		txnStatuseList.add(MVTransactionStatus.SETTLED);
		filter.setTxnStatusList(txnStatuseList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnStatus().equals(MVTransactionStatus.SETTLED));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithTypeDebitNStatusSuccess() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionType> txnTypeList = new ArrayList<MVTransactionType>();
		txnTypeList.add(MVTransactionType.DEBIT);
		filter.setTxnTypeList(txnTypeList);
		
		List<MVTransactionStatus> txnStatuseList = new ArrayList<MVTransactionStatus>();
		txnStatuseList.add(MVTransactionStatus.SUCCESS);
		filter.setTxnStatusList(txnStatuseList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnType().equals(MVTransactionType.DEBIT)
					&& txn.getTxnStatus().equals(MVTransactionStatus.SUCCESS));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithTypeDebitNStatusSettled() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		List<MVTransactionType> txnTypeList = new ArrayList<MVTransactionType>();
		txnTypeList.add(MVTransactionType.DEBIT);
		filter.setTxnTypeList(txnTypeList);
		
		List<MVTransactionStatus> txnStatuseList = new ArrayList<MVTransactionStatus>();
		txnStatuseList.add(MVTransactionStatus.SETTLED);
		filter.setTxnStatusList(txnStatuseList);
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnType().equals(MVTransactionType.DEBIT)
					&& txn.getTxnStatus().equals(MVTransactionStatus.SETTLED));
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithAmount() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		filter.setFromAmount(new BigDecimal(5));
		filter.setToAmount(new BigDecimal(15));
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTotalTxnAmount().intValue()>= 5 && txn.getTotalTxnAmount().intValue() <= 15);
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithFromAmount() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		filter.setFromAmount(new BigDecimal(5));
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTotalTxnAmount().intValue()>= 5);
		}
	}
	
	@Test
	public void testGetMerchantViewFilterToAmount() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		filter.setToAmount(new BigDecimal(15));
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTotalTxnAmount().intValue() <= 15);
		}
	}
	
	@Test
	public void testGetMerchantViewFilterWithDateRange() {
		GetMerchantViewFilterRequest request = new GetMerchantViewFilterRequest() ;
		request.setMerchantId("test_merchant_id");
		
		MerchantViewFilters filter = new MerchantViewFilters();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		filter.setEndDate(cal.getTime());
		cal.add(Calendar.DATE, -2);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		filter.setStartDate(cal.getTime());
		request.setFilters(filter);
		
		List<MVTransactionDTO> txnList= dao.getMerchantViewFilter(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		Assert.assertTrue(txnList.size()<=request.getLimit());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTxnDate().after(request.getFilters().getStartDate())
					&& txn.getTxnDate().before(request.getFilters().getEndDate()));
		}
	}
	
	@Test
	public void testGetMerchantViewSearchBasic() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setTransactionId("test_fc_txn_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		
	}
	
	@Test
	public void testGetMerchantViewSearchTxnId() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setTransactionId("test_fc_txn_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getFcTxnId().equals("test_fc_txn_id"));
		}
	}
	
	@Test
	public void testGetMerchantViewSearchMcntTxnId() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setMerchantTxnId("test_mcnt_txn_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getMerchantTxnId().equals("test_mcnt_txn_id"));
		}
	}
	
	@Test
	public void testGetMerchantViewSearchSettlementId() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setSettlementId("test_settle_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getSettlementId().equals("test_settle_id"));
		}
	}
	
	@Test
	public void testGetMerchantViewSearchOrderId() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setOrderId("test_mcnt_txn_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getOrderId().equals("test_mcnt_txn_id"));
		}
	}
	
	@Test
	public void testGetMerchantViewSearchProductId() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setProductId("test_product_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getProductId().equals("test_product_id"));
		}
	}
	
	@Test
	public void testGetMerchantViewSearchTerminalId() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setTerminalId("test_terminal_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getTerminalId().equals("test_terminal_id"));
		}
	}
	
	@Test
	public void testGetMerchantViewSearchStoreId() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setStoreId("test_store_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getStoreId().equals("test_store_id"));
		}
	}
	
	@Test
	public void testGetMerchantViewSearchCustomerId() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setCustomerId("test_cust_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getCustId().equals("test_cust_id"));
		}
	}
	
	@Test
	public void testGetMerchantViewSearchTxnIdOrOrderId() {
		GetMerchantViewSearchRequest request = new GetMerchantViewSearchRequest() ;
		request.setMerchantId("test_merchant_id");
		MerchantViewSearch criteria = new MerchantViewSearch();
		criteria.setTransactionId("test_fc_txn_id");
		criteria.setOrderId("test_mcnt_txn_id");
		request.setSearchCriteria(criteria);
		List<MVTransactionDTO> txnList= dao.getMerchantViewSearch(request);
		Assert.assertTrue(txnList!=null && !txnList.isEmpty());
		for(MVTransactionDTO txn: txnList){
			Assert.assertTrue(txn.getFcTxnId().equals("test_fc_txn_id")
					|| txn.getOrderId().equals("test_mcnt_txn_id"));
		}
	}

}
*/