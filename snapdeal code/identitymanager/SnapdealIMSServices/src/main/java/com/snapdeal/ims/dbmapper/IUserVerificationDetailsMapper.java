package com.snapdeal.ims.dbmapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.ims.dbmapper.entity.UserVerification;

public interface IUserVerificationDetailsMapper {
	/**
	 * This function will create guest user_id and 
	 * auto_generated token entry in database
	 * @param UserVerification
	 */
	public void create(UserVerification userVerificationEntity);

	/**
	 * This function will update token expiry time for given token
	 * @param token
	 * @param tokenExpiryTime
	 */
	public void update(@Param("code") String code, 
			@Param("tokenExpiryTime") Timestamp tokenExpiryTime);

	/**
	 * Get UserVerification entry from IMS database by userId
	 * @param userId
	 * @return 
	 */
	public UserVerification getUserVerificationDetailsByUserId(@Param("userId") String userId);
	
	/**
	 * Get UserVerification entry from IMS database by token
	 * @param userId
	 * @return 
	 */
	public UserVerification getUserVerificationDetailsByCode(@Param("code") String code);

	/**
	 * 
	 * @param code
	 */
	public void deleteUserVerificationDetailsByCode(@Param("code") String code);
}
