package com.snapdeal.opspanel.promotion.utils;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
@Data
public class AWSConfiguration {

   @Value("${snapdeal.sdMoneyDashboard.amazon.bucketName}")
   private String bucketName;

   @Value("${snapdeal.sdMoneyDashboard.amazon.destinationPrefix}")
   private String destinationPrefix;

   @Value("${snapdeal.sdMoneyDashboard.amazon.accessKeyId}")
   private String accessKeyId;

   @Value("${snapdeal.sdMoneyDashboard.amazon.secretAccessKey}")
   private String secretAccessKey;

   @Value("${snapdeal.sdMoneyDashboard.amazon.downloadUrlExpirationTime}")
   private long downloadUrlExpirationTime;

   @Value("${snapdeal.sdMoneyDashboard.amazon.localDownloadPath}")
   private String localDownloadPath;

   @Bean
   @Scope
   public AmazonS3Client initClient() throws Exception {

      BasicAWSCredentials awsCreds = new BasicAWSCredentials( accessKeyId, secretAccessKey );
      AmazonS3Client s3client = new AmazonS3Client(awsCreds);
      return s3client;
   }

}
