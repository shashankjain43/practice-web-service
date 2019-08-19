package com.snapdeal.payments.view.sqs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.model.Message;

/**
 * Amazon SNS/SQS Implementation of Queue Service
 * 
 * @author shagun
 *
 * @param <T>
 */
@Component("AmazonSQSService")
public class QueueServiceImpl implements QueueService {

   @Autowired
   private AmazonSQSExecutor executor;

   @Override
   public void submit(PaymentsMessage message) throws QueueException {
      // No operation
   }

   @Override
   public List<PaymentsMessage> poll(String queueName) throws QueueException {
      List<Message> messages = executor.receiveMessage(queueName);
      List<PaymentsMessage> paymentMessages = new ArrayList<PaymentsMessage>();
      for (Message message : messages) {
         PaymentsMessage paymentMessage = new PaymentsMessage();
         paymentMessage.setHeaders(message.getAttributes());
         paymentMessage.setMessageBody(message.getBody());
         paymentMessage.setMessageId(message.getMessageId());
         paymentMessage.setReceiptHandle(message.getReceiptHandle());
         paymentMessages.add(paymentMessage);
      }
      return paymentMessages;
   }

   @Override
   public void delete(String queueName, String receiptHandle) throws QueueException {
      executor.deleteMessage(queueName, receiptHandle);
   }

   @Override
   public void purge(String queueName) throws QueueException {
      executor.purge(queueName);

   }

}
