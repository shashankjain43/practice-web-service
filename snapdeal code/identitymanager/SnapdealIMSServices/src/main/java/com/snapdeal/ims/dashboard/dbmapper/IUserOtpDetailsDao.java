package com.snapdeal.ims.dashboard.dbmapper;

import java.util.List;

import com.snapdeal.ims.entity.UserOtp;
import com.snapdeal.ims.request.GetUserOtpDetailsRequest;

public interface IUserOtpDetailsDao {
	public List<UserOtp> getUserOtpDetailsWithEmail(GetUserOtpDetailsRequest request);
	public List<UserOtp> getUserOtpDetailsWithMobile(GetUserOtpDetailsRequest request);
	public List<UserOtp> getUserOtpDetailsWithUserid(GetUserOtpDetailsRequest request);
	public List<UserOtp> getUserOtpDetailsWithOtpid(GetUserOtpDetailsRequest request);

}
