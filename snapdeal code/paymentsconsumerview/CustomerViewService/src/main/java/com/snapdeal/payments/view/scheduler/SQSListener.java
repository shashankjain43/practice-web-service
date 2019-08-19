package com.snapdeal.payments.view.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class SQSListener implements CommandLineRunner {

   @Autowired
   PaymentsViewTSMSQSReader paymentsViewTSMSQSReader;

   @Override
   public void run(String... args) throws Exception {
 
	   //merchantViewTSMSQSReader.readTSMQueue();
	  // merchantViewTSMSQSReader.readTSMQueue();
	   //paymentsViewTSMSQSReader.readTSMQueue();
   }
}
