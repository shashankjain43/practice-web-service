package com.snapdeal.bulkprocess.executor;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.AsyncResult;

import com.snapdeal.bulkprocess.model.BulkError;
import com.snapdeal.bulkprocess.model.GenericRequestResponseValues;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;


public class GenericCallable implements Callable<Object> {
	

	
	IRowProcessor  processor;
	
	String[] header;

	String[] rowValues;
	
	Map<String, String> map;
	
	long rowNum;
	
	Object sharedObject; 

	String activityId;
	
	Map<String, String> headerValues;

	public GenericCallable(IRowProcessor processor, String[] header, String[] rowValues, Map<String, String> map,
			long rowNum, Object sharedObject, String activityId, Map<String, String> headerValues) {
		super();
		this.processor = processor;
		this.header = header;
		this.rowValues = rowValues;
		this.map = map;
		this.rowNum = rowNum;
		this.sharedObject = sharedObject;
		this.activityId = activityId;
		this.headerValues = headerValues;
	}

	@Override
	public Object call() throws Exception {
		GenericRequestResponseValues rowResponse = new GenericRequestResponseValues();
		try {
			rowResponse.setResponseValues((processor).execute(header, rowValues, map, rowNum, sharedObject, headerValues));
		} catch(Throwable e) {
			rowResponse.setResponseValues(new BulkError(BulkProcessorUtils.ASYNC_ERROR_MSG));
		}
		rowResponse.setRequestValues(rowValues);
		return (rowResponse);
	}

}
