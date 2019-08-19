package com.snapdeal.bulkprocess.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListFilesRequest {
	
	private String userId;
	
	private String activityId;
	
	private String prevMarker;
	
	private String marker;
	
	private int pageNumber;
	
	private int pageSize;
	
	@JsonProperty
	private boolean isInputFile;
	
	@JsonProperty
	private boolean isSuperUser;

}
