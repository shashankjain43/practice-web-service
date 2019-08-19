package com.snapdeal.merchant.taskscheduler;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.sound.midi.MidiDevice.Info;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.snapdeal.merchant.amazonS3Service.IAmazonS3Service;
import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dao.IBulkRefundDao;
import com.snapdeal.merchant.dao.entity.BulkRefundEntity;
import com.snapdeal.merchant.enums.FileType;
import com.snapdeal.merchant.enums.RefundStatus;
import com.snapdeal.merchant.enums.ReportType;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.exception.S3ServiceException;
import com.snapdeal.merchant.file.handler.IFileDecorator;
import com.snapdeal.merchant.file.handler.exception.FileHandlingException;
import com.snapdeal.merchant.persistence.IFileInfoManager;
import com.snapdeal.merchant.request.MerchantRefundAmountRequest;
import com.snapdeal.merchant.request.UploadFileRequest;
import com.snapdeal.merchant.rest.http.util.AggregatorUtil;
import com.snapdeal.merchant.util.ExcelConstants;
import com.snapdeal.merchant.utils.AmazonS3FilePathUtil;
import com.snapdeal.payments.aggregator.response.RefundResponse;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;
import com.snapdeal.payments.ts.response.RetryInfo;
import com.snapdeal.payments.ts.response.RetryInfo.RetryType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BRTaskExecutor implements TaskExecutor<BulkRefundTaskRequest> {

	@Autowired
	private MpanelConfig config;

	@Autowired
	private AmazonS3Client s3Client;

	@Autowired
	private AggregatorUtil aggUtil;

	@Autowired
	private IFileInfoManager fileInfoManager;

	@Resource
	@Qualifier("fileHandler")
	Map<ReportType, Map<FileType, IFileDecorator>> fileHandler;

	@Autowired
	private IAmazonS3Service s3Service;

	@Autowired
	private IBulkRefundDao refundDao;

	@Override
	public ExecutorResponse execute(BulkRefundTaskRequest taskRequest) {

		long taskStartTime = System.currentTimeMillis();
		long taskStopTimelimit = config.getTaskExecutionStopTime();

		ExecutorResponse response = new ExecutorResponse();
		response.setStatus(Status.SUCCESS);
		response.setCompletionLog("Completed");
		RetryInfo retryInfo = new RetryInfo();
		retryInfo.setType(RetryType.LINEAR);
		retryInfo.setWaitTime(config.getTaskRetryWaitTime());

		BulkRefundEntity bulkEntity = new BulkRefundEntity();
		bulkEntity.setId(taskRequest.getId());

		try {
			log.info("getting entry from db for refundID : {} ", taskRequest.getId());
			bulkEntity = refundDao.getBulkRefundInfo(bulkEntity);
		} catch (Exception e) {
			log.info("Getting Exception while fetching bulk refund in DB :{} ", e);
			response.setStatus(Status.RETRY);// retry in this case
			response.setCompletionLog(e.toString());
			response.setAction(retryInfo);
			return response;
		}

		log.info("Processing started for bulk Refund Task ID : {}", taskRequest.getTaskId());

		String objectKey = AmazonS3FilePathUtil.createAmazonS3FilePath(taskRequest.getMerchantId(),
				taskRequest.getFileName(), config.getS3Prefix());
		String bucketName = config.getS3BucketName();

		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, objectKey);
		log.info("Going to Download file Amazon s3");
		S3Object object = s3Client.getObject(getObjectRequest);

		log.info("S3 Object : {} ", object);

		InputStream ioStream = object.getObjectContent();

		String merchantId = taskRequest.getMerchantId();

		Workbook workbook=null;
		BulkRefundEntity entity = new BulkRefundEntity();
		int successCount = 0, failureCount = 0, totalNumOfRow = 0;
		try {

			workbook = WorkbookFactory.create(ioStream);

			Sheet firstSheet = workbook.getSheetAt(0);

			totalNumOfRow = firstSheet.getPhysicalNumberOfRows() - 1;

			log.info("total numbers of row in bulk refund file : {}", totalNumOfRow);

			long timeNowStart = System.currentTimeMillis();

			log.info("task start time : {}", timeNowStart);

			StringBuilder idemKey = new StringBuilder();

			Iterator<Row> rowIterator = firstSheet.iterator();

			Row firstRow = rowIterator.next();

			Iterator<Cell> cellIterator = firstRow.cellIterator();

			Cell refundTxnIdCell = firstRow.createCell(3);
			refundTxnIdCell.setCellType(Cell.CELL_TYPE_STRING);
			refundTxnIdCell.setCellValue(ExcelConstants.refundTxnId);

			Cell statusCell = firstRow.createCell(4);
			statusCell.setCellType(Cell.CELL_TYPE_STRING);
			statusCell.setCellValue(ExcelConstants.status);

			Cell msgCell = firstRow.createCell(5);
			msgCell.setCellType(Cell.CELL_TYPE_STRING);
			msgCell.setCellValue(ExcelConstants.message);

			while (rowIterator.hasNext()) {

				String ordercellValue = null, commentCellValue = null, amountCellValue = null;
				RefundResponse aggResponse=null;

				Row nextRow = rowIterator.next();

				refundTxnIdCell = nextRow.getCell(3, Row.CREATE_NULL_AS_BLANK);
				refundTxnIdCell.setCellType(Cell.CELL_TYPE_STRING);

				statusCell = nextRow.getCell(4, Row.CREATE_NULL_AS_BLANK);
				statusCell.setCellType(Cell.CELL_TYPE_STRING);

				msgCell = nextRow.getCell(5, Row.CREATE_NULL_AS_BLANK);
				msgCell.setCellType(Cell.CELL_TYPE_STRING);

				if (nextRow.getCell(4).getStringCellValue() != RefundStatus.SUCCESS.toString()
						&& nextRow.getCell(4).getStringCellValue() != RefundStatus.FAILED.toString()) {
					cellIterator = nextRow.cellIterator();
				} else
					continue;

				try {

					MerchantRefundAmountRequest refundRequest = new MerchantRefundAmountRequest();
					refundRequest.setMerchantId(merchantId);


					if (nextRow.getCell(0) != null) {
						Cell orderCell = nextRow.getCell(0);
						orderCell.setCellType(1);
						ordercellValue = orderCell.getStringCellValue();
					}
					if (nextRow.getCell(1) != null) {
						Cell amountCell = nextRow.getCell(1);
						amountCell.setCellType(1);
						amountCellValue = amountCell.getStringCellValue();
					}
					if (nextRow.getCell(2) != null) {
						Cell commentCell = nextRow.getCell(2);
						commentCell.setCellType(1);
						commentCellValue = commentCell.getStringCellValue();
					}

					/*if (("".equals(ordercellValue)) || ordercellValue == null) {
						if (("".equals(amountCellValue)) || amountCellValue == null) {
							if (("".equals(commentCellValue)) || commentCellValue == null) {
								totalNumOfRow--;
								continue;

							}
						}
					}*/
					
					if (ordercellValue.length() == 0 || ordercellValue == null) {
						if (amountCellValue.length() == 0 || amountCellValue == null) {
							if (commentCellValue.length() == 0 || commentCellValue == null) {
								totalNumOfRow--;
								continue;

							}
						}
					}

					
					BigDecimal amount = new BigDecimal(amountCellValue);
					refundRequest.setAmount(amount);
					refundRequest.setComments(commentCellValue);
					refundRequest.setOrderId(ordercellValue);

					idemKey.setLength(0);
					idemKey = idemKey.append(ordercellValue).append("-").append(taskRequest.getFileIdemKey())
							.append("-").append(nextRow.getRowNum());

					refundRequest.setRefId(idemKey.toString());

					log.info("refund request for record no: {}   {}", nextRow.getRowNum(), refundRequest);

					aggResponse = aggUtil.refundMoney(refundRequest);

					log.info("refund response for record no: {}   {}", nextRow.getRowNum(), aggResponse);

					nextRow.getCell(3).setCellValue(aggResponse.getRefundTxnId());
					nextRow.getCell(4).setCellValue(RefundStatus.SUCCESS.toString());
					nextRow.getCell(5).setCellValue(ErrorConstants.REFUND_SUCCESS_MSG);

					successCount++;

					log.info("Refund done successfully for record no : {}", nextRow.getRowNum());

				} catch (NumberFormatException nfe) {
					log.error("Exception because of Invalid amount record number: {} {}", nextRow.getRowNum(), nfe);
					nextRow.getCell(4).setCellValue(RefundStatus.FAILED.toString());
					nextRow.getCell(5).setCellValue(ErrorConstants.REFUND_INVALID_AMOUNT_MSG);

					failureCount++;

				} catch (MerchantException ex) {
					log.error("MerchantException while bulk refund record number: {} {} {}", nextRow.getRowNum(),
							ex.getMessage(), ex);

					if (ex.getErrCode() == ErrorConstants.UNABLE_TO_SUBMIT_BULK_REQUEST_CODE) {
						nextRow.getCell(4).setCellValue(RefundStatus.INDETERMINANT.toString());
					} else {
						nextRow.getCell(4).setCellValue(RefundStatus.FAILED.toString());
						failureCount++;
					}
					nextRow.getCell(5).setCellValue(ex.getErrMessage());

				} catch (Exception e) { // check for msg
					log.error("Generic error while bulk refund  record number: {} {} {}", nextRow.getRowNum(),
							e.getMessage(), e);
					nextRow.getCell(4).setCellValue(RefundStatus.FAILED.toString());
					nextRow.getCell(5).setCellValue(ErrorConstants.FAILED_TO_PROCESS_RECORD_MSG);
					failureCount++;
				}

				if (System.currentTimeMillis() - taskStartTime > taskStopTimelimit) {

					log.info("task taking too much time in execution, crosses task stop time limit");
					response.setStatus(Status.RETRY);
					response.setCompletionLog("task taking too much time for execution.");
					response.setAction(retryInfo);

					if (totalNumOfRow == successCount) {
						entity.setRefundStatus(RefundStatus.SUCCESS);
					} else if (totalNumOfRow == failureCount) {
						entity.setRefundStatus(RefundStatus.FAILED);
					} else if ((successCount + failureCount) != totalNumOfRow) {
						entity.setRefundStatus(RefundStatus.INITIATED);
					} else {
						entity.setRefundStatus(RefundStatus.PARTIAL_SUCCESS);
					}

					log.info("successCount and failureCount, if it crosses task stop time limit : {}  {}", successCount,
							failureCount);
					entity.setId(taskRequest.getId());

					log.info("uploading file on S3 and going to update status, if it crosses task stop time limit");

					String localFileLoc = saveAndUploadRefundFile(workbook, merchantId, taskRequest.getFileName(),
							entity);

					return response;
				}

			} // end of while loop

			if (totalNumOfRow == successCount) {
				entity.setRefundStatus(RefundStatus.SUCCESS);
			} else if (totalNumOfRow == failureCount) {
				entity.setRefundStatus(RefundStatus.FAILED);
			} else if ((successCount + failureCount) != totalNumOfRow) {
				entity.setRefundStatus(RefundStatus.INITIATED);
			} else {
				entity.setRefundStatus(RefundStatus.PARTIAL_SUCCESS);
			}

			log.info("successCount and failureCount: {}  {}", successCount, failureCount);
			entity.setId(taskRequest.getId());

			String localFileLoc = saveAndUploadRefundFile(workbook, merchantId, taskRequest.getFileName(), entity);

			Map<FileType, IFileDecorator> txnFileMapping = fileHandler.get(ReportType.TXN);
			IFileDecorator xlsTxnFileDecorator = txnFileMapping.get(FileType.XLS);

			xlsTxnFileDecorator.delete(localFileLoc);

			long timeNowEnd = System.currentTimeMillis();
			long diff = timeNowEnd - timeNowStart;
			log.info("total time in execution : {} ", diff);

		} catch (MerchantException e) {
			log.error("exception During Bulk refund  : {} {} ", e.getErrMessage(), e);
			response.setStatus(Status.RETRY);
			response.setCompletionLog(e.toString());
			response.setAction(retryInfo);

		} catch (IOException e) {
			log.error("not able to close workbook or iostream : {} ", e);
			response.setStatus(Status.RETRY);
			response.setCompletionLog(e.toString());
			response.setAction(retryInfo);

		} catch (Exception e)

		{
			log.error("Exception while processing bulk  refund  :{}", e);
			response.setStatus(Status.RETRY);
			response.setCompletionLog(e.toString());
			response.setAction(retryInfo);

		}

		if ((successCount + failureCount) != totalNumOfRow) {

			response.setStatus(Status.RETRY);
			response.setCompletionLog("retry for successfully submitting the request.");
			response.setAction(retryInfo);
			return response;
		} else
			return response;

	}

	public String saveAndUploadRefundFile(Workbook wb, String merchantId, String fileName,
			BulkRefundEntity updateEntity) throws MerchantException {

		StringBuilder localFilePath = new StringBuilder();

		int refundId;

		Map<FileType, IFileDecorator> txnFileMapping = fileHandler.get(ReportType.TXN);
		IFileDecorator xlsTxnFileDecorator = txnFileMapping.get(FileType.XLS);

		// DownloadStatus finalStatus = DownloadStatus.FAILED;

		try {

			// refundId = fileInfoManager.createFileInfo(entity);

			localFilePath.append(config.getTempFilePath()).append(fileName);

			xlsTxnFileDecorator.save(wb, localFilePath.toString());

			log.info("generated workbook, going to upload to s3");

			// upload to s3
			UploadFileRequest uploadInfo = new UploadFileRequest();
			uploadInfo.setBucketName(config.getS3BucketName());
			uploadInfo.setDestinationprefix(config.getS3Prefix());
			uploadInfo.setSubPrefix(merchantId);
			uploadInfo.setUploadFileLocation(localFilePath.toString());
			uploadInfo.setUploadFileName(fileName);

			s3Service.pushFile(s3Client, uploadInfo);

		} catch (S3ServiceException se) {
			log.error("failed to upload file : {}", se);
			throw new MerchantException(ErrorConstants.UNABLE_TO_SUBMIT_BULK_REQUEST_CODE, se.getMessage());
		} catch (Exception e) {
			log.error("failed to process upload request : {}", e);
			throw new MerchantException(ErrorConstants.UNABLE_TO_SUBMIT_BULK_REQUEST_CODE, e.getMessage());

		}

		// update db
		try {
			refundDao.updateBulkRefundInfoStatus(updateEntity);
		} catch (Exception e) {
			log.error("failed to update record export request : {}", e);
			throw new MerchantException(ErrorConstants.UNABLE_TO_SUBMIT_BULK_REQUEST_CODE, e.getMessage());
		}

		return localFilePath.toString();

	}

}
