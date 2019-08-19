package com.snapdeal.opspanel.promotion.rp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.snapdeal.bulkprocess.model.GenericBulkTaskRequest;
import com.snapdeal.bulkprocess.service.BulkService;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.Response.UploadResponse;
import com.snapdeal.opspanel.promotion.constant.BulkPromotionConstants;
import com.snapdeal.opspanel.promotion.enums.FileState;
import com.snapdeal.opspanel.promotion.enums.InstrumentType;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.model.BulkPromotionRequest;
import com.snapdeal.opspanel.promotion.model.FileMetaEntity;
import com.snapdeal.opspanel.promotion.model.UserAccountModel;
import com.snapdeal.opspanel.promotion.request.CorpRequest;
import com.snapdeal.opspanel.promotion.request.FormData;
import com.snapdeal.opspanel.promotion.request.VerifyCampaignKeyRequest;
import com.snapdeal.opspanel.promotion.service.BulkPromotionAcccessService;
import com.snapdeal.opspanel.promotion.service.CSVService;
import com.snapdeal.opspanel.promotion.service.FileMetaService;
import com.snapdeal.opspanel.promotion.service.FileService;
import com.snapdeal.opspanel.promotion.utils.AmazonS3Utils;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.payments.ts.TaskScheduler;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.io.FilenameUtils;

@Controller
@Slf4j
@RequestMapping("file/")
public class FileController {

	@Value("${bulk.localDir}")
	private String localDir;

	@Value("${file.size}")
	private Integer filesize;

	@Autowired
	HttpServletRequest servletRequest;

	@Autowired
	FileService fileService;

	@Autowired
	CSVService csvService;

	@Autowired
	FileMetaService fileMetaService;

	@Autowired
	AmazonS3Utils amazonUtils;

	@Autowired
	TaskScheduler taskScheduler;

	@Autowired
	BulkPromotionAcccessService accessService;

	@Autowired
	BulkService bulkService;
	
	@Autowired
	private TokenService tokenService;

