package com.snapdeal.bulkprocess.configuration;

import java.util.List;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.snapdeal.bulkprocess.registration.AmazonConfigStore;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;
import com.snapdeal.opspanel.Amazons3.utils.GenericAmazonS3Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableAsync
public class BulkConfiguration {

	@Autowired
	List<IBulkFileRegistration> registrationList;

	@PostConstruct
	public void  setConfig(){
		
		for (IBulkFileRegistration registration : registrationList) {
			log.info("Initiating Amazon Client!");
			AmazonConfigStore amazonConfigStore= registration.getS3Config();
			try {
				registration.setAmazonS3(new GenericAmazonS3Utils(amazonConfigStore.getDestinationPrefix(),
						amazonConfigStore.getBucketName(), amazonConfigStore.getAccessKeyId(),
						amazonConfigStore.getSecretAccessKey(), amazonConfigStore.getDownloadUrlExpirationTime()));

			} catch (Exception e) {
				log.info("Failed to initiate Amazon Client!");

			}
		}
	}

/*	@Bean(name=BulkProcessorUtils.EXECUTOR_NAME)
	public Executor getAsyncExecutor() {

		int minThreads = Integer.MAX_VALUE;
		for (IBulkFileRegistration registration : registrationList) {
			if (registration.getThreadPoolSize() < minThreads) {
				minThreads = registration.getThreadPoolSize();
			}
		}
		if(minThreads>50) {
			minThreads=50;
		}
		int maxPoolSize= minThreads;
		minThreads-=(minThreads-0)/4;

		ThreadPoolTaskExecutor customExecutor = new ThreadPoolTaskExecutor();
		customExecutor.setMaxPoolSize(maxPoolSize);
		customExecutor.setThreadNamePrefix(BulkProcessorUtils.EXECUTOR_NAME);
		customExecutor.setCorePoolSize(minThreads);
		customExecutor.setQueueCapacity(1000000);
		customExecutor.initialize();

		return customExecutor;
	}*/

	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return null;
	}

}
