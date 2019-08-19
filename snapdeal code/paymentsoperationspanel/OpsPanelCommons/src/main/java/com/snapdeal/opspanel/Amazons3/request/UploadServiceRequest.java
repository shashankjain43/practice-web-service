package com.snapdeal.opspanel.Amazons3.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NonNull;

@Data
public class UploadServiceRequest {
	@NotNull(message="File Sorce can not be left empty")
	private String fileSource;
	@NotNull(message="Email can not be left null")
	private String email;
	
	private String merchantId;
	private String refundKey;
	private String merchantName;
	@NotNull(message="File Name Can not be left null")
	private String fileName;
	@NotNull(message="Upload time can not be left null")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy hh:mm:ss", timezone="IST" )
	private Date uploadTime;
	@NotNull(message="Please specify whether file is input or output")
	@JsonProperty
	private boolean isInputFile;
	
	
}
