package com.snapdeal.ims.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.UniqueMobileVerificationResponse;
import com.snapdeal.ims.service.IMobileVerificationService;
import com.snapdeal.ims.service.provider.UmsServiceProvider;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service
@Slf4j
public class MobileVerificationServiceImpl implements
		IMobileVerificationService {

	@Autowired
	private IOTPService otpService;
	
	@Autowired
	IActivityDataService activityDataService;

	@Autowired
	private AuthorizationContext context;
	
	@Autowired
	private ITokenService tokenService;
	
	@Autowired
	private UmsServiceProvider serviceProvider;

	
	@Override
   @Timed
   @Marked
	public MobileVerificationStatusResponse isMobileVerified(
			MobileVerificationStatusRequest request) {

		return serviceProvider.getIMSService().isMobileVerified(request);
	}

	
	@Override
   @Timed
   @Marked
	public UniqueMobileVerificationResponse verifyUniqueMobile(
			UniqueMobileVerificationRequest request) {
		return serviceProvider.getIMSService().verifyUniqueMobile(request);
	}
}
