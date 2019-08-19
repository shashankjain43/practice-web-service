package com.snapdeal.ims.client;

import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.UpgradeUserByEmailRequest;
import com.snapdeal.ims.response.UpgradeUserByEmailResponse;

public interface IJobSchedularServiceClient {

	/**
	    * It is an API for creating new user with email.
	    */
	   public UpgradeUserByEmailResponse upgradeUser(UpgradeUserByEmailRequest request)
	      throws ServiceException, HttpTransportException;

}
