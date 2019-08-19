package com.snapdeal.merchant.rest.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.snapdeal.merchant.amazonS3Service.IAmazonS3Service;
import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dao.IBulkRefundDao;
import com.snapdeal.merchant.dao.IDownloadHistoryDao;
import com.snapdeal.merchant.dao.entity.BulkRefundEntity;
import com.snapdeal.merchant.dao.entity.DownloadEntity;
import com.snapdeal.merchant.dao.entity.FilterEntity;
import com.snapdeal.merchant.dto.MPTransactionDTO;
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
import com.snapdeal.merchant.persistence.exception.PersistenceException;
import com.snapdeal.merchant.request.MerchantBulkRefundRequest;
import com.snapdeal.merchant.request.MerchantGetFilterTransactionRequest;
import com.snapdeal.merchant.request.MerchantGetSearchTransactionRequest;
import com.snapdeal.merchant.request.MerchantGetTransactionRequest;
import com.snapdeal.merchant.request.MerchantGetUserMerchantRequest;
import com.snapdeal.merchant.request.MerchantRefundAmountForTxnRequest;
import com.snapdeal.merchant.request.MerchantRefundAmountRequest;
import com.snapdeal.merchant.request.UploadFileRequest;
import com.snapdeal.merchant.response.MerchantBulkRefundResponse;
import com.snapdeal.merchant.response.MerchantExportTxnResponse;
import com.snapdeal.merchant.response.MerchantGetTransactionResponse;
import com.snapdeal.merchant.response.MerchantRefundAmountForTxnResponse;
import com.snapdeal.merchant.response.MerchantRefundAmountResponse;
import com.snapdeal.merchant.rest.http.util.AggregatorUtil;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.http.util.MVUtil;
import com.snapdeal.merchant.rest.service.IBulkRefundService;
import com.snapdeal.merchant.rest.service.ITransactionService;
import com.snapdeal.merchant.taskscheduler.BRTaskSerializer;
import com.snapdeal.merchant.taskscheduler.BulkRefundTaskRequest;
import com.snapdeal.merchant.taskscheduler.TaskRegistrationInfoImpl;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.merchant.util.MVMapper;
import com.snapdeal.merchant.util.SymbolTable;
import com.snapdeal.mob.enums.IntegrationMode;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.payments.aggregator.response.RefundResponse;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dao.TaskDao;
import com.snapdeal.payments.ts.dto.TaskDTO;
import com.snapdeal.payments.ts.entity.Task;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.response.GetTotalRefundedAmountForTxnResponse;
import com.snapdeal.payments.view.merchant.commons.response.GetTransactionsResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionManagementServiceImpl implements ITransactionService, IBulkRefundService {

	@Autowired
	private MVUtil mvUtil;

	@Autowired
	private AggregatorUtil aggUtil;

	@Autowired
	private MOBUtil mobUtil;

	@Autowired
	private IDownloadHistoryDao historyDao;

	@Autowired
	private IBulkRefundDao refundDao;

	@Resource
	@Qualifier("fileHandler")
	Map<ReportType, Map<FileType, IFileDecorator>> fileHandler;

	@Autowired
	private IFileInfoManager fileInfoManager;

	@Autowired
	private IAmazonS3Service s3Service;

	@Autowired
	private TaskScheduler taskScheduler;

	@Autowired
	private AmazonS3Client s3Client;

	@Autowired
	private MpanelConfig config;

	@Autowired
	private TaskDao bulkRefundTaskDao;

	@Autowired
	private BRTaskSerializer brTaskSerializer;

	@Logged
	@Override
	public MerchantGetTransactionResponse getTxnsOfMerchant(MerchantGetFilterTransactionRequest request)
			throws MerchantException {

		MerchantGetTransactionResponse response = new MerchantGetTransactionResponse();

		GetTransactionsResponse mvResponse = mvUtil.getTxnsOfMerchant(request);

		List<MVTransactionDTO> mvTxnList = mvResponse.getMvTransactions();

		List<MPTransactionDTO> mpTxnList = new ArrayList<MPTransactionDTO>();

		if (mvTxnList != null) {
			for (MVTransactionDTO mvTransactionDTO : mvTxnList) {
				MPTransactionDTO mpTxnDTO = (MPTransactionDTO) MVMapper.MVToMPTxnMapping(mvTransactionDTO);
				mpTxnList.add(mpTxnDTO);

			}
		}

		response.setMpTransactions(mpTxnList);

		return response;
	}

	@Logged
	@Override
	public MerchantGetTransactionResponse getTxnsOfMerchantBySearch(MerchantGetSearchTransactionRequest request)
			throws MerchantException {

		GetTransactionsResponse mvResponse = mvUtil.getTxnsOfMerchantBySearch(request);

		MerchantGetTransactionResponse mpResponse = new MerchantGetTransactionResponse();

		List<MVTransactionDTO> mvTxnList = mvResponse.getMvTransactions();

		List<MPTransactionDTO> mpTxnList = new ArrayList<MPTransactionDTO>();

		if (mvTxnList != null) {
			for (MVTransactionDTO mvTransactionDTO : mvTxnList) {
				MPTransactionDTO mpTxnDTO = (MPTransactionDTO) MVMapper.MVToMPTxnMapping(mvTransactionDTO);
				mpTxnList.add(mpTxnDTO);

			}
		}

		mpResponse.setMpTransactions(mpTxnList);

		return mpResponse;
	}

	@Logged
	@Override
	public MerchantGetTransactionResponse getMerchantTxns(MerchantGetTransactionRequest request)
			throws MerchantException {

		MerchantGetTransactionResponse response = new MerchantGetTransactionResponse();

		GetTransactionsResponse mvResponse = mvUtil.getMerchantTxns(request);

		List<MVTransactionDTO> mvTxnList = mvResponse.getMvTransactions();

		List<MPTransactionDTO> mpTxnList = new ArrayList<MPTransactionDTO>();

		if (mvTxnList != null) {
			for (MVTransactionDTO mvTransactionDTO : mvTxnList) {
				MPTransactionDTO mpTxnDTO = (MPTransactionDTO) MVMapper.MVToMPTxnMapping(mvTransactionDTO);
				mpTxnList.add(mpTxnDTO);

			}
		}

		response.setMpTransactions(mpTxnList);

		return response;

	}

	@Logged
	@Override
	public MerchantRefundAmountResponse refundMoney(MerchantRefundAmountRequest request) throws MerchantException {

		MerchantGetUserMerchantRequest getMerchantRequest = new MerchantGetUserMerchantRequest();
		RefundResponse aggResponse = null;
		getMerchantRequest.setToken(request.getToken());
		getMerchantRequest.setUserId(request.getLoggedUserId());

		// validate merchant type. if offline then deny such request
		onlineRefundCheck(getMerchantRequest);

		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmm");
		String stringDate = formatter.format(new Date());

		log.info("going to generate reference id");
		StringBuilder idemId = new StringBuilder();
		idemId.append(stringDate).append(UUID.randomUUID().toString().substring(0, 3));

		Random random = new Random();

		try {
			Integer firstRandomIndex = random.nextInt(36);
			Integer secondRandomIndex = random.nextInt(36);
			idemId.append(SymbolTable.getSymbol(firstRandomIndex));
			idemId.append(SymbolTable.getSymbol(secondRandomIndex));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		}

		log.info("Generated reference id {}", idemId.toString());

		request.setRefId(idemId.toString());

		aggResponse = aggUtil.refundMoney(request);
		MerchantRefundAmountResponse response = new MerchantRefundAmountResponse();
		response.setStatus(true);
		response.setRefundTxnId(aggResponse.getRefundTxnId());
		return response;
	}

	@Logged
	@Override
	public MerchantExportTxnResponse exportTxn(MerchantGetTransactionRequest request, FileType fileType, String userId)
			throws MerchantException {

		List<MPTransactionDTO> mpTxnDtoList = new ArrayList<MPTransactionDTO>();
	
		for (int i = 1; i <= config.getGetLoopCount(); i++) {

			List<MPTransactionDTO> mpTxnDto = new ArrayList<MPTransactionDTO>();
			request.setLimit(config.getTxnExportCount()); 
			request.setPage(i);
			
			MerchantGetTransactionResponse response = getMerchantTxns(request);

			if (response.getMpTransactions().isEmpty() )
				break;

			mpTxnDto = response.getMpTransactions();
			mpTxnDtoList.addAll(mpTxnDto);
		}

		MerchantExportTxnResponse exportResponse = new MerchantExportTxnResponse();

		StringBuilder localFilePath = new StringBuilder();
		StringBuilder remoteFileName = new StringBuilder();

		if (mpTxnDtoList == null || mpTxnDtoList.isEmpty()) {

			throw new MerchantException(RequestExceptionCodes.NO_RECORD_TO_EXPORT.getErrCode(),
					RequestExceptionCodes.NO_RECORD_TO_EXPORT.getErrMsg());
		}

		// save the record
		FilterEntity fEntity = new FilterEntity();

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String filterMetaData = null;
		try {
			filterMetaData = ow.writeValueAsString(request.getFilters());
		} catch (JsonProcessingException e1) {

			log.error("Json Processing Exception : {} ", e1);
			throw new MerchantException(ErrorConstants.UNABLE_TO_PROCESS_REQUEST);

		}

		String hashCode = String.valueOf(request.hashCode());
		fEntity.setFilterHash(hashCode);

		fEntity.setFilterMetaData(filterMetaData);

		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String startDate = formatter.format(new Date(request.getFilters().getStartDate()));

		String endDate = formatter.format(new Date(request.getFilters().getEndDate()));

		remoteFileName.append("TXN").append("_").append(startDate).append("_").append(endDate).append("_")
				.append(System.currentTimeMillis());

		switch (fileType) {
		case CSV:
			remoteFileName.append(".csv");
			break;
		case PDF:
			remoteFileName.append(".pdf");
			break;
		case XLS:
			remoteFileName.append(".xls");
			break;
		default:
			break;

		}

		String fileName = remoteFileName.toString();
		DownloadEntity entity = new DownloadEntity();
		entity.setStatus(DownloadStatus.INPROGRESS);
		entity.setUserId(userId);
		entity.setFileName(fileName);
		entity.setViewed(false);

		int historyId;

		Map<FileType, IFileDecorator> txnFileMapping = fileHandler.get(ReportType.TXN);
		IFileDecorator xlsTxnFileDecorator = txnFileMapping.get(fileType);

		DownloadStatus finalStatus = DownloadStatus.FAILED;

		try {

			historyId = fileInfoManager.createFileInfo(fEntity, entity);
			localFilePath.append(config.getTempFilePath()).append(userId).append("_").append(fileName);

			Object object = xlsTxnFileDecorator.decorate(mpTxnDtoList);
			xlsTxnFileDecorator.save(object, localFilePath.toString());

			log.info("generated workbook, going to upload to s3");

			// upload to s3
			UploadFileRequest uploadInfo = new UploadFileRequest();
			uploadInfo.setBucketName(config.getS3BucketName());
			uploadInfo.setDestinationprefix(config.getS3Prefix());
			uploadInfo.setSubPrefix(userId);
			uploadInfo.setUploadFileLocation(localFilePath.toString());
			uploadInfo.setUploadFileName(remoteFileName.toString());

			s3Service.pushFile(s3Client, uploadInfo);
			finalStatus = DownloadStatus.SUCCESS;

		} catch (PersistenceException e) {
			log.error("getting Error while inserting record in db : {}", e);
			throw new MerchantException(RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrCode(),
					RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrMsg());
		} catch (FileHandlingException e) {
			log.error("getting Error while Exporting Txn to Excel : {}", e);
			throw new MerchantException(RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrCode(),
					RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrMsg());
		} catch (S3ServiceException se) {
			log.error("failed to upload file : {}", se);
			throw new MerchantException(ErrorConstants.EXPORT_FILE_GEN_MSG);
		} catch (Exception e) {
			log.error("failed to process export request : {}", e);
			throw new MerchantException(RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrCode(),
					RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrMsg());

		}

		// update db
		try {
			DownloadEntity updateEntity = new DownloadEntity();
			updateEntity.setId(historyId);
			updateEntity.setStatus(finalStatus);
			if (finalStatus.equals(DownloadStatus.SUCCESS))
				updateEntity.setFileName(remoteFileName.toString());

			historyDao.updateDownloadInfoStatus(updateEntity);
		} catch (Exception e) {
			log.error("failed to update record export request : {}", e);
			throw new MerchantException(RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrCode(),
					RequestExceptionCodes.GENERIC_INTERNAL_SERVER.getErrMsg());
		}

		xlsTxnFileDecorator.delete(localFilePath.toString());

		exportResponse.setMessage(ErrorConstants.SUCCESS_MSG_FOR_EXPORT_TXN);
		return exportResponse;
	}

	@Logged
	@Override
	public MerchantBulkRefundResponse bulkRefund(MerchantBulkRefundRequest request, InputStream ioStream)
			throws MerchantException {

		String merchantId = request.getMerchantId();
		String token = request.getToken();

		Long inputFileTimeStamp = System.currentTimeMillis();
		Random random = new Random();
		Integer randomNum = random.nextInt(9999);
		StringBuilder IdemKey = new StringBuilder();
		IdemKey = IdemKey.append(inputFileTimeStamp).append("-").append(randomNum);
		String fileIdemKey = IdemKey.toString();

		MerchantGetUserMerchantRequest getMerchantRequest = new MerchantGetUserMerchantRequest();

		getMerchantRequest.setToken(token);
		getMerchantRequest.setUserId(request.getLoggedUserId());

		// validate merchant type. if offline then deny such request
		onlineRefundCheck(getMerchantRequest);

		Workbook wb = null;

		try {
			wb = WorkbookFactory.create(ioStream);

			Sheet firstSheet = wb.getSheetAt(0);

			int lastRowNum = firstSheet.getLastRowNum();

			if (lastRowNum < 1) {
				log.info("There is no record for refund in uploaded excel file .");
				throw new MerchantException(ErrorConstants.NO_RECORD_PROVIDED_FOR_BULK_REFUND_CODE,
						ErrorConstants.NO_RECORD_PROVIDED_FOR_BULK_REFUND_MSG);
			}

		} catch (Exception e) {
			log.error("Exception while processing bulk  refund  :{}", e);
			throw new MerchantException(ErrorConstants.UNABLE_TO_PROCESS_BULK_REFUND_REQUEST_CODE,
					ErrorConstants.UNABLE_TO_PROCESS_BULK_REFUND_REQUEST_MSG);
		}

		StringBuilder fileName = new StringBuilder();
		String fileExtn = null;
		if (wb instanceof HSSFWorkbook) {
			fileExtn = AppConstants.xlsExtn;
		} else if (wb instanceof XSSFWorkbook) {
			fileExtn = AppConstants.xlsxExtn;
		}

		fileName = fileName.append("REFUND").append("_").append(request.getLoggedLoginName()).append("_")
				.append(fileIdemKey).append(fileExtn);

		BulkRefundEntity bulkRefundEntity = new BulkRefundEntity();
		bulkRefundEntity.setMerchantId(merchantId);
		bulkRefundEntity.setUserLoginName(request.getLoggedLoginName());
		bulkRefundEntity.setFileIdemKey(fileIdemKey);
		bulkRefundEntity.setRefundStatus(RefundStatus.INITIATED);
		bulkRefundEntity.setFileName(fileName.toString());

		/* InputStream iostream1= WorkbookFactory. */

		int refundId = UploadInputFileAndSaveInfoInDB(wb, bulkRefundEntity, FileType.XLS);

		log.info("going to create task for bulk refund");

		Task task = new Task();
		StringBuilder taskId = new StringBuilder();

		taskId = taskId.append("BRTask-").append(fileIdemKey).append("-").append(request.getLoggedLoginName());

		BulkRefundTaskRequest brTaskRequest = new BulkRefundTaskRequest();

		brTaskRequest.setFileName(fileName.toString());
		brTaskRequest.setMerchantId(request.getMerchantId());
		brTaskRequest.setTaskId(taskId.toString());
		brTaskRequest.setFileIdemKey(fileIdemKey);
		brTaskRequest.setUserId(request.getLoggedUserId());
		brTaskRequest.setUserName(request.getLoggedLoginName());
		brTaskRequest.setId(refundId);

		TaskDTO refundDTO = new TaskDTO();
		refundDTO.setRequest(brTaskRequest);
		refundDTO.setCurrentScheduleTime(new Date(System.currentTimeMillis()));
		refundDTO.setTaskType(TaskRegistrationInfoImpl.TASK_TYPE);

		task.setTaskMetaData(brTaskSerializer.toString(brTaskRequest));

		try {
			log.info("Submitting task for refundId : {}", refundId);
			taskScheduler.submitTask(refundDTO);
		} catch (Exception e) {
			log.info("unable to create bulk refund task");

			throw new MerchantException(ErrorConstants.UNABLE_TO_PROCESS_BULK_REFUND_REQUEST_CODE,
					ErrorConstants.UNABLE_TO_PROCESS_BULK_REFUND_REQUEST_MSG);
		}

		MerchantBulkRefundResponse response = new MerchantBulkRefundResponse();
		response.setMessage(ErrorConstants.SUCCESS_MSG_FOR_BULK_REFUND);
		return response;
	}

	@Logged
	@Override
	public MerchantRefundAmountForTxnResponse getRefundAmountForTxn(MerchantRefundAmountForTxnRequest request)
			throws MerchantException {

		GetTotalRefundedAmountForTxnResponse mvResponse = null;

		MerchantRefundAmountForTxnResponse response = new MerchantRefundAmountForTxnResponse();

		mvResponse = mvUtil.getRefundAmountForTxn(request);

		if (mvResponse.getTotalRefundedAmount() != null)
			response.setTotalRefundAmount(mvResponse.getTotalRefundedAmount());
		else
			response.setTotalRefundAmount(new BigDecimal(0));

		return response;
	}

	private int UploadInputFileAndSaveInfoInDB(Workbook wb, BulkRefundEntity entity, FileType fileType)
			throws MerchantException {

		StringBuilder localFilePath = new StringBuilder();
		int refundId;
		String fileName = entity.getFileName();
		String merchantId = entity.getMerchantId();

		localFilePath.append(config.getTempFilePath()).append(fileName);

		Map<FileType, IFileDecorator> txnFileMapping = fileHandler.get(ReportType.TXN);
		IFileDecorator xlsTxnFileDecorator = txnFileMapping.get(fileType);

		try {
			xlsTxnFileDecorator.save(wb, localFilePath.toString());
		} catch (FileHandlingException e) {
			log.error("Bulk Input File not saved on local ", e);
			throw new MerchantException("unable to process Your Request please try after some time.");
		}

		log.info("uploading input file to s3 ");

		// upload to s3
		UploadFileRequest uploadInfo = new UploadFileRequest();
		uploadInfo.setBucketName(config.getS3BucketName());
		uploadInfo.setDestinationprefix(config.getS3Prefix());
		uploadInfo.setSubPrefix(merchantId);
		uploadInfo.setUploadFileLocation(localFilePath.toString());
		uploadInfo.setUploadFileName(fileName);

		try {
			s3Service.pushFile(s3Client, uploadInfo);
		} catch (S3ServiceException se) {
			log.error("failed to upload input file : {}", se);
			throw new MerchantException("could not process request.please try after some time");
		}

		xlsTxnFileDecorator.delete(localFilePath.toString());

		try {
			log.info("creating entry in db ");
			refundId = fileInfoManager.createFileInfo(entity);
		} catch (PersistenceException e) {
			log.error("failed to update input file info in database : {}", e);
			throw new MerchantException("could not process request.please try after some time");
		}

		return refundId;

	}

	private void onlineRefundCheck(MerchantGetUserMerchantRequest request) throws MerchantException {

		GetUserMerchantResponse mobResponse = mobUtil.getMerchantForUser(request);

		String intMode = mobResponse.getMerchantDetails().getIntegrationMode();

		if (!(IntegrationMode.ONLINE.getIntegrationMode().equalsIgnoreCase(intMode))) {
			throw new MerchantException("", ErrorConstants.ONLY_ONLINE_REFUND_ALLOWED_MSG);
		}
	}
}
