package com.snapdeal.ums.server.services.impl;



import com.snapdeal.base.model.common.ServiceRequest;


public class GetUserBrandRequest extends ServiceRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1573139334375843595L;



	
	public GetUserBrandRequest() {
    }


	private String value;

	
	
	public GetUserBrandRequest(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value= value;
	}
	
	
}
