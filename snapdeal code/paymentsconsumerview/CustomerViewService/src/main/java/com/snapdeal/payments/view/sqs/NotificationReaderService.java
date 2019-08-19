package com.snapdeal.payments.view.sqs;

import java.util.List;


/**
 * Reader Service to enable Reading Notification and acknowledge Notification
 * 
 * @author shagun
 *
 */
public interface NotificationReaderService {

   /**
    * Reads a list of Notification messages present in the Queue Specified
    * 
    * @return
    * @throws NotificationReaderException
    */
   public List<TSMNotification> readNotifications(String queueName)
            throws NotificationReaderException;

   /**
    * Deletes the Notification Message from the Queue
    * 
    * @usage this method needs to be invoked on every Notification in the list
    *        received after reading notifications
    * @param receiptHandle
    * @throws NotificationReaderException
    */
   public void deleteNotification(String queueName, String receiptHandle)
            throws NotificationReaderException;

}
