package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class UploadFileRequest extends AbstractRequest{

	
	private static final long serialVersionUID = 1L;
	
	String subPrefix;
	String uploadFileLocation;
	String uploadFileName;
	String bucketName;
	String destinationprefix;
}
