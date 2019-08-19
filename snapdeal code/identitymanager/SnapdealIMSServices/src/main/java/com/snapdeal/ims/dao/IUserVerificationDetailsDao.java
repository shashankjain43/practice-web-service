package com.snapdeal.ims.dao;

import com.snapdeal.ims.dbmapper.entity.UserVerification;

public interface IUserVerificationDetailsDao {
	/**
	 * This function will create guest user_id and auto_generated token entry in
	 * database
	 * 
	 * @param UserVerification
	 */
	public void create(UserVerification userVerificationEntity);

	/**
	 * 
	 * @param code
	 * @return
	 */
	public UserVerification getUserVerificationDetailsByCode(String code);
	
	
	public String getUserIdbyVerificationCode(String code);

	boolean deleteUserVerificationCode(String code);

}
