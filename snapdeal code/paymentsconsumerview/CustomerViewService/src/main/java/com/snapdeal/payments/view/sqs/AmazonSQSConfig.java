package com.snapdeal.payments.view.sqs;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AmazonSQSConfig {

   @Value("#{'${snapdeal.payments.sqs.accesskey}'.split(',')}")
   private List<String> sqsAccessKey;

   @Value("#{'${snapdeal.payments.sqs.secretkey}'.split(',')}")
   private List<String> sqsSecretKey;

   @Value("#{'${snapdeal.payments.sqs.queuename}'.split(',')}")
   private List<String> sqsQueueName;

   @Value("#{'${snapdeal.payments.sqs.region}'.split(',')}")
   private List<String> sqsRegion;

   @Value("#{'${snapdeal.payments.sqs.wait.time}'.split(',')}")
   private List<String> waitTime;

   @Value("#{'${snapdeal.payments.sqs.visibility.time}'.split(',')}")
   private List<String> visibilityTime;

}
