package com.snapdeal.opspanel.Amazons3.service.impl;

import java.io.File;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;
import com.snapdeal.opspanel.Amazons3.request.DownloadRefundFileRequest;
import com.snapdeal.opspanel.Amazons3.request.UploadServiceRequest;
import com.snapdeal.opspanel.Amazons3.response.FileListingResponse;
import com.snapdeal.opspanel.Amazons3.service.BulkRefundService;
import com.snapdeal.opspanel.Amazons3.utils.GenericAmazonS3Utils;
import com.snapdeal.opspanel.Amazons3.utils.OpsPanelCommonsUtils;

@Slf4j
@Service("BulkRefundService")
@Validated
public class BulkRefundServiceImpl implements BulkRefundService {

	@Value("${snapdeal.sdMoneyDashboard.amazon.bulkrefund.destinationPrefix}")
	private String destinationPrefix;
	

	@Value("${snapdeal.sdMoneyDashboard.amazon.bucketName}")
	private String bucketName;
	

	@Value("${snapdeal.sdMoneyDashboard.amazon.accessKeyId}")
	private String accessKeyId;
	

	@Value("${snapdeal.sdMoneyDashboard.amazon.secretAccessKey}")
	private String secretAccessKey;
	

	@Value("${snapdeal.sdMoneyDashboard.amazon.downloadUrlExpirationTime}")
	private long downloadUrlExpirationTime;
	

	@Value("${snapdeal.sdMoneyDashboard.amazon.localDownloadPath}")
	private String localDownloadPath;
	

	private String checkInputFile;
	private String destination;
	
	

	private final String FILE_NAME = "filename";
	private final String MERCHANNT_ID = "merchantid";
	private final String MERCHANT_NAME = "merchantname";
	private final String UPLOADED_BY = "uploadedby";
	private final String UPLOADED_TIME = "uploadedtime";
	private final String INPUT = "input";
	private final String OUTPUT = "output";
	private final String REFUNDKEY="refundkey";

	GenericAmazonS3Utils s3client;
	
	//Method For initialising S3 client
	@PostConstruct
	public void init() throws PaymentsCommonException {
		try {
			s3client = new GenericAmazonS3Utils(destinationPrefix,
					bucketName, accessKeyId, secretAccessKey,
					downloadUrlExpirationTime);
		} catch( Exception e ) {
			if(log.isInfoEnabled()){
			log.info("Exception While Initialising the s3 client");
			}
			throw new PaymentsCommonException( e.getMessage() );
		}
	}

	//Method for Uploading refundfile to the Amazon s3 server
	@Override
	public String uploadRefundFile(UploadServiceRequest request)
			throws PaymentsCommonException {

		Map<String, String> userMetadata = new HashMap<String, String>();
		// Check if it is Input / Output File

		if (request.isInputFile()) {
			checkInputFile = "YES";
			destination = "/" + INPUT + "/"
					+ request.getEmail().replace("@", ".") + "/"
					+ request.getFileName();
			// Filling up Usermetadata from request Object

			String date = dateToString(request.getUploadTime());
			
			userMetadata.put(FILE_NAME, request.getFileName());
			userMetadata.put(MERCHANNT_ID, request.getMerchantId());
			userMetadata.put(MERCHANT_NAME, request.getMerchantName());
			userMetadata.put(UPLOADED_BY, request.getEmail());
			userMetadata.put(REFUNDKEY, request.getRefundKey());
			userMetadata.put(UPLOADED_TIME, date);
			userMetadata.put(INPUT, checkInputFile);
		} else {
			checkInputFile = "NO";
			destination = "/" + OUTPUT + "/"
					+ request.getEmail().replace("@", ".") + "/"
					+ request.getFileName();

		}

		// Check if file is already present on the server or not

		if (s3client.isValidFile(destination)) {
			throw new PaymentsCommonException(
					"File already exist. Please upload a new file.");
		}

		s3client.putObject(request.getFileSource(), destination, userMetadata);
		return destinationPrefix+destination;

	}

