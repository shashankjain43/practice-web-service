package com.snapdeal.ums.admin.sdwallet.server.services.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import com.snapdeal.base.services.request.context.RequestContextSRO;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.RefundSDWalletAgainstOrderIdResponse;
import com.snapdeal.ums.core.entity.SDWallet;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.core.entity.SDWalletHistory;
import com.snapdeal.ums.core.entity.SDWalletTxnHistory;
import com.snapdeal.ums.dao.user.sdwallet.ISDWalletDao;

public class TestSDWalletServiceInternalImpl {

	@InjectMocks
	SDWalletServiceInternalImpl testSDWalletServiceInternalImpl;

	@Mock
	ISDWalletDao testSDWalletDao;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreditSDWallet_Null() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3, testTransactionId = null, testExpiryDay = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = null;
		int testNewTransactionId = 3;
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);
		RequestContextSRO testRequestContextSRO = null;
		CreditSDWalletResponse testResponse = Mockito
				.mock(CreditSDWalletResponse.class);
		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(null);
		OngoingStubbing<Integer> txnHistoryId = Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);
		SDWallet testSDWallet = Mockito.mock(SDWallet.class);
		testSDWallet.setId(testUserId);
		SDWalletHistory testSDWalletHistory = Mockito
				.mock(SDWalletHistory.class);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(testSDWalletHistory);
		Mockito.when(testSDWalletDao.getSDWalletTransaction(any(Integer.class)))
				.thenReturn(null);
		int newTransactionId = testSDWalletServiceInternalImpl.creditSDWallet(
				testUserId, testAmount, testActivityTypeId, testOrderCode,
				testTransactionId, testSource, testExpiryDay, testRequestedBy,
				testIdempotentId, testRequestContextSRO, testResponse);
		Assert.assertEquals(testNewTransactionId, newTransactionId);
		Assert.assertNotNull(txnHistoryId);
	}

	@Test
	public void testCreditSDWallet_NotNull() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3, testTransactionId = null, testExpiryDay = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = "testIdempotentId";
		int testNewTransactionId = 3;
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);
		RequestContextSRO testRequestContextSRO = Mockito
				.mock(RequestContextSRO.class);
		CreditSDWalletResponse testResponse = Mockito
				.mock(CreditSDWalletResponse.class);
		SDWalletTxnHistory testTxnHistory = Mockito
				.mock(SDWalletTxnHistory.class);
		testTxnHistory.setSdWalletTxnId(testTransactionId);
		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(
				testTxnHistory);
		OngoingStubbing<Integer> txnHistoryId = Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);
		SDWallet testSDWallet = Mockito.mock(SDWallet.class);
		testSDWallet.setId(testUserId);
		SDWalletHistory testSDWalletHistory = Mockito
				.mock(SDWalletHistory.class);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(testSDWalletHistory);
		Mockito.when(testSDWalletDao.getSDWalletTransaction(any(Integer.class)))
				.thenReturn(null);
		Integer newTransactionId = testSDWalletServiceInternalImpl
				.creditSDWallet(testUserId, testAmount, testActivityTypeId,
						testOrderCode, testTransactionId, testSource,
						testExpiryDay, testRequestedBy, testIdempotentId,
						testRequestContextSRO, testResponse);
		Assert.assertEquals(null, newTransactionId);
	}

	@Test
	public void testCreditSDWallet_TxIdNotNull() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3, testTransactionId = 3, testExpiryDay = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = null;
		int testNewTransactionId = 3;
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);

		RequestContextSRO testRequestContextSRO = null;
		CreditSDWalletResponse testResponse = Mockito
				.mock(CreditSDWalletResponse.class);

		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(null);

		OngoingStubbing<Integer> txnHistoryId = Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);

		SDWallet testSDWallet = new SDWallet();
		testSDWallet.setId(testUserId);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(null);

		SDWalletHistory testSDWalletHistory = new SDWalletHistory();
		testSDWalletHistory.setExpiry(new Date());
		testSDWalletHistory.setAmount(testAmount);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(null);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);

		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(testSDWalletHistory);

		Mockito.when(testSDWalletDao.getSDWalletTransaction(any(Integer.class)))
				.thenReturn(null);

		List<SDWalletHistory> testSDWalletHistoryListOfTransactionId = new ArrayList<SDWalletHistory>();
		testSDWalletHistoryListOfTransactionId.add(testSDWalletHistory);

		Mockito.when(
				testSDWalletDao
						.getSDWalletHistoryForRefundByTransactionId(any(Integer.class)))
				.thenReturn(testSDWalletHistoryListOfTransactionId);

		Mockito.when(testSDWalletDao.getExpiryDaysCorrespondingToActivity(1))
				.thenReturn(testExpiryDay);

		int newTransactionId = testSDWalletServiceInternalImpl.creditSDWallet(
				testUserId, testAmount, testActivityTypeId, testOrderCode,
				testTransactionId, testSource, testExpiryDay, testRequestedBy,
				testIdempotentId, testRequestContextSRO, testResponse);
		Assert.assertEquals(testNewTransactionId, newTransactionId);
		Assert.assertNotNull(txnHistoryId);
	}

	@Test
	public void testCreditSDWallet_TxIdNotNull_Else() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3, testTransactionId = 3, testExpiryDay = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = null;
		int testNewTransactionId = 3;
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);

		RequestContextSRO testRequestContextSRO = null;
		CreditSDWalletResponse testResponse = Mockito
				.mock(CreditSDWalletResponse.class);

		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(null);

		OngoingStubbing<Integer> txnHistoryId = Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);

		SDWallet testSDWallet = new SDWallet();
		testSDWallet.setId(testUserId);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(null);

		SDWalletHistory testSDWalletHistory = new SDWalletHistory();
		Date testDate = new Date();
		testDate.setYear(5555);
		testSDWalletHistory.setExpiry(testDate);
		testSDWalletHistory.setAmount(-testAmount);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(null);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);

		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(testSDWalletHistory);

		Mockito.when(testSDWalletDao.getSDWalletTransaction(any(Integer.class)))
				.thenReturn(null);

		List<SDWalletHistory> testSDWalletHistoryListOfTransactionId = new ArrayList<SDWalletHistory>();
		testSDWalletHistoryListOfTransactionId.add(testSDWalletHistory);

		Mockito.when(
				testSDWalletDao
						.getSDWalletHistoryForRefundByTransactionId(any(Integer.class)))
				.thenReturn(testSDWalletHistoryListOfTransactionId);

		Mockito.when(testSDWalletDao.getExpiryDaysCorrespondingToActivity(1))
				.thenReturn(testExpiryDay);

		int newTransactionId = testSDWalletServiceInternalImpl.creditSDWallet(
				testUserId, testAmount, testActivityTypeId, testOrderCode,
				testTransactionId, testSource, testExpiryDay, testRequestedBy,
				testIdempotentId, testRequestContextSRO, testResponse);
		Assert.assertEquals(testNewTransactionId, newTransactionId);
		Assert.assertNotNull(txnHistoryId);
	}

	@Test
	public void testDebitSDWallet_ContextSRONull() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = null;
		int testNewTransactionId = 3;
		RequestContextSRO testRequestContextSRO = null;
		DebitSDWalletResponse testResponse = Mockito
				.mock(DebitSDWalletResponse.class);
		Mockito.when(
				testSDWalletDao
						.getTotalAvailableSDWalletBalanceByUserId(any(Integer.class)))
				.thenReturn(testAmount + 1);
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);
		Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);
		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(null);
		SDWallet testSDWallet = new SDWallet();
		Date testDate = new Date();
		testDate.setYear(5555);
		testSDWallet.setExpiry(testDate);
		testSDWallet.setAmount(testAmount);
		List<SDWallet> testSDWalletHistoryListOfTransactionId = new ArrayList<SDWallet>();
		testSDWalletHistoryListOfTransactionId.add(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.getAvailableSDWalletByUserId(any(Integer.class)))
				.thenReturn(testSDWalletHistoryListOfTransactionId);

		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(null);
		Mockito.when(testSDWalletDao.getSDWalletTransaction(any(Integer.class)))
				.thenReturn(null);

		int newTransactionId = testSDWalletServiceInternalImpl.debitSDWallet(
				testUserId, testAmount, testActivityTypeId, testOrderCode,
				testSource, testRequestedBy, testIdempotentId,
				testRequestContextSRO, testResponse);
		Assert.assertEquals(testNewTransactionId, newTransactionId);
	}

	@Test
	public void testDebitSDWallet_ContextSRONotNull() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = null;
		int testNewTransactionId = 3;
		RequestContextSRO testRequestContextSRO = new RequestContextSRO();
		DebitSDWalletResponse testResponse = Mockito
				.mock(DebitSDWalletResponse.class);
		Mockito.when(
				testSDWalletDao
						.getTotalAvailableSDWalletBalanceByUserId(any(Integer.class)))
				.thenReturn(testAmount + 1);
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);
		Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);
		SDWalletTxnHistory testSDWalletTxnHistory = new SDWalletTxnHistory();
		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(
				testSDWalletTxnHistory);
		SDWallet testSDWallet = new SDWallet();
		Date testDate = new Date();
		testDate.setYear(5555);
		testSDWallet.setExpiry(testDate);
		testSDWallet.setAmount(testAmount);
		List<SDWallet> testSDWalletHistoryListOfTransactionId = new ArrayList<SDWallet>();
		testSDWalletHistoryListOfTransactionId.add(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.getAvailableSDWalletByUserId(any(Integer.class)))
				.thenReturn(testSDWalletHistoryListOfTransactionId);

		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(null);
		Mockito.when(testSDWalletDao.getSDWalletTransaction(any(Integer.class)))
				.thenReturn(null);

		Integer newTransactionId = testSDWalletServiceInternalImpl
				.debitSDWallet(testUserId, testAmount, testActivityTypeId,
						testOrderCode, testSource, testRequestedBy,
						testIdempotentId, testRequestContextSRO, testResponse);
		Assert.assertEquals(null, newTransactionId);
	}

	@Test
	public void testDebitSDWallet_ContextSRONull_Else() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = null;
		int testNewTransactionId = 3;
		RequestContextSRO testRequestContextSRO = null;
		DebitSDWalletResponse testResponse = Mockito
				.mock(DebitSDWalletResponse.class);
		Mockito.when(
				testSDWalletDao
						.getTotalAvailableSDWalletBalanceByUserId(any(Integer.class)))
				.thenReturn(testAmount + 1);
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);
		Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);

		SDWallet testSDWallet = new SDWallet();
		Date testDate = new Date();
		testDate.setYear(5555);
		testSDWallet.setExpiry(testDate);
		testSDWallet.setAmount(testAmount - 1);
		List<SDWallet> testSDWalletHistoryListOfTransactionId = new ArrayList<SDWallet>();
		testSDWalletHistoryListOfTransactionId.add(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.getAvailableSDWalletByUserId(any(Integer.class)))
				.thenReturn(testSDWalletHistoryListOfTransactionId);
		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(null);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(null);
		Mockito.when(testSDWalletDao.getSDWalletTransaction(any(Integer.class)))
				.thenReturn(null);

		int newTransactionId = testSDWalletServiceInternalImpl.debitSDWallet(
				testUserId, testAmount, testActivityTypeId, testOrderCode,
				testSource, testRequestedBy, testIdempotentId,
				testRequestContextSRO, testResponse);
		Assert.assertEquals(testNewTransactionId, newTransactionId);
	}

	@Test
	public void testRefundSDWalletAgainstOrderId_ContextSRONull() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = null;
		int testNewTransactionId = 3;
		RequestContextSRO testRequestContextSRO = null;
		RefundSDWalletAgainstOrderIdResponse testResponse = Mockito
				.mock(RefundSDWalletAgainstOrderIdResponse.class);
		Mockito.when(
				testSDWalletDao
						.getTotalAvailableSDWalletBalanceByUserId(any(Integer.class)))
				.thenReturn(testAmount + 1);
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);
		Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);
		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(null);

		List<SDWalletHistory> testSDWalletHistoryListOfTransactionId = new ArrayList<SDWalletHistory>();

		Mockito.when(
				testSDWalletDao
						.getSDWalletHistoryForRefundByOrderId(any(String.class)))
				.thenReturn(testSDWalletHistoryListOfTransactionId);

		SDWallet testSDWallet = new SDWallet();
		testSDWallet.setId(testUserId);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(null);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);

		int newTransactionId = testSDWalletServiceInternalImpl
				.refundSDWalletAgainstOrderId(testUserId, testAmount,
						testActivityTypeId, testOrderCode, testSource,
						testRequestedBy, testIdempotentId,
						testRequestContextSRO, testResponse);
		Assert.assertEquals(testNewTransactionId, newTransactionId);
	}

	@Test
	public void testRefundSDWalletAgainstOrderId_ContextSRONotNull_Else() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = null;
		int testNewTransactionId = 3;
		RequestContextSRO testRequestContextSRO = null;
		RefundSDWalletAgainstOrderIdResponse testResponse = Mockito
				.mock(RefundSDWalletAgainstOrderIdResponse.class);
		Mockito.when(
				testSDWalletDao
						.getTotalAvailableSDWalletBalanceByUserId(any(Integer.class)))
				.thenReturn(testAmount + 1);
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);
		Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);

		SDWalletHistory testSDWalletHistory = new SDWalletHistory();
		testSDWalletHistory.setExpiry(new Date());
		testSDWalletHistory.setAmount(-testAmount);
		List<SDWalletHistory> testSDWalletHistoryListOfTransactionId = new ArrayList<SDWalletHistory>();
		testSDWalletHistoryListOfTransactionId.add(testSDWalletHistory);
		Mockito.when(
				testSDWalletDao
						.getSDWalletHistoryForRefundByOrderId(any(String.class)))
				.thenReturn(testSDWalletHistoryListOfTransactionId);
		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(null);
		SDWallet testSDWallet=new SDWallet();
		testSDWallet.setId(testUserId);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(null);
		Mockito.when(testSDWalletDao.getSDWalletTransaction(any(Integer.class)))
				.thenReturn(null);
		
		SDWalletActivityType testSDWalletActivityType=new SDWalletActivityType();
		testSDWalletActivityType.setId(testUserId);
		Mockito.when(testSDWalletDao.getSDWalletActivityTypeByCode(any(String.class))).thenReturn(testSDWalletActivityType);
		Mockito.when(testSDWalletDao.getExpiryDaysCorrespondingToActivity(any(Integer.class))).thenReturn(testAmount);

		int newTransactionId = testSDWalletServiceInternalImpl.refundSDWalletAgainstOrderId(
				testUserId, testAmount, testActivityTypeId, testOrderCode,
				testSource, testRequestedBy, testIdempotentId,
				testRequestContextSRO, testResponse);
		Assert.assertEquals(testNewTransactionId, newTransactionId);
	}
	
	@Test
	public void testRefundSDWalletAgainstOrderId_ContextSRONotNull() {
		Integer testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource", testRequestedBy = "testRequestedBy", testIdempotentId = null;
		int testNewTransactionId = 3;
		RequestContextSRO testRequestContextSRO = null;
		RefundSDWalletAgainstOrderIdResponse testResponse = Mockito
				.mock(RefundSDWalletAgainstOrderIdResponse.class);
		Mockito.when(
				testSDWalletDao
						.getTotalAvailableSDWalletBalanceByUserId(any(Integer.class)))
				.thenReturn(testAmount + 1);
		Mockito.when(testSDWalletDao.generateTransactionId(any(Integer.class)))
				.thenReturn(testNewTransactionId);
		Mockito.when(
				testSDWalletDao
						.addSDWalletTxnHistory(any(SDWalletTxnHistory.class)))
				.thenReturn(-1);

		SDWalletHistory testSDWalletHistory = new SDWalletHistory();
		testSDWalletHistory.setExpiry(new Date());
		testSDWalletHistory.setAmount(testAmount);
		List<SDWalletHistory> testSDWalletHistoryListOfTransactionId = new ArrayList<SDWalletHistory>();
		testSDWalletHistoryListOfTransactionId.add(testSDWalletHistory);
		Mockito.when(
				testSDWalletDao
						.getSDWalletHistoryForRefundByOrderId(any(String.class)))
				.thenReturn(testSDWalletHistoryListOfTransactionId);
		Mockito.when(
				testSDWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(
						any(String.class), any(String.class))).thenReturn(null);
		SDWallet testSDWallet=new SDWallet();
		testSDWallet.setId(testUserId);
		Mockito.when(testSDWalletDao.mergeSDWallet(any(SDWallet.class)))
				.thenReturn(testSDWallet);
		Mockito.when(
				testSDWalletDao
						.mergeSDWalletHistory(any(SDWalletHistory.class)))
				.thenReturn(null);
		Mockito.when(testSDWalletDao.getSDWalletTransaction(any(Integer.class)))
				.thenReturn(null);
		
		SDWalletActivityType testSDWalletActivityType=new SDWalletActivityType();
		testSDWalletActivityType.setId(testUserId);
		Mockito.when(testSDWalletDao.getSDWalletActivityTypeByCode(any(String.class))).thenReturn(testSDWalletActivityType);
		Mockito.when(testSDWalletDao.getExpiryDaysCorrespondingToActivity(any(Integer.class))).thenReturn(testAmount);

		int newTransactionId = testSDWalletServiceInternalImpl.refundSDWalletAgainstOrderId(
				testUserId, testAmount, testActivityTypeId, testOrderCode,
				testSource, testRequestedBy, testIdempotentId,
				testRequestContextSRO, testResponse);
		Assert.assertEquals(testNewTransactionId, newTransactionId);
	}

}
