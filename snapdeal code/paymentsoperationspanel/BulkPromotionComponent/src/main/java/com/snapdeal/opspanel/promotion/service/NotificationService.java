package com.snapdeal.opspanel.promotion.service;

import com.snapdeal.opspanel.promotion.model.NotificationRequestModel;
import com.snapdeal.payments.notification.model.NotifierResponse;


public interface NotificationService {

	public NotifierResponse sendNotification(NotificationRequestModel notificationRequestModel) throws Exception;
}
