package com.snapdeal.ums.ext.userAddress;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * 
 * @version 1.0, 08-May-2015
 * @author shashank
 */
public enum UserAddressStatus {

	UNKNOWN("UNKNOWN"), SAFE("SAFE"), UNSAFE("UNSAFE");

	private String value;

	UserAddressStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	@JsonValue
	public String toJson() {
		return name().toUpperCase();
	}

	@JsonCreator
	public static UserAddressStatus fromJson(String text) {
		return valueOf(text.toUpperCase());
	}

}
