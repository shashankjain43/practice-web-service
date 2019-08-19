package com.snapdeal.ims.dashboard.dbmapper;

import java.util.List;

import com.snapdeal.ims.entity.UserHistory;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;

public interface IUserHistoryDetailsMapper {
	/*
	 * copies value in userHistory table each time updation of enabledState, deletedState
	 *, mobileNumber or when user updates its general information.
	*/
	public List<UserHistory> getUserHistoryDetails(GetUserHistoryDetailsRequest request);

}
