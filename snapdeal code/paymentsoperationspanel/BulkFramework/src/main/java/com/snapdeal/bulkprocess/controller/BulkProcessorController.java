package com.snapdeal.bulkprocess.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.exception.BulkProcessorException;
import com.snapdeal.bulkprocess.factory.BulkRegistrationFactory;
import com.snapdeal.bulkprocess.model.BulkFrameworkResponse;
import com.snapdeal.bulkprocess.model.FileValidationResult;
import com.snapdeal.bulkprocess.model.GenericBulkTaskRequest;
import com.snapdeal.bulkprocess.model.ListFilesRequest;
import com.snapdeal.bulkprocess.model.UploadRequest;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.bulkprocess.service.AmazonServices;
import com.snapdeal.bulkprocess.service.BulkService;
import com.snapdeal.bulkprocess.utils.BulkGenericValidations;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;
import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.rms.service.TokenService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bulkprocess/")
@Slf4j
public class BulkProcessorController {

	@Autowired
	AmazonServices amazonService;

	@Autowired
	BulkService bulkService;

	@Autowired 
	BulkRegistrationFactory bulkRegistrationFactory;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	TokenService tokenService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public BulkFrameworkResponse upload(@RequestParam Map<String,String> allRequestParams, @RequestPart("file") MultipartFile file) throws BulkProcessorException, IOException, PaymentsCommonException {

		IBulkFileRegistration registration =null;
		String activityId=null;

		String msg= null;
		String fileName=null;
		if(allRequestParams.containsKey(BulkProcessorUtils.ACTIVITY_ID)){
			activityId = allRequestParams.get(BulkProcessorUtils.ACTIVITY_ID);
			registration = getRegistration(activityId);
		} else {
			throw new BulkProcessorException("BPC-0001", BulkProcessorUtils.ACTIVITY_ID_NOT_FOUND);
		}
		if(!BulkGenericValidations.preStartChecks(registration)){
			throw new BulkProcessorException("BPC-0009", BulkProcessorUtils.PRE_START_CHECKS_FAILED);
		}

		Map<String, String> headerValues = getHeadersMap();
		if(registration.getValidator()!= null && !registration.getValidator().hasPermissionForAction(registration.getUserId(), BulkProcessEnum.UPLOAD, headerValues)){
			throw new BulkProcessorException("BPC-0010", BulkProcessorUtils.AUTHORIZATION_FAILED);
		}

		fileName = GenericChecks(file, fileName,registration.getMAXFileSizeInMB());
		File inputFile = new File(registration.getLocalDir() + fileName);

		try {
			saveFile(file, registration, fileName);

			log.info("File saved locally ...\n \n");

			validateFile(allRequestParams, registration, fileName);

			log.info("File Validation complete ... \n \n");

			String s3Path = uploadToS3(allRequestParams, registration, activityId, fileName);

			log.info("File uploaded to s3 ...\n \n");

			submitJob(allRequestParams, registration, activityId, fileName, s3Path);

			log.info("Job submitted :  activityId = " + activityId + " , fileName = " +  fileName + "\n");

			msg = BulkProcessorUtils.SUCCESSFUL_UPLOAD + fileName + "\n";

		} catch (BulkProcessorException bpe) {
			log.info(bpe.getClass().getName() + " Occured! Deleting local input file...");
			if(!inputFile.delete()){
				log.info("Unable to delete local  input file!");
			}
			throw bpe;
		} catch (PaymentsCommonException pce){
			log.info(pce.getClass().getName() + " Occured! Deleting local input file...");
			if(!inputFile.delete()){
				log.info("Unable to delete local  input file!");
			}
			throw new BulkProcessorException("BPC-0002", BulkProcessorUtils.S3_FAIL_MSG + pce.getMessage());
		} catch (Exception e) {
			log.info(e.getClass().getName() + " Occured! Deleting local input file...");
			if(!inputFile.delete()){
				log.info("Unable to delete local  input file!");
			}
			throw new BulkProcessorException("BPC-0003", BulkProcessorUtils.ERROR_OCCURED + e.getMessage());
		}
		return new BulkFrameworkResponse(msg);
	}

	private String GenericChecks(MultipartFile file, String fileName, int maxFileSizeInMb)
			throws UnsupportedEncodingException, BulkProcessorException {
		if (file != null) {
			fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
			log.info("\n\n NEW FILE RECIEVED :" + fileName );
		}

		if (file != null) {

			fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
			fileName = "input_" + (Long.MAX_VALUE-new Date().getTime()) + "_" + fileName;

			String ext = FilenameUtils.getExtension(fileName);

			if (!ext.equals("csv")) {
				throw new BulkProcessorException("BPC-0004", BulkProcessorUtils.FILE_NOT_CSV_MSG);
			}

			if (((file.getSize()/(1024*1024)) > maxFileSizeInMb)) {
				throw new BulkProcessorException("BPC-0005",BulkProcessorUtils.FILE_SIZE_LIMIT_MSG );
			}

		}
		return fileName;
	}

