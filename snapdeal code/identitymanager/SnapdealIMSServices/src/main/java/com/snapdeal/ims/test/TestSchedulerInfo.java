package com.snapdeal.ims.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class TestSchedulerInfo implements TaskRegistrationInfo {

	@Autowired
	private TestSchedulerExecutor executor;

	@Autowired
	private TaskSerializer<TestTaskRequest> serializer;

	@Override
	public String getTaskType() {
		return "TestScheduler";
	}

	@Override
	public TaskExecutor getTaskExecutor() {
		return executor;
	}

	@Override
	public TaskSerializer<?> getTaskSerializer() {
		return serializer;
	}


	@Override
	public long getRetryLimit() {
		return 10;
	}

	@Override
	public long getExecutionTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isRecurring() {
		// TODO Auto-generated method stub
		return false;
	}

}
