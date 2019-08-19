package com.snapdeal.opspanel.promotion.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;

@Component
public class AmazonS3Utils {

   @Autowired
   private AWSConfiguration config;
   
   public AWSConfiguration getConfig(){
	   return config;
   }

   private String getAmazonS3KeyPrefix( String emailId ) {
      return config.getDestinationPrefix() + emailId.substring( 0, emailId.indexOf("@") ) + "/";
   }

   public String createAmazonS3FilePath( String emailId, String fileName ) {
      return getAmazonS3KeyPrefix( emailId ) + fileName;
   }

   public void pushFile(String source, String emailId, String fileName) throws Exception {
      AmazonS3 s3client = config.initClient();
      File file = new File(source);
      s3client.setEndpoint("s3-ap-southeast-1.amazonaws.com");
      s3client.putObject(
               new PutObjectRequest( config.getBucketName(), createAmazonS3FilePath(emailId, fileName), file));
   }

   public ArrayList<String> listFiles( String emailId ) throws Exception {
      ArrayList<String> fileList = new ArrayList<String>();
      String keysPrefix = getAmazonS3KeyPrefix( emailId );

      ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
               .withBucketName(config.getBucketName()).withPrefix(keysPrefix);
      ObjectListing objectListing;
      AmazonS3 s3client = config.initClient();

      do {
         objectListing = s3client.listObjects(listObjectsRequest);
         for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            fileList.add(objectSummary.getKey().replace(keysPrefix, ""));
         }
         listObjectsRequest.setMarker(objectListing.getNextMarker());
      } while (objectListing.isTruncated());
      return fileList;
   }
public URL getDownloadUrl(String emailId, String fileName) throws Exception {
		Date expiration = new java.util.Date();
		long milliSeconds = expiration.getTime();
		milliSeconds += 1000 * config.getDownloadUrlExpirationTime();
		expiration.setTime(milliSeconds);
		String s3FilePath = createAmazonS3FilePath(emailId, fileName);
		AmazonS3 s3client = config.initClient();
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				config.getBucketName(), s3FilePath);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);
		generatePresignedUrlRequest.setExpiration(expiration);
		try {
			ObjectMetadata objectMetadata = s3client.getObjectMetadata(config.getBucketName(), s3FilePath);
			return s3client.generatePresignedUrl(generatePresignedUrlRequest);
		} catch (AmazonS3Exception s3e) {
			if (s3e.getStatusCode() == 404) {
				try {
					ObjectMetadata objectMetadata = s3client.getObjectMetadata(config.getBucketName(),
							createAmazonS3FilePath(emailId, "output_" + fileName));
					generatePresignedUrlRequest.setKey(createAmazonS3FilePath(emailId, "output_" + fileName));
					return s3client.generatePresignedUrl(generatePresignedUrlRequest);
				} catch (AmazonS3Exception s3Exception) {
					throw new PaymentsCommonException("File not found on server");
				}
			} else {
				throw new PaymentsCommonException("File not found on server");
			}
		}
	}


   public boolean downloadFile( String emailId, String fileName ) throws Exception {
      String amazonFilePath = createAmazonS3FilePath( emailId, fileName );
      File localFile = new File( config.getLocalDownloadPath() + fileName );
      AmazonS3 s3client = config.initClient();
         s3client.getObject(new GetObjectRequest(config.getBucketName(), amazonFilePath), localFile);
         return localFile.exists() && localFile.canRead();
   }
}
