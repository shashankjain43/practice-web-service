package com.snapdeal.notifier.email.service.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.notifier.email.constant.NotifierConst;
import com.snapdeal.notifier.email.reponse.EmailResponse;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.email.service.IEmailService;
import com.snapdeal.notifier.email.task.IEmailSender;
import com.snapdeal.notifier.email.template.EmailTemplateCache;
import com.snapdeal.notifier.exception.InternalServerException;
import com.snapdeal.notifier.exception.ValidationException;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;
import com.snapdeal.payments.ts.exception.DuplicateTaskException;
import com.snapdeal.payments.ts.exception.InvalidTaskTypeException;

@Slf4j
@Component
public class EmailService implements IEmailService{

	@Autowired
	private TaskScheduler taskScheduler;

	@Autowired
	private IEmailSender octaneEmailSender;
	
	@Override
	public EmailResponse sendEmail(EmailMessage message,
			boolean transactional)
					throws ValidationException,
					InternalServerException {
	   
	   
      /*Step1: Validating whether template is present in the cache 
       * corresponding to given key*/
      String templateHTMLStr = EmailTemplateCache.getInstance().getTemplate(message.getTemplateKey());
      
      /*Step2: Send(or create task) email*/
      if(!transactional){
         return createEmailTask(message);
			} else {
				return 
						buildResponse(
								octaneEmailSender.send(
										buildRequest(message),
                                        templateHTMLStr, 
										message.getSubject(), 
										message.getTags()
										)
								);
			}
		}

	private com.snapdeal.base.notification.email.EmailMessage 
	buildRequest(EmailMessage message) {


		com.snapdeal.base.notification.email.EmailMessage emailMessage = 
				new com.snapdeal.base.notification.email.EmailMessage(message.getTo(), 
						message.getFrom(), 
						message.getReplyTo(), 
						null);
		return emailMessage;
	}

	private EmailResponse buildResponse(com.snapdeal.mail.client.model.EmailResponse clientResponse) {
		EmailResponse response = new EmailResponse();
		response.setResponseCode(clientResponse.getRequestId());
		return response;
	}

	private EmailResponse createEmailTask(EmailMessage message) {

		final com.snapdeal.notifier.email.request.EmailRequest request = 
				new com.snapdeal.notifier.email.request.EmailRequest();
		request.setMessage(message);
		request.setSubject(message.getSubject());
		request.setTags(message.getTags());
		request.setTaskId(message.getTaskId());
		final TaskDTO taskDTO = new TaskDTO();
		taskDTO.setRequest(request);
		taskDTO.setCurrentScheduleTime(new Date());
		taskDTO.setTaskType(NotifierConst.EMAIL_TASK_TYPE);
		taskDTO.setCurrentScheduleTime(new Date());

		try {
			taskScheduler.submitTask(taskDTO);
		} catch (DuplicateTaskException e) {
			final String exceptionMsg = 
					String.format("DuplicateTaskException while creating EmailTask: %s",
							request.toString());
			log.error(exceptionMsg, e);
		} catch (InvalidTaskTypeException e) {
			String exceptionMsg = 
					String.format(
							"InvalidTaskTypeException while creating EmailTask: %s", 
							request.toString());
			log.error(exceptionMsg, e);
			throw e;
		}
		return null;
	}
}
