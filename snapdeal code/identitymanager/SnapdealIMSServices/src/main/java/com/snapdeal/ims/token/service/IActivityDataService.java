package com.snapdeal.ims.token.service;


public interface IActivityDataService {
	
	void validateToken(String token);
	
	void setActivityDataByUserId(String userId);

	void setActivityDataByToken(String token);

	void setActivityDataByEmailId(String emailId);

   void validateToken(String token, boolean isOCLinkFlow);
}
