package com.snapdeal.ims.task.test;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.wallet.request.UserMigrationStatusRequest;
import com.snapdeal.ims.wallet.request.WalletRequest;
import com.snapdeal.ims.wallet.request.WalletSerializer;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompleteTaskInfo implements TaskRegistrationInfo {

	@Autowired
	private CompleteTaskExecutor executor;

	@Autowired
	private CompleteTaskSerializer serializer;

	@Override
	public TaskExecutor getTaskExecutor() {
		return executor;
	}

	@Override
	public TaskSerializer<CompleteTaskRequest> getTaskSerializer() {
		return serializer;
	}

	@Override
	public String getTaskType() {
		return ServiceCommonConstants.CREATE_COMPLETE_TASK_TYPE;
	}

	@Override
	public long getRetryLimit() {
	   return ServiceCommonConstants.DEFAULT_CREATE_COMPLETE_TASK_TYPE;
	}

	@Override
	public long getExecutionTime() {
      return 30 * 1000;
	}

	@Override
	public boolean isRecurring() {
		return false;
	}
}
