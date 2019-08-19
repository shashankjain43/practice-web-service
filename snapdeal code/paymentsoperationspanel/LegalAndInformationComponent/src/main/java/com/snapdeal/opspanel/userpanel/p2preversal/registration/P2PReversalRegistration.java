package com.snapdeal.opspanel.userpanel.p2preversal.registration;

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
import com.snapdeal.opspanel.userpanel.p2preversal.constants.P2PReversalConstants;
import com.snapdeal.opspanel.userpanel.p2preversal.processor.P2PReversalProcessor;
import com.snapdeal.opspanel.userpanel.p2preversal.validator.P2PReversalValidator;
@Component
public class P2PReversalRegistration extends IBulkFileRegistration{

	@Autowired
	HttpServletRequest httpServletRequest;
	
	@Autowired
	P2PReversalProcessor processor;
	
	@Autowired
	P2PReversalValidator validator;
	
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
		
		return P2PReversalConstants.P2P_PARTIAL_REVERSAL;
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
		return new AmazonConfigStore(bucketName, destinationPrefix+P2PReversalConstants.P2P_PARTIAL_REVERSAL_DESTINATION_PREFIX, accessKeyId , secretAccessKey, downloadUrlExpirationTime, localDownloadPath);
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
		return 10000l;
	}

}
