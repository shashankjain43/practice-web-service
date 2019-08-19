package com.snapdeal.merchant.response;

import java.io.InputStream;

import lombok.Data;

@Data
public class DownloadFileStreamResponse extends AbstractResponse{
	
	private static final long serialVersionUID = 1L;

	InputStream input;
}
