package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class DownloadFileRequest extends AbstractRequest{

	private static final long serialVersionUID = 1L;
	

	String objectKey;
	String bucketName;
	int ExpirationTime;
}
