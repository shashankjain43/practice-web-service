package com.snapdeal.merchant.amazonS3Service;

import java.net.URL;

import com.amazonaws.services.s3.AmazonS3Client;
import com.snapdeal.merchant.exception.S3ServiceException;
import com.snapdeal.merchant.request.DownloadFileRequest;
import com.snapdeal.merchant.request.UploadFileRequest;
import com.snapdeal.merchant.response.UploadFileResponse;

public interface IAmazonS3Service {
	
	public URL getFileStream(AmazonS3Client s3Client,DownloadFileRequest request  ) throws S3ServiceException;
	
	public UploadFileResponse pushFile(AmazonS3Client s3Client,UploadFileRequest request) throws S3ServiceException;
}
