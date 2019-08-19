package com.snapdeal.ims.service;

import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.UniqueMobileVerificationResponse;

public interface IMobileVerificationService {

	public UniqueMobileVerificationResponse verifyUniqueMobile(
			UniqueMobileVerificationRequest request);

	public MobileVerificationStatusResponse isMobileVerified(
			MobileVerificationStatusRequest request);
}
