package com.snapdeal.opspanel.userpanel.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InfoPanelS3Utils {

	@Value("${snapdeal.sdMoneyDashboard.amazon.userpanel.destinationPrefix}")
	private String destinationPrefix;

	@Value("${snapdeal.sdMoneyDashboard.amazon.bucketName}")
	private String bucketName;

	@Value("${snapdeal.sdMoneyDashboard.amazon.accessKeyId}")
	private String accessKeyId;

	@Value("${snapdeal.sdMoneyDashboard.amazon.secretAccessKey}")
	private String secretAccessKey;

	@Value("${snapdeal.sdMoneyDashboard.amazon.downloadUrlExpirationTime}")
	private long downloadUrlExpirationTime;

	AmazonS3Client s3client;

	@PostConstruct
	private void initClient() throws Exception {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
		s3client = new AmazonS3Client(awsCreds);

	}

	public void putObject(String source, String destination, Map<String, String> userMetadata) {

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setUserMetadata(userMetadata);

		File file = new File(source);
		s3client.setEndpoint("s3-ap-southeast-1.amazonaws.com");
		s3client.putObject(
				new PutObjectRequest(bucketName, destinationPrefix + destination, file).withMetadata(objectMetadata));

	}

	public URL generatePresignedUrl(String destination) throws Exception {

		Date expiration = new java.util.Date();
		long milliSeconds = expiration.getTime();
		milliSeconds += 1000 * downloadUrlExpirationTime;
		expiration.setTime(milliSeconds);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
				destinationPrefix + destination);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);
		generatePresignedUrlRequest.setExpiration(expiration);

		URL s3Url =s3client.generatePresignedUrl(generatePresignedUrlRequest);

		return s3Url;

	}

	public List<String> listFiles( String prefix ) throws Exception {

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
				.withBucketName(bucketName).withPrefix( destinationPrefix + prefix );
		ObjectListing objectListing;
		List<String> fileList = new ArrayList<String>();

		do {

			objectListing = s3client.listObjects(listObjectsRequest);

			for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
				String key = objectSummary.getKey();
	            fileList.add( key.replace( destinationPrefix + prefix, "") );
			}

			listObjectsRequest.setMarker(objectListing.getNextMarker());

		} while (objectListing.isTruncated());

		return fileList;
	}

	public Map<String, String> getFileMetaData( String destination ) throws Exception {

		ObjectMetadata objectMetadata = s3client.getObjectMetadata(bucketName, destinationPrefix + destination );
		return objectMetadata.getUserMetadata();

	}

}
