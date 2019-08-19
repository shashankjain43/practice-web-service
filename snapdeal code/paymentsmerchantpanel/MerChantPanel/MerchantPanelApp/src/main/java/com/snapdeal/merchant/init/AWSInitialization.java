package com.snapdeal.merchant.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.snapdeal.merchant.config.MpanelConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AWSInitialization {
	
	@Autowired
	private MpanelConfig config;

	@Bean
	public AmazonS3Client initAWS() throws Exception {
		try {
			AmazonS3Client s3client = new AmazonS3Client(new BasicAWSCredentials(config.getS3AccessKey(), config.getS3SecretKey()));
			s3client.setEndpoint(config.getS3EndPoint());
			return s3client;
		} catch (Exception e) {
			log.error("Error occurred in aggregator initialization {}", e);
			throw e;
		}
	}

}
