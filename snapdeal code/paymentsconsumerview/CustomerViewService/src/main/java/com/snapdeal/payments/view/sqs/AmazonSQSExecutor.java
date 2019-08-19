package com.snapdeal.payments.view.sqs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.PurgeQueueRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

@Component
public class AmazonSQSExecutor {

   @Autowired
   AmazonSQSFactory sqsFactory;

   public List<Message> receiveMessage(String queueName) throws QueueException {
      try {
         SQSObject sqsObj = sqsFactory.getAmazonSQSClient(queueName);
         ReceiveMessageRequest receiveRequest = new ReceiveMessageRequest(sqsObj.getQueueUrl());
         receiveRequest.setMaxNumberOfMessages(10);
         receiveRequest.setVisibilityTimeout(Integer.parseInt(sqsObj.getVisibilityTime()));
         receiveRequest.setWaitTimeSeconds(Integer.parseInt(sqsObj.getWaitTime()));
         return sqsObj.getAmazonSqs().receiveMessage(receiveRequest).getMessages();
      } catch (AmazonClientException ace) {
         throw new QueueException(ace);
      }
   }

   public void purge(String queueName) throws QueueException {
      try {
         SQSObject sqsObj = sqsFactory.getAmazonSQSClient(queueName);
         PurgeQueueRequest purgeQueueRequest = new PurgeQueueRequest();
         purgeQueueRequest.setQueueUrl(sqsObj.getQueueUrl());
         sqsObj.getAmazonSqs().purgeQueue(purgeQueueRequest);

      } catch (AmazonClientException ace) {
         throw new QueueException(ace);
      }
   }

   public void deleteMessage(String queueName, String receiptHandle) throws QueueException {
      try {
         SQSObject sqsObj = sqsFactory.getAmazonSQSClient(queueName);
         // sqsObj.getAmazonSqs().purgeQueue(purgeQueueRequest);
         sqsObj.getAmazonSqs().deleteMessage(
                  new DeleteMessageRequest(sqsObj.getQueueUrl(), receiptHandle));
      } catch (AmazonClientException ace) {
         throw new QueueException(ace);
      }
   }

}
