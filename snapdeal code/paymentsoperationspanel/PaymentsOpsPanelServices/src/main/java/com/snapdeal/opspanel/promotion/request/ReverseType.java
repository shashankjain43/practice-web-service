package com.snapdeal.opspanel.promotion.request;

public enum ReverseType {
	WALLET("WALLET"),PG("PG"),WALLET_AND_PG("WALLET_AND_PG");
	
	private String val;

	private ReverseType(String val) {
		this.val = val;
	}
	
	public String getReverseTypeVal(){
		return val;
	}
	
	public ReverseType getReverseType(String val){
		for(ReverseType type:ReverseType.values()) {
    		if(type.name().equalsIgnoreCase(val)) {
    			return type;
    		}
    	}
    	return null;
	}

}
