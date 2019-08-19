package com.snapdeal.ims.enums;

public enum UserUpgradeChannel {

	UBER("Uber"),
	OTHERS("Others");
	
	private String value;
	
	private UserUpgradeChannel(String value){
		this.value = value;
	}
	
	private String getValue(){
		return value;
	}
}
