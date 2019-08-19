package com.snapdeal.opspanel.userpanel.service;

import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.SearchUserRequest;
import com.snapdeal.opspanel.userpanel.response.SearchUserResponse;

@Service("searchUserServicesAgg")
public interface SearchUserServicesAgg {
	
	public SearchUserResponse searchUser(SearchUserRequest searchUserRequest) throws InfoPanelException;

}
