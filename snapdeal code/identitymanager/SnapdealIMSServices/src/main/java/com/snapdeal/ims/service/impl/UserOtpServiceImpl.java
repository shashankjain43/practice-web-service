package com.snapdeal.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ims.dashboard.dbmapper.IUserOtpDetailsDao;
import com.snapdeal.ims.entity.UserOtp;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.GetUserOtpDetailsRequest;
import com.snapdeal.ims.response.GetUserOtpDetailsResponse;
import com.snapdeal.ims.service.IUserOtpService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service
@Transactional("transactionManager")
public class UserOtpServiceImpl implements IUserOtpService {
	@Autowired
	IUserOtpDetailsDao userOtpDetailsDao;

	@Override
	@Logged
	@Timed
	@Marked
	public GetUserOtpDetailsResponse getUserOtpDetails(
			GetUserOtpDetailsRequest request) {
		GetUserOtpDetailsResponse response = new GetUserOtpDetailsResponse();
		List<UserOtp> userOtpDetails = null;
		switch (request.getSearchField()) {
		case EMAIL_ID:
			userOtpDetails = userOtpDetailsDao
					.getUserOtpDetailsWithEmail(request);
			break;
		case MOBILE_NUMBER:
			userOtpDetails = userOtpDetailsDao
					.getUserOtpDetailsWithMobile(request);
			break;
		case USER_ID:
			userOtpDetails = userOtpDetailsDao
					.getUserOtpDetailsWithUserid(request);
			break;
		case OTP_ID:
			userOtpDetails = userOtpDetailsDao
					.getUserOtpDetailsWithOtpid(request);
			break;
		default:
			throw new IMSServiceException(
					IMSRequestExceptionCodes.INVALID_SEARCHFIELD.errCode(),
					IMSRequestExceptionCodes.INVALID_SEARCHFIELD.errMsg());

		}
		if (userOtpDetails == null || userOtpDetails.size() == 0) {
			throw new IMSServiceException(
					IMSRequestExceptionCodes.OTP_NOT_FOUND.errCode(),
					IMSRequestExceptionCodes.OTP_NOT_FOUND.errMsg());
		}
		response.setUserOtpDetails(userOtpDetails);
		return response;

	}
}