package com.snapdeal.opspanel.bulk.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.batch.listeners.BulkRefundListener;
import com.snapdeal.opspanel.bulk.service.BulkService;

@EnableBatchProcessing
@Service("BulkJobService")
public class BulkJobServiceImpl implements BulkService {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	BulkRefundListener listener;
	
	@Autowired
	private JobRepositoryFactoryBean jobRepositoryFactoryBean;

	@Autowired
	@Qualifier("bulkStep")
	private Step step;
	

	public void submitjob(String localFilePath,String outputFilePath,String emailId,String fileName, String refundKey,String merchantId) throws Exception {
		// TODO Auto-generated method stub

		JobParameter pathToFile = new JobParameter(localFilePath);
		JobParameter pathToOFile = new JobParameter(outputFilePath);
		JobParameter email = new JobParameter(emailId);
		JobParameter fileParam = new JobParameter(fileName);
		JobParameter refundKeyParam= new JobParameter(refundKey);
		JobParameter merchantIdParam= new JobParameter(merchantId);
		Map<String, JobParameter> jobMap = new HashMap<String, JobParameter>();
		jobMap.put("pathToFile", pathToFile);
		jobMap.put("pathToOutputFile",  pathToOFile);
		jobMap.put("emailId",  email);
		jobMap.put("filename", fileParam);
		jobMap.put("merchantId", merchantIdParam);
		jobMap.put("refundKeyParam", refundKeyParam);
		final JobParameters jobParameters = new JobParameters(jobMap);

		Job job;
		job = jobs.get(refundKey +"_"+ new Date().getTime() +"_"+ new Random().nextLong()).start(step).listener(listener).repository(jobRepositoryFactoryBean.getObject()).build();
		jobLauncher.run(job, jobParameters);
	}
}