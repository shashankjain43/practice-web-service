package com.snapdeal.payments.view.sqs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.tsm.entity.NotificationMessage;

@Component
@Slf4j
public class NotificationReaderServiceImpl implements NotificationReaderService {

   @Autowired
   @Qualifier("AmazonSQSService")
   QueueService queueService;

   private static final String MESSAGE = "Message";

   @Autowired
   NotificationReaderSerializationFactory<NotificationMessage> notificationSerializer;


   @Autowired
   NotificationReaderSerializationFactory<Map> mapSerializer;

   @Override
   @Marked
   @Timed
   @ExceptionMetered
   public List<TSMNotification> readNotifications(String queueName)
            throws NotificationReaderException {
      List<TSMNotification> tsmNotifications = new ArrayList<TSMNotification>();
      try {
         List<PaymentsMessage> notifications = queueService.poll(queueName);
         log.debug("Received Message count : " + notifications.size());
         for (PaymentsMessage notification : notifications) {
            TSMNotification tsmNotification = new TSMNotification();
            tsmNotification.setReceiptHandle(notification.getReceiptHandle());
            String messageBody = mapSerializer
                     .parseJsonToEntity(notification.getMessageBody(), Map.class).get(MESSAGE)
                     .toString();
            NotificationMessage notificationMsg = getNotificationMessageDeserialized(messageBody);
            if (notificationMsg != null) {
               tsmNotification.setNotificationMessage(notificationMsg);
               tsmNotifications.add(tsmNotification);
            }
         }
        log.debug("TSM Notification Message count : " + tsmNotifications.size());
      } catch (QueueException e) {
         log.error(e.getLocalizedMessage(), e);
         throw new NotificationReaderException(e);
      }
      return tsmNotifications;
   }

   private NotificationMessage getNotificationMessageDeserialized(String messageBody) {
      try {
         return notificationSerializer.parseJsonToEntity(messageBody, NotificationMessage.class);
      } catch (NotificationReaderException e) {
        log.error("Error occured while parsing message from SQSL");
        return null ;
      }

   }

   @Override
   @Marked
   @Timed
   @ExceptionMetered
   public void deleteNotification(String queueName, String receiptHandle)
            throws NotificationReaderException {
      try {
         queueService.delete(queueName, receiptHandle);
   //      log.debug("Deleted Message with Handle : " + receiptHandle);
      } catch (QueueException e) {
         log.error(e.getLocalizedMessage(), e);
         throw new NotificationReaderException(e);
      }
   }

}
