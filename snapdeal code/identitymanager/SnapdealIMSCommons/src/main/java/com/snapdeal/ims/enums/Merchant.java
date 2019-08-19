package com.snapdeal.ims.enums;

public enum Merchant {
	
	FREECHARGE("FREECHARGE"),
	SNAPDEAL("SNAPDEAL"),
	ONECHECK("ONECHECK");
	
	private String merchantName;

	private Merchant(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getMerchantName() {
		return merchantName;
	}
	
	public static Merchant forName(String value) {
	      if (value != null) {
	         for (Merchant eachSrc : values()) {
	            if (eachSrc.name().equalsIgnoreCase(value.trim())) {
	               return eachSrc;
	            }
	         }
	      }
	      return null;
	   }

}
