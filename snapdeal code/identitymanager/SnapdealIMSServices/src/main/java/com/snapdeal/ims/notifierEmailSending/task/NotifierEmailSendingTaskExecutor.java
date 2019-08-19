package com.snapdeal.ims.notifierEmailSending.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.fcNotifier.client.impl.NotifierServiceClient;
import com.snapdeal.fcNotifier.exception.HttpTransportException;
import com.snapdeal.ims.Notifier.Emailsender.request.NotifierEmailSendingRequest;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.registration.TaskRequest;
import com.snapdeal.payments.ts.response.ExecutorResponse;
import com.snapdeal.payments.ts.response.ExecutorResponse.Status;
import com.snapdeal.payments.ts.response.RetryInfo;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Component("NotifierEmailSender")
public class NotifierEmailSendingTaskExecutor implements TaskExecutor<TaskRequest> {

	@Autowired
	private NotifierServiceClient fcNotifier;

	@Override
	public ExecutorResponse execute(TaskRequest request) {

		final ExecutorResponse execResponse = new ExecutorResponse();
		final NotifierEmailSendingRequest notifierRequest = (NotifierEmailSendingRequest) request;
		try {
			log.info("sending Email for verification via FcNotifier for id"+notifierRequest.getTaskId()+
					"and email"+notifierRequest.getFcNotifierEmailRequest().getEmailMessage().getReplyTo());
			
			fcNotifier.sendEmail(notifierRequest.getFcNotifierEmailRequest());
			execResponse.setCompletionLog("SUCCESS");
			execResponse.setStatus(Status.SUCCESS);
			
		} catch (HttpTransportException e) {

			log.error(
						"HttpTransportException while sending email, metadata: "
								+ request + "...retrying", e);
				log.error(e.getMessage());

				RetryInfo retryInfo = new RetryInfo();
				retryInfo.setType(RetryInfo.RetryType.EXPONENTIAL);
				retryInfo.setExpBase(ServiceCommonConstants.NOTIFIER_EMAIL_SENDER_FAILURE_TASK_EXPONENTIAL_BASE);
				retryInfo
						.setWaitTime(ServiceCommonConstants.NOTIFIER_EMAIL_SENDER_FAILURE_TASK_REEXECUTION_WAIT_TIME);
				execResponse.setAction(retryInfo);
				execResponse.setStatus(Status.RETRY);
				
			} 
		 catch (Exception e) {

			log.error(
					"Exception occured while sending email api, metadata: "
							+ request, e);
			execResponse.setCompletionLog(e.getMessage());
			execResponse.setStatus(Status.FAILURE);
		}

		return execResponse;
	}
}