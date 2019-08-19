package com.snapdeal.opspanel.promotion.bulkprocessortester;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.registration.AmazonConfigStore;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;

@Component
public class BulkProcessorRegistration extends IBulkFileRegistration{

	@Override
	public AmazonConfigStore getS3Config() {
		// TODO Auto-generated method stub
		return new AmazonConfigStore("sdstg", "paymentsdashboard", "AKIAI7K62YZASCNQAI7A", "Tnx+fA78aoUGaqBZak7m6WnQRuwbMzKnykFmqrSl", 1800, "/home/local/JASPERINDIA/avinash.katiyar/");
	}

	@Override
	public String getBulkActivityId() {
		// TODO Auto-generated method stub
		return "testBulkAgain";
	}

	@Override
	public String getLocalDir() {
		// TODO Auto-generated method stub
		return "/home/local/JASPERINDIA/avinash.katiyar/";
	}

	@Override
	public IRowProcessor getProcessor() {
		// TODO Auto-generated method stub
		return new TRowProcessor();
	}

	@Override
	public IBulkValidator getValidator() {
		// TODO Auto-generated method stub
		return new TFileValidator();
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return "avinash.katiyar";
	}

	@Override
	public int getThreadPoolSize() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public long getChunkSize() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public ThreadPoolTaskExecutor getExecutor() {
		ThreadPoolTaskExecutor customExecutor = new ThreadPoolTaskExecutor();
		customExecutor.setMaxPoolSize(20);
		customExecutor.setThreadNamePrefix("DeadPool");
		customExecutor.setCorePoolSize(10);
		customExecutor.setQueueCapacity(1000000);
		customExecutor.initialize();

		return customExecutor;
	}
	
}
