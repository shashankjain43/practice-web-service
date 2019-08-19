package com.snapdeal.ims.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SendOTPByEnum {
	
	   FREECHARGE("FREECHARGE"),  // Used for cases related to IMS(Freecharge Wallet)
	   SNAPDEAL("SNAPDEAL"),
	   V0FREECHARGE("V0FREECHARGE"); // Used for cases related to freecharge passthrough

		private final String sendOTPBy;

		SendOTPByEnum(String sendOTPBy) {
			this.sendOTPBy = sendOTPBy;
		}
		
		@org.codehaus.jackson.annotate.JsonValue
		public String getSendOTPByEnum() {
			return this.sendOTPBy;
		}
		
		@JsonCreator
		public static SendOTPByEnum forValue(String value) {
			if (null != value) {
				for (SendOTPByEnum eachPurpose : values()) {
					if (eachPurpose.getSendOTPByEnum().equals(value)) {
						return eachPurpose;
					}
				}
			}
			return null;
		}
}
