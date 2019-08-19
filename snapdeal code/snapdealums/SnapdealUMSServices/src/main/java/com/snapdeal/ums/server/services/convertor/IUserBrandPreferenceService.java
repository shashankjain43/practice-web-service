package com.snapdeal.ums.server.services.convertor;

import com.snapdeal.ums.server.services.impl.GetUserBrandRequest;
import com.snapdeal.ums.server.services.impl.GetUserBrandResponse;

public interface IUserBrandPreferenceService {

	public GetUserBrandResponse getUserBrandByEmailOrId(GetUserBrandRequest request);
	

}
