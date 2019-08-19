package com.snapdeal.merchant.jobs;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.amazonaws.services.s3.AmazonS3Client;
import com.snapdeal.merchant.amazonS3Service.IAmazonS3Service;
import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dao.IBulkRefundDao;
import com.snapdeal.merchant.dao.entity.BulkRefundEntity;
import com.snapdeal.merchant.enums.DownloadStatus;
import com.snapdeal.merchant.enums.FileType;
import com.snapdeal.merchant.enums.RefundStatus;
import com.snapdeal.merchant.enums.ReportType;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.exception.S3ServiceException;
import com.snapdeal.merchant.file.handler.IFileDecorator;
import com.snapdeal.merchant.file.handler.exception.FileHandlingException;
import com.snapdeal.merchant.persistence.IFileInfoManager;
import com.snapdeal.merchant.request.MerchantRefundAmountRequest;
import com.snapdeal.merchant.request.UploadFileRequest;
import com.snapdeal.merchant.rest.http.util.AggregatorUtil;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.merchant.util.ExcelConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BulkRefundJob implements Runnable {

	private Workbook workbook;

	private String merchantId;

	private String token;

	private AggregatorUtil aggUtil;
	
	private IBulkRefundDao refundDao;
	
	Map<ReportType, Map<FileType, IFileDecorator>> fileHandler;
	
	private IFileInfoManager fileInfoManager;
	
	private IAmazonS3Service s3Service;
	
	private AmazonS3Client s3Client;
	
	private MpanelConfig config;
	
	private String userName;
	
	private Integer totalNumOfRow;
	
	public BulkRefundJob(Workbook workbook,String merchantId,String token,
			MpanelConfig config,AggregatorUtil aggUtil,IBulkRefundDao refundDao,
			Map<ReportType, Map<FileType, IFileDecorator>> fileHandler,
			IFileInfoManager fileInfoManager,IAmazonS3Service s3Service,
			AmazonS3Client s3Client,String userName,Integer totalNumOfRow) {
		this.workbook = workbook;
		this.merchantId = merchantId;
		this.token = token;
		this.config = config;
		this.aggUtil = aggUtil;
		this.refundDao = refundDao;
		this.fileHandler = fileHandler;
		this.fileInfoManager = fileInfoManager;
		this.s3Service = s3Service;
		this.s3Client = s3Client;
		this.userName = userName;
		this.totalNumOfRow = totalNumOfRow;
	}

	@Override
	public void run() {

		try {

			long timeNowStart = System.currentTimeMillis();
			int successCount = 0;
			Sheet firstSheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = firstSheet.iterator();

			Row firstRow = rowIterator.next();

			Iterator<Cell> cellIterator = firstRow.cellIterator();

			Cell statusCell = firstRow.createCell(3);
			statusCell.setCellType(Cell.CELL_TYPE_STRING);
			statusCell.setCellValue(ExcelConstants.status);

			Cell msgCell = firstRow.createCell(4);
			msgCell.setCellType(Cell.CELL_TYPE_STRING);
			msgCell.setCellValue(ExcelConstants.message);

			while (rowIterator.hasNext()) {

				String ordercellValue=null, commentCellValue = null , amountCellValue=null;
				
				Row nextRow = rowIterator.next();

				statusCell = nextRow.createCell(3);
				statusCell.setCellType(Cell.CELL_TYPE_STRING);

				msgCell = nextRow.createCell(4);
				msgCell.setCellType(Cell.CELL_TYPE_STRING);
				
				cellIterator = nextRow.cellIterator();

				try {

					MerchantRefundAmountRequest refundRequest = new MerchantRefundAmountRequest();
					refundRequest.setMerchantId(merchantId);
					refundRequest.setToken(token);

					if (cellIterator.hasNext()){
						Cell cell = cellIterator.next();
						cell.setCellType(1);
						ordercellValue=cell.getStringCellValue();
					} 
					
					if (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						cell.setCellType(1);
						amountCellValue = cell.getStringCellValue();
						
					} 

					if (cellIterator.hasNext()){
						Cell cell = cellIterator.next();
						cell.setCellType(1);
						commentCellValue = cell.getStringCellValue();
					}
					
					if("".equals(ordercellValue)  && "".equals(amountCellValue) )
						continue;
	
					BigDecimal amount = new BigDecimal(amountCellValue);
					refundRequest.setAmount(amount);
					refundRequest.setComments(commentCellValue);
					refundRequest.setOrderId(ordercellValue);
					
					aggUtil.refundMoney(refundRequest);

					nextRow.getCell(3).setCellValue(RefundStatus.SUCCESS.toString());
					nextRow.getCell(4).setCellValue(ErrorConstants.REFUND_SUCCESS_MSG);

					successCount++;
					
					log.info("Refund done successfully ");

				} catch (NumberFormatException nfe) {

					log.error("Exception because of Invalid amount record number: {} {}", nextRow.getRowNum(), nfe);
					nextRow.getCell(3).setCellValue(RefundStatus.FAILED.toString());
					nextRow.getCell(4).setCellValue(ErrorConstants.REFUND_INVALID_AMOUNT_MSG);

				} catch (MerchantException ex) {

					log.error("MerchantException while bulk refund record number: {} {} {}", nextRow.getRowNum(),
							ex.getMessage(), ex);
					nextRow.getCell(3).setCellValue(RefundStatus.FAILED.toString());
					nextRow.getCell(4).setCellValue(ex.getErrMessage());
				} catch (Exception e) {
					log.error("Generic error while bulk refund  record number: {} {} {}", nextRow.getRowNum(),
							e.getMessage(), e);
					nextRow.getCell(3).setCellValue(RefundStatus.FAILED.toString());
					nextRow.getCell(4).setCellValue(ErrorConstants.FAILED_TO_PROCESS_RECORD_MSG);
				}

			} // end of while loop

			BulkRefundEntity entity = new BulkRefundEntity();
			
			if(totalNumOfRow == successCount)
			{
				entity.setRefundStatus(RefundStatus.SUCCESS);
			}else if(successCount == 0)
			{
				entity.setRefundStatus(RefundStatus.FAILED);
			}else
			{
				entity.setRefundStatus(RefundStatus.PARTIAL_SUCCESS);
			}
				
			
			String localFileLoc = saveAndUploadRefundFile(workbook, merchantId,entity);
			
			Map<FileType, IFileDecorator> txnFileMapping = fileHandler.get(ReportType.TXN);
			IFileDecorator xlsTxnFileDecorator = txnFileMapping.get(FileType.XLS);

			xlsTxnFileDecorator.delete(localFileLoc);
			
			long timeNowEnd = System.currentTimeMillis();
			long diff = timeNowEnd - timeNowStart;
			log.info("total time in execution : {} ", diff);

		} catch (MerchantException e) {
			log.error("exception During Bulk refund  : {} {} ", e.getErrMessage(), e);
			
		} catch (Exception e) {
			log.error("Exception while processing bulk  refund  :{}", e);
		}

	}

	private String saveAndUploadRefundFile(Workbook wb, String merchantId,BulkRefundEntity entity) throws MerchantException {

		StringBuilder localFilePath = new StringBuilder();
		StringBuilder remoteFileName = new StringBuilder();

		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyHHmmSS");
		String stringDate = formatter.format(new Date());
		String fileExtn = null;
		
		if(wb instanceof HSSFWorkbook) {
			fileExtn = AppConstants.xlsExtn;
		} else if(wb instanceof XSSFWorkbook) {
			fileExtn = AppConstants.xlsxExtn;
		}
		remoteFileName.append("REFUND").append("_").append(this.userName).append("_").append(stringDate).append(fileExtn);

		String fileName = remoteFileName.toString();

		/*entity.setUploadStatus(DownloadStatus.INPROGRESS);*/
		entity.setMerchantId(merchantId);

		int refundId;

		Map<FileType, IFileDecorator> txnFileMapping = fileHandler.get(ReportType.TXN);
		IFileDecorator xlsTxnFileDecorator = txnFileMapping.get(FileType.XLS);

		DownloadStatus finalStatus = DownloadStatus.FAILED;

		try {

			refundId = fileInfoManager.createFileInfo(entity);

			localFilePath.append(config.getTempFilePath()).append(merchantId).append("_").append(fileName);

			xlsTxnFileDecorator.save(wb, localFilePath.toString());

			log.info("generated workbook, going to upload to s3");

			// upload to s3
			UploadFileRequest uploadInfo = new UploadFileRequest();
			uploadInfo.setBucketName(config.getS3BucketName());
			uploadInfo.setDestinationprefix(config.getS3Prefix());
			uploadInfo.setSubPrefix(merchantId);
			uploadInfo.setUploadFileLocation(localFilePath.toString());
			uploadInfo.setUploadFileName(remoteFileName.toString());

			s3Service.pushFile(s3Client, uploadInfo);
			finalStatus = DownloadStatus.SUCCESS;

		} catch (FileHandlingException e) {
			log.error("getting Error while Exporting Txn to Excel : {}", e);
			throw new MerchantException(RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrCode(),
					RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrMsg());
		} catch (S3ServiceException se) {
			log.error("failed to upload file : {}", se);
			throw new MerchantException(ErrorConstants.EXPORT_FILE_GEN_MSG);
		} catch (Exception e) {
			log.error("failed to process upload request : {}", e);
			throw new MerchantException(RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrCode(),
					RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrMsg());

		}

		// update db
		try {
			BulkRefundEntity updateEntity = new BulkRefundEntity();

			updateEntity.setId(refundId);
			/*updateEntity.setUploadStatus(finalStatus);*/
			updateEntity.setFileName(fileName);

			refundDao.updateBulkRefundInfoStatus(updateEntity);
		} catch (Exception e) {
			log.error("failed to update record export request : {}", e);
			throw new MerchantException(RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrCode(),
					RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrMsg());
		}

		return localFilePath.toString();

	}


}
