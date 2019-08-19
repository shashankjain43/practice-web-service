package com.snapdeal.opspanel.batch.listeners;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;
import com.snapdeal.opspanel.Amazons3.request.UploadServiceRequest;
import com.snapdeal.opspanel.Amazons3.service.BulkRefundService;
import com.snapdeal.opspanel.Amazons3.utils.OpsPanelCommonsUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BulkRefundListener implements JobExecutionListener{


	@Autowired
	BulkRefundService bulkRefundService;

	public void beforeJob(JobExecution jobExecution) {
		log.info("before job");

	}

	public void afterJob(JobExecution jobExecution) {
		JobParameters jobParameters=jobExecution.getJobParameters();
		Map<String, JobParameter> map=jobParameters.getParameters();
		JobParameter emailParam=map.get("emailId");
		String userEmail=(String) emailParam.getValue();

		JobParameter inputFileParam=map.get("pathToFile");
		String pathToFile=(String) inputFileParam.getValue();

		JobParameter outputFileParam=map.get("pathToOutputFile");
		String pathToOutputFile=(String) outputFileParam.getValue();

		JobParameter fileNameParam=map.get("filename");
		String fileName=(String) fileNameParam.getValue();

		UploadServiceRequest uploadRequest = new UploadServiceRequest();
		uploadRequest.setEmail(userEmail);
		try {
			uploadRequest.setFileName(OpsPanelCommonsUtils.getOutputFilePathForCSV(fileName));
		} catch (PaymentsCommonException e) {
			log.info("BULK REFUND STEP : Error, file is not an csv");
		}
		uploadRequest.setFileSource(pathToOutputFile);

		uploadRequest.setUploadTime(new Date());
		uploadRequest.setInputFile(false);

		log.info("After job");
		boolean outfileuploadsuccess = true;
		try {
			String s3uploadedPath = bulkRefundService.uploadRefundFile(uploadRequest);
			log.info("BULK REFUND STEP: Output File Uploaded to the Amazon s3");
		} catch (PaymentsCommonException e) {
			log.info("BULK REFUND STEP : FAILURE IN UPLOADING OUTPUT FILE TO THE AMAZONS3");
			outfileuploadsuccess = false;
			log.info("BULK REFUND STEP : \n Output File Saved Since failure in UPLOADING FILE TO AMAZON");
		}
		
		boolean isInputFileDeleted=new File ((pathToFile)).delete();
		
		if(outfileuploadsuccess){
			new File ((pathToOutputFile)).delete();
		}
		if(!isInputFileDeleted ) {
			log.info("BULK REFUND STEP : \n\n Alert- Bulk Refund Input file not getting deleted \n\n");
		}
		
	}





}
