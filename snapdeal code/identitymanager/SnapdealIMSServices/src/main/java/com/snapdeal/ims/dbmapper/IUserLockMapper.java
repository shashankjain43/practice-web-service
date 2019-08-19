package com.snapdeal.ims.dbmapper;

import com.snapdeal.ims.dbmapper.entity.UserLockDetails;

import org.apache.ibatis.annotations.Param;

public interface IUserLockMapper {
	
	/**
	 * This function will create user lock entry in IMS database
	 * @param userLockDetails
	 */
	public void lockUserEntry(UserLockDetails userLockDetails);

	/**
	 * This function will delete locked user in IMS database
	 * @param userId
	 */
	public void unLockUser(@Param("userId") String userId);

	/**
	 * Get user lock info by userId from IMS database
	 * @param userId
	 * @return
	 */
	public UserLockDetails getLockUserEntry(@Param("userId") String userId);

	/**
	 * Update user lock info in IMS database
	 * @param userId
	 * @param configType
	 * @param configValue
	 * @param discription
	 */
	public void updateLockUserEntry(UserLockDetails userLockDetails);
}
