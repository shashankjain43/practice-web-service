package com.snapdeal.ims.fortknox.task;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.fortknox.request.FortKnoxRequest;
import com.snapdeal.ims.fortknox.request.FortKnoxSerializer;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FortKnoxTaskInfo implements TaskRegistrationInfo {

	@Autowired
	private FortKnoxTaskExecutor executor;

	@Autowired
	private FortKnoxSerializer serializer;

	@Override
	public TaskExecutor getTaskExecutor() {
		return executor;
	}

	@Override
	public TaskSerializer<FortKnoxRequest> getTaskSerializer() {
		return serializer;
	}

	@Override
	public String getTaskType() {
		return ServiceCommonConstants.CREATE_FORTKNOX_MERGE;
	}

	@Override
	public long getRetryLimit() {
	   return ServiceCommonConstants.DEFAULT_FORTKNOX_MERGE_RETRY_LIMIT;
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
