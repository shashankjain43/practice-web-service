package com.snapdeal.ims.dto;

	
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@Data
public class ClientConfig {
	private String clientKey;
	private String clientId;
	private int apiTimeOut;
	@JsonIgnoreProperties
	private String appRequestId;

	public ClientConfig() {
	   appRequestId = UUID.randomUUID().toString();
	}
}
