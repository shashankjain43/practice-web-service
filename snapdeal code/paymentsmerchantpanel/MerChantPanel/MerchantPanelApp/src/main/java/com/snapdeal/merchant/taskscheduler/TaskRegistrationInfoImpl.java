package com.snapdeal.merchant.taskscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class TaskRegistrationInfoImpl implements TaskRegistrationInfo{

	
	 @Autowired
	 private BRTaskExecutor taskExecutor;
	 
	 @Autowired
	 private BRTaskSerializer serializer;
	 
	 @Autowired
	 private MpanelConfig config;
	 
	 public static final String TASK_TYPE = "BRTask-"; 
	 
	 //public static final long RETRY_LIMIT = 10;
	 
	@Override
	public long getExecutionTime() {

		return config.getTaskExecutionTime();
	}

	@Override
	public long getRetryLimit() {
		
		return config.getTaskRetryLimit();
		
	}

	@Override
	public TaskExecutor getTaskExecutor() {

		return taskExecutor;
	}

	@Override
	public TaskSerializer<BulkRefundTaskRequest> getTaskSerializer() {

		return serializer;
	}

	@Override
	public String getTaskType() {
		
		return TASK_TYPE;
	}

	@Override
	public boolean isRecurring() {

		return false;
	}

}
