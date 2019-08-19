package com.snapdeal.ims.service;

import com.snapdeal.fcNotifier.exception.ServiceException;
import com.snapdeal.ims.Notifier.Emailsender.request.NotifierEmailSendingRequest;

public interface INotifierEmailSendingService {

	void createNotifierEmailSendingTask(NotifierEmailSendingRequest emailRequest) throws ServiceException;

}
