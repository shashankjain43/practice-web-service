package com.snapdeal.ims.dao;

import com.google.common.base.Optional;
import com.snapdeal.ims.dbmapper.entity.UserLockDetails;

import java.util.Date;


public interface IUserLockDao {
	
	/**
	 * This function will create user lock info in IMS database
	 */
	public void lockUserEntry(UserLockDetails userLockDetails);

	/**
	 * This function will delete locked user in IMS database
	 * @param userId
	 * 
	 */
	public void unLockUser(String userId);

	/**
	 * Get login locked user info by userId from IMS database
	 * @param userId
	 */
	public Optional<UserLockDetails> getLockUserEntry(String userId);

	/**
	 * Update user lock info in IMS database
	 * @param userID
	 * @param loginAttempts
	 * @param status
	 * @param expiryTime
	 */
	public void updateLockUserEntry(UserLockDetails userLockDetails);
}
