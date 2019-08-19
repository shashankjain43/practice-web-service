package com.snapdeal.ims.client.dbmapper.entity.info;

public enum ClientType {
	USER_FACING("USER_FACING"),NON_USER_FACING("NON_USER_FACING");

	private String clientType;

	ClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getValue() {
		return clientType;
	}
}
