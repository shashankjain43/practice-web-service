package com.snapdeal.ims.mojoClient.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.fortknox.request.FortKnoxRequest;
import com.snapdeal.ims.fortknox.request.FortKnoxSerializer;
import com.snapdeal.ims.fortknox.task.FortKnoxTaskExecutor;
import com.snapdeal.ims.mojoClient.request.MojoClientSerializer;
import com.snapdeal.ims.mojoClient.request.MojoRequest;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class MojoTaskInfo implements TaskRegistrationInfo {

		@Autowired
		private MojoTaskExecutor executor;

		@Autowired
		private MojoClientSerializer serializer;

		@Override
		public TaskExecutor getTaskExecutor() {
			return executor;
		}

		@Override
		public TaskSerializer<MojoRequest> getTaskSerializer() {
			return serializer;
		}

		@Override
		public String getTaskType() {
			return ServiceCommonConstants.CREATE_MOJOCLIENT;
		}

		@Override
		public long getRetryLimit() {
		   return ServiceCommonConstants.DEFAULT_MOJOCLIENT_RETRY_LIMIT;
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
