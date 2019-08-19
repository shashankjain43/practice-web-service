package com.snapdeal.vanila.bulk.merchanthierarchy.merchant.registration;

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
import com.snapdeal.opspanel.constants.AWSConstants;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.processor.BulkMerchantHierarchyMerchantProcessor;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.utils.BulkMerchantHierarchyMerchantConstants;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.validator.BulkMerchantHierarchyMerchantValidator;
import com.snapdeal.vanila.enums.BulkMerchantHeirarchyActivityIds;

@Component
public class BulkMerchantHierarchyMerchantRegistration extends IBulkFileRegistration{

	@Autowired
	BulkMerchantHierarchyMerchantProcessor processor;
	
	@Autowired
	BulkMerchantHierarchyMerchantValidator validator;
	
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
		// TODO Auto-generated method stub
		return new AmazonConfigStore(bucketName, destinationPrefix + AWSConstants.BULK_MERCHANT_HIERARCHY_MERCHANT_DESTINATION_PREFIX , accessKeyId , secretAccessKey, downloadUrlExpirationTime, localDownloadPath);
	}

	@Override
	public String getBulkActivityId() {
		// TODO Auto-generated method stub
		return BulkMerchantHeirarchyActivityIds.MH_MERCHANT_ACTIVITY.toString();
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
	
	@Override
	public long getChunkSize() {
		return 10000l;
	}


}
