package com.snapdeal.ims.wallet.task;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.wallet.request.WalletRequest;
import com.snapdeal.ims.wallet.request.WalletSerializer;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WalletTaskInfo implements TaskRegistrationInfo {

	@Autowired
	private WalletTaskExecutor executor;

	@Autowired
	private WalletSerializer serializer;

	@Override
	public TaskExecutor getTaskExecutor() {
		return executor;
	}

	@Override
	public TaskSerializer<WalletRequest> getTaskSerializer() {
		return serializer;
	}

	@Override
	public String getTaskType() {
		return ServiceCommonConstants.CREATE_SDWALLET_TASK_TYPE;
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
