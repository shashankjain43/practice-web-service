package com.snapdeal.opspanel.Amazons3.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DownloadRefundFileRequest {
	@NotNull(message="File Name can not be left empty")
	private String fileName;
	@Email(message="Please input valid email Id")
	private String emailId;
	@JsonProperty
	private boolean isInputFile;

}
