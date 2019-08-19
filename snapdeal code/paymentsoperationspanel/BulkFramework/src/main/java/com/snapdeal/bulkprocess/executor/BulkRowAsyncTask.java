package com.snapdeal.bulkprocess.executor;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.log.Log;
import com.snapdeal.bulkprocess.factory.BulkRegistrationFactory;
import com.snapdeal.bulkprocess.model.BulkError;
import com.snapdeal.bulkprocess.model.GenericRequestResponseValues;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;
import com.snapdeal.payments.ts.TaskScheduler;

@Component
@Slf4j
public class BulkRowAsyncTask {

	@Autowired
	TaskScheduler taskScheduler;

	@Autowired
	BulkRegistrationFactory bulkFactory;

	public Future<Object> execute(String[] header, String[] rowValues, Map<String, String> map, long rowNum, Object sharedObject, String activityId, IRowProcessor processor, ThreadPoolTaskExecutor executor, Map<String, String> headerValues) {

	
		GenericCallable callable = new GenericCallable(processor, header, rowValues, map, rowNum, sharedObject,activityId, headerValues);
		try {
			
			Future<Object> response = executor.submit(callable);
			log.info("Row Number " + rowNum + " executed in Async executer \n");
			return response;
		} catch (RejectedExecutionException ex) {
			log.info("RejectedExecutionException while executing " + rowNum + " in Async Executer \n");
			GenericRequestResponseValues rowResponse = new GenericRequestResponseValues();
			rowResponse.setResponseValues(new BulkError(BulkProcessorUtils.EXECUTER_REJECTED));
			return new AsyncResult<Object>(rowResponse);
		}
		
	}

}