package com.snapdeal.merchant.rest.service.impl;

import static org.mockito.Matchers.any;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.amazonaws.services.s3.AmazonS3Client;
import com.snapdeal.merchant.amazonS3Service.IAmazonS3Service;
import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dao.IBulkRefundDao;
import com.snapdeal.merchant.dao.IDownloadHistoryDao;
import com.snapdeal.merchant.dao.entity.DownloadEntity;
import com.snapdeal.merchant.dto.MerchantReportDetails;
import com.snapdeal.merchant.entity.DownloadInfo;
import com.snapdeal.merchant.enums.DownloadStatus;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.exception.S3ServiceException;
import com.snapdeal.merchant.request.DownloadFileRequest;
import com.snapdeal.merchant.request.MerchantGetDownloadInfoRequest;
import com.snapdeal.merchant.request.MerchantInvoiceDetailsRequest;
import com.snapdeal.merchant.request.MerchantSettlementReportRequest;
import com.snapdeal.merchant.response.MerchantGetDownloadInfoResponse;
import com.snapdeal.merchant.response.MerchantInvoiceDetailsResponse;
import com.snapdeal.merchant.response.MerchantSettlementReportResponse;
import com.snapdeal.merchant.rest.http.util.SRUtil;
import com.snapdeal.merchant.util.DateToTimeStampConverter;
import com.snapdeal.merchant.utils.AmazonS3FilePathUtil;
import com.snapdeal.payments.settlement.report.client.SettlementReportClient;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsResponse;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsResponse;
import com.snapdeal.payments.settlement.report.explorer.model.ReportDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:*/spring/application-context.xml")
public class DownloadServiceImplTest extends AbstractTestNGSpringContextTests {

	@InjectMocks
	private DownloadServiceImpl downloadService;

	@Mock
	private IDownloadHistoryDao dao;

	@Mock
	private IAmazonS3Service s3Service;

	@Mock
	private MpanelConfig config;

	@Mock
	private IBulkRefundDao bulkRefundDao;

	@Mock
	private AmazonS3Client s3Client;

	@Spy
	private SRUtil srUtil;

	@Spy
	private DateToTimeStampConverter dateToTSConverter;

	@Mock
	private SettlementReportClient srClient;

	@BeforeClass
	public void beforeClass() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterClass
	public void afterClass() {
	}

	@Test
	public void downloadFileTest() throws MerchantException, MalformedURLException, S3ServiceException {
		MerchantGetDownloadInfoRequest request = new MerchantGetDownloadInfoRequest();
		String actualResonse = null;
		String expectedResponse = "https://downloadFile";

		DownloadEntity entity = new DownloadEntity();
		entity.setId(1);
		entity.setUserId("userId");

		DownloadEntity record = createDownloadEntity();
		record.setStatus(DownloadStatus.SUCCESS);

		URL url = new URL("https://downloadFile");

		request.setId(entity.getId());
		request.setUserId(entity.getUserId());

		Mockito.when(dao.getUserDownloadInfo(Matchers.eq(entity))).thenReturn(record);

		DownloadFileRequest downloadParam = new DownloadFileRequest();

		downloadParam.setBucketName(config.getS3BucketName());
		downloadParam.setExpirationTime(config.getS3UrlExpiryTime());
		String objectkey = AmazonS3FilePathUtil.createAmazonS3FilePath(request.getUserId(), record.getFileName(),
				config.getS3Prefix());
		downloadParam.setObjectKey(objectkey);

		Mockito.when(s3Service.getFileStream(any(AmazonS3Client.class), Matchers.eq(downloadParam))).thenReturn(url);

		/*
		 * downloadService.get) downloadService.setS3Service(s3Service);
		 */

		actualResonse = downloadService.downloadFile(request);

		Assert.assertEquals(actualResonse, expectedResponse, "Invalid Response form downloadFile API");

	}

	@Test(expectedExceptions = MerchantException.class)
	public void downloadFileDownloadStatusFailTest() throws MerchantException {
		MerchantGetDownloadInfoRequest request = new MerchantGetDownloadInfoRequest();
		DownloadEntity entity = new DownloadEntity();
		entity.setId(1);
		entity.setUserId("userId");

		request.setId(entity.getId());
		request.setUserId(entity.getUserId());

		DownloadEntity record = createDownloadEntity();
		record.setStatus(DownloadStatus.FAILED);

		/* downloadService.setDao(dao); */
		Mockito.when(dao.getUserDownloadInfo(Matchers.eq(entity))).thenReturn(record);

		downloadService.downloadFile(request);

	}

	@Test(expectedExceptions = MerchantException.class)
	public void downloadFileNoRecordFailTest() throws MerchantException {
		MerchantGetDownloadInfoRequest request = new MerchantGetDownloadInfoRequest();
		DownloadEntity entity = new DownloadEntity();
		entity.setId(1);
		entity.setUserId("userId");

		request.setId(entity.getId());
		request.setUserId(entity.getUserId());

		/* downloadService.setDao(dao); */
		Mockito.when(dao.getUserDownloadInfo(Matchers.eq(entity))).thenReturn(null);

		downloadService.downloadFile(request);

	}

