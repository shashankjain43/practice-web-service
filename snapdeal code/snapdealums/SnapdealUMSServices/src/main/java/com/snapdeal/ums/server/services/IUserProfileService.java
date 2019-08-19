package com.snapdeal.ums.server.services;

import com.snapdeal.ums.admin.userprofile.GetUserProfileRequest;
import com.snapdeal.ums.admin.userprofile.GetUserProfileResponse;
import com.snapdeal.ums.exception.userAddress.UserAddressException;


public interface IUserProfileService {

	
	public GetUserProfileResponse getUserProfileByEmailOrId(GetUserProfileRequest request) throws UserAddressException;
}