	//Method For getting File Listing For A particular User
	@Override
	public List<FileListingResponse> getFileListings(String email)
			throws PaymentsCommonException {

		String prefix = "/" + INPUT + "/" + email.replace("@", ".") + "/";

		// Get All Output File
		String outputPrefix = "/" + OUTPUT + "/" + email.replace("@", ".")
				+ "/";
		
		HashSet<String> set = new HashSet<String>();
		set = convertListToSet(s3client.listFiles(outputPrefix));

		List<String> fileList = s3client.listFiles(prefix);

		List<FileListingResponse> fileListingResponseList = new ArrayList<FileListingResponse>();

		for (String file : fileList) {
			FileListingResponse fileListingResponse = new FileListingResponse();
			fileListingResponse.setFileName(file);
			if (set.contains(OpsPanelCommonsUtils.getOutputFilePathForCSV(file))) {
				fileListingResponse.setHasOutput(true);
			} else {
				fileListingResponse.setHasOutput(false);
			}

			Map<String, String> fileMetaData = null;

			fileMetaData = s3client.getFileMetaData(prefix  + file);
			fileListingResponse.setEmail(fileMetaData.get(UPLOADED_BY));
			try {
				String fileUploadTime = fileMetaData.get(UPLOADED_TIME);
				Date date = stringToDate(fileUploadTime);
				fileListingResponse.setUploadTime(date);
			} catch (Exception e) {
				if(log.isDebugEnabled()){
				log.info("Excception while parsing date from amazon s3 file "
						+ fileMetaData + " " + e);
				}
				throw new PaymentsCommonException(
						"Error In Listing While Parsing");
			}

			fileListingResponse.setMerchantId(fileMetaData.get(MERCHANNT_ID));
			fileListingResponse
					.setMerchantName(fileMetaData.get(MERCHANT_NAME));
			fileListingResponse.setRefundKey(fileMetaData.get(REFUNDKEY));
			if (fileMetaData.get(INPUT).equalsIgnoreCase("Yes")) {
				fileListingResponseList.add(fileListingResponse);
			}

		}
		Collections.sort(fileListingResponseList);
		return fileListingResponseList;

	}
	
	//Retrieving FileLIsting From s3 for SuperUser
	@Override
	public List<FileListingResponse> getAllFileListings()
			throws PaymentsCommonException {

		String prefix = "/" + INPUT + "/";
		
		// Get All Output File
		String outputPrefix = "/" + OUTPUT + "/" ;
		
		HashSet<String> set = new HashSet<String>();
		set = convertListToSet(s3client.listFiles(outputPrefix));
		
		List<String> fileList = s3client.listFiles(prefix);
		
		List<FileListingResponse> fileListingResponseList = new ArrayList<FileListingResponse>();
		
				
		
		for (String file : fileList) {
			FileListingResponse fileListingResponse = new FileListingResponse();
			fileListingResponse.setFileName(file.substring(file
					.lastIndexOf("/") + 1));
			
			//Check if the 
			if (set.contains(OpsPanelCommonsUtils.getOutputFilePathForCSV(file))) {
				fileListingResponse.setHasOutput(true);
			} else {
				fileListingResponse.setHasOutput(false);
			}
			
			Map<String, String> fileMetaData = null;

			fileMetaData = s3client.getFileMetaData(prefix  + file);
			fileListingResponse.setEmail(fileMetaData.get(UPLOADED_BY));
			fileListingResponse.setRefundKey(fileMetaData.get(REFUNDKEY));
			try {
				String fileUploadTime = fileMetaData.get(UPLOADED_TIME);
				Date date = stringToDate(fileUploadTime);
				fileListingResponse.setUploadTime(date);
			} catch (Exception e) {
				if(log.isInfoEnabled()){
				log.info("Excception while parsing date from amazon s3 file "
						+ fileMetaData + " " + e);
				}
				/*throw new PaymentsCommonException(
						"Error In Listing While Parsing");*/
			}

			fileListingResponse.setMerchantId(fileMetaData.get(MERCHANNT_ID));
			fileListingResponse
					.setMerchantName(fileMetaData.get(MERCHANT_NAME));

			if (fileMetaData.get(INPUT).equalsIgnoreCase("Yes")) {
				fileListingResponseList.add(fileListingResponse);
			}

		}
		Collections.sort(fileListingResponseList);
		return fileListingResponseList;
	}

