package com.snapdeal.ums.server.services.impl;

import java.util.List;

import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.user.GetUserByIdResponse;

public class GetUserBrandResponse extends ServiceResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6743673820641394324L;
	
	private List<String> listOfUserBrands;
	
	

	private GetUserByIdResponse userIdResponse;

	private GetUserByEmailResponse userResponse;

	public List<String> getListOfUserBrands() {
		return listOfUserBrands;
	}

	public void setListOfUserBrands(List<String> listOfUserBrands) {
		this.listOfUserBrands = listOfUserBrands;
	}

	public GetUserBrandResponse(GetUserByEmailResponse userResponse) {
		super();
		this.userResponse = userResponse;
	}

	public GetUserBrandResponse(GetUserByIdResponse userIdResponse) {
		super();
		this.userIdResponse = userIdResponse;
	}

	public GetUserBrandResponse() {
		// TODO Auto-generated constructor stub
	}

	public GetUserByEmailResponse getUserResponse() {
		return userResponse;
	}

	public void setUserResponse(GetUserByEmailResponse userResponse) {
		this.userResponse = userResponse;
	}

	public GetUserByIdResponse getUserIdResponse() {
		return userIdResponse;
	}

	public void setUserIdResponse(GetUserByIdResponse userIdResponse) {
		this.userIdResponse = userIdResponse;
	}
}