	private void saveFile(MultipartFile file, IBulkFileRegistration registration, String fileName)
			throws IOException, FileNotFoundException {
		byte[] bytes = file.getBytes();
		BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(registration.getLocalDir() + fileName)));
		buffStream.write(bytes);
		buffStream.close();
	}

	private void validateFile(Map<String, String> allRequestParams, IBulkFileRegistration registration, String fileName)
			throws FileNotFoundException, IOException, BulkProcessorException {
		if(registration.getValidator()!=null) {
			log.info("Inside validateFile \n");

			File inputFile = new File(registration.getLocalDir() + fileName);

			FileValidationResult fileValidationResult = validateInputFile(inputFile, allRequestParams,registration);
			if (fileValidationResult != null) {
				if (!fileValidationResult.isValidationSuccess()) {
					log.info("ERROR : " + fileValidationResult.getErrorMessage());
					throw new BulkProcessorException("BPC-0009",
							fileValidationResult.getErrorMessage());

				}
			}
			}
	}
	

	private String uploadToS3(Map<String, String> allRequestParams, IBulkFileRegistration registration,
			String activityId, String fileName) throws BulkProcessorException, PaymentsCommonException {
		UploadRequest uploadRequest = new UploadRequest();
		uploadRequest.setUserId(registration.getUserId());
		uploadRequest.setFileName(fileName);
		uploadRequest.setFileSource(registration.getLocalDir()+fileName);
		uploadRequest.setInputFile(true);
		uploadRequest.setUploadTime(new Date());
		uploadRequest.setActivityId(activityId);
		uploadRequest.setInputRequestParams(allRequestParams);
		String s3Path = amazonService.uploadToS3(uploadRequest);
		log.info("Input fileName : " + fileName + ", uploaded successfully to s3 at Path : " + s3Path);
		return s3Path;
	}

	private void submitJob(Map<String, String> allRequestParams, IBulkFileRegistration registration, String activityId,
			String fileName, String s3Path) {

		Map<String, String> headerValues = getHeadersMap();

		GenericBulkTaskRequest genericBulkTaskRequest = new GenericBulkTaskRequest();
		genericBulkTaskRequest.setFileMeta(allRequestParams);
		genericBulkTaskRequest.setFileName(fileName);
		genericBulkTaskRequest.setLocalPath(registration.getLocalDir()+fileName);
		genericBulkTaskRequest.setS3Path(s3Path);
		genericBulkTaskRequest.setUploadTime(new Date());
		genericBulkTaskRequest.setUserId(registration.getUserId());
		genericBulkTaskRequest.setActivityId(activityId);
		genericBulkTaskRequest.setHeaderValues(headerValues);

		bulkService.submitJob(genericBulkTaskRequest);
		log.info("Job submitted for the file " + fileName + "\n");
	}

	private IBulkFileRegistration getRegistration(String activityId) throws BulkProcessorException{
		IBulkFileRegistration registration = bulkRegistrationFactory.getIBulkFileRegistrationInfo(activityId);
		if(registration == null){
			throw new BulkProcessorException("BPC-0006", BulkProcessorUtils.REGISTRATION_NOT_FOUND);
		}
		return registration;
	}

	/*@RequestMapping(value = "/list", method = RequestMethod.POST)
	public @ResponseBody BulkFrameworkResponse listFiles(@RequestBody ListFilesRequest listFilesRequest) throws PaymentsCommonException{
		return new BulkFrameworkResponse(amazonService.listFilesForUser(listFilesRequest));

	}*/

	@RequestMapping(value = "/listPage", method = RequestMethod.POST)
	public @ResponseBody BulkFrameworkResponse listFilesWithPagination(@RequestBody ListFilesRequest listFilesRequest) throws PaymentsCommonException, BulkProcessorException{
		IBulkFileRegistration registration = getRegistration(listFilesRequest.getActivityId());

		Map<String, String> headerValues = getHeadersMap();
		if(registration.getValidator()!= null && !registration.getValidator().hasPermissionForAction(registration.getUserId(), BulkProcessEnum.LIST_PAGE, headerValues)){
			throw new BulkProcessorException("BPC-0010", BulkProcessorUtils.AUTHORIZATION_FAILED);
		}

		String emailId = registration.getUserId();
		listFilesRequest.setUserId(emailId);
		return new BulkFrameworkResponse(amazonService.listFilesForUserWithPagination(listFilesRequest, emailId));

	}

	@RequestMapping(value = "/listUserPageForSuperUser", method = RequestMethod.POST)
	public @ResponseBody BulkFrameworkResponse listFilesWithPaginationForSuperUser(@RequestBody ListFilesRequest listFilesRequest) throws PaymentsCommonException, BulkProcessorException{
		IBulkFileRegistration registration = getRegistration(listFilesRequest.getActivityId());

		Map<String, String> headerValues = getHeadersMap();
		if(registration.getValidator()!= null && !registration.getValidator().hasPermissionForAction(registration.getUserId(), BulkProcessEnum.LIST_PAGE_FOR_SUPERUSER, headerValues)){
			throw new BulkProcessorException("BPC-0010", BulkProcessorUtils.AUTHORIZATION_FAILED);
		}

		listFilesRequest.setSuperUser(false);
		return new BulkFrameworkResponse(amazonService.listFilesForUserWithPagination(listFilesRequest, listFilesRequest.getUserId()));

	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public @ResponseBody BulkFrameworkResponse downloadFile(@RequestParam String fileName, @RequestParam String userId, @RequestParam String activityId, @RequestParam boolean isInputFile) throws PaymentsCommonException, BulkProcessorException{
		IBulkFileRegistration registration = getRegistration(activityId);

		/*	Map<String, String> headerValues = getHeadersMap();
		if(registration.getValidator()!= null && !registration.getValidator().hasPermissionForAction(registration.getUserId(), BulkProcessEnum.DOWNLOAD_FILE, headerValues)){
			throw new BulkProcessorException("BPC-0010", BulkProcessorUtils.AUTHORIZATION_FAILED);
		}*/
		if(!isInputFile){
			fileName = registration.getOutputFileNameForInput(fileName);
		}
		String emailId = registration.getUserId();
		return new BulkFrameworkResponse(amazonService.generatePresignedUrl(fileName, activityId, isInputFile, userId));

	}

	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public @ResponseBody BulkFrameworkResponse getAllUsers(@RequestParam String activityId) throws PaymentsCommonException{
		return new BulkFrameworkResponse(amazonService.getAllUsersFolders(activityId));
	}

	
	
	@RequestMapping(value = "/getSampleFile", method = RequestMethod.POST)
	public @ResponseBody BulkFrameworkResponse getSampleFile(@RequestParam Map<String,String> allRequestParams) throws PaymentsCommonException, BulkProcessorException{
		if(!allRequestParams.containsKey(BulkProcessorUtils.ACTIVITY_ID)){
			throw new BulkProcessorException("BPC-0001", BulkProcessorUtils.ACTIVITY_ID_NOT_FOUND);
		}
		String activityId = allRequestParams.get(BulkProcessorUtils.ACTIVITY_ID);
		IBulkFileRegistration registration = getRegistration(activityId);
		String sampleFilePath = registration.getSampleFilePath(allRequestParams);
		return new BulkFrameworkResponse(sampleFilePath);

	}

	private String getEmailFromSession() throws OpsPanelException {
		String token = httpServletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);
	
		return emailId;
	}

	private FileValidationResult validateInputFile(File inputFile, Map<String,String> fileMetaData, IBulkFileRegistration registration) throws IOException {
		BufferedReader buffer = null;
		FileInputStream inputFileStream = new FileInputStream(inputFile);
		try {
			buffer = new BufferedReader(new InputStreamReader(inputFileStream, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		FileValidationResult fileValidationResult = new FileValidationResult();
		fileValidationResult.setValidationSuccess(true);
		fileValidationResult.setErrorMessage(null);
		fileValidationResult.setLineNumber(-1);
		ValidationResponse validation;
		validation = registration.getValidator().validate(fileMetaData, inputFile);

		if (validation != null) {
			if (!validation.isValid()) {
				log.info("Validation failed, Error = " + validation.getErrorCode() + " : " + validation.getErrorMessage());
				fileValidationResult.setErrorMessage("Validation failed, Error = " + validation.getErrorCode() + " : " + validation.getErrorMessage());
				fileValidationResult.setLineNumber(-1);
				fileValidationResult.setValidationSuccess(false);
				return fileValidationResult;
			}
		}
		if (registration.getValidator()!= null && registration.getValidator().shouldValidateEachRow()) {
			String firstLine = buffer.readLine();
			String[] headers = firstLine.split(",", -1);

			int headerSize = headers.length;

			for(int i=0; i<headerSize; i++){
				headers[i] = headers[i].trim();
			}
			String line = null;
			long rowNumber = 1;
			while ((line = buffer.readLine()) != null
					&& !line.trim().equalsIgnoreCase("")) {
				rowNumber++;
				log.info("Currently valiadting row number : " + rowNumber);
				String[] values = line.split(",", -1);
				int valueSize = values.length;

				for(int i=0; i<valueSize; i++){
					values[i] = values[i].trim();
				}
				ValidationResponse validateThisRow = registration
						.getValidator().validateRow(headers, values,
								fileMetaData);

				if (validateThisRow != null) {
					if (!validateThisRow.isValid()) {
						log.info("Validation failed at row number "+rowNumber+" , Error = "
								+ validateThisRow.getErrorCode() + " : "
								+ validateThisRow.getErrorMessage());
						fileValidationResult
						.setErrorMessage("Validation failed at row number "+rowNumber+" , Error = "
								+ validateThisRow.getErrorCode() + " : "
								+ validateThisRow.getErrorMessage());
						fileValidationResult.setLineNumber(rowNumber);
						fileValidationResult.setValidationSuccess(false);
						return fileValidationResult;
					}
				}
			}
		}
		return fileValidationResult;
	}

	public Map<String,String> getHeadersMap(){
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		Map<String,String> headerValues = new HashMap<String, String>();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			headerValues.put(headerName, httpServletRequest.getHeader(headerName));

		}
		return headerValues;
	}

}