	@Test(expectedExceptions = MerchantException.class)
	public void downloadFileS3Exception() throws MerchantException, S3ServiceException {
		MerchantGetDownloadInfoRequest request = new MerchantGetDownloadInfoRequest();
		DownloadEntity entity = new DownloadEntity();
		entity.setId(1);
		entity.setUserId("userId");

		request.setId(entity.getId());
		request.setUserId(entity.getUserId());

		DownloadEntity record = createDownloadEntity();
		record.setStatus(DownloadStatus.SUCCESS);

		Mockito.when(dao.getUserDownloadInfo(Matchers.eq(entity))).thenReturn(record);

		DownloadFileRequest downloadParam = new DownloadFileRequest();

		downloadParam.setBucketName(config.getS3BucketName());
		downloadParam.setExpirationTime(config.getS3UrlExpiryTime());
		String objectkey = AmazonS3FilePathUtil.createAmazonS3FilePath(request.getUserId(), record.getFileName(),
				config.getS3Prefix());
		downloadParam.setObjectKey(objectkey);

		Mockito.when(s3Service.getFileStream(any(AmazonS3Client.class), Matchers.eq(downloadParam)))
				.thenThrow(new S3ServiceException("Amazon service Exception"));

		/*
		 * downloadService.setDao(dao); downloadService.setS3Service(s3Service);
		 */

		downloadService.downloadFile(request);

	}

	@Test(expectedExceptions = MerchantException.class)
	public void downloadFileExceptionTest() throws MerchantException, S3ServiceException {
		MerchantGetDownloadInfoRequest request = new MerchantGetDownloadInfoRequest();
		DownloadEntity entity = new DownloadEntity();
		entity.setId(1);
		entity.setUserId("userId");

		request.setId(entity.getId());
		request.setUserId(entity.getUserId());

		Exception e = new RuntimeException("Exception form DAO");

		Mockito.doThrow(e).when(dao).getUserDownloadInfo(Matchers.eq(entity));

		/* downloadService.setDao(dao); */

		downloadService.downloadFile(request);

	}

	@Test(expectedExceptions = MerchantException.class)
	public void downloadInfoExceptionFailTest() throws MerchantException {
		MerchantGetDownloadInfoRequest request = new MerchantGetDownloadInfoRequest();

		DownloadEntity entity = new DownloadEntity();

		entity.setUserId("userId");

		request.setUserId(entity.getUserId());

		Exception e = new RuntimeException("exception message");
		/* downloadService.setDao(dao); */

		Mockito.doThrow(e).when(dao).getUserDownloadInfoList(Matchers.eq(entity));

		downloadService.getDownloadInfo(request);
	}

