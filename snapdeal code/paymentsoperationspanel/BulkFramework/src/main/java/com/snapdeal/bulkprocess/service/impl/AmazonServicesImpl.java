package com.snapdeal.bulkprocess.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.snapdeal.bulkprocess.exception.BulkProcessorException;
import com.snapdeal.bulkprocess.factory.BulkRegistrationFactory;
import com.snapdeal.bulkprocess.model.FileModel;
import com.snapdeal.bulkprocess.model.FileDTO;
import com.snapdeal.bulkprocess.model.ListFilesRequest;
import com.snapdeal.bulkprocess.model.UploadRequest;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.bulkprocess.service.AmazonServices;
import com.snapdeal.bulkprocess.service.BulkService;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;
import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;
import com.snapdeal.opspanel.Amazons3.response.ListObjectsWithMarker;
import com.snapdeal.opspanel.Amazons3.utils.GenericAmazonS3Utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("amazonService")
@Data
public class AmazonServicesImpl implements AmazonServices {



	

	/*	@Autowired
	private IBulkFileRegistration bulkFileRegistration;*/

	@Autowired 
	BulkRegistrationFactory bulkRegistrationFactory;

	@Autowired
	BulkService bulkService;

	public String uploadToS3(UploadRequest request) throws BulkProcessorException, PaymentsCommonException {

		String checkInputFile;
		String destination;

		IBulkFileRegistration registration=bulkRegistrationFactory.getIBulkFileRegistrationInfo(request.getActivityId());
		GenericAmazonS3Utils amazonS3= registration.getAmazonS3();

		Map<String, String> metaData = new HashMap<String, String>();
		// Check if it is Input / Output File


		if (request.isInputFile()) {
			checkInputFile = "YES";
			destination=registration.getDestinationS3PathForInputFile(request.getFileName(),request.getUserId());
			// Filling up Usermetadata from request Object

			String date = dateToString(request.getUploadTime());

			metaData.put(BulkProcessorUtils.FILE_NAME, request.getFileName());
			metaData.put(BulkProcessorUtils.UPLOADED_BY, request.getUserId());
			metaData.put(BulkProcessorUtils.UPLOADED_TIME, date);
			metaData.put(BulkProcessorUtils.INPUT, checkInputFile);
			/*metaData.put(BulkProcessorUtils.ACTIVITY_ID, registration.getBulkActivityId());*/
			
			Map<String, String> requestMetaData = request.getInputRequestParams();
			for (Map.Entry<String, String> entry : requestMetaData.entrySet())
			{
				metaData.put(entry.getKey(), entry.getValue());
			}
			
		} else {
			checkInputFile = "NO";
			destination =registration.getDestinationS3PathForOutputFile(request.getFileName(),request.getUserId()); 

		}

		// Check if file is already present on the server or not

		if (amazonS3.isValidFile(destination)) {
			return amazonS3.getDestinationPrefix() + destination;
			/*throw new PaymentsCommonException("File already exist. Please upload a new file.");*/
		}

		amazonS3.putObject(request.getFileSource(), destination, metaData);
		return amazonS3.getDestinationPrefix() + destination;

	}

	public boolean validateFile(String fileSource) {

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileSource);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(inputStreamReader);

			String line;
			br.readLine();

			int numberOfRows = 0;

