package com.snapdeal.bulkprocess.service;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.snapdeal.bulkprocess.exception.BulkProcessorException;
import com.snapdeal.bulkprocess.model.FileDTO;
import com.snapdeal.bulkprocess.model.FileModel;
import com.snapdeal.bulkprocess.model.ListFilesRequest;
import com.snapdeal.bulkprocess.model.UploadRequest;
import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;


public interface AmazonServices {

	
	public String uploadToS3(UploadRequest request) throws BulkProcessorException, PaymentsCommonException;
	
	public String dateToString(Date date) throws PaymentsCommonException;
	
	
	public boolean validateFile(String fileSource);
	
	public File localDownloadFile(String destination,String fileName,String activityId) throws BulkProcessorException, PaymentsCommonException;

	public FileDTO listFilesForUserWithPagination(ListFilesRequest listFilesRequest,String userId) throws PaymentsCommonException;
	
	public URL generatePresignedUrl(String fileName, String activityId, boolean isInputFile, String emailId) throws PaymentsCommonException;

	public List<String> getAllUsersFolders(String activityId) throws PaymentsCommonException;
}
