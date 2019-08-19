package com.snapdeal.ims.enums;

import org.codehaus.jackson.annotate.JsonCreator;

public enum GetConfigurationDetails {

	OTP("OTP"),
	RESET_PASSWORD("RESET_PASSWORD") ;
	
	
	private final String getConfigurationDetails;
	GetConfigurationDetails(String getConfigurationDetails) {
		this.getConfigurationDetails = getConfigurationDetails;
	}
	@org.codehaus.jackson.annotate.JsonValue
	public String getGetConfigurationDetails() {
		return this.getConfigurationDetails;
	}
	@JsonCreator
	public static GetConfigurationDetails forValue(String value) {
		if (null != value) {
			for (GetConfigurationDetails getConfigurationDetails : values()) {
				if (getConfigurationDetails.getGetConfigurationDetails().equalsIgnoreCase(value)) {
					return getConfigurationDetails;
				}

			}
		}
		return null;

	}
}
