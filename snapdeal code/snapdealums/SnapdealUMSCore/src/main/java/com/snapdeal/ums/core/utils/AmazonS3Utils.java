/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snapdeal.ums.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.snapdeal.ums.core.dto.S3FileDTO;

/**
 * 
 * @version 1.0, 26-Dec-2014
 * @author Shashank Jain<jain.shashank@snapdeal.com>
 */
@Service
public class AmazonS3Utils {

	private static final Logger LOG = LoggerFactory
			.getLogger(AmazonS3Utils.class);

	public boolean uploadFileToS3(String accessId, String secretKey,
			String fileName, File file, String contentType, String bucketName)
			throws AmazonServiceException, AmazonClientException {
		try {

			String md5Hash = generateMD5(file);
			AWSCredentials s3Credentials = new BasicAWSCredentials(accessId,
					secretKey);
			// create a client connection based on credentials
			AmazonS3 s3Client = new AmazonS3Client(s3Credentials);
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					bucketName, fileName, file);
			ObjectMetadata metaData = new ObjectMetadata();
			metaData.setContentType(contentType);
			// upload file to folder and set it to public
			PutObjectResult response = s3Client.putObject(putObjectRequest
					.withCannedAcl(CannedAccessControlList.PublicRead)
					.withMetadata(metaData));

			if (md5Hash.equals(response.getETag())) {
				LOG.info("Successfully uploaded key: " + fileName + " from bucket: "
						+ bucketName);
				return true;
			} else {
				return false;
			}
		} catch (AmazonServiceException ase) {
			LOG.error("Error Message: " + ase.getMessage()
					+ " HTTP Status Code: " + ase.getStatusCode()
					+ " AWS Error Code: " + ase.getErrorCode()
					+ " Error Type: " + ase.getErrorType() + " Request ID: "
					+ ase.getRequestId());
			throw ase;
		} catch (AmazonClientException ace) {

			LOG.error("Error Message: " + ace.getMessage());
			throw ace;
		}
	}

	private String generateMD5(File file) {

		try {
			FileInputStream inputStream = new FileInputStream(file);
			MessageDigest digest = MessageDigest.getInstance("MD5");

			byte[] bytesBuffer = new byte[1024];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
				digest.update(bytesBuffer, 0, bytesRead);
			}

			byte[] hashedBytes = digest.digest();

			return convertByteArrayToHexString(hashedBytes);
		} catch (Exception e) {
			LOG.error("Error Message: " + e.getMessage());
		}
		return null;
	}

	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString(
					(arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}

	public S3FileDTO downloadFirstFileFromS3BucketDir(String s3AccessId,
			String s3SecretKey, String s3BucketName, String dirName) {

		S3FileDTO fileDTO = null;
		try {
			AWSCredentials s3Credentials = new BasicAWSCredentials(s3AccessId,
					s3SecretKey);
			AmazonS3 s3Client = new AmazonS3Client(s3Credentials);

			// This list the objects particular to a directory(prefix) under
			// given bucket
			ObjectListing list = s3Client.listObjects(s3BucketName, dirName);
			do {
				List<S3ObjectSummary> summaries = list.getObjectSummaries();

				for (S3ObjectSummary summary : summaries) {
					String summaryKey = summary.getKey();
					LOG.info("FileName= "
							+ summaryKey.substring(summaryKey.lastIndexOf('/') + 1));
					LOG.info("key= " + summaryKey);
					/* Retrieve object */
					GetObjectRequest request = new GetObjectRequest(
							s3BucketName, summaryKey);
					S3Object object = s3Client.getObject(request);
					InputStream inFileStream = object.getObjectContent();
					/*
					 * File outFile = new File(summaryKey);
					 * IOUtils.copy(objectContent, new
					 * FileOutputStream(outFile)); files.add(outFile);
					 */
					fileDTO = new S3FileDTO();
					fileDTO.setFileName(summaryKey.substring(summaryKey
							.lastIndexOf('/') + 1));
					if (inFileStream != null) {
						fileDTO.setContent(IOUtils.toByteArray(inFileStream));
					}
					return fileDTO;
				}

				list = s3Client.listNextBatchOfObjects(list);

			} while (list.isTruncated());
		} catch (AmazonServiceException ase) {
			LOG.error("Error Message: " + ase.getMessage()
					+ " HTTP Status Code: " + ase.getStatusCode()
					+ " AWS Error Code: " + ase.getErrorCode()
					+ " Error Type: " + ase.getErrorType() + " Request ID: "
					+ ase.getRequestId());
			throw ase;
		} catch (AmazonClientException ace) {

			LOG.error("Error Message: " + ace.getMessage());
			throw ace;
		} catch (FileNotFoundException fnfe) {
			LOG.error("Error Message: " + fnfe.getMessage());
			LOG.error("FileNotFoundException: ", fnfe);
		} catch (IOException ioe) {
			LOG.error("Error Message: " + ioe.getMessage());
		}
		return fileDTO;
	}

	public List<String> downloadListOfKeysFromBucketDir(String s3AccessId,
			String s3SecretKey, String s3BucketName, String dirName) {
		List<String> inputKeys = new ArrayList<String>();
		try {
			AWSCredentials s3Credentials = new BasicAWSCredentials(s3AccessId,
					s3SecretKey);
			AmazonS3 s3Client = new AmazonS3Client(s3Credentials);

			// This list the objects particular to a directory under given
			// bucket
			ObjectListing list = s3Client.listObjects(s3BucketName, dirName);

			do {
				List<S3ObjectSummary> summaries = list.getObjectSummaries();
				for (S3ObjectSummary summary : summaries) {
					String summaryKey = summary.getKey();
					inputKeys.add(summaryKey);
				}
				list = s3Client.listNextBatchOfObjects(list);
			} while (list.isTruncated());

		} catch (AmazonServiceException ase) {
			LOG.error("Error Message: " + ase.getMessage()
					+ " HTTP Status Code: " + ase.getStatusCode()
					+ " AWS Error Code: " + ase.getErrorCode()
					+ " Error Type: " + ase.getErrorType() + " Request ID: "
					+ ase.getRequestId());
			throw ase;
		} catch (AmazonClientException ace) {

			LOG.error("Error Message: " + ace.getMessage());
			throw ace;
		}
		return inputKeys;
	}

	public byte[] downloadFile(String s3AccessId, String s3SecretKey,
			String s3BucketName, String keyName) {
		byte[] inFile = null;
		try {
			AWSCredentials s3Credentials = new BasicAWSCredentials(s3AccessId,
					s3SecretKey);
			AmazonS3 s3Client = new AmazonS3Client(s3Credentials);
			GetObjectRequest request = new GetObjectRequest(s3BucketName,
					keyName);
			S3Object object = s3Client.getObject(request);
			InputStream inFileStream = object.getObjectContent();
			inFile = IOUtils.toByteArray(inFileStream);
			LOG.info("Successfully downloaded key: " + keyName + " from bucket: "
					+ s3BucketName);
		} catch (AmazonServiceException ase) {
			LOG.error("Error Message: " + ase.getMessage()
					+ " HTTP Status Code: " + ase.getStatusCode()
					+ " AWS Error Code: " + ase.getErrorCode()
					+ " Error Type: " + ase.getErrorType() + " Request ID: "
					+ ase.getRequestId());
			throw ase;
		} catch (AmazonClientException ace) {
			LOG.error("Error Message: " + ace.getMessage());
			throw ace;
		} catch (FileNotFoundException fnfe) {
			LOG.error("Error Message: " + fnfe.getMessage());
			LOG.error("FileNotFoundException: ", fnfe);
		} catch (IOException ioe) {
			LOG.error("Error Message: " + ioe.getMessage());
		}
		return inFile;
	}

	public void deleteFile(String s3AccessId, String s3SecretKey,
			String s3BucketName, String keyName) {
		try {
			AWSCredentials s3Credentials = new BasicAWSCredentials(s3AccessId,
					s3SecretKey);
			AmazonS3 s3Client = new AmazonS3Client(s3Credentials);
			DeleteObjectRequest request = new DeleteObjectRequest(s3BucketName,
					keyName);
			s3Client.deleteObject(request);
			LOG.info("Successfully deleted key: " + keyName + " from bucket: "
					+ s3BucketName);
		} catch (AmazonServiceException ase) {
			LOG.error("Error Message: " + ase.getMessage()
					+ " HTTP Status Code: " + ase.getStatusCode()
					+ " AWS Error Code: " + ase.getErrorCode()
					+ " Error Type: " + ase.getErrorType() + " Request ID: "
					+ ase.getRequestId());
			throw ase;
		} catch (AmazonClientException ace) {
			LOG.error("Error Message: " + ace.getMessage());
			throw ace;
		}
	}

	public void copyFile(String s3AccessId, String s3SecretKey,
			String sourceBucketName, String sourceKey,
			String destinationBucketName, String destinationKey) {
		try {
			AWSCredentials s3Credentials = new BasicAWSCredentials(s3AccessId,
					s3SecretKey);
			AmazonS3 s3Client = new AmazonS3Client(s3Credentials);
			CopyObjectRequest copyObjRequest = new CopyObjectRequest(
					sourceBucketName, sourceKey, destinationBucketName,
					destinationKey);
			s3Client.copyObject(copyObjRequest);
			LOG.info("Successfully copied from sourceKey: {}, sourceBucket: {} "
					+ "to destKey: {} ,destBucket: {}",new Object[]{sourceKey,sourceBucketName
							,destinationKey,destinationBucketName});
		} catch (AmazonServiceException ase) {
			LOG.error("Error Message: " + ase.getMessage()
					+ " HTTP Status Code: " + ase.getStatusCode()
					+ " AWS Error Code: " + ase.getErrorCode()
					+ " Error Type: " + ase.getErrorType() + " Request ID: "
					+ ase.getRequestId());
			throw ase;
		} catch (AmazonClientException ace) {
			LOG.error("Error Message: " + ace.getMessage());
			throw ace;
		}
	}
}