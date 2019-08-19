package com.snapdeal.payments.view.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.view.commons.enums.Source;
import com.snapdeal.payments.view.service.ITSMQueueHandler;
import com.snapdeal.payments.view.sqs.TSMNotification;

@Service
public class TSMNotifcationService {

	
	@Qualifier("PaymentsViewQueueHandler")
	@Autowired
	private ITSMQueueHandler tsmQueueHandler ;
	
	public void updateTSMNotifcation(NotificationMessage notificationMsg){
		TSMNotification notification = new TSMNotification();
		notification.setNotificationMessage(notificationMsg);
		tsmQueueHandler.processTask(notification, "DIRECT", Source.AUDIT);
	}
}