	@Test
	public void getDownloadInfoTest() throws MerchantException {

		MerchantGetDownloadInfoRequest request = new MerchantGetDownloadInfoRequest();
		request.setUserId("userId");
		request.setMerchantId("merchantId");
		request.setToken("token");
		request.setId(1);

		MerchantGetDownloadInfoResponse actualResponse = null;

		// creating Expected Response
		MerchantGetDownloadInfoResponse expectedResponse = new MerchantGetDownloadInfoResponse();

		List<DownloadInfo> downloadInfoList = new ArrayList<DownloadInfo>();
		downloadInfoList.add(createDownloadInfo(createDownloadEntity()));
		downloadInfoList.add(createDownloadInfo(createDownloadEntity()));

		expectedResponse.setInfo(downloadInfoList);

		List<DownloadEntity> downloadEntityList = new ArrayList<DownloadEntity>();
		downloadEntityList.add(createDownloadEntity());
		downloadEntityList.add(createDownloadEntity());

		DownloadEntity entity = new DownloadEntity();

		entity.setUserId("userId");

		Mockito.when(dao.getUserDownloadInfoList(Matchers.eq(entity))).thenReturn(downloadEntityList);

		/* downloadService.setDao(dao); */

		actualResponse = downloadService.getDownloadInfo(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from getDownloadInfo API");
	}

	@Test
	public void testGetSettlementReport() throws MerchantException, MalformedURLException, ParseException {

		MerchantSettlementReportRequest request = new MerchantSettlementReportRequest();
		request.setMerchantId("merchantId123");
		request.setPageSize(10);
		request.setToken("token123");
		request.setStartTime(new Long("1456806780000"));
		request.setEndTime(new Long("1459339436000"));

		
		MerchantReportDetails merchantReportDetails = new MerchantReportDetails();
		merchantReportDetails.setEndTime("1459339436000");
		merchantReportDetails.setStartTime("1456806780000");
		URL url = new URL("http://downloadSettlementReport");
		merchantReportDetails.setFileDownloadUrl(url);
		merchantReportDetails.setReportName("reportName");
		
		List<MerchantReportDetails> merchantReportDetailsList = new ArrayList<MerchantReportDetails>();
		merchantReportDetailsList.add(merchantReportDetails);
		
		MerchantSettlementReportResponse actualResponse;
		MerchantSettlementReportResponse expectedResponse = new MerchantSettlementReportResponse();
		expectedResponse.setMerchantReportDetails(merchantReportDetailsList);

		GetMerchantSettlementReportDetailsRequest srRequest = new GetMerchantSettlementReportDetailsRequest();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String start = "2016-03-01 10:03:00.000";
		String end = "2016-03-30 17:33:56.000";
		Date startDate = formatter.parse(start);
		Date endDate =  formatter.parse(end);
		srRequest.setStartTime(startDate);
		srRequest.setEndTime(endDate);
		srRequest.setMerchantId("merchantId123");
		srRequest.setPageSize(10);

		ReportDetails reportDetail = new ReportDetails();
		reportDetail.setStartTime("2016-03-01 10:03:00.000");
		reportDetail.setEndTime("2016-03-30 17:33:56.000");
		URL urls = new URL("http://downloadSettlementReport");
		reportDetail.setFileDownloadUrl(urls);
		reportDetail.setReportName("reportName");
		
		List<ReportDetails> reportDetailsList = new ArrayList<ReportDetails>();
		reportDetailsList.add(reportDetail);
		
		
		GetMerchantSettlementReportDetailsResponse srResponse = new GetMerchantSettlementReportDetailsResponse();
		srResponse.setReportDetailsList(reportDetailsList);
		
		srUtil.setSrClient(srClient);
		Mockito.when(srClient.getMerchantSettlementReportDetails(srRequest)).thenReturn(srResponse);
		
		actualResponse = downloadService.getSettlementReport(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from getSettlementReport API");

		
		
	}
	
	@Test
	public void testGetMerchantInvoice() throws MerchantException, MalformedURLException, ParseException {

		MerchantInvoiceDetailsRequest request = new MerchantInvoiceDetailsRequest();
		request.setMerchantId("merchantId123");
		request.setPageSize(10);
		request.setToken("token123");
		request.setStartTime(new Long("1456806780000"));
		request.setEndTime(new Long("1459339436000"));

		
		MerchantReportDetails merchantInvoiceDetails = new MerchantReportDetails();
		merchantInvoiceDetails.setEndTime("1459339436000");
		merchantInvoiceDetails.setStartTime("1456806780000");
		URL url = new URL("http://downloadSettlementReport");
		merchantInvoiceDetails.setFileDownloadUrl(url);
		merchantInvoiceDetails.setReportName("reportName");
		
		List<MerchantReportDetails> merchantReportDetailsList = new ArrayList<MerchantReportDetails>();
		merchantReportDetailsList.add(merchantInvoiceDetails);
		
		MerchantInvoiceDetailsResponse actualResponse;
		MerchantInvoiceDetailsResponse expectedResponse = new MerchantInvoiceDetailsResponse();
		expectedResponse.setMerchantInvoiceDetails(merchantReportDetailsList);

		GetMerchantInvoiceDetailsRequest srRequest = new GetMerchantInvoiceDetailsRequest();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String start = "2016-03-01 10:03:00.000";
		String end = "2016-03-30 17:33:56.000";
		Date startDate = formatter.parse(start);
		Date endDate =  formatter.parse(end);
		srRequest.setStartTime(startDate);
		srRequest.setEndTime(endDate);
		srRequest.setMerchantId("merchantId123");
		srRequest.setPageSize(10);

		ReportDetails reportDetail = new ReportDetails();
		reportDetail.setStartTime("2016-03-01 10:03:00.000");
		reportDetail.setEndTime("2016-03-30 17:33:56.000");
		URL urls = new URL("http://downloadSettlementReport");
		reportDetail.setFileDownloadUrl(urls);
		reportDetail.setReportName("reportName");
		
		List<ReportDetails> reportDetailsList = new ArrayList<ReportDetails>();
		reportDetailsList.add(reportDetail);
		
		
		GetMerchantInvoiceDetailsResponse srResponse = new GetMerchantInvoiceDetailsResponse();
		srResponse.setReportDetailsList(reportDetailsList);
		
		srUtil.setSrClient(srClient);
		Mockito.when(srClient.getMerchantInvoiceDetails(srRequest)).thenReturn(srResponse);
		
		actualResponse = downloadService.getMerchantInvoice(request);

		Assert.assertEquals(actualResponse, expectedResponse, "Invalid Response from getInvoiceReport API");

		
		
	}

	public DownloadInfo createDownloadInfo(DownloadEntity record) {
		DownloadInfo info = new DownloadInfo();
		info.setId(record.getId());
		info.setStatus(record.getStatus());
		info.setViewed(record.isViewed());
		info.setFileName(record.getFileName());
		info.setTimestamp(record.getCreatedOn().getTime());

		return info;
	}

	public DownloadEntity createDownloadEntity() {
		DownloadEntity downloadEntity = new DownloadEntity();
		downloadEntity.setCreatedOn(new Date(new Long("1452148344000")));
		downloadEntity.setFileName("fileName");
		downloadEntity.setFilterId(1);
		downloadEntity.setObjectKey("objectKey");
		downloadEntity.setStatus(DownloadStatus.INPROGRESS);
		downloadEntity.setUserId("userId");
		downloadEntity.setViewed(false);
		downloadEntity.setId(1);

		return downloadEntity;
	}
}
