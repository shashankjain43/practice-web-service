package com.snapdeal.merchant.rest.service.impl;

import static org.mockito.Matchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.snapdeal.merchant.amazonS3Service.IAmazonS3Service;
import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dao.IDownloadHistoryDao;
import com.snapdeal.merchant.dto.MPSearch;
import com.snapdeal.merchant.dto.MPTransactionDTO;
import com.snapdeal.merchant.dto.MPViewFilters;
import com.snapdeal.merchant.enums.FileType;
import com.snapdeal.merchant.enums.MPTransactionStatus;
import com.snapdeal.merchant.enums.MPTransactionType;
import com.snapdeal.merchant.enums.ReportType;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.file.handler.IFileDecorator;
import com.snapdeal.merchant.persistence.IFileInfoManager;
import com.snapdeal.merchant.request.MerchantGetTransactionRequest;
import com.snapdeal.merchant.request.MerchantRefundAmountForTxnRequest;
import com.snapdeal.merchant.request.MerchantRefundAmountRequest;
import com.snapdeal.merchant.response.MerchantGetTransactionResponse;
import com.snapdeal.merchant.response.MerchantRefundAmountForTxnResponse;
import com.snapdeal.merchant.response.MerchantRefundAmountResponse;
import com.snapdeal.merchant.rest.http.util.AggregatorUtil;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.http.util.MVUtil;
import com.snapdeal.merchant.test.util.MVMapper;
import com.snapdeal.mob.client.IUserService;
import com.snapdeal.mob.enums.IntegrationMode;
import com.snapdeal.mob.request.GetUserMerchantRequest;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.mob.ui.response.MerchantDetails;
import com.snapdeal.payments.aggregator.api.IPaymentAggregatorService;
import com.snapdeal.payments.aggregator.exception.client.ServiceException;
import com.snapdeal.payments.aggregator.request.RefundRequest;
import com.snapdeal.payments.aggregator.response.RefundResponse;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetTotalRefundedAmountForTxnRequest;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewFilters;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewSearch;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalRefundedAmountForTxnResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetTransactionsResponse;
import com.snapdeal.payments.view.merchant.commons.service.IMerchantViewService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:*/spring/application-context.xml")
public class TransactionManagementServiceImplTest extends AbstractTestNGSpringContextTests {

	@InjectMocks
	private TransactionManagementServiceImpl txnService;

	@Spy
	private MVUtil mvUtil;

	@Spy
	private MOBUtil mobUtil;

	@Spy
	private AggregatorUtil aggUtil;

	@Mock
	private IMerchantViewService mvClient;

	@Mock
	private MpanelConfig config;

	@Mock
	private IAmazonS3Service s3Service;

	@Mock
	private IDownloadHistoryDao historyDao;

	@Mock
	Map<ReportType, Map<FileType, IFileDecorator>> fileHandler;

	@Mock
	private IFileInfoManager fileInfoManager;

	@Mock
	IFileDecorator<List<MPTransactionDTO>, Workbook> xlsTxnFileDecorator;

	@Mock
	private IPaymentAggregatorService aggregatorService;

	@Mock
	private IUserService userService;

