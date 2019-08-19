package com.snapdeal.bulkprocess.model;

import java.util.Date;
import java.util.Map;

import lombok.Data;

import com.snapdeal.payments.ts.registration.TaskRequest;
 
@Data
public class GenericBulkTaskRequest implements TaskRequest {

	private String fileName;
	
	private String localPath;
	
	private String s3Path;
	
	private Map<String,String> fileMeta;
	
	private String userId;
	
	private Date uploadTime;
	
	private String activityId;

	public String getTaskId() {
		return fileName + "_" + String.valueOf(uploadTime.getTime());
	}
	
	private Map<String, String> headerValues;
}
