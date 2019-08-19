package com.snapdeal.ims.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.fcNotifier.exception.ServiceException;
import com.snapdeal.fcNotifier.request.EmailNotifierRequest;
import com.snapdeal.ims.Notifier.Emailsender.request.NotifierEmailSendingRequest;
import com.snapdeal.ims.service.INotifierEmailSenderServiceHelper;
import com.snapdeal.ims.service.INotifierEmailSendingService;


@Component
public class NotifierEmailSenderServiceHelper implements INotifierEmailSenderServiceHelper {
	
	
	@Autowired
	private  INotifierEmailSendingService emailService;

	@Override
	public void createNotifierEmailSendingTask(EmailNotifierRequest request) throws ServiceException {
		NotifierEmailSendingRequest emailRequest = new NotifierEmailSendingRequest();
		emailRequest.setFcNotifierEmailRequest(request);
		String taskId=request.getEmailMessage().getTaskId();
		if(taskId==null)
			taskId=UUID.randomUUID().toString()+System.currentTimeMillis();
		emailRequest.setTaskId(request.getEmailMessage().getTaskId());
		emailService.createNotifierEmailSendingTask(emailRequest);
	}
}