	@BeforeClass
	public void beforeClass() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

	
	@Test
	public void getMerchantTxnsTest() throws MerchantException
	{
		MerchantGetTransactionRequest request = new MerchantGetTransactionRequest();
		request.setFilters(createMPViewFilters());
		request.setLimit(10);
		request.setLoggedLoginName("loggedLoginName");
		request.setLoggedUserId("loggedUserId");
		request.setMerchantId("merchantId");
		request.setOrderby(1);
		request.setPage(0);
		request.setSearchCriteria(createSearchCriteria());
		request.setToken("token");
		
		
		MerchantGetTransactionResponse expectedResponse = new MerchantGetTransactionResponse();
		
		MerchantGetTransactionResponse actualResponse = new MerchantGetTransactionResponse();
		
		MPTransactionDTO mpTransaction1 =  MVMapper.MVToMPTxnMapping(createMVTransactionDTO());
		MPTransactionDTO mpTransaction2 =  MVMapper.MVToMPTxnMapping(createMVTransactionDTO());
		
		List<MPTransactionDTO> mvTxnList =  new ArrayList<MPTransactionDTO>();
		mvTxnList.add(mpTransaction1);
		mvTxnList.add(mpTransaction2);
		expectedResponse.setMpTransactions(mvTxnList);
		
		
		 GetTransactionsResponse  mvResponse =  new GetTransactionsResponse();
		 List<MVTransactionDTO> mvTransactionList = new ArrayList<MVTransactionDTO>();
		 mvTransactionList.add(createMVTransactionDTO());
		 mvTransactionList.add(createMVTransactionDTO());
		 mvResponse.setMvTransactions(mvTransactionList);
		 
		 mvResponse.setMvTransactions(mvTransactionList);
		 
		 GetMerchantViewSearchWithFilterRequest mvRequest = new GetMerchantViewSearchWithFilterRequest();

		mvRequest.setMerchantId(request.getMerchantId());

		mvRequest.setToken(request.getToken());
			
		MerchantViewFilters mvFilter = (MerchantViewFilters) MVMapper.MPToMVFilterMapping(request.getFilters());

		mvRequest.setFilters(mvFilter);
			
		MerchantViewSearch mvSearch = (MerchantViewSearch) MVMapper.MPToMVSearchMapping(request.getSearchCriteria());

		mvRequest.setSearchCriteria(mvSearch);
				
		mvRequest.setFilters(mvFilter);
		mvRequest.setLimit(request.getLimit());
		mvRequest.setPage(request.getPage());
		mvRequest.setOrderby(request.getOrderby());
			
		 mvUtil.setMvClient(mvClient);
		Mockito.when(mvClient.getMerchantViewSearchWithFilter(Matchers.eq(mvRequest))).thenReturn(mvResponse);
		
		actualResponse =  txnService.getMerchantTxns(request);
		
		
	}
	
/*	@Test
	public void exportTxnTest() throws MerchantException {
		MerchantExportTxnResponse expectedResponse = new MerchantExportTxnResponse();
		expectedResponse.setMessage(ErrorConstants.SUCCESS_MSG_FOR_EXPORT_TXN);

		MerchantExportTxnResponse actualResponse = new MerchantExportTxnResponse();

		config.setTxnExportCount(10);
		config.setTempFilePath("tempFilePath/");
		config.setS3BucketName("s3BucketName");
		config.setS3Prefix("s3Prefix");

		MerchantGetTransactionRequest getTxnRequest = new MerchantGetTransactionRequest();

		GetTransactionsResponse getTxnResponse = new GetTransactionsResponse();

		List<MVTransactionDTO> mvTransactions = new ArrayList<MVTransactionDTO>();
		mvTransactions.add(createMVTransactionDTO());
		mvTransactions.add(createMVTransactionDTO());
		getTxnResponse.setMvTransactions(mvTransactions);

		mvUtil.setMvClient(mvClient);
		Mockito.when(mvClient.getMerchantViewSearchWithFilter((any(GetMerchantViewSearchWithFilterRequest.class))))
				.thenReturn(getTxnResponse);

		DownloadEntity entity = new DownloadEntity();
		FilterEntity fEntity = new FilterEntity();

		Map<FileType, IFileDecorator> txnFileMapping = new HashMap<FileType, IFileDecorator>();
		txnFileMapping.put(FileType.XLS, new XLSTxnFileDecorator());
		txnFileMapping.put(FileType.PDF, new PDFTxnFileDecorator());
		txnFileMapping.put(FileType.CSV, new CSVTxnFileDecorator());

		try {
			Mockito.when(fileInfoManager.createFileInfo(any(FilterEntity.class), any(DownloadEntity.class)))
					.thenReturn(1);

			Mockito.when(s3Service.pushFile(any(AmazonS3Client.class), any(UploadFileRequest.class)))
					.thenReturn(new UploadFileResponse());

			Mockito.when(fileHandler.get(ReportType.TXN)).thenReturn(txnFileMapping);

			Mockito.when(historyDao.updateDownloadInfoStatus(any(DownloadEntity.class))).thenReturn(1);

			Mockito.doNothing().when(xlsTxnFileDecorator).delete(any(String.class));

			Mockito.doNothing().when(xlsTxnFileDecorator).save(any(Workbook.class), any(String.class));

		} catch (PersistenceException e) {
			throw new MerchantException(e.getMessage());
		} catch (FileHandlingException e) {
			throw new MerchantException(e.toString());
		} catch (S3ServiceException e) {
			throw new MerchantException(e.toString());
		} catch (Exception e) {
			throw new MerchantException(e.toString());
		}

		MerchantGetTransactionRequest request = new MerchantGetTransactionRequest();
		request.setFilters(createMPViewFilters());
		request.setLimit(10);
		request.setLoggedLoginName("loggedLoginName");
		request.setLoggedUserId("loggedUserId");
		request.setMerchantId("merchantId");
		request.setOrderby(0);
		request.setPage(0);
		request.setSearchCriteria(createMPSearch());
		request.setToken("token");

		actualResponse = txnService.exportTxn(request, FileType.XLS, "userId");

		Assert.assertEquals(expectedResponse, actualResponse);

	}*/