	//Method For Downloading Refund File by giving a download link to the UI
	@Override
	public URL downloadRefundFile(DownloadRefundFileRequest request)
			throws PaymentsCommonException {

		if (request.isInputFile()) {
			destination = "/" + INPUT + "/"
					+ request.getEmailId().replace("@", ".") + "/"
					+ request.getFileName();

		} else {
			destination = "/" + OUTPUT + "/"
					+ request.getEmailId().replace("@", ".") + "/"
					+ OpsPanelCommonsUtils.getOutputFilePathForCSV(request.getFileName());

		}

		if (!s3client.isValidFile(destination)) {
			throw new PaymentsCommonException(
					"File does not exist. Hence , can not be downloaded");
		}
		return s3client.generatePresignedUrl(destination);

	}

	//Getting File Meta Data For File Listing Process
	@Override
	public Map<String, String> getFileMetaData(String email, String fileName)
			throws PaymentsCommonException {

		String destination = "/" + INPUT + "/" + email.replace("@", ".") + "/"
				+ fileName;
		if (!s3client.isValidFile(destination)) {
			throw new PaymentsCommonException(
					"File does not exist. Thus, not able to get metadata.");
		}
		return s3client.getFileMetaData(destination);

	}

	//Downloading File From S3 to local machine specified location
	@Override
	public boolean localdownloadFile(DownloadRefundFileRequest request)
			throws PaymentsCommonException {

		if (request.isInputFile()) {
			String amazonFilePath = destinationPrefix + "/" + INPUT + "/"
					+ request.getEmailId().replace("@", ".") + "/"
					+ request.getFileName();
			File localFile = new File(localDownloadPath + request.getFileName());
			GenericAmazonS3Utils s3client;
			try {
				s3client = new GenericAmazonS3Utils(destinationPrefix,
						bucketName, accessKeyId, secretAccessKey,
						downloadUrlExpirationTime);
				return s3client.localDownload(amazonFilePath, localFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(log.isInfoEnabled()){
				log.info("Error in Downloading File to local. Please check local path" + e.getMessage());
				}
				throw new PaymentsCommonException("Error in Downloading File to local. Please check local path" + e);
			}
			
		} else {
			return false;
		}

	}
	
	//Check if the Refund Key (i.e. Idempotency ID is already used or not
	@Override
	public boolean checkRefundKey(@NotNull String key) throws PaymentsCommonException{
		
		String prefix = "/" + INPUT + "/";
		key = key.trim();
		List<String> fileList = s3client.listFiles(prefix);
		for (String file : fileList) {
			Map<String, String> fileMetaData = null;
			fileMetaData = s3client.getFileMetaData(prefix  + file);
			try{
			if(key.equals(fileMetaData.get(REFUNDKEY))){
				return true;
			}
			}catch(NullPointerException e){
				if(log.isInfoEnabled()){
				log.info("BULK REFUND SEVICE: REFUND KEY CAN NOT BE LEFT NULL" + e);
				log.info(e.getMessage());
				}
				throw new PaymentsCommonException("REFUND KEY CAN NOT BE LEFT NULL");
			}
		}
		return false;
		
	}
	

	public HashSet<String> convertListToSet(List<String> list) {
		if (!list.isEmpty()) {
			HashSet<String> set = new HashSet<String>();
			for (String each : list) {
				set.add(each);
			}
			return set;
		} else {
			return new HashSet<String>();
		}
	}
	
	public String dateToString(Date date) throws PaymentsCommonException{
		try{
		
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			return formatter.format(date);
		
		}catch(Exception e){
			if(log.isInfoEnabled()){
			log.info("Error While parsing date to String" + e);
			}
			throw new PaymentsCommonException("Error in Parsing date . Please check the date format." + e);
		}
	}
	
	public Date stringToDate(String date) throws PaymentsCommonException {
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			return formatter.parse(date);
		}catch(Exception e){
			if(log.isInfoEnabled()){
			log.info("Error while parsing from String to date" + e);
			}
			throw new PaymentsCommonException("Error in Parsing String . Please check the String format." + e);
		}
	}
}
