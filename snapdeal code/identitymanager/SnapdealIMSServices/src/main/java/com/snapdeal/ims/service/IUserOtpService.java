package com.snapdeal.ims.service;

import com.snapdeal.ims.request.GetUserOtpDetailsRequest;
import com.snapdeal.ims.response.GetUserOtpDetailsResponse;

/**
 * 
 * @author radhika
 *
 */
public interface IUserOtpService {
	public GetUserOtpDetailsResponse getUserOtpDetails(GetUserOtpDetailsRequest request);

}
