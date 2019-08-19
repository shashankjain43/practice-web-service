package com.snapdeal.ims.dashboard.dbmapper;

import java.util.List;

import com.snapdeal.ims.entity.UserEntity;
import com.snapdeal.ims.entity.UserSearchEnteredEntity;


public interface IUserSearchDao {
	
	public String getUpgradeStatus(String value);
	
	public List<UserEntity> getUserSearch(UserSearchEnteredEntity value);

	public List<UserEntity> getUserArchivedSearch(UserSearchEnteredEntity userEnteredValue);
	
	
}
