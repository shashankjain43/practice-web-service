package com.snapdeal.ims.service;

import java.util.List;

import com.snapdeal.ims.entity.UserEntity;
import com.snapdeal.ims.entity.UserSearchEnteredEntity;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;
import com.snapdeal.ims.response.GetUserHistoryDetailsResponse;


public interface IUserSearchService {
	public List<UserEntity> getUserByBasicSearch(UserSearchEnteredEntity userEnteredValue);

	public List<UserEntity> getArchivedUser(UserSearchEnteredEntity userEnteredValue);
	
	public GetUserHistoryDetailsResponse getUserHistoryDetails(GetUserHistoryDetailsRequest request);

}