	@Test
	public void getRefundAmountForTxnTest() throws MerchantException {
		MerchantRefundAmountForTxnRequest request = new MerchantRefundAmountForTxnRequest();

		request.setTxnRefType("txnRefType");
		request.setTxnRefId("txnRefId");
		request.setToken("token");
		request.setOrderId("orderId");
		request.setMerchantId("merchantId");

		MerchantRefundAmountForTxnResponse expectedResponse = new MerchantRefundAmountForTxnResponse();
		MerchantRefundAmountForTxnResponse actualResponse = new MerchantRefundAmountForTxnResponse();

		expectedResponse.setTotalRefundAmount(new BigDecimal(1000));

		GetTotalRefundedAmountForTxnRequest mvRequest = new GetTotalRefundedAmountForTxnRequest();
		mvRequest.setFcTxnId(request.getTxnRefId());
		mvRequest.setFcTxnRefType(request.getTxnRefType());
		mvRequest.setOrderId(request.getOrderId());
		mvRequest.setMerchantId(request.getMerchantId());
		mvRequest.setToken(request.getToken());

		GetTotalRefundedAmountForTxnResponse mvResponse = new GetTotalRefundedAmountForTxnResponse();
		mvResponse.setTotalRefundedAmount(new BigDecimal(1000));

		mvUtil.setMvClient(mvClient);
		Mockito.when(mvClient.getTotalRefundedAmountForTxn(Matchers.eq(mvRequest))).thenReturn(mvResponse);

		actualResponse = txnService.getRefundAmountForTxn(request);

		Assert.assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void refundMoneytest() throws MerchantException {

		config.setApiRetryCount(3);

		MerchantRefundAmountResponse expectedResponse = new MerchantRefundAmountResponse();
		expectedResponse.setRefundTxnId("refundTxnId");
		expectedResponse.setStatus(true);

		MerchantRefundAmountResponse actualResponse = new MerchantRefundAmountResponse();

		RefundResponse aggResponse = new RefundResponse();
		aggResponse.setAmount(new BigDecimal(100));
		aggResponse.setMerchantTxnId("orderId");
		aggResponse.setReferenceId("referenceId");
		aggResponse.setRefundTxnId("refundTxnId");
		aggResponse.setStatus("status");

		GetUserMerchantResponse getUserMerchantResponse = new GetUserMerchantResponse();
		MerchantDetails merchantDetails = new MerchantDetails();
		getUserMerchantResponse.setMerchantDetails(createMerchantDetails());

		try {
			Mockito.when(aggregatorService.refundPayment(any(RefundRequest.class))).thenReturn(aggResponse);

			Mockito.when(userService.getUserMerchant(any(GetUserMerchantRequest.class)))
					.thenReturn(getUserMerchantResponse);

		} catch (ServiceException e) {
			new MerchantException(e.getErrorMessage());
		} catch (com.snapdeal.mob.exception.ServiceException e) {
			new MerchantException(e.toString());
		}

		MerchantRefundAmountRequest request = new MerchantRefundAmountRequest();
		request.setAmount(new BigDecimal(100));
		request.setComments("test");
		request.setOrderId("orderId");
		request.setMerchantId("merchantId");
		request.setToken("token");
		
		aggUtil.setAggregatorService(aggregatorService);
		aggUtil.setConfig(config);
		mobUtil.setUserService(userService);
		actualResponse = txnService.refundMoney(request);

		Assert.assertEquals(expectedResponse, actualResponse);
	}

	private MerchantDetails createMerchantDetails() {
		MerchantDetails merchantDetails = new MerchantDetails();

		merchantDetails.setIntegrationMode(IntegrationMode.ONLINE.getIntegrationMode());
		merchantDetails.setAddress1("address1");
	
		return merchantDetails;
	}

	private MPSearch createSearchCriteria() {
		MPSearch mpSearch = new MPSearch();
		mpSearch.setCustomerId("customerId");
		mpSearch.setMerchantTxnId("merchantTxnId");
		mpSearch.setOrderId("orderId");
		mpSearch.setProductId("productId");
		mpSearch.setSettlementId("settlementId");
		mpSearch.setStoreId("storeId");
		mpSearch.setTerminalId("terminalId");
		mpSearch.setTransactionId("transactionId");

		return mpSearch;
	}

	public MPSearch createMPSearch() {
		MPSearch mpSearch = new MPSearch();
		mpSearch.setCustomerId("customerId");
		mpSearch.setMerchantTxnId("merchantTxnId");
		mpSearch.setOrderId("orderId");
		mpSearch.setProductId("productId");
		mpSearch.setSettlementId("settlementId");
		mpSearch.setStoreId("storeId");
		mpSearch.setTerminalId("terminalId");
		mpSearch.setTransactionId("transactionId");

		return mpSearch;
	}

	private MPViewFilters createMPViewFilters() {
		MPViewFilters mpViewFilters = new MPViewFilters();
		mpViewFilters.setEndDate(new Long("1450353255000"));
		mpViewFilters.setStartDate(new Long("1449748455000"));
		mpViewFilters.setFromAmount(new BigDecimal(1.86));
		mpViewFilters.setToAmount(new BigDecimal(2000.08));
		List<MPTransactionStatus> txnStatusList = new ArrayList<MPTransactionStatus>();
		List<MPTransactionType> txnTypeList = new ArrayList<MPTransactionType>();

		txnStatusList.add(MPTransactionStatus.SUCCESS);
		txnStatusList.add(MPTransactionStatus.FAILED);

		mpViewFilters.setTxnStatusList(txnStatusList);

		txnTypeList.add(MPTransactionType.DEBIT);
		txnTypeList.add(MPTransactionType.REFUND);
		mpViewFilters.setTxnTypeList(txnTypeList);

		return mpViewFilters;
	}

	private MVTransactionDTO createMVTransactionDTO() {
		MVTransactionDTO mvTxnDto = new MVTransactionDTO();

		mvTxnDto.setCustId("custId");
		mvTxnDto.setCustIP("custIP");
		mvTxnDto.setCustName("custName");
		mvTxnDto.setFcTxnId("fcTxnId");
		mvTxnDto.setLocation("location");
		mvTxnDto.setMerchantFee(new BigDecimal(1.234));
		mvTxnDto.setMerchantId("merchantId");
		mvTxnDto.setMerchantName("merchantName");
		mvTxnDto.setMerchantTxnId("merchantTxnId");
		mvTxnDto.setNetDeduction(new BigDecimal(2.89));
		mvTxnDto.setOrderId("orderId");
		mvTxnDto.setPayableAmount(new BigDecimal(1009.08));
		mvTxnDto.setProductId("productId");
		mvTxnDto.setServiceTax(new BigDecimal(5.3));
		mvTxnDto.setSettlementId("settlementId");
		mvTxnDto.setShippingCity("shippingCity");
		mvTxnDto.setStoreId("storeId");
		mvTxnDto.setStoreName("storeName");
		mvTxnDto.setSwachBharatCess(new BigDecimal(1.2));
		mvTxnDto.setTerminalId("terminalId");
		mvTxnDto.setTotalTxnAmount(new BigDecimal(100000.12));
		mvTxnDto.setTxnDate(new Date(new Long("1450353255000")));
		mvTxnDto.setTxnStatus(MVTransactionStatus.PENDING);
		mvTxnDto.setTxnType(MVTransactionType.DEBIT);
		String dispInfoJson = "{\"Name\":\"2Haw2V9aE0\",\"email\":\"bTNXKrplw4\",\"mobile\":\"t1jtM13axX\",\"userId\":\"VjAxIzI3YWFjM2E5LTVkZWMtNDY4Yi1hZTM4LTBiYjM4MTM2NTBlZg\"}";
		mvTxnDto.setDisplayInfo(dispInfoJson);
		

		return mvTxnDto;
	}

	/*
	 * @Test public void getTxnsOfMerchantBySearchTest() throws
	 * MerchantException { MerchantGetSearchTransactionRequest request = new
	 * MerchantGetSearchTransactionRequest(); request.setLimit(1);
	 * request.setMerchantId("merchantId"); request.setOrderby(0);
	 * request.setPage(1); request.setToken("token");
	 * request.setSearchCriteria(createSearchCriteria());
	 * 
	 * MerchantGetTransactionResponse expectedResponse = new
	 * MerchantGetTransactionResponse(); List<MPTransactionDTO>
	 * mpTransactionList = new ArrayList<MPTransactionDTO>();
	 * 
	 * mpTransactionList.add(MVMapper.MVToMPTxnMapping(createMVTransactionDTO())
	 * );
	 * mpTransactionList.add(MVMapper.MVToMPTxnMapping(createMVTransactionDTO())
	 * );
	 * 
	 * expectedResponse.setMpTransactions(mpTransactionList);
	 * 
	 * GetMerchantViewSearchRequest mvRequest = new
	 * GetMerchantViewSearchRequest(); GetTransactionsResponse mvResponse = new
	 * GetTransactionsResponse();
	 * 
	 * List<MVTransactionDTO> mvTransactionList = new
	 * ArrayList<MVTransactionDTO>();
	 * 
	 * mvTransactionList.add(createMVTransactionDTO());
	 * mvTransactionList.add(createMVTransactionDTO());
	 * 
	 * mvResponse.setMvTransactions(mvTransactionList);
	 * 
	 * // mvUtil.setMvClient(mvClient);
	 * 
	 * Mockito.when(mvClient.getMerchantViewSearch(any(
	 * GetMerchantViewSearchRequest.class))) .thenReturn(mvResponse);
	 * 
	 * MerchantGetTransactionResponse actualResponse = null;
	 * 
	 * actualResponse = txnService.getTxnsOfMerchantBySearch(request);
	 * 
	 * 
	 * Assert.assertEquals(expectedResponse, actualResponse);
	 * 
	 * }
	 */

	/*
	 * @Test public void getTxnsOfMerchantByFilterTest() throws
	 * MerchantException { MerchantGetFilterTransactionRequest request = new
	 * MerchantGetFilterTransactionRequest(); request.setOrderby(1);
	 * request.setLimit(1); request.setPage(0);
	 * request.setMerchantId("merchantIdf"); request.setToken("tokenf");
	 * request.setFilters(createMPViewFilters());
	 * 
	 * MerchantGetTransactionResponse actualResponse ;
	 * 
	 * MerchantGetTransactionResponse expectedResponse = new
	 * MerchantGetTransactionResponse();
	 * 
	 * List<MPTransactionDTO> mpTransactionList = new
	 * ArrayList<MPTransactionDTO>();
	 * mpTransactionList.add(MVMapper.MVToMPTxnMapping(createMVTransactionDTO())
	 * );
	 * mpTransactionList.add(MVMapper.MVToMPTxnMapping(createMVTransactionDTO())
	 * ); expectedResponse.setMpTransactions(mpTransactionList);
	 * 
	 * 
	 * GetMerchantViewFilterRequest mvRequest = new
	 * GetMerchantViewFilterRequest();
	 * mvRequest.setFilters(MVMapper.MPToMVFilterMapping(createMPViewFilters()))
	 * ; mvRequest.setLimit(1); mvRequest.setMerchantId("merchantIdf");
	 * mvRequest.setOrderby(1); mvRequest.setPage(0);
	 * 
	 * 
	 * GetTransactionsResponse mvResponse = new GetTransactionsResponse();
	 * List<MVTransactionDTO> mvTransactionList = new
	 * ArrayList<MVTransactionDTO>();
	 * 
	 * mvTransactionList.add(createMVTransactionDTO());
	 * mvTransactionList.add(createMVTransactionDTO());
	 * 
	 * mvResponse.setMvTransactions(mvTransactionList);
	 * 
	 * mvUtil.setMvClient(mvClient);
	 * 
	 * Mockito.when(mvClient.getMerchantViewFilter(Matchers.eq(mvRequest))).
	 * thenReturn(mvResponse);
	 * 
	 * actualResponse = txnService.getTxnsOfMerchant(request);
	 * 
	 * Assert.assertEquals(expectedResponse, actualResponse);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @Test(expectedExceptions = MerchantException.class) public void
	 * getTxnsByFilterHTTPTransportFailTest() throws MerchantException { String
	 * mId= UUID.randomUUID().toString(); MerchantGetFilterTransactionRequest
	 * request = new MerchantGetFilterTransactionRequest();
	 * request.setOrderby(1); request.setLimit(1); request.setPage(0);
	 * request.setMerchantId(mId); request.setToken("tokenhf");
	 * request.setFilters(createMPViewFilters()); GetMerchantViewFilterRequest
	 * mvRequest = new GetMerchantViewFilterRequest(); mvRequest.setOrderby(1);
	 * mvRequest.setLimit(1); mvRequest.setPage(0);
	 * mvRequest.setMerchantId(mId);
	 * mvRequest.setFilters(MVMapper.MPToMVFilterMapping(createMPViewFilters()))
	 * ;
	 * 
	 * PaymentsViewServiceException e = new
	 * PaymentsViewServiceException("MVError", "PaymentsViewServiceException");
	 * HttpTransportException e = new HttpTransportException("httpCode",
	 * "Testing HttpTransportException from MerchantView");
	 * mvUtil.setMvClient(mvClient);
	 * Mockito.when(mvClient.getMerchantViewFilter(Matchers.eq(mvRequest))).
	 * thenThrow(e);
	 * 
	 * txnService.getTxnsOfMerchant(request);
	 * 
	 * 
	 * }
	 * 
	 * @Test(expectedExceptions = MerchantException.class) public void
	 * getTxnsByFilterMVServiceExceptionFailTest() throws MerchantException {
	 * String mId= UUID.randomUUID().toString();
	 * MerchantGetFilterTransactionRequest request = new
	 * MerchantGetFilterTransactionRequest(); request.setOrderby(1);
	 * request.setLimit(1); request.setPage(0); request.setMerchantId(mId);
	 * request.setToken("tokensf"); request.setFilters(createMPViewFilters());
	 * 
	 * GetMerchantViewFilterRequest mvRequest = new
	 * GetMerchantViewFilterRequest(); mvRequest.setOrderby(1);
	 * mvRequest.setLimit(1); mvRequest.setPage(0);
	 * mvRequest.setMerchantId(mId);
	 * mvRequest.setFilters(MVMapper.MPToMVFilterMapping(createMPViewFilters()))
	 * ;
	 * 
	 * PaymentsViewServiceException paymentsViewServiceException = new
	 * PaymentsViewServiceException("MVError", "PaymentsViewServiceException");
	 * mvUtil.setMvClient(mvClient);
	 * Mockito.when(mvClient.getMerchantViewFilter(Matchers.eq(mvRequest))).
	 * thenThrow(paymentsViewServiceException);
	 * 
	 * txnService.getTxnsOfMerchant(request);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * @Test(expectedExceptions = MerchantException.class) public void
	 * getTxnsBySearchHTTPTransportFailTest() throws MerchantException { String
	 * mId= UUID.randomUUID().toString(); MerchantGetSearchTransactionRequest
	 * request = new MerchantGetSearchTransactionRequest(); request.setLimit(1);
	 * request.setMerchantId(mId); request.setOrderby(0); request.setPage(0);
	 * request.setToken("token");
	 * 
	 * request.setSearchCriteria(createSearchCriteria());
	 * 
	 * GetMerchantViewSearchRequest mvRequest = new
	 * GetMerchantViewSearchRequest(); mvRequest.setLimit(1);
	 * mvRequest.setMerchantId(mId); mvRequest.setOrderby(0);
	 * mvRequest.setPage(0);
	 * mvRequest.setSearchCriteria(MVMapper.MPToMVSearchMapping(
	 * createSearchCriteria()));
	 * 
	 * HttpTransportException httpTransportException = new
	 * HttpTransportException("httpCode",
	 * "Testing HttpTransportException from MerchantView");
	 * mvUtil.setMvClient(mvClient);
	 * Mockito.when(mvClient.getMerchantViewSearch(Matchers.eq(mvRequest))).
	 * thenThrow(httpTransportException);
	 * 
	 * txnService.getTxnsOfMerchantBySearch(request);
	 * 
	 * 
	 * }
	 * 
	 * @Test(expectedExceptions = MerchantException.class) public void
	 * getTxnsBySearchMVServiceExceptionFailTest() throws MerchantException {
	 * String mId= UUID.randomUUID().toString();
	 * MerchantGetSearchTransactionRequest request = new
	 * MerchantGetSearchTransactionRequest(); request.setLimit(1);
	 * request.setMerchantId(mId); request.setOrderby(0); request.setPage(0);
	 * request.setToken("token");
	 * 
	 * request.setSearchCriteria(createSearchCriteria());
	 * 
	 * GetMerchantViewSearchRequest mvRequest = new
	 * GetMerchantViewSearchRequest(); mvRequest.setLimit(1);
	 * mvRequest.setMerchantId(mId); mvRequest.setOrderby(0);
	 * mvRequest.setPage(0);
	 * mvRequest.setSearchCriteria(MVMapper.MPToMVSearchMapping(
	 * createSearchCriteria()));
	 * 
	 * PaymentsViewServiceException paymentsViewServiceException = new
	 * PaymentsViewServiceException("MVError", "PaymentsViewServiceException");
	 * mvUtil.setMvClient(mvClient);
	 * Mockito.when(mvClient.getMerchantViewSearch(Matchers.eq(mvRequest))).
	 * thenThrow(paymentsViewServiceException);
	 * 
	 * txnService.getTxnsOfMerchantBySearch(request);
	 * 
	 * }
	 */

}
