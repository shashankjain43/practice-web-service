package com.snapdeal.opspanel.promotion.service.impl;

import org.springframework.stereotype.Service;

import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.client.impl.UserServiceClientImpl;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.opspanel.promotion.service.IMSService;

@Service("IMSService")
public class IMSServiceImpl implements IMSService {

	
	IUserServiceClient iuserserviceclient= new UserServiceClientImpl();
	
	public String getimsIdByMobileNUmber(String mobilenumber) throws HttpTransportException, ServiceException {
		GetUserByMobileRequest request = new GetUserByMobileRequest();
		request.setMobileNumber(mobilenumber);
		GetUserResponse response = iuserserviceclient.getUserByVerifiedMobile(request);
		return response.getUserDetails().getUserId();
	}

	public String getimsIdByEmail(String emailId) throws HttpTransportException, ServiceException {
		GetUserByEmailRequest request = new GetUserByEmailRequest();
		request.setEmailId(emailId);
		GetUserResponse response = iuserserviceclient.getUserByEmail(request);
		return response.getUserDetails().getUserId();
	}
	
	public String getEmailId(String userId) throws HttpTransportException, ServiceException {
		GetUserByIdRequest request = new GetUserByIdRequest();
		request.setUserId(userId);
		GetUserResponse response = iuserserviceclient.getUserById(request);
		return response.getUserDetails().getEmailId();
	}

	
	@Override
	public UserDetailsDTO getUserByImsId(String imsId) throws HttpTransportException, ServiceException {
		
		GetUserByIdRequest request = new GetUserByIdRequest();
		request.setUserId(imsId);
		GetUserResponse response = iuserserviceclient.getUserById(request);
		return response.getUserDetails();
		
	}
	
	public UserDetailsDTO getUserByMobile(String mobilenumber) throws HttpTransportException, ServiceException {
		GetUserByMobileRequest request = new GetUserByMobileRequest();
		request.setMobileNumber(mobilenumber);
		GetUserResponse response = iuserserviceclient.getUserByVerifiedMobile(request);
		return response.getUserDetails();
	}

	
	
	
}
