package com.snapdeal.payments.view.sqs;

import lombok.Data;

import com.snapdeal.payments.tsm.entity.NotificationMessage;
@Data
public class TSMNotification {
   private NotificationMessage notificationMessage;
   private String receiptHandle;
}
