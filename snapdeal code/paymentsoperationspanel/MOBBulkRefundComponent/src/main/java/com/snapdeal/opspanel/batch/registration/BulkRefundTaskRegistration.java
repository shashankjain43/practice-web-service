package com.snapdeal.opspanel.batch.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.opspanel.bulk.request.BulkRefundTaskRequest;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class BulkRefundTaskRegistration  implements TaskRegistrationInfo  {

	@Autowired
	private TaskExecutor<BulkRefundTaskRequest> taskExecutor;
	
	@Autowired
	TaskSerializer<BulkRefundTaskRequest> taskSerializer;	
	
	public long getExecutionTime() {
		
		return 86400 * 1000;
	}

	public long getRetryLimit() {
	
		return 1;
	}

	public TaskExecutor getTaskExecutor() {
		
		return taskExecutor;
	}

	public TaskSerializer<?> getTaskSerializer() {
		// TODO Auto-generated method stub
		return taskSerializer;
	}

	public String getTaskType() {

		return "BULK_REFUND";
	}

	public boolean isRecurring() {
	
		return false;
	}

}
