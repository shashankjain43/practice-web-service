package com.snapdeal.bulkprocess.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.executor.BulkMainExecutor;
import com.snapdeal.bulkprocess.serializer.GenericTaskSerializer;
import com.snapdeal.bulkprocess.utils.BulkProcessorUtils;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class TaskSchedulerRegistration implements TaskRegistrationInfo{
	
	@Autowired
	@Qualifier("genericBulkExecuter")
	private BulkMainExecutor bulkMainExecuter;

	@Autowired
	@Qualifier("genericBulkSerializer")
	private GenericTaskSerializer taskSerializer;

	public String getTaskType() {
		// TODO Auto-generated method stub
		return BulkProcessorUtils.GENERIC_TASK_TYPE;
	}

	public TaskExecutor getTaskExecutor() {
		// TODO Auto-generated method stub
		return bulkMainExecuter;
	}

	public TaskSerializer<?> getTaskSerializer() {
		// TODO Auto-generated method stub
		return taskSerializer;
	}

	public long getExecutionTime() {
		return 48 * 3600 * 1000 ;
	}

	public long getRetryLimit() {
		// TODO Auto-generated method stub
		return BulkProcessorUtils.RETRY_COUNT;
	}

	public boolean isRecurring() {
		// TODO Auto-generated method stub
		return false;
	}

}
