package com.snapdeal.ims.dbmapper;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.ims.dbmapper.entity.User;

public interface IUserMapper {

   /**
    * This function will create a new user and
    * auto generate Id in persistent store
    * 
    * @param user
    */
   public void create(User user);

   /**
    * This function returns user by id
    * 
    * @param userId
    * @return user corresponding to given id
    */
   public User getUserById(@Param("userId") String userId);

   /**
    * This function returns user by sdUserId
    * 
    * @param sdUserId
    * @return user corresponding to given id
    */
   public User getUserBySdId(@Param("sdUserId") Integer sdUserId);

   /**
    * This function returns user by fcUserId
    * 
    * @param fcUserId
    * @return user corresponding to given id
    */
   public User getUserByFcId(@Param("fcUserId") Integer fcUserId);

   /**
    * This function returns user by sdFcUserId
    * 
    * @param sdFcUserId
    * @return user corresponding to given id
    */
   public User getUserBySdFcId(@Param("sdFcUserId") Integer sdFcUserId);

   /**
    * This function returns user by emailId
    * 
    * @param emailId
    * @return user corresponding to given emailId
    */
   public User getUserByEmail(@Param("emailId") String emailId);

   /**
    * This function returns user by mobileNumber
    * 
    * @param mobileNumber
    * @return user corresponding to given mobileNumber
    */
   public User getUserByMobileNumber(@Param("mobileNumber")String mobileNumber);

   /**
    * This function will update details of a user
    * corresponding to given userId
    * 
    * @param user
    * @return the no. of rows affected
    */
   public Integer updateById(@Param("user") User user);

   /**
    * This function will update details of a user
    * corresponding to given sdUserId
    * 
    * @param user
    * @return the no. of rows affected
    */
   public Integer updateBySdId(@Param("user") User user);

   /**
    * This function will update details of a user
    * corresponding to given fcUserId
    * 
    * @param user
    * @return the no. of rows affected
    */
   public Integer updateByFcId(@Param("user") User user);

   /**
    * This function will update details of a user
    * corresponding to given sdfcUserId
    * 
    * @param user
    * @return the no. of rows affected
    */
   public Integer updateBySdFcId(@Param("user") User user);

   /**
    * This function will hard delete details of a user
    * corresponding to given id
    * 
    * @param userId
    */
	public void delete(@Param("userId") String userId);

	/**
	 * This function will update createWalletStatus(NOT_CREATED/IN_PROGRESS/CREATED/FAILED)
	 * @param userId
	 * @param newCreateWalletStatus
	 */
	public void updateCreateWalletStatus(@Param("userId") String userId, @Param("newStatus") String newStatus);

	
	/**
    * This function will update details of a user
    * corresponding to given emailId
    * 
    * @param user
    * @return the no. of rows affected
    */
   public Integer updateByEmailId(@Param("user") User user);

   public Integer getDummyResult(); 

}
