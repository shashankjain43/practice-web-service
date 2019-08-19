package com.snapdeal.opspanel.promotion.enums;

public enum VerificationCallStatus {
	
	CONNECTEDVAREIFIED("Connected-Verified"),
	CONNECTEDVERIFICATIONFAIL("Connected - Verification Fail"),
	CONNECTEDNOTVERIFIED("Connected - Not Verified"),
	CALLBACK("Call Back"),
	NOANSWER("No Answer"),
	SWITCHEDOFF("Switched Off"),
	NOTREACHABLE("Not Reachable"),
	OTHERS("Others");

	private String stringValue;
	
	VerificationCallStatus(String temp) {
		stringValue=temp;
	}

	public String getStringValue(){
		return stringValue;	
	}
}
