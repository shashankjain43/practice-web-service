package com.snapdeal.bulkprocess.model;

import java.util.Date;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class UploadRequest {
	
	@NotNull(message="File Sorce can not be left empty")
	private String fileSource;
	
	@NotNull(message="Email can not be left null")
	private String userId;
	
	@NotNull(message="File Name Can not be left null")
	private String fileName;
	
	@NotNull(message="Upload time can not be left null")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z", timezone="IST" )
	private Date uploadTime;
	
	@NotNull(message="Please specify whether file is input or output")
	@JsonProperty
	private boolean isInputFile;
	
	private  String activityId;
	
	private Map<String, String> inputRequestParams;

}
