package com.snapdeal.merchant.response;

import lombok.Data;

@Data
public class UploadFileResponse extends AbstractResponse {
	
	private static final long serialVersionUID = 1L;
	
	String objectKey;

}
