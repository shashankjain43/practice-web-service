package com.snapdeal.merchant.entity;

import com.snapdeal.merchant.enums.DownloadStatus;
import com.snapdeal.merchant.enums.RefundStatus;

import lombok.Data;

@Data
public class BulkDownloadInfo {

	private int id;
	
	private boolean viewed;
	
	private String fileName;
	
	private DownloadStatus uploadStatus;
	
	private RefundStatus refundStatus;
	
	private long timestamp;
}
