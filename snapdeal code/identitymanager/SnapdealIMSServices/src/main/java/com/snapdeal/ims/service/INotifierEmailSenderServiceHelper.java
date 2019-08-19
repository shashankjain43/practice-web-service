package com.snapdeal.ims.service;

import com.snapdeal.fcNotifier.exception.ServiceException;
import com.snapdeal.fcNotifier.request.EmailNotifierRequest;

public interface INotifierEmailSenderServiceHelper {

	public void createNotifierEmailSendingTask(EmailNotifierRequest request) throws ServiceException;
}
