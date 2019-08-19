package com.snapdeal.opspanel.promotion.bulkframework;

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
import com.snapdeal.opspanel.promotion.constant.BulkPromotionConstants;
import com.snapdeal.opspanel.promotion.model.BulkPromotionSharedObject;


@Component
public class BulkPromotionRegistration extends IBulkFileRegistration {

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

	@Value("${file.size}")
	private Integer filesize;

	@Autowired
	BulkPromotionRowProcessor bulkPromotionRowProcessor;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	@Qualifier( "bulkPromotionExecutor" )
	ThreadPoolTaskExecutor taskExecutor;

	@Override
	public String getBulkActivityId() {
		return BulkPromotionConstants.BULK_PROMOTION_ACTIVITY_ID;
	}

	@Override
	public String getLocalDir() {
		return localDir; 
	}

	@Override
	public IRowProcessor getProcessor() {
		return bulkPromotionRowProcessor;
	}

	@Override
	public AmazonConfigStore getS3Config() {
		AmazonConfigStore amazonConfigStore = new AmazonConfigStore(
				bucketName,
				destinationPrefix,
				accessKeyId,
				secretAccessKey,
				downloadUrlExpirationTime,
				localDownloadPath
				);

		return amazonConfigStore;
	}

	@Override
	public String getUserId() {
		return null;
	}

	@Override
	public IBulkValidator getValidator() {
		return null;
	}

	@Override
	public String getDestinationS3PathForInputFile(String fileName,String userId) {
		String emailId = userId;
		return emailId.substring( 0, emailId.indexOf("@") ) + "/" + fileName;
	}

	@Override
	public String getDestinationS3PathForOutputFile(String fileName,String userId) {
		String emailId = userId;
		return emailId.substring( 0, emailId.indexOf("@") ) + "/" + getOutputFilePathForCSV( fileName );
	}

	@Override
	public Object getSharedObject() {
		BulkPromotionSharedObject bulkPromotionSharedObject = new BulkPromotionSharedObject();
		bulkPromotionSharedObject.setProcessingRows( 0 );
		return bulkPromotionSharedObject;
	};

	@Override
	public ThreadPoolTaskExecutor getExecutor() {
		return taskExecutor;
	}

	@Override
	public long getChunkSize() {
		return BulkPromotionConstants.chunkSize;
	}

	private String getOutputFilePathForCSV( String inputFilePath ) {
		return inputFilePath.substring(0, inputFilePath.indexOf(".csv")) + "_response.csv";
	}

}
