package com.snapdeal.ims.enums;

public enum Platform {

	WEB("WEB"), WINDOWS_APP("WINDOWS_APP"),WINDOWS_TAB("WINDOWS_TAB"), WAP("WAP"), OTHERS("OTHERS"),ANDROID_APP("ANDROID_APP"),ANDROID_TAB("ANDROID_TAB"),IOS_APP("IOS_APP"),IOS_TAB("IOS_TAB"),SNAPLITE("SNAPLITE");
	
	private String value;

	private Platform(String value) {
		this.value = value;
	}

	private String getValue() {
		return value;
	}

	public static Platform forValue(String value) {
		if (null != value) {
			for (Platform eachPlatform : values()) {
				if (eachPlatform.getValue().equals(value)) {
					return eachPlatform;
				}
			}
		}
		return null;
	}
}
