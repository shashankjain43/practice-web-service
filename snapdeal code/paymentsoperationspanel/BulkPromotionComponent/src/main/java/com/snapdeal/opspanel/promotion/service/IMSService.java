package com.snapdeal.opspanel.promotion.service;

import org.springframework.stereotype.Service;

import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;

@Service("IMSService")
public interface IMSService {
	public String getimsIdByMobileNUmber(String mobilenumber)
			throws HttpTransportException, ServiceException;

	public String getimsIdByEmail(String emailId)
			throws HttpTransportException, ServiceException;

	public String getEmailId(String userId) throws HttpTransportException,
			ServiceException;
	
	public UserDetailsDTO getUserByImsId(String imsId) throws HttpTransportException,
	ServiceException; 
	
	public UserDetailsDTO getUserByMobile(String imsId) throws HttpTransportException,
	ServiceException;
	
	
}
