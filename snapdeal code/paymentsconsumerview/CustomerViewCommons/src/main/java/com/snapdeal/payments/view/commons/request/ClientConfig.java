package com.snapdeal.payments.view.commons.request;

import java.util.UUID;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
