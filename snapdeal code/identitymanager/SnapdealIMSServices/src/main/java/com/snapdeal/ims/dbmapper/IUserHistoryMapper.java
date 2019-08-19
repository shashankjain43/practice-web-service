package com.snapdeal.ims.dbmapper;

import java.util.List;

import com.snapdeal.ims.entity.UserHistory;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;

public interface IUserHistoryMapper {
	/*
	 * copies value in userHistory table each time updation of enabledState, deletedState
	 *, mobileNumber or when user updates its general information.
	*/
	public Integer maintainUserHistory(UserHistory user);

}
