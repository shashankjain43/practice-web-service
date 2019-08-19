package com.snapdeal.opspanel.userpanel.walletreversal.registration;

import javax.servlet.http.HttpServletRequest;

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
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.walletreversal.constants.WalletReversalConstants;
import com.snapdeal.opspanel.userpanel.walletreversal.processor.WalletReversalProcessor;
import com.snapdeal.opspanel.userpanel.walletreversal.validator.WalletReversalValidator;

@Component
public class WalletReversalRegistration extends IBulkFileRegistration{

	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Autowired
	WalletReversalProcessor processor;
	
	@Autowired
	WalletReversalValidator validator;
	
	@Autowired
	@Qualifier("bulkReversalExecutor")
	ThreadPoolTaskExecutor taskExecuter;
	
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
	
	@Autowired
	private TokenService tokenService;

	@Override
	public String getBulkActivityId() {
		
		return WalletReversalConstants.WALLET_REVERSAL_ACTIVITYID;
	}

	@Override
	public ThreadPoolTaskExecutor getExecutor() {
		
		return taskExecuter;
	}

	@Override
	public String getLocalDir() {
		
		return localDir;
	}

	@Override
	public IRowProcessor getProcessor() {
		return processor;
	}

	@Override
	public AmazonConfigStore getS3Config() {
		return new AmazonConfigStore(bucketName, destinationPrefix+WalletReversalConstants.WALLET_REVERSAL_DESTINATION_PREFIX, accessKeyId , secretAccessKey, downloadUrlExpirationTime, localDownloadPath);
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
	public IBulkValidator getValidator() {
		return validator;
	}
	
	@Override
	public long getChunkSize() {
		return 1000l;
	}

}

