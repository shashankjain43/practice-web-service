package com.snapdeal.ims.common;

import com.snapdeal.ims.enums.SendOTPByEnum;

import lombok.Getter;


public enum NewConfigurationConstant {

	CIPHER_UNIQUE_KEY("cipher.unique.key"),
	MD5_SALT_PASSWORD_ENCRYPTION("md5_password_encryption");
	
	private String value;
	
	private NewConfigurationConstant(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}

}
