package com.snapdeal.ims.client.dbmapper.entity.info;

public enum ClientPlatform {
	WEB("WEB"), WAP("WAP"), APP("APP");

	private String clientPlatform;

	ClientPlatform(String clientPlatform) {
		this.clientPlatform = clientPlatform;
	}

	public String getValue() {
		return clientPlatform;
	}
}
