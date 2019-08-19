//package com.snapdeal.opspanel.promotion.task.registration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.snapdeal.opspanel.promotion.request.BulkTaskRequest;
//import com.snapdeal.payments.ts.registration.TaskExecutor;
//import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
//import com.snapdeal.payments.ts.registration.TaskSerializer;
//
//@Component
//public class BulkTaskRegistration implements TaskRegistrationInfo {
//
//	@Autowired
//	private TaskExecutor<BulkTaskRequest> taskExecutor;
//
//	@Autowired
//	TaskSerializer<BulkTaskRequest> taskSerializer;
//
//	private long retryCount = 1;
//
//	@Override
//	public long getRetryLimit() {
//		return retryCount;
//	}
//
//	@Override
//	public TaskExecutor getTaskExecutor() {
//		return taskExecutor;
//	}
//
//	@Override
//	public String getTaskType() {
//		return "BULK_CALL";
//	}
//
//	@Override
//	public long getExecutionTime() {
//		return 86400 * 1000 ;
//	}
//
//	@Override
//	public TaskSerializer<?> getTaskSerializer() {
//		return taskSerializer;
//	}
//
//	@Override
//	public boolean isRecurring() {
//		return false;
//	}
//}
