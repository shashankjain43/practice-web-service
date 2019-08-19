package com.snapdeal.opspanel.promotion.bulkframework;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.snapdeal.bulkprocess.registration.AmazonConfigStore;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.bulkprocess.registration.IRowProcessor;

public class registration extends IBulkFileRegistration {

	@Override
	public AmazonConfigStore getS3Config() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBulkActivityId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRowProcessor getProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBulkValidator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThreadPoolTaskExecutor getExecutor() {
		// TODO Auto-generated method stub
		return null;
	}
}
