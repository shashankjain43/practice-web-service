package com.snapdeal.bulkprocess.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FileModel {
	
	private String activityId;
	
	private Map<String, String> allMetaData;
	
	private String inputFileName;
	
	private String outputFileName;
	
	@JsonProperty
	private boolean hasOutputFile;

}
