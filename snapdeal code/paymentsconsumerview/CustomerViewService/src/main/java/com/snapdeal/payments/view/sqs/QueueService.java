package com.snapdeal.payments.view.sqs;

import java.util.List;

/**
 * QueueService to enable submit, poll, delete a message in the queue
 * 
 * @author shagun
 *
 * @param <T>
 */
public interface QueueService {

   public void submit(PaymentsMessage message) throws QueueException;

   public List<PaymentsMessage> poll(String queueName) throws QueueException;

   public void delete(String queueName, String receiptHandle) throws QueueException;

   public void purge(String queueName) throws QueueException;

}
