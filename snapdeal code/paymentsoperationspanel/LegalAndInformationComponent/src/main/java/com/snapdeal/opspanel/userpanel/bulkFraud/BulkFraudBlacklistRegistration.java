package com.snapdeal.opspanel.userpanel.bulkFraud;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.registration.AmazonConfigStore;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.constants.AWSConstants;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.bulkReverseTxn.BulkReverseTxnProcessor;

@Component
public class BulkFraudBlacklistRegistration extends  IBulkFileRegistration{

	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Autowired
	@Qualifier("bulkPromotionExecutor")
	ThreadPoolTaskExecutor taskExecuter;
	
	@Autowired
	BulkFraudBlacklistProcessor processor;
	
	@Autowired
	BulkFraudBlacklistValidator validator;
	
	@Autowired
	private TokenService tokenService;
	
	@Value("${bulk.localDir}")
	private String localDir;
	
	@Value("${snapdeal.sdMoneyDashboard.amazon.bucketName}")
	private String bucketName;

	@Value("${snapdeal.sdMoneyDashboard.amazon.destinationPrefix}")
	private String destinationPrefix;

	@Value("${snapdeal.sdMoneyDashboard.amazon.accessKeyId}")
	private String accessKeyId;

	@Value("${snapdeal.sdMoneyDashboard.amazon.secretAccessKey}")
	private String secretAccessKey;

	@Value("${snapdeal.sdMoneyDashboard.amazon.downloadUrlExpirationTime}")
	private long downloadUrlExpirationTime;

	@Value("${snapdeal.sdMoneyDashboard.amazon.localDownloadPath}")
	private String localDownloadPath;
	
	@Override
	public AmazonConfigStore getS3Config() {
		return new AmazonConfigStore(bucketName, destinationPrefix + AWSConstants.BULK_FRAUD, accessKeyId , secretAccessKey, downloadUrlExpirationTime, localDownloadPath);
	}

	@Override
	public String getBulkActivityId() {
		// TODO Auto-generated method stub
		return BulkFraudBlacklistConstatnts.BULK_FRAUD_ACTIVITY_ID;
	}

	@Override
	public String getUserId() {
		String token = httpServletRequest.getHeader("token");
		String emailId = null;
		try {
			emailId = tokenService.getEmailFromToken(token);
		} catch (OpsPanelException e) {
			return null;
		}

		return emailId;
	}

	@Override
	public ThreadPoolTaskExecutor getExecutor() {
		// TODO Auto-generated method stub
		return taskExecuter;
	}

	@Override
	public String getLocalDir() {
		// TODO Auto-generated method stub
		return localDir;
	}

	@Override
	public IRowProcessor getProcessor() {
		// TODO Auto-generated method stub
		return processor;
	}

	@Override
	public IBulkValidator getValidator() {
		// TODO Auto-generated method stub
		return validator;
	}
}
