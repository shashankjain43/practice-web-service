package com.snapdeal.ims.client.dbmapper.entity.info;

public enum ActivityStatus {

	SUCCESS("success"), FAILURE("failure");
	private String description;

	private ActivityStatus(String description) {
		this.description = description;
	}

	public String toString() {
		return description;
	}

}