	//TODO: Add proper validations here
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody GenericResponse upload( @RequestPart(value = "file" ) MultipartFile file,
			@Valid BulkPromotionRequest bulkPromotionRequest) throws Exception {
		// GenericControllerUtils.checkBindingResult( result, "upload in file controller " );
		if (file != null) {
			String fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
			log.info("\n\n\n\n NEW FILE RECIEVED :" + fileName + "\nbusinessEntity :" + bulkPromotionRequest.getBusinessEntity() + "\nCorpId:"
					+ bulkPromotionRequest.getCorpId() + "\nactivity:" + bulkPromotionRequest.getActivity() + "\nInstrument:" + bulkPromotionRequest.getInstrument() + "\nId_type:" + bulkPromotionRequest.getId_type()
					+ "\nIdempotancy id:" + bulkPromotionRequest.getIdempotencyId() + "\nis_park:" + bulkPromotionRequest.getIsPark() + "\nisEmailSupressed:"
					+ bulkPromotionRequest.getIsEmailSuppressed() + "\nisSMSSupressed:" + bulkPromotionRequest.getIsSMSSuppressed() + "\nemailTemplateid:" + bulkPromotionRequest.getEmailTemplateId()
					+ "\nsmstemplateid:" + bulkPromotionRequest.getSmsTemplateId() + "\n\n\n"+"isWalletNotificationSuppressed "+ bulkPromotionRequest.getIsWalletNotificationSuppressed() );
		}
		UploadResponse response = new UploadResponse();

		String emailId = getEmailFromRms();

		if (bulkPromotionRequest.getIdempotencyId() != null) {
			bulkPromotionRequest.setIdempotencyId( bulkPromotionRequest.getIdempotencyId().trim() );
		}
		//TODO: Move access service to bulk promotion component
		if (accessService.checkPermission(emailId, bulkPromotionRequest.getBusinessEntity(), bulkPromotionRequest.getCorpId(), InstrumentType.valueOf(bulkPromotionRequest.getInstrument() ))) {
			throw new WalletServiceException("MT-5015",
					"You do not have permission to run bulk operations for this type of selection !");
		}

		//TODO: remove this json conversion, if possible
		UserAccountModel userAccountModel = new Gson().fromJson(bulkPromotionRequest.getCorpId(), UserAccountModel.class);

		String fileName = null;
		String msg = "";
		Date currDate = new java.util.Date();
		String corporateAccountId = null;
		Gson gson = new Gson();
		CorpRequest corpRequest = gson.fromJson( bulkPromotionRequest.getCorpId(), CorpRequest.class);
		corporateAccountId = corpRequest.getAccountId();
		FormData formRequest = encapsulateForm(bulkPromotionRequest.getBusinessEntity(), bulkPromotionRequest.getActivity(), bulkPromotionRequest.getInstrument(), corporateAccountId, bulkPromotionRequest.getId_type(),
				bulkPromotionRequest.getIsPark(), bulkPromotionRequest.getIdempotencyId(), new Timestamp(currDate.getTime()), bulkPromotionRequest.getSmsTemplateId(), bulkPromotionRequest.getEmailTemplateId(),
				bulkPromotionRequest.getIsEmailSuppressed(), bulkPromotionRequest.getIsSMSSuppressed(), bulkPromotionRequest.getIsWalletNotificationSuppressed() );

		//TODO: Use generic file controller for uploading file.
		if (file != null) {

			fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
			if (!fileMetaService.getFileMetaStatusForAnyUser(emailId, fileName).isEmpty()) {
				throw new WalletServiceException("MT-5035",
						"We had recieved a file with the same name in past, This operation is not permitted !");
			}

			try {
				String ext = FilenameUtils.getExtension(fileName);

				if (!ext.equals("csv")) {
					throw new WalletServiceException("MT-5037", "Sorry The file is not an csv file");

				}
				//TODO : ENABLE THIS
				/*		if (file.getSize() > filesize) {
					throw new WalletServiceException("MT-5038",
							"Sorry The file size is greater than limit " + ((double) filesize) / 1000000 + " MB");
				}
				 */
				byte[] bytes = file.getBytes();
				BufferedOutputStream buffStream = new BufferedOutputStream(
						new FileOutputStream(new File(localDir + fileName)));
				buffStream.write(bytes);
				buffStream.close();

				msg = "You have successfully uploaded " + fileName + "\n";

			} catch (WalletServiceException wse) {
				log.info("Exception while uploading file " + ExceptionUtils.getFullStackTrace( wse ) );
				deleteFile( localDir + fileName );
				throw wse;
			} catch (Exception e) {
				log.info("Exception while uploading file " + ExceptionUtils.getFullStackTrace( e ) );
				deleteFile( localDir + fileName );
				throw new WalletServiceException("MT-5025", "Upload failed!");
			}

			formRequest.setFileName( fileName );
			FileMetaEntity fileEntity = new FileMetaEntity();

			try {
				try {
					csvService.validateFile(localDir + fileName, bulkPromotionRequest.getId_type(), fileEntity);
					csvService.checkCorpAccountBalance(userAccountModel.getAccountId(), fileEntity.getTotalMoney());
				} catch (WalletServiceException wse) {
					log.info("Exception while validating file or checking corp account balance " + ExceptionUtils.getFullStackTrace( wse ) );
					throw wse;
				}

				try {
					amazonUtils.pushFile(localDir + fileName, emailId, fileName);
				} catch (Exception e) {
					log.info("Exception while uploading file to amazon s3 utils " + ExceptionUtils.getFullStackTrace( e ) );
					try {
						amazonUtils.pushFile(localDir + fileName, emailId, fileName);
					} catch( Exception ex ) {
						log.info( "Exception while retrying file to amazon s3 utils " + ExceptionUtils.getFullStackTrace( ex ) );
						throw new WalletServiceException( "BP-1000", "Connection issue with amazon s3. " );
					}
				}
			}  catch( Exception ex ) {
				deleteFile( localDir + fileName );
				throw ex;
			}

			fileEntity.setFileName(fileName);
			fileEntity.setStatus(FileState.FILE_UPLOAD_SCHEDULED.name());

			try {
				amazonUtils.pushFile(localDir + fileName, emailId, fileName);
			} catch (Exception e) {
				log.info("Exception while uploading file to amazon s3 utils " + e);
				fileMetaService.updateFileMetaStatus(getFileMetaEntity(fileName, FileState.FILE_UPLOAD_TO_S3_FAIL));
				throw e;
			}
			//TODO:
			/*File uploadedFile = new File(localDir + fileName);
			uploadedFile.delete();*/

			fileEntity.setStatus(FileState.SCHEDULED_EXECUTION.name());
			fileEntity.setSuccessRowsNum(0l);
			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone("IST"));
			fileEntity.setUploadTime(cal.getTime());
			fileEntity.setUserId(emailId);
			fileEntity.setIdempotencyId(bulkPromotionRequest.getIdempotencyId());
			fileEntity.setMailTemplateId(bulkPromotionRequest.getEmailTemplateId());
			fileEntity.setSmsTemplateId(bulkPromotionRequest.getSmsTemplateId());
			fileEntity.setCorporateAccountName(userAccountModel.getAccountName());
			fileMetaService.insertFileMetaEntity(fileEntity);

			String s3Path = amazonUtils.createAmazonS3FilePath(emailId, fileName);

			GenericBulkTaskRequest genericBulkTaskRequest = new GenericBulkTaskRequest();
			genericBulkTaskRequest.setFileMeta( new ObjectMapper().convertValue( formRequest, Map.class ) );
			genericBulkTaskRequest.setFileName(fileName);
			genericBulkTaskRequest.setLocalPath( localDir +fileName);
			genericBulkTaskRequest.setS3Path(s3Path);
			genericBulkTaskRequest.setUploadTime(new Date());
			genericBulkTaskRequest.setUserId(emailId);
			genericBulkTaskRequest.setActivityId( BulkPromotionConstants.BULK_PROMOTION_ACTIVITY_ID );

			bulkService.submitJob(genericBulkTaskRequest);

			return getGenericResponse(msg);
		} else {
			return getGenericResponse("Unable to upload. File is empty.");

		}
	}


	@RequestMapping(value = "/verifyCampaignKey", method = RequestMethod.POST)
	public @ResponseBody GenericResponse verifyCampaignKey(@RequestBody VerifyCampaignKeyRequest request) throws Exception {

		GenericResponse response = new GenericResponse();
		response.setData(fileService.getCampaignKeyDetails(request.getKey().trim()));
		return response;
	}

	private String getEmailFromRms() throws WalletServiceException, OpsPanelException {

		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		return emailId;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public @ResponseBody List<FileMetaEntity> listFiles() throws Exception {

		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		return fileMetaService.getAllFilesMetaByUser(emailId);
	}

	@RequestMapping(value = "/downloadfile", method = RequestMethod.GET)
	public @ResponseBody GenericResponse dowloadFilePath(@RequestParam("fileName") String fileName,
			@RequestParam("isOutputFile") boolean isOutputFile) throws WalletServiceException, OpsPanelException {
		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);
		
		String username = fileMetaService.getUserIdForFile(fileName);
		try {
			if (!username.equalsIgnoreCase(emailId)
					&& !accessService.getUsersRoles(emailId).equalsIgnoreCase("superuser")) {
				throw new WalletServiceException("MT-5036", "Access not allowed for the file ");
			}
			if (isOutputFile) {
				return getGenericResponse(fileService.getPresignDownloadLink(
						fileName.substring(0, fileName.lastIndexOf(".csv")) + "_response.csv", username));
			}
			return getGenericResponse(fileService.getPresignDownloadLink(fileName, username));
		} catch (Exception e) {
			log.info("Exception while getting presigned url for downloadFile " + e);
			throw new WalletServiceException("MT-1109", ExceptionUtils.getFullStackTrace( e ) );

		}

	}

	private GenericResponse getGenericResponse(Object walletResponse) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(walletResponse);
		return genericResponse;
	}

	private FileMetaEntity getFileMetaEntity(String fileName, FileState state) {
		FileMetaEntity fileEntity = new FileMetaEntity();
		fileEntity.setFileName(fileName);
		fileEntity.setStatus(state.name());
		return fileEntity;
	}

	private FormData encapsulateForm(String businessEntity, String activity, String instrument, String corpId,
			String id_type, Boolean isPark, String idempotencyId, Timestamp uploadTimestamp, String smsTemplateId,
			String mailTemplateId, Boolean isEmailSuppressed, Boolean isSMSSuppressed
			,Boolean isWalletNotificationSuppressed) {
		FormData formRequest = new FormData();
		formRequest.setBusinessEntity(businessEntity);
		formRequest.setActivity(activity);
		formRequest.setId_type(id_type);
		formRequest.setInstrument(InstrumentType.valueOf(instrument));
		formRequest.setIsPark(isPark);
		formRequest.setIdempotencyId(idempotencyId);
		formRequest.setUploadTimestamp(uploadTimestamp);
		formRequest.setSmsTemplateId(smsTemplateId);
		formRequest.setEmailTemplateId(mailTemplateId);
		formRequest.setIsEmailSuppressed(isEmailSuppressed);
		formRequest.setIsSMSSuppressed(isSMSSuppressed);
		formRequest.setCorpId(corpId);
		formRequest.setIsWalletNotificationSuppressed(isWalletNotificationSuppressed);
		return formRequest;
	}

	private void deleteFile( String filePath ) {

		File fileToBeDeleted = new File( filePath );
		if ( fileToBeDeleted.exists()) {
			fileToBeDeleted.delete();
		}
	}
}