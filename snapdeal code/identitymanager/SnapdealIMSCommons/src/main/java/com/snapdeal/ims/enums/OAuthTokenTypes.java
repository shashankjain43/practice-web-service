package com.snapdeal.ims.enums;

public enum OAuthTokenTypes {

	AUTH_CODE("AUTH_CODE"),
	ACCESS_TOKEN("ACCESS_TOKEN"),
	REFRESH_TOKEN("REFRESH_TOKEN");
	
	private String tokenType;

	private OAuthTokenTypes(String tokenType) {
		this.tokenType = tokenType;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public static OAuthTokenTypes forName(String value) {
	      if (value != null) {
	         for (OAuthTokenTypes eachSrc : values()) {
	            if (eachSrc.name().equalsIgnoreCase(value.trim())) {
	               return eachSrc;
	            }
	         }
	      }
	      return null;
	   }

	
}
