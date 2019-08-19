package com.snapdeal.merchant.amazonS3Service.impl;

import java.io.File;
import java.net.URL;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.snapdeal.merchant.amazonS3Service.IAmazonS3Service;
import com.snapdeal.merchant.exception.S3ServiceException;
import com.snapdeal.merchant.request.DownloadFileRequest;
import com.snapdeal.merchant.request.UploadFileRequest;
import com.snapdeal.merchant.response.UploadFileResponse;
import com.snapdeal.merchant.utils.AmazonS3FilePathUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AmazonS3ServiceImpl implements IAmazonS3Service {


	public URL getFileStream(AmazonS3Client s3Client,DownloadFileRequest request ) throws S3ServiceException {

		String bucketName = request.getBucketName();
		String key = request.getObjectKey();

		URL url ;
		try {
			log.info("Downloading an object");
			url = generatePresignedUrl(s3Client,request);

		} catch (AmazonServiceException ase) {
			log.error(
					"Exception occurred when request made it to Amazon S3: {}", ase);
			log.error("Error Message:  {}  ", ase.getMessage());
			log.error("HTTP Status Code: {} ", ase.getStatusCode());
			log.error("AWS Error Code: {}  ", ase.getErrorCode());
			log.error("Error Type: {}      ", ase.getErrorType());
			log.error("Request ID:   {}    ", ase.getRequestId());
			
			throw new S3ServiceException("Amazon service exception when request made it to Amazon S3");
		} catch (AmazonClientException ace) {
			log.error(
					"Client encountered  an internal error while trying to  communicate with S3: {}", ace);
			log.error("Error Message: {}", ace.getMessage());
			
			throw new S3ServiceException("Client internal error");
		}
		
		return url;

	}

	public UploadFileResponse pushFile(AmazonS3Client s3Client,UploadFileRequest request) throws S3ServiceException {

		UploadFileResponse response = new UploadFileResponse();
		String bucketName = request.getBucketName();
		String destinationprefix = request.getDestinationprefix();
		String objectKey = null;

		try {

			log.info("Uploading a new object to S3 from a file\n");

			File file = new File(request.getUploadFileLocation());
			objectKey = AmazonS3FilePathUtil.createAmazonS3FilePath(request.getSubPrefix(), request.getUploadFileName(), destinationprefix);

			s3Client.putObject(new PutObjectRequest(bucketName, objectKey, file));

		} catch (AmazonServiceException ase) {
			log.error(
					"Exception occurred when request made it to Amazon S3: {}", ase);
			log.error("Error Message:  {}  ", ase.getMessage());
			log.error("HTTP Status Code: {}", ase.getStatusCode());
			log.error("AWS Error Code:  {} ", ase.getErrorCode());
			log.error("Error Type:      {} ", ase.getErrorType());
			log.error("Request ID:       {} ", ase.getRequestId());
			
			throw new S3ServiceException("Amazon service exception when request made it to Amazon S3");
		} catch (AmazonClientException ace) {
			log.error(
					"Client encountered  an internal error while trying to  communicate with S3: {}", ace);
			log.error("Error Message: {}", ace.getMessage());
			
			throw new S3ServiceException("Client internal error");
		}
		
		response.setObjectKey(objectKey);
		return response;
	}

	private URL generatePresignedUrl(AmazonS3Client s3Client,DownloadFileRequest request) {
		
		String bucketName = request.getBucketName();
		String objectKey = request.getObjectKey();
		int downloadURLExpirationtime = request.getExpirationTime();
		
		 Date expiration = new java.util.Date();
	      long milliSeconds = expiration.getTime();
	      milliSeconds += 1000 * downloadURLExpirationtime;
	      expiration.setTime(milliSeconds);
	      
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				bucketName, objectKey);

		generatePresignedUrlRequest.setMethod(HttpMethod.GET);
		generatePresignedUrlRequest.setExpiration(expiration);

		return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
	}
	
	
}
