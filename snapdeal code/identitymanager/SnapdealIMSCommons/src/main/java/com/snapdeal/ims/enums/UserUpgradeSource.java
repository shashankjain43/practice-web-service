package com.snapdeal.ims.enums;

public enum UserUpgradeSource {

	UBER("Uber"),
	OTHERS("Others");
	
	private String value;
	
	private UserUpgradeSource(String value){
		this.value = value;
	}
	
	private String getValue(){
		return value;
	}
}
