package com.snapdeal.merchant.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobExecutor {
	
	private static ExecutorService jobExecutor; 
	
	static {
		jobExecutor = Executors.newFixedThreadPool(15);
	}
	
	private JobExecutor() {
		
	}

	public static void submitJob(Runnable job) {
		jobExecutor.submit(job);
	}

}
