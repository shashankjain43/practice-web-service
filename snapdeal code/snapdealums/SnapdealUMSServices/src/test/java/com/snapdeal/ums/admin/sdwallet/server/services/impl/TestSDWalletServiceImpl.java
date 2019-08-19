package com.snapdeal.ums.admin.sdwallet.server.services.impl;

import static org.mockito.Matchers.any;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.snapdeal.base.services.request.context.RequestContextSRO;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.RefundSDWalletAgainstOrderIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.RefundSDWalletAgainstOrderIdResponse;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletServiceInternal;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.impl.IMSService;
import com.snapdeal.ums.server.services.impl.IMSService.UserOwner;

public class TestSDWalletServiceImpl {

	@InjectMocks
	SDWalletServiceImpl testSDWalletServiceImpl;

	@Mock
	IMSService testIMSService;

	@Mock
	IUserServiceInternal testUserServiceInternal;

	@Mock
	ISDWalletServiceInternal testSDWalletServiceInternal;
	
	@Mock
	RequestContextSRO testRequestContextSRO;
	
	@Mock
	CreditSDWalletResponse testCreditSDWalletResponse;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreditSDWallet_InvalidUser() {
		CreditSDWalletRequest testRequest = new CreditSDWalletRequest();
		int testUserId = 3, testAmount = 3, testActivityTypeId = 3, testTransactionId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource";
		boolean expected = false;
		testRequest.setUserId(testUserId);
		testRequest.setAmount(testAmount);
		testRequest.setActivityTypeId(testActivityTypeId);
		testRequest.setTransactionId(testTransactionId);
		testRequest.setOrderCode(testOrderCode);
		testRequest.setSource(testSource);
		UserOwner testOwner = Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class)))
				.thenReturn(testOwner);
		Mockito.when(
				testUserServiceInternal.isUserExistsById(any(Integer.class)))
				.thenReturn(expected);
		String expectedMessage = "User does not exist";
		CreditSDWalletResponse testResponse = testSDWalletServiceImpl
				.creditSDWallet(testRequest);
		Assert.assertEquals(expected, testResponse.isSuccessful());
		Assert.assertEquals(expectedMessage, testResponse.getMessage());
	}

	@Test
	public void testCreditSDWallet_ValidUser_TxIdNull() {
		CreditSDWalletRequest testRequest = new CreditSDWalletRequest();
		int testUserId = 3, testAmount = 3, testActivityTypeId = 3, testTransactionId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource";
		boolean expected = true;
		testRequest.setUserId(testUserId);
		testRequest.setAmount(testAmount);
		testRequest.setActivityTypeId(testActivityTypeId);
		testRequest.setTransactionId(testTransactionId);
		testRequest.setOrderCode(testOrderCode);
		testRequest.setSource(testSource);
		UserOwner testOwner = Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class)))
				.thenReturn(testOwner);
		Mockito.when(
				testUserServiceInternal.isUserExistsById(any(Integer.class)))
				.thenReturn(expected);
		Mockito.when(
				testSDWalletServiceInternal.creditSDWallet(any(Integer.class),
						any(Integer.class), any(Integer.class),
						any(String.class), any(Integer.class),
						any(String.class), any(Integer.class),
						any(String.class), any(String.class),
						any(RequestContextSRO.class),
						any(CreditSDWalletResponse.class))).thenReturn(null);
		CreditSDWalletResponse testResponse = testSDWalletServiceImpl
				.creditSDWallet(testRequest);
		String expectedMessage="Credit of SDWallet is UNSUCCESSFUL for userId: "
				+ testUserId
				+ " amount: "
				+ testAmount
				+ " activityTypeId: "
				+ testActivityTypeId
				+ " orderCode: "
				+ testOrderCode
				+ " transactionId: "
				+ testTransactionId
				+ " source: "
				+ testSource;
		Assert.assertEquals(!expected, testResponse.isSuccessful());
		Assert.assertEquals(expectedMessage, testResponse.getMessage());
	}

	@Test
	public void testCreditSDWallet_ValidUser_TxIdNotNull() {
		CreditSDWalletRequest testRequest = new CreditSDWalletRequest();
		int testUserId = 3, testAmount = 3, testActivityTypeId = 3, testTransactionId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource";
		boolean expected = true;
		testRequest.setUserId(testUserId);
		testRequest.setAmount(testAmount);
		testRequest.setActivityTypeId(testActivityTypeId);
		testRequest.setTransactionId(testTransactionId);
		testRequest.setOrderCode(testOrderCode);
		testRequest.setSource(testSource);
		UserOwner testOwner = Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class)))
				.thenReturn(testOwner);
		Mockito.when(
				testUserServiceInternal.isUserExistsById(any(Integer.class)))
				.thenReturn(expected);
		Integer testTxnId=3;
		Mockito.when(
				testSDWalletServiceInternal.creditSDWallet(any(Integer.class),
						any(Integer.class), any(Integer.class),
						any(String.class), any(Integer.class),
						any(String.class), any(Integer.class),
						any(String.class), any(String.class),
						any(RequestContextSRO.class),
						any(CreditSDWalletResponse.class))).thenReturn(testTxnId);
		CreditSDWalletResponse testResponse = testSDWalletServiceImpl
				.creditSDWallet(testRequest);
		Assert.assertEquals(expected, testResponse.isSuccessful());
		Assert.assertEquals(testTxnId, testResponse.getTransactionId());
	}
	
	@Test
	public void testCreditSDWallet_InvalidRequest()
	{
		CreditSDWalletRequest testRequest = new CreditSDWalletRequest();
		boolean expected = false;
		String testMessage="Invalid request";
		CreditSDWalletResponse testResponse = testSDWalletServiceImpl
				.creditSDWallet(testRequest);
		Assert.assertEquals(expected, testResponse.isSuccessful());
		Assert.assertEquals(testMessage, testResponse.getMessage());
	}
	
	@Test
	public void testDebitSDWallet_InvalidRequest()
	{
		DebitSDWalletRequest testRequest = new DebitSDWalletRequest();
		boolean expected = false;
		String testMessage="Invalid request";
		DebitSDWalletResponse testResponse = testSDWalletServiceImpl
				.debitSDWallet(testRequest);
		Assert.assertEquals(expected, testResponse.isSuccessful());
		Assert.assertEquals(testMessage, testResponse.getMessage());
	}
	
	@Test
	public void testDebititSDWallet_InvalidUser()
	{
		DebitSDWalletRequest testRequest = new DebitSDWalletRequest();
		int testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource";
		boolean expected = false;
		testRequest.setUserId(testUserId);
		testRequest.setAmount(testAmount);
		testRequest.setActivityTypeId(testActivityTypeId);
		testRequest.setOrderCode(testOrderCode);
		testRequest.setSource(testSource);
		UserOwner testOwner = Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class)))
				.thenReturn(testOwner);
		Mockito.when(
				testUserServiceInternal.isUserExistsById(any(Integer.class)))
				.thenReturn(expected);
		String expectedMessage = "User does not exist";
		DebitSDWalletResponse testResponse = testSDWalletServiceImpl
				.debitSDWallet(testRequest);
		Assert.assertEquals(expected, testResponse.isSuccessful());
		Assert.assertEquals(expectedMessage, testResponse.getMessage());
	}
	
	@Test
	public void testDebitSDWallet_ValidUser_TxIdNull() {
		DebitSDWalletRequest testRequest = new DebitSDWalletRequest();
		int testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource";
		boolean expected = true;
		testRequest.setUserId(testUserId);
		testRequest.setAmount(testAmount);
		testRequest.setActivityTypeId(testActivityTypeId);
		testRequest.setOrderCode(testOrderCode);
		testRequest.setSource(testSource);
		UserOwner testOwner = Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class)))
				.thenReturn(testOwner);
		Mockito.when(
				testUserServiceInternal.isUserExistsById(any(Integer.class)))
				.thenReturn(expected);
		Mockito.when(
				testSDWalletServiceInternal.debitSDWallet(any(Integer.class),
						any(Integer.class), any(Integer.class),
						any(String.class), any(String.class),
						any(String.class), any(String.class),
						any(RequestContextSRO.class),
						any(DebitSDWalletResponse.class))).thenReturn(null);
		DebitSDWalletResponse testResponse = testSDWalletServiceImpl
				.debitSDWallet(testRequest);
		String expectedMessage="Insuffcient SDWalletCash to deduct";
		Assert.assertEquals(!expected, testResponse.isSuccessful());
		Assert.assertEquals(expectedMessage, testResponse.getMessage());
	}
	
	@Test
	public void testDebitSDWallet_ValidUser_TxIdNotNull() {
		DebitSDWalletRequest testRequest = new DebitSDWalletRequest();
		int testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource";
		boolean expected = true;
		testRequest.setUserId(testUserId);
		testRequest.setAmount(testAmount);
		testRequest.setActivityTypeId(testActivityTypeId);
		testRequest.setOrderCode(testOrderCode);
		testRequest.setSource(testSource);
		UserOwner testOwner = Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class)))
				.thenReturn(testOwner);
		Mockito.when(
				testUserServiceInternal.isUserExistsById(any(Integer.class)))
				.thenReturn(expected);
		Integer testTxnId=3;
		Mockito.when(
				testSDWalletServiceInternal.debitSDWallet(any(Integer.class),
						any(Integer.class), any(Integer.class),
						any(String.class), 
						any(String.class), 
						any(String.class), any(String.class),
						any(RequestContextSRO.class),
						any(DebitSDWalletResponse.class))).thenReturn(testTxnId);
		DebitSDWalletResponse testResponse = testSDWalletServiceImpl
				.debitSDWallet(testRequest);
		Assert.assertEquals(expected, testResponse.isSuccessful());
		Assert.assertEquals(testTxnId, testResponse.getTransactionId());
	}
	
	@Test
	public void testRefundSDWalletAgainstOrderId_InvalidRequest()
	{
		RefundSDWalletAgainstOrderIdRequest testRequest = new RefundSDWalletAgainstOrderIdRequest();
		boolean expected = false;
		String testMessage="Invalid request";
		RefundSDWalletAgainstOrderIdResponse testResponse = testSDWalletServiceImpl
				.refundSDWalletAgainstOrderId(testRequest);
		Assert.assertEquals(expected, testResponse.isSuccessful());
		Assert.assertEquals(testMessage, testResponse.getMessage());
	}
	
	@Test
	public void testRefundSDWalletAgainstOrderId_InvalidUser()
	{
		RefundSDWalletAgainstOrderIdRequest testRequest = new RefundSDWalletAgainstOrderIdRequest();
		int testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource";
		boolean expected = false;
		testRequest.setUserId(testUserId);
		testRequest.setAmount(testAmount);
		testRequest.setActivityTypeId(testActivityTypeId);
		testRequest.setOrderCode(testOrderCode);
		testRequest.setSource(testSource);
		UserOwner testOwner = Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class)))
				.thenReturn(testOwner);
		Mockito.when(
				testUserServiceInternal.isUserExistsById(any(Integer.class)))
				.thenReturn(expected);
		String expectedMessage = "User does not exist";
		RefundSDWalletAgainstOrderIdResponse testResponse = testSDWalletServiceImpl
				.refundSDWalletAgainstOrderId(testRequest);
		Assert.assertEquals(expected, testResponse.isSuccessful());
		Assert.assertEquals(expectedMessage, testResponse.getMessage());
	}
	
	@Test
	public void testRefundSDWalletAgainstOrderId_ValidUser_TxIdNull() {
		RefundSDWalletAgainstOrderIdRequest testRequest = new RefundSDWalletAgainstOrderIdRequest();
		int testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource";
		boolean expected = true;
		testRequest.setUserId(testUserId);
		testRequest.setAmount(testAmount);
		testRequest.setActivityTypeId(testActivityTypeId);
		testRequest.setOrderCode(testOrderCode);
		testRequest.setSource(testSource);
		UserOwner testOwner = Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class)))
				.thenReturn(testOwner);
		Mockito.when(
				testUserServiceInternal.isUserExistsById(any(Integer.class)))
				.thenReturn(expected);
		Mockito.when(
				testSDWalletServiceInternal.refundSDWalletAgainstOrderId(any(Integer.class),
						any(Integer.class), any(Integer.class),
						any(String.class), any(String.class),
						any(String.class), any(String.class),
						any(RequestContextSRO.class),
						any(RefundSDWalletAgainstOrderIdResponse.class))).thenReturn(null);
		RefundSDWalletAgainstOrderIdResponse testResponse = testSDWalletServiceImpl
				.refundSDWalletAgainstOrderId(testRequest);
		String expectedMessage="No orderCode data found against this refund";
		Assert.assertEquals(!expected, testResponse.isSuccessful());
		Assert.assertEquals(expectedMessage, testResponse.getMessage());
	}
	
	@Test
	public void testRefundSDWalletAgainstOrderId_ValidUser_TxIdNotNull() {
		RefundSDWalletAgainstOrderIdRequest testRequest = new RefundSDWalletAgainstOrderIdRequest();
		int testUserId = 3, testAmount = 3, testActivityTypeId = 3;
		String testOrderCode = "testOrderCode", testSource = "testSource";
		boolean expected = true;
		testRequest.setUserId(testUserId);
		testRequest.setAmount(testAmount);
		testRequest.setActivityTypeId(testActivityTypeId);
		testRequest.setOrderCode(testOrderCode);
		testRequest.setSource(testSource);
		UserOwner testOwner = Enum.valueOf(UserOwner.class, "UMS");
		Mockito.when(testIMSService.getUserOwnerByUserId(any(Integer.class)))
				.thenReturn(testOwner);
		Mockito.when(
				testUserServiceInternal.isUserExistsById(any(Integer.class)))
				.thenReturn(expected);
		Integer testRefundId=3;
		Mockito.when(
				testSDWalletServiceInternal.refundSDWalletAgainstOrderId(any(Integer.class),
						any(Integer.class), any(Integer.class),
						any(String.class), 
						any(String.class), 
						any(String.class), any(String.class),
						any(RequestContextSRO.class),
						any(RefundSDWalletAgainstOrderIdResponse.class))).thenReturn(testRefundId);
		RefundSDWalletAgainstOrderIdResponse testResponse = testSDWalletServiceImpl
				.refundSDWalletAgainstOrderId(testRequest);
		Assert.assertEquals(expected, testResponse.isSuccessful());
		Assert.assertEquals(testRefundId, testResponse.getRefundSDWalletAgainstOrderId());
	}
}
