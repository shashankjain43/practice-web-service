package com.snapdeal.ims.snsClient;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.InternalErrorException;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;

/**
 * Created by dixant.mittal on 3/2/16.
 */

@Slf4j
@Component
public class IMSSNSClient {

	private static IMSSNSClient imsSNSClient = null;

	private AmazonSNSClient snsClient = null;

	@Value("${ims.sns.access.key}")
	private String accessKey;

	@Value("${ims.sns.secret.key}")
	private String secretKey;

	@Value("${ims.sns.regionName}")
	private String regionName;

	@Value("${ims.sns.topicArn}")
	private String topicArn;

	private void init() {

	   snsClient = new AmazonSNSClient(new BasicAWSCredentials(accessKey,
				secretKey));

		try {
			snsClient.setRegion(Region.getRegion(Regions.fromName(regionName)));
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}
		log.info("SNS Client Created.");
	}

	public boolean publishMessage(String message) {

		PublishRequest publishRequest = new PublishRequest(topicArn, message);
		try {
			PublishResult publishResult = snsClient.publish(publishRequest);
			log.info("Message sent Successfully with MessageID: "
					+ publishResult.getMessageId());
			return true;
		} catch (InternalErrorException e) {
			throw e;
		} catch (AmazonServiceException e) {
			log.error(e.getMessage());
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}
	}

	@PostConstruct
	public void loadSnsClient() {
		init();
	}
}
