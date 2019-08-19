package com.snapdeal.ims.client.dbmapper.entity.info;

public enum ClientStatus {
	ACTIVE("ACTIVE"),INACTIVE("INACTIVE");

	private String clientStatus;

	ClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}

	public String getValue() {
		return clientStatus;
	}
}
