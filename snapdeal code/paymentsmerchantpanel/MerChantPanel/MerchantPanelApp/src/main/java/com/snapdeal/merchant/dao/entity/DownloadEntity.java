package com.snapdeal.merchant.dao.entity;

import java.util.Date;

import com.snapdeal.merchant.enums.DownloadStatus;

import lombok.Data;

@Data
public class DownloadEntity {

	private Integer id;
	
	private Integer filterId;
	
	private DownloadStatus status;
	
	private boolean viewed;
	
	private String userId;
	
	private String fileName;
	
	private Date createdOn;
	 
	private String objectKey;
}
