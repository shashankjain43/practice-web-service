package com.snapdeal.vanila.bulkFOS;

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

@Component
public class BulkFOSRegistration extends IBulkFileRegistration {
	
	@Autowired
	BulkFOSRowProcessor fosProcessor;
	
	@Autowired
	BulkFOSValidator validator;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	@Qualifier("bulkFOSExecutor")
	ThreadPoolTaskExecutor taskExecuter;
	
	@Value("${bulk.localDir}")
	private String localDir;
	
	@Value("${snapdeal.sdMoneyDashboard.amazon.bucketName}")
	private String bucketName;

	@Value("${snapdeal.sdmoneydashboard.amazon.bulkfos.destinationprefix}")
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
	public String getBulkActivityId() {
		return BulkFOSConstants.BULK_FOS_ACTIVITY_ID;
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
		return fosProcessor;
	}

	@Override
	public AmazonConfigStore getS3Config() {
		return new AmazonConfigStore(bucketName, destinationPrefix, accessKeyId , secretAccessKey, downloadUrlExpirationTime, localDownloadPath);
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
		// TODO Auto-generated method stub
		return validator;
	}

}
