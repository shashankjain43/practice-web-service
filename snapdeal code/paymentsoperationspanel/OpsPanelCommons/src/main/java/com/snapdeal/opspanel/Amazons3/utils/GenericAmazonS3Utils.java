package com.snapdeal.opspanel.Amazons3.utils;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;
import com.snapdeal.opspanel.Amazons3.response.ListObjectsWithMarker;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class GenericAmazonS3Utils {

	private String destinationPrefix;

	private String bucketName;

	private String accessKeyId;

	private String secretAccessKey;

	private long downloadUrlExpirationTime;

	AmazonS3Client s3client;

	private void initClient() throws Exception {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId,
				secretAccessKey);
		s3client = new AmazonS3Client(awsCreds);

	}

	public GenericAmazonS3Utils(String destinationPrefix, String bucketName,
			String accessKeyId, String secretAccessKey,
			long downloadUrlExpirationTime) throws Exception {
		super();
		this.destinationPrefix = destinationPrefix;
		this.bucketName = bucketName;
		this.accessKeyId = accessKeyId;
		this.secretAccessKey = secretAccessKey;
		this.downloadUrlExpirationTime = downloadUrlExpirationTime;
		this.initClient();
	}

	public void putObject(String source, String destination,
			Map<String, String> userMetadata) throws PaymentsCommonException {
		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setUserMetadata(userMetadata);

			File file = new File(source);
			s3client.setEndpoint("s3-ap-southeast-1.amazonaws.com");
			s3client.putObject(new PutObjectRequest(bucketName,
					destinationPrefix + destination, file)
					.withMetadata(objectMetadata));
		} catch (AmazonServiceException ase) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonServiceException, " + "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response " + "for some reason.");
			log.info("Error Message:    " + ase.getMessage());
			log.info("HTTP Status Code: " + ase.getStatusCode());
			log.info("AWS Error Code:   " + ase.getErrorCode());
			log.info("Error Type:       " + ase.getErrorType());
			log.info("Request ID:       " + ase.getRequestId());
			}
			throw new PaymentsCommonException("Amazon service exception when request made it to Amazon S3");

		} catch (AmazonClientException ace) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonClientException, " + "which means the client encountered "
					+ "an internal error while trying to communicate" + " with S3, "
					+ "such as not being able to access the network.");
			log.info("Error Message: " + ace.getMessage());
			}
			throw new PaymentsCommonException("Client Internal Error");
		}

	}

	public URL generatePresignedUrl(String destination)
			throws PaymentsCommonException {
		try {
			Date expiration = new java.util.Date();
			long milliSeconds = expiration.getTime();
			milliSeconds += 1000 * downloadUrlExpirationTime;
			expiration.setTime(milliSeconds);

			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
					bucketName, destinationPrefix + destination);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET);
			generatePresignedUrlRequest.setExpiration(expiration);

			URL s3Url = s3client
					.generatePresignedUrl(generatePresignedUrlRequest);

			return s3Url;
		} catch (AmazonServiceException ase) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonServiceException, " + "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response " + "for some reason.");
			log.info("Error Message:    " + ase.getMessage());
			log.info("HTTP Status Code: " + ase.getStatusCode());
			log.info("AWS Error Code:   " + ase.getErrorCode());
			log.info("Error Type:       " + ase.getErrorType());
			log.info("Request ID:       " + ase.getRequestId());
			}
			throw new PaymentsCommonException("Amazon service exception when request made it to Amazon S3");

		} catch (AmazonClientException ace) {
			log.info("Caught an AmazonClientException, "
					+ "which means the client encountered "
					+ "an internal error while trying to communicate"
					+ " with S3, "
					+ "such as not being able to access the network.");
			log.info("Error Message: " + ace.getMessage());

			throw new PaymentsCommonException("Client Internal Error");
		}

	}

	public List<String> listFiles(String prefix) throws PaymentsCommonException {
		try {
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
					.withBucketName(bucketName).withPrefix(
							destinationPrefix + prefix);
			ObjectListing objectListing;
			List<String> fileList = new ArrayList<String>();

			do {

				objectListing = s3client.listObjects(listObjectsRequest);

				for (S3ObjectSummary objectSummary : objectListing
						.getObjectSummaries()) {
					String key = objectSummary.getKey();
					fileList.add(key.replace(destinationPrefix + prefix, ""));
				}

				listObjectsRequest.setMarker(objectListing.getNextMarker());

			} while (objectListing.isTruncated());

			return fileList;
		} catch (AmazonServiceException ase) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonServiceException, " + "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response " + "for some reason.");
			log.info("Error Message:    " + ase.getMessage());
			log.info("HTTP Status Code: " + ase.getStatusCode());
			log.info("AWS Error Code:   " + ase.getErrorCode());
			log.info("Error Type:       " + ase.getErrorType());
			log.info("Request ID:       " + ase.getRequestId());
			}
			throw new PaymentsCommonException("Amazon service exception when request made it to Amazon S3");

		} catch (AmazonClientException ace) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonClientException, " + "which means the client encountered "
					+ "an internal error while trying to communicate" + " with S3, "
					+ "such as not being able to access the network.");
			log.info("Error Message: " + ace.getMessage());
			}
			throw new PaymentsCommonException("Client Internal Error");
		}
	}
	
	public List<S3ObjectSummary> listKeys(String prefix) throws PaymentsCommonException {
		try {
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
					.withBucketName(bucketName).withPrefix(
							destinationPrefix + prefix);
			ObjectListing objectListing;
			List<S3ObjectSummary> keyList = new ArrayList<S3ObjectSummary>();

			do {

				objectListing = s3client.listObjects(listObjectsRequest);

				for (S3ObjectSummary objectSummary : objectListing
						.getObjectSummaries()) {
					keyList.add(objectSummary);
				}

				listObjectsRequest.setMarker(objectListing.getNextMarker());

			} while (objectListing.isTruncated());

			return keyList;
		} catch (AmazonServiceException ase) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonServiceException, " + "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response " + "for some reason.");
			log.info("Error Message:    " + ase.getMessage());
			log.info("HTTP Status Code: " + ase.getStatusCode());
			log.info("AWS Error Code:   " + ase.getErrorCode());
			log.info("Error Type:       " + ase.getErrorType());
			log.info("Request ID:       " + ase.getRequestId());
			}
			throw new PaymentsCommonException("Amazon service exception when request made it to Amazon S3");

		} catch (AmazonClientException ace) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonClientException, " + "which means the client encountered "
					+ "an internal error while trying to communicate" + " with S3, "
					+ "such as not being able to access the network.");
			log.info("Error Message: " + ace.getMessage());
			}
			throw new PaymentsCommonException("Client Internal Error");
		}
	}
	
	
	public ListObjectsWithMarker listNextFiles(String prefix, String marker, int pageNumber, int pageSize) throws PaymentsCommonException {
		try {
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
			.withBucketName(bucketName).withPrefix(
					destinationPrefix + prefix).withMaxKeys(pageSize);
			if(pageNumber != 1){
				listObjectsRequest.setMarker(marker);
			}
			ObjectListing objectListing;
			List<String> fileList = new ArrayList<String>();

			objectListing = s3client.listObjects(listObjectsRequest);
			for (S3ObjectSummary objectSummary : objectListing
					.getObjectSummaries()) {
				String key = objectSummary.getKey();
				fileList.add(key.replace(destinationPrefix + prefix, ""));
			}

			ListObjectsWithMarker listObjectsWithMarker = new ListObjectsWithMarker();
			listObjectsWithMarker.setFiles(fileList);

			if(objectListing.isTruncated()){
				listObjectsWithMarker.setNextMarker(objectListing.getNextMarker());
			} else {
				listObjectsWithMarker.setNextMarker(null);
			}
			return listObjectsWithMarker;

		} catch (AmazonServiceException ase) {
			log.info("Caught an AmazonServiceException, "
					+ "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response "
					+ "for some reason.");
			log.info("Error Message:    " + ase.getMessage());
			log.info("HTTP Status Code: " + ase.getStatusCode());
			log.info("AWS Error Code:   " + ase.getErrorCode());
			log.info("Error Type:       " + ase.getErrorType());
			log.info("Request ID:       " + ase.getRequestId());

			throw new PaymentsCommonException(
					"Amazon service exception when request made it to Amazon S3");

		} catch (AmazonClientException ace) {
			log.info("Caught an AmazonClientException, "
					+ "which means the client encountered "
					+ "an internal error while trying to communicate"
					+ " with S3, "
					+ "such as not being able to access the network.");
			log.info("Error Message: " + ace.getMessage());

			throw new PaymentsCommonException("Client Internal Error");
		}
	}

	public Map<String, String> getFileMetaData(String destination)
			throws PaymentsCommonException {
		try {
			ObjectMetadata objectMetadata = s3client.getObjectMetadata(
					bucketName, destinationPrefix + destination);
			return objectMetadata.getUserMetadata();
		} catch (AmazonServiceException ase) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonServiceException, " + "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response " + "for some reason.");
			log.info("Error Message:    " + ase.getMessage());
			log.info("HTTP Status Code: " + ase.getStatusCode());
			log.info("AWS Error Code:   " + ase.getErrorCode());
			log.info("Error Type:       " + ase.getErrorType());
			log.info("Request ID:       " + ase.getRequestId());
			}
			throw new PaymentsCommonException("Amazon service exception when request made it to Amazon S3");

		} catch (AmazonClientException ace) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonClientException, " + "which means the client encountered "
					+ "an internal error while trying to communicate" + " with S3, "
					+ "such as not being able to access the network.");
			log.info("Error Message: " + ace.getMessage());
			}
			throw new PaymentsCommonException("Client Internal Error");
		}
	}
	
	
	/*public List<String> listFilesWithPagination(String prefix, int pageNumber, int maxKeys) throws PaymentsCommonException {
		try {
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName).withPrefix(destinationPrefix + prefix).withMaxKeys(maxKeys);
					ObjectListing objectListing;
			List<String> fileList = new ArrayList<String>();
			for(objectListing = s3client.listObjects(listObjectsRequest); pageNumber-- >0 ; objectListing = s3client.listNextBatchOfObjects(objectListing)){

					if(pageNumber ==0){
						for (S3ObjectSummary objectSummary : objectListing
								.getObjectSummaries()) {
							String key = objectSummary.getKey();
							fileList.add(key.replace(destinationPrefix + prefix, ""));
						}
					}
			}
			return fileList;
		} catch (AmazonServiceException ase) {
			log.info("Caught an AmazonServiceException, "
					+ "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response "
					+ "for some reason.");
			log.info("Error Message:    " + ase.getMessage());
			log.info("HTTP Status Code: " + ase.getStatusCode());
			log.info("AWS Error Code:   " + ase.getErrorCode());
			log.info("Error Type:       " + ase.getErrorType());
			log.info("Request ID:       " + ase.getRequestId());

			throw new PaymentsCommonException(
					"Amazon service exception when request made it to Amazon S3");

		} catch (AmazonClientException ace) {
			log.info("Caught an AmazonClientException, "
					+ "which means the client encountered "
					+ "an internal error while trying to communicate"
					+ " with S3, "
					+ "such as not being able to access the network.");
			log.info("Error Message: " + ace.getMessage());

			throw new PaymentsCommonException("Client Internal Error");
		}
	}*/


	

	public boolean isValidFile(String destination)
			throws PaymentsCommonException {
		boolean isValidFile = true;
		try {
			ObjectMetadata objectMetadata = s3client.getObjectMetadata(
					bucketName, destinationPrefix + destination);
		} catch (AmazonS3Exception s3e) {
			if (s3e.getStatusCode() == 404) {
				// i.e. 404: NoSuchKey - The specified key does not exist
				isValidFile = false;
			} else {
				throw new PaymentsCommonException("Amazon Client Internal Error"); // rethrow all S3 exceptions other than 404
			}
		}

		return isValidFile;
	}

	public boolean localDownload(String destination, File localfile)
			throws PaymentsCommonException {

		try {
			
			s3client.getObject(new GetObjectRequest(bucketName, destination),
					localfile);
			return localfile.exists() && localfile.canRead();
		} catch (AmazonServiceException ase) {
			if(log.isInfoEnabled()){
			log.info("Caught an AmazonServiceException, " + "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response " + "for some reason.");
			log.info("Error Message:    " + ase.getMessage());
			log.info("HTTP Status Code: " + ase.getStatusCode());
			log.info("AWS Error Code:   " + ase.getErrorCode());
			log.info("Error Type:       " + ase.getErrorType());
			log.info("Request ID:       " + ase.getRequestId());
			}
			throw new PaymentsCommonException(
					"Amazon service exception when request made it to Download on local server");

		}

	}
	public static void main(String[] args){
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String strDate = formatter.format(date);
		System.out.println(strDate);
		
	}
	
}


