package com.snapdeal.merchant.entity;

import com.snapdeal.merchant.enums.DownloadStatus;

import lombok.Data;

@Data
public class DownloadInfo {
	
	private int id;
	
	private boolean viewed;
	
	private String fileName;
	
	private DownloadStatus status;
	
	private long timestamp;

}
