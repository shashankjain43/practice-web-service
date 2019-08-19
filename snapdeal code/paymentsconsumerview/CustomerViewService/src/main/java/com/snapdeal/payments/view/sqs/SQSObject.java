package com.snapdeal.payments.view.sqs;

import lombok.Data;

import com.amazonaws.services.sqs.AmazonSQS;
@Data
public class SQSObject {
   private AmazonSQS amazonSqs;
   private String waitTime;
   private String visibilityTime;
   private String queueUrl;
}
