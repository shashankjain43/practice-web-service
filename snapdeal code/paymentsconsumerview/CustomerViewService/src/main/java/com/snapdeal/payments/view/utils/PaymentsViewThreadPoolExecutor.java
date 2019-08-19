package com.snapdeal.payments.view.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;

@Service
public class PaymentsViewThreadPoolExecutor extends ThreadPoolExecutor {

	public PaymentsViewThreadPoolExecutor() {

		super(PaymentsViewConstants.CORE_POOL_SIZE,
				PaymentsViewConstants.MAX_POOL_SIZE,
				PaymentsViewConstants.KEEP_ALIVE_TIME, 
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(2000),
				new ThreadPoolExecutor.CallerRunsPolicy() );
	}

	public void submitTask(Runnable r) {
		submit(r);
	}
}
