package com.snapdeal.ims.wallet.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.wallet.request.UserMigrationStatusRequest;
import com.snapdeal.ims.wallet.request.UserMigrationStatusSerializer;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class UserMigrationStatusTaskInfo implements TaskRegistrationInfo {

	@Autowired
	UserMigrationStatusTaskExecutor executor;

	@Autowired
	UserMigrationStatusSerializer serializer;

	@Override
	public TaskExecutor getTaskExecutor() {
		return executor;
	}

	@Override
	public TaskSerializer<UserMigrationStatusRequest> getTaskSerializer() {
		return serializer;
	}

	@Override
	public String getTaskType() {
		return ServiceCommonConstants.NOTIFY_MIGRATION_STATE_CHANGE;
	}

	@Override
	public long getRetryLimit() {
		return ServiceCommonConstants.DEFAULT_CREATE_SDWALLET_RETRY_LIMIT;
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
