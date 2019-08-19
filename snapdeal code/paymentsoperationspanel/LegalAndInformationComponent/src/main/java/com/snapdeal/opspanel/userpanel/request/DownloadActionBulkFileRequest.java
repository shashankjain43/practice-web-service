package com.snapdeal.opspanel.userpanel.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DownloadActionBulkFileRequest {

	private String fileName;
	private String userName;
	private String time;

	@JsonProperty
	private boolean isOutputFile;

}
