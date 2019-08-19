package com.snapdeal.ums.admin.userprofile;

import com.snapdeal.base.model.common.ServiceRequest;

public class GetUserProfileRequest extends ServiceRequest{
	
	/**
	 *  Request class to pass the parameter to get user details
	 */

	private static final long serialVersionUID = 1L;



	


	private String value;

	//private Integer id;
	
	public GetUserProfileRequest(String value) {
		
		super();
		this.value = value;
	}
	
	public GetUserProfileRequest() {
    }
	
//	public GetUserProfileRequest(Integer id) {
//		super();
//		this.id = id;
//	}

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
