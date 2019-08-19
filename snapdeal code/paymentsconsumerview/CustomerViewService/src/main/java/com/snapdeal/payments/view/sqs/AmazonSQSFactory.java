package com.snapdeal.payments.view.sqs;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;

@Slf4j
@Component
public class AmazonSQSFactory {
   
   private Map<String, SQSObject> amazonSqsMap;

   @Autowired
   public AmazonSQSFactory(AmazonSQSConfig sqsConfig){
      amazonSqsMap = new HashMap<String, SQSObject>();
      int count = 0;
      for(String queueName: sqsConfig.getSqsQueueName()){
         AWSCredentials credentials = new BasicAWSCredentials(sqsConfig.getSqsAccessKey().get(count),
                  sqsConfig.getSqsSecretKey().get(count));
         AmazonSQS amazonSqs = new AmazonSQSClient(credentials);
         amazonSqs.setRegion(Region.getRegion(Regions.fromName(sqsConfig.getSqsRegion().get(count))));  
         SQSObject sqsObj = new SQSObject();
         sqsObj.setAmazonSqs(amazonSqs);
         sqsObj.setQueueUrl(amazonSqs.getQueueUrl(new GetQueueUrlRequest(queueName)).getQueueUrl());
         sqsObj.setWaitTime(sqsConfig.getWaitTime().get(count));
         sqsObj.setVisibilityTime(sqsConfig.getVisibilityTime().get(count));
         amazonSqsMap.put(queueName, sqsObj);
         count++;
      }     
   }
  
   public SQSObject getAmazonSQSClient(String queueName) {
      return amazonSqsMap.get(queueName);
   }

}
