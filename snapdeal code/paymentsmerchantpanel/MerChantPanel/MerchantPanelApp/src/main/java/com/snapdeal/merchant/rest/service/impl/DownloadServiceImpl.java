
package com.snapdeal.merchant.rest.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.snapdeal.merchant.amazonS3Service.IAmazonS3Service;
import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dao.IBulkRefundDao;
import com.snapdeal.merchant.dao.IDownloadHistoryDao;
import com.snapdeal.merchant.dao.entity.BulkRefundEntity;
import com.snapdeal.merchant.dao.entity.DownloadEntity;
import com.snapdeal.merchant.dto.MerchantReportDetails;
import com.snapdeal.merchant.entity.BulkDownloadInfo;
import com.snapdeal.merchant.entity.DownloadInfo;
import com.snapdeal.merchant.enums.DownloadStatus;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.DownloadFileRequest;
import com.snapdeal.merchant.request.MerchantBulkRefundDownloadFileRequest;
import com.snapdeal.merchant.request.MerchantBulkRefundDownloadInfoRequest;
import com.snapdeal.merchant.request.MerchantGetDownloadInfoRequest;
import com.snapdeal.merchant.request.MerchantInvoiceDetailsRequest;
import com.snapdeal.merchant.request.MerchantSettlementReportRequest;
import com.snapdeal.merchant.response.MerchantBulkDownloadInfoResponse;
import com.snapdeal.merchant.response.MerchantGetDownloadInfoResponse;
import com.snapdeal.merchant.response.MerchantInvoiceDetailsResponse;
import com.snapdeal.merchant.response.MerchantSettlementReportResponse;
import com.snapdeal.merchant.rest.http.util.SRUtil;
import com.snapdeal.merchant.rest.service.IDownloadService;
import com.snapdeal.merchant.util.DateToTimeStampConverter;
import com.snapdeal.merchant.utils.AmazonS3FilePathUtil;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsResponse;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsResponse;
import com.snapdeal.payments.settlement.report.explorer.model.ReportDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DownloadServiceImpl implements IDownloadService {

	@Autowired
	private IDownloadHistoryDao dao;

	@Autowired
	private IBulkRefundDao bulkRefundDao;

	@Autowired
	private IAmazonS3Service s3Service;

	@Autowired
	private MpanelConfig config;

	@Autowired
	private AmazonS3Client s3Client;

	@Autowired
	private SRUtil srUtil;

	@Autowired
	private DateToTimeStampConverter dateToTSConverter;

	@Logged
	@Override
	public MerchantGetDownloadInfoResponse getDownloadInfo(MerchantGetDownloadInfoRequest request)
			throws MerchantException {
		DownloadEntity entity = new DownloadEntity();
		// entity.setId(request.getId());
		entity.setUserId(request.getUserId());

		MerchantGetDownloadInfoResponse response = null;

		try {
			List<DownloadEntity> records = dao.getUserDownloadInfoList(entity);
			response = new MerchantGetDownloadInfoResponse();
			List<DownloadInfo> infoList = new ArrayList<DownloadInfo>();
			response.setInfo(infoList);
			for (DownloadEntity record : records) {
				DownloadInfo info = new DownloadInfo();
				info.setId(record.getId());
				info.setStatus(record.getStatus());
				info.setViewed(record.isViewed());
				info.setFileName(record.getFileName());
				info.setTimestamp(record.getCreatedOn().getTime());
				infoList.add(info);
			}

			return response;
		} catch (Exception e) {
			log.error("Error in download info {} {}", e.getMessage(), e);
			throw new MerchantException("Could not retrieve info");
		}

	}

	@Logged
	@Override
	public String downloadFile(MerchantGetDownloadInfoRequest request) throws MerchantException {
		DownloadEntity entity = new DownloadEntity();
		entity.setId(request.getId());
		entity.setUserId(request.getUserId());

		try {
			DownloadEntity record = dao.getUserDownloadInfo(entity);
			if (record == null) {
				log.error(ErrorConstants.INVALID_DOWNLOAD_MSG);
				throw new MerchantException(ErrorConstants.INVALID_DOWNLOAD_MSG);
			}

			if (!DownloadStatus.SUCCESS.equals(record.getStatus()) || record.getFileName() == null) {
				log.error(ErrorConstants.INVALID_FILE_STATUS_MSG);
				throw new MerchantException(ErrorConstants.INVALID_FILE_STATUS_MSG);
			}

			DownloadFileRequest downloadParam = new DownloadFileRequest();
			downloadParam.setBucketName(config.getS3BucketName());
			downloadParam.setExpirationTime(config.getS3UrlExpiryTime());
			String objectkey = AmazonS3FilePathUtil.createAmazonS3FilePath(request.getUserId(), record.getFileName(),
					config.getS3Prefix());
			downloadParam.setObjectKey(objectkey);
			log.info("going to fetch url for download request");
			URL url = s3Service.getFileStream(s3Client, downloadParam);

			DownloadEntity updateStatus = new DownloadEntity();
			updateStatus.setViewed(true);
			updateStatus.setId(request.getId());
			log.info("going to update status in db");
			dao.updateDownloadInfoVisited(updateStatus);

			return url.toString();
		} catch (MerchantException e) {
			throw e;
		} catch (Exception e) {
			log.error("Error in download info {} {}", e.getMessage(), e);
			throw new MerchantException("Could not retrieve info");
		}

	}

	@Logged
	@Override
	public MerchantBulkDownloadInfoResponse getBulkRefundDownloadInfo(MerchantBulkRefundDownloadInfoRequest request)
			throws MerchantException {

		BulkRefundEntity entity = new BulkRefundEntity();

		entity.setMerchantId(request.getMerchantId());

		MerchantBulkDownloadInfoResponse response = null;

		try {
			List<BulkRefundEntity> records = bulkRefundDao.getBulkRefundInfoList(entity);

			response = new MerchantBulkDownloadInfoResponse();

			List<BulkDownloadInfo> infoList = new ArrayList<BulkDownloadInfo>();
			response.setInfo(infoList);
			for (BulkRefundEntity record : records) {

				BulkDownloadInfo info = new BulkDownloadInfo();
				info.setId(record.getId());

				/* info.setUploadStatus(record.getUploadStatus()); */
				info.setRefundStatus(record.getRefundStatus());
				/* info.setViewed(record.isViewed()); */
				info.setFileName(record.getFileName());
				info.setTimestamp(record.getCreatedOn().getTime());
				infoList.add(info);

			}

			return response;
		} catch (Exception e) {
			log.error("Error in download Bulk Refund info {} {}", e.getMessage(), e);
			throw new MerchantException("Could not retrieve info");
		}
	}

	@Logged
	@Override
	public String downloadBulkRefundFile(MerchantBulkRefundDownloadFileRequest request) throws MerchantException {

		BulkRefundEntity entity = new BulkRefundEntity();
		entity.setMerchantId(request.getMerchantId());
		entity.setId(request.getId());

		try {
			BulkRefundEntity record = bulkRefundDao.getBulkRefundInfo(entity);
			if (record == null) {
				log.error(ErrorConstants.INVALID_DOWNLOAD_MSG);
				throw new MerchantException(ErrorConstants.INVALID_DOWNLOAD_MSG);
			}

			if (record.getFileName() == null) {
				log.error(ErrorConstants.INVALID_FILE_STATUS_MSG);
				throw new MerchantException(ErrorConstants.INVALID_FILE_STATUS_MSG);
			}

			DownloadFileRequest downloadParam = new DownloadFileRequest();
			downloadParam.setBucketName(config.getS3BucketName());
			downloadParam.setExpirationTime(config.getS3UrlExpiryTime());
			String objectkey = AmazonS3FilePathUtil.createAmazonS3FilePath(request.getMerchantId(),
					record.getFileName(), config.getS3Prefix());
			downloadParam.setObjectKey(objectkey);
			log.info("going to fetch url for download request");
			URL url = s3Service.getFileStream(s3Client, downloadParam);

			/*
			 * BulkRefundEntity updateStatus = new BulkRefundEntity();
			 * updateStatus.setViewed(true);
			 * updateStatus.setId(request.getId()); log.info(
			 * "going to update status in db");
			 * bulkRefundDao.updateBulkRefundInfoVisited(updateStatus);
			 */

			return url.toString();
		} catch (MerchantException e) {
			throw e;
		} catch (Exception e) {
			log.error("Error in download info {} {}", e.getMessage(), e);
			throw new MerchantException("Could not retrieve info");
		}
	}

	public String downloadRefundTemplate() throws MerchantException {
		return config.getRefundTemplateFile();
	}

	@Logged
	@Override
	public MerchantSettlementReportResponse getSettlementReport(MerchantSettlementReportRequest request)
			throws MerchantException {

		MerchantSettlementReportResponse response = new MerchantSettlementReportResponse();

		GetMerchantSettlementReportDetailsResponse srResponse = srUtil.getSettlementReport(request);

		List<MerchantReportDetails> merchantReportList = new ArrayList<MerchantReportDetails>();
		List<ReportDetails> srReportList = srResponse.getReportDetailsList();

		for (ReportDetails srReport : srReportList) {

			MerchantReportDetails merchantReport = new MerchantReportDetails();
			merchantReport.setFileDownloadUrl(srReport.getFileDownloadUrl());
			merchantReport.setReportName(srReport.getReportName());

			if (srReport.getStartTime() != null) {

				Long startTime = dateToTSConverter.dateToTimestampConverter(srReport.getStartTime());

				merchantReport.setStartTime(String.valueOf(startTime));
			}
			if (srReport.getEndTime() != null) {

				Long endTime = dateToTSConverter.dateToTimestampConverter(srReport.getEndTime());

				merchantReport.setEndTime(String.valueOf(endTime));
			}

			merchantReportList.add(merchantReport);
		}
		response.setMerchantReportDetails(merchantReportList);

		return response;
	}

	@Logged
	@Override
	public MerchantInvoiceDetailsResponse getMerchantInvoice(MerchantInvoiceDetailsRequest request)
			throws MerchantException {

		MerchantInvoiceDetailsResponse response = new MerchantInvoiceDetailsResponse();
		GetMerchantInvoiceDetailsResponse srResponse = srUtil.getInvoiceDetails(request);
		List<MerchantReportDetails> merchantReportList = new ArrayList<MerchantReportDetails>();
		List<ReportDetails> srReportList = srResponse.getReportDetailsList();

		if (srReportList != null) {

			for (ReportDetails srReport : srReportList) {

				MerchantReportDetails merchantReport = new MerchantReportDetails();
				merchantReport.setFileDownloadUrl(srReport.getFileDownloadUrl());
				merchantReport.setReportName(srReport.getReportName());

				if (srReport.getStartTime() != null) {

					Long startTime = dateToTSConverter.dateToTimestampConverter(srReport.getStartTime());

					merchantReport.setStartTime(String.valueOf(startTime));
				}
				if (srReport.getEndTime() != null) {

					Long endTime = dateToTSConverter.dateToTimestampConverter(srReport.getEndTime());

					merchantReport.setEndTime(String.valueOf(endTime));
				}

				merchantReportList.add(merchantReport);
			}
		}
		response.setMerchantInvoiceDetails(merchantReportList);

		return response;
	}
}
