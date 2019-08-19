package com.snapdeal.ims.client;

import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.UniqueMobileVerificationResponse;

public interface IMobileVerificationServiceClient {

	public UniqueMobileVerificationResponse verifyUniqueMobile(UniqueMobileVerificationRequest request)
	   throws ServiceException, HttpTransportException;
	
	public MobileVerificationStatusResponse mobileVerificationStatus(MobileVerificationStatusRequest request)
	    throws ServiceException, HttpTransportException;
}
