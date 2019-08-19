package com.snapdeal.ims.notifierEmailSending.task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.Notifier.Emailsender.request.NotifierEmailSendingRequest;
import com.snapdeal.ims.Notifier.Emailsender.request.NotifierEmailSendingSerializer;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRegistrationInfo;
import com.snapdeal.payments.ts.registration.TaskSerializer;

@Component
public class NotifierEmailSendingTaskInfo  implements TaskRegistrationInfo {

	@Autowired
	private NotifierEmailSendingTaskExecutor executor;

	@Autowired
	private NotifierEmailSendingSerializer serializer;

	@Override
	public TaskExecutor getTaskExecutor() {
		return executor;
	}

	@Override
	public TaskSerializer<NotifierEmailSendingRequest> getTaskSerializer() {
		return serializer;
	}

	@Override
	public String getTaskType() {
		return ServiceCommonConstants.CREATE_NOTIFIER_EMAIL_SENDER_MERGE;
	}

	@Override
	public long getRetryLimit() {
	   return ServiceCommonConstants.DEFAULT_NOTIFIER_EMAIL_SENDER_RETRY_LIMIT;
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
