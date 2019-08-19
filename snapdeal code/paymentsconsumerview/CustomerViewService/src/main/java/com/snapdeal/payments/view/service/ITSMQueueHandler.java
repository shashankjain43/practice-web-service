package com.snapdeal.payments.view.service;

import com.snapdeal.payments.view.commons.enums.Source;
import com.snapdeal.payments.view.sqs.TSMNotification;

public interface ITSMQueueHandler {
	
	public void processTask(final TSMNotification notification,
							final String queueName,
							final Source source) ;
}
