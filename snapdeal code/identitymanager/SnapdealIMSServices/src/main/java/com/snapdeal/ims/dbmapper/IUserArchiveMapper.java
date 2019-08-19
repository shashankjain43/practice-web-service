package com.snapdeal.ims.dbmapper;

import org.apache.ibatis.annotations.Param;


public interface IUserArchiveMapper {

	/**
	 * moves user table row corresponding to emailId to user_archive table
	 */
	public Integer archiveUser(@Param("emailId") String emailId);
	
	/**
	 * deletes upgrade table row corresponding to emailId
	 */
	public Integer deleteUpgradedUser(@Param("emailId")String emailId);
	
	/**
	 * deletes user table row corresponding to emailId
	 */
	public Integer deleteUser(@Param("emailId")String emailId);

}