			while ((line = br.readLine()) != null) {
				numberOfRows++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public File localDownloadFile(String destination, String fileName, String activityId)
			throws BulkProcessorException, PaymentsCommonException {

		IBulkFileRegistration registration=bulkRegistrationFactory.getIBulkFileRegistrationInfo(activityId);
		GenericAmazonS3Utils amazonS3= registration.getAmazonS3();

		String localDownloadPath =registration.getLocalDir();
		File localFile = new File(localDownloadPath + fileName);
		amazonS3.localDownload(destination, localFile);
		return localFile;
	}

	public URL generatePresignedUrl(String fileName, String activityId, boolean isInputFile,String userId) throws PaymentsCommonException {
		IBulkFileRegistration registration=bulkRegistrationFactory.getIBulkFileRegistrationInfo(activityId);
		GenericAmazonS3Utils amazonS3= registration.getAmazonS3();
		String destination;
		if(isInputFile) {
			destination =registration.getDestinationS3PathForInputFile(fileName,userId);
		} else {
			destination =registration.getDestinationS3PathForOutputFile(fileName,userId);
		}
		return amazonS3.generatePresignedUrl(destination);
	}
	
	/*public FileDTO listFilesForUser(ListFilesRequest listFilesRequest) throws PaymentsCommonException {
		IBulkFileRegistration registration=bulkRegistrationFactory.getIBulkFileRegistrationInfo(listFilesRequest.getActivityId());
		GenericAmazonS3Utils amazonS3= registration.getAmazonS3();
		List<String> inputFiles = amazonS3.listFilesWithPagination(registration.getListingPathForUserAndActivity(listFilesRequest.getUserId(), listFilesRequest.getActivityId(), true), listFilesRequest.getPageNumber(), listFilesRequest.getPageSize());
		List<FileModel> fileModels = new ArrayList<FileModel>();
		for(String inputFile : inputFiles) {
			inputFile = inputFile.replaceAll("/", "");
			FileModel fileModel = new FileModel();
			fileModel.setInput(inputFile);
			fileModel.setOutput("output_" + inputFile);
			String inputFileDestination = registration.getDestinationS3PathForInputFile(inputFile);
			fileModel.setAllMetaData(amazonS3.getFileMetaData(inputFileDestination));
			
			fileModels.add(fileModel);
			
		}
		FileDTO allFiles =  new FileDTO();
		allFiles.setFiles(fileModels);
		allFiles.setNextMarker(null);
		return allFiles;
		
	}*/
	
	public FileDTO listFilesForUserWithPagination(ListFilesRequest listFilesRequest, String userId) throws PaymentsCommonException {
		IBulkFileRegistration registration = bulkRegistrationFactory.getIBulkFileRegistrationInfo(listFilesRequest.getActivityId());
		GenericAmazonS3Utils amazonS3= registration.getAmazonS3();
		String prefix = null;
		String outputPrefix = null;
		if(listFilesRequest.isSuperUser() == true){
			prefix = registration.getListingPathForSuperUser(listFilesRequest.getActivityId(), true);
			outputPrefix = registration.getListingPathForSuperUser(listFilesRequest.getActivityId(), false);
		} else {
			prefix = registration.getListingPathForUserAndActivity(listFilesRequest.getUserId(), listFilesRequest.getActivityId(), true);
			outputPrefix = registration.getListingPathForUserAndActivity(listFilesRequest.getUserId(), listFilesRequest.getActivityId(), false);
		}
		ListObjectsWithMarker listObjectsWithMarker = amazonS3.listNextFiles(prefix, listFilesRequest.getMarker(), listFilesRequest.getPageNumber(), listFilesRequest.getPageSize() );
		/*ListObjectsWithMarker listObjectsWithMarkerForOutput = amazonS3.listNextFiles(outputPrefix, listFilesRequest.getMarker(), listFilesRequest.getPageNumber(), listFilesRequest.getPageSize() );
		
		Set<String> outputFilesSet = new HashSet<String>();
		List<String> outputFiles = listObjectsWithMarkerForOutput.getFiles();
		for(String outputFile : outputFiles){
			outputFile = outputFile.substring(outputFile.lastIndexOf("/")+1);
			outputFilesSet.add(outputFile);
		}*/
		
		
		List<FileModel> fileModels = new ArrayList<FileModel>();
		for(String inputFile : listObjectsWithMarker.getFiles()) {
			/*inputFile = inputFile.replaceAll("/", "");*/
			FileModel fileModel = new FileModel();
/*			fileModel.setInput(inputFile);
			fileModel.setOutput(registration.getOutputFileNameForInput(inputFile));*/
			String inputFileDestination = prefix + inputFile;
			Map<String,String> fileMetaData = amazonS3.getFileMetaData(inputFileDestination);
			fileModel.setAllMetaData(fileMetaData);
			fileModel.setInputFileName(fileMetaData.get("filename"));
			fileModel.setOutputFileName(registration.getOutputFileNameForInput(fileMetaData.get("filename")));
			fileModel.setActivityId(fileMetaData.get("activityid"));
			
			String outputDestination = registration.getDestinationS3PathForOutputFile(fileModel.getOutputFileName(), fileModel.getAllMetaData().get("uploadedby"));
			if(amazonS3.isValidFile(outputDestination)){
				fileModel.setHasOutputFile(true);
			} else {
				fileModel.setHasOutputFile(false);
			}
			
			fileModels.add(fileModel);
			
		}
		FileDTO allFiles =  new FileDTO();
		allFiles.setFiles(fileModels);
		allFiles.setPrevMarker(listFilesRequest.getPrevMarker());
		allFiles.setNextMarker(listObjectsWithMarker.getNextMarker());
		return allFiles;
		
	}
	
	public List<String> getAllUsersFolders(String activityId) throws PaymentsCommonException{
		IBulkFileRegistration registration = bulkRegistrationFactory.getIBulkFileRegistrationInfo(activityId);
		GenericAmazonS3Utils amazonS3= registration.getAmazonS3();
		String prefix = registration.getListingPathForSuperUser(activityId, true);
		List<S3ObjectSummary> s3ObjectSummaries = new ArrayList<S3ObjectSummary>();
		s3ObjectSummaries = amazonS3.listKeys(prefix);
		Set<String> allUsers = new HashSet<String>();
		for(S3ObjectSummary objectSummary : s3ObjectSummaries ){
			String key = objectSummary.getKey();
			int lastIdxOfSlash = -1;
			int secondLastIdxOfSlash = -1;
			lastIdxOfSlash = key.lastIndexOf("/");
			if(lastIdxOfSlash != -1){
				String remainingString = key.substring(0, lastIdxOfSlash);
				secondLastIdxOfSlash =  remainingString.lastIndexOf("/");
				if(secondLastIdxOfSlash != -1){
					allUsers.add(key.substring(secondLastIdxOfSlash+1, lastIdxOfSlash));
				}
			}
			
		}
		
		List<String> allUsersList = new ArrayList<String>(allUsers);
		return allUsersList;
		
	}
	
	
	public String dateToString(Date date) throws PaymentsCommonException {
		try {

			SimpleDateFormat formatter = new SimpleDateFormat("EEE dd/MM/yyyy hh:mm:ss a z");
			return formatter.format(date);

		} catch (Exception e) {
			log.info("Error While parsing date to String" + e);
			throw new PaymentsCommonException("Error in Parsing date . Please check the date format." + e);
		}
	}



}
