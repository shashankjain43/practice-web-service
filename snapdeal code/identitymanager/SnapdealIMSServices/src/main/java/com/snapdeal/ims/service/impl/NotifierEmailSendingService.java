package com.snapdeal.ims.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.fcNotifier.client.INotifierServiceClient;
import com.snapdeal.ims.Notifier.Emailsender.request.NotifierEmailSendingRequest;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.service.INotifierEmailSendingService;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;
import com.snapdeal.payments.ts.exception.DuplicateTaskException;
import com.snapdeal.payments.ts.exception.InvalidTaskTypeException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class NotifierEmailSendingService implements INotifierEmailSendingService{
	@Autowired
	private TaskScheduler taskScheduler;

	@Autowired
	private INotifierServiceClient fcNotifier;

	@Override
	@Timed
	@Marked
	public void createNotifierEmailSendingTask(NotifierEmailSendingRequest emailRequest) {
		try {
			fcNotifier.sendEmail(emailRequest.getFcNotifierEmailRequest());
		} catch (Exception exp) {
			final TaskDTO taskDTO = new TaskDTO();
			taskDTO.setRequest(emailRequest);
			taskDTO.setCurrentScheduleTime(new Date());
			taskDTO.setTaskType(ServiceCommonConstants.CREATE_NOTIFIER_EMAIL_SENDER_MERGE);

			try {

				taskScheduler.submitTask(taskDTO);
			} catch (DuplicateTaskException e) {

				final String exceptionMsg = String.format(
						"DuplicateTaskException while creating NotifierEmailSendingTask, "
								+ "EmailMessage: %s, taskId: %s",
						emailRequest.getFcNotifierEmailRequest().getEmailMessage(), emailRequest.getTaskId());
				log.error(exceptionMsg, e);
			} catch (InvalidTaskTypeException e) {

				String exceptionMsg = String.format(
						"InvalidTaskTypeException while creating IMSFortKnoxTask, " + "EmailMessage: %s, taskId: %s",
						emailRequest.getFcNotifierEmailRequest().getEmailMessage(), emailRequest.getTaskId());
				log.error(exceptionMsg, e);
				throw e;
			}

		}

	}
}

