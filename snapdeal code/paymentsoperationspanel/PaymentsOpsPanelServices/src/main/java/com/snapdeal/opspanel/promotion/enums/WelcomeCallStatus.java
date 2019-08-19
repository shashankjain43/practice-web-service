package com.snapdeal.opspanel.promotion.enums;

public enum WelcomeCallStatus {

	CONNECTEDVAREIFIED("Connected-Verified"),
	
	CONNECTEDNOTVERIFIED("Connected - Not Verified"),
	CALLBACK("Call back"),
	NOANSWER("No Answer"),
	SWITCHEDOFF("Switched Off"),
	NOTREACHABLE("Not Reachable"),
	NOTINTERESTED("Not Intrested"),
	OTHERS("Others");

	private String stringValue;
	
	private WelcomeCallStatus(String temp) {
		stringValue=temp;
	}

	public String getstringValue(){
		return stringValue;
		
	}
	
}
