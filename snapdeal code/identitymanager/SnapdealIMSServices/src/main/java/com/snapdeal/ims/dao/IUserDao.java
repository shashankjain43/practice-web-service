package com.snapdeal.ims.dao;

import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.entity.UserHistory;
import com.snapdeal.ims.enums.CreateWalletStatus;


public interface IUserDao {
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
   public User getUserById(String userId);

   /**
    * This function returns user by sdUserId
    * 
    * @param sdUserId
    * @return user corresponding to given id
    */
   public User getUserBySdId(Integer sdUserId);

   /**
    * This function returns user by fcUserId
    * 
    * @param fcUserId
    * @return user corresponding to given id
    */
   public User getUserByFcId(Integer fcUserId);

   /**
    * This function returns user by sdFcUserId
    * 
    * @param sdFcUserId
    * @return user corresponding to given id
    */
   public User getUserBySdFcId(Integer sdFcUserId);

   /**
    * This function returns user by mobile number
    * 
    * @param mobileNumber
    * @return user corresponding to given mobileNumber
    */
   public User getUserByMobileNumber(String mobileNumber);

   /**
    * This function returns user by emailId
    * 
    * @param emailId
    * @return user corresponding to given emailId
    */
   public User getUserByEmail(String emailId);

   /**
    * This function will update details of a user
    * corresponding to given id
    * 
    * @param user
    */
   public void updateById(User user);

   /**
    * This function will update details of a user
    * corresponding to given id
    * 
    * @param user
    */
   public void updateBySdId(User user);

   /**
    * This function will update details of a user
    * corresponding to given id
    * 
    * @param user
    */
   public void updateByFcId(User user);

   /**
    * This function will update details of a user
    * corresponding to given id
    * 
    * @param user
    */
   public void updateBySdFcId(User user);

   /**
    * This function hard delete user for corresponding id
    * 
    * @param userId
    */

   public void delete(String userId);

   /**
    * This function will update createWalletStatus(NOT_CREATED/IN_PROGRESS/CREATED/FAILED)
    * @param userId
    * @param newCreateWalletStatus
    */
   public void updateCreateWalletStatus(String userId, CreateWalletStatus newStatus); 

   /**
    * This function will update details of a user
    * corresponding to given email id
    * 
    * @param user
    */
   public void updateByEmailId(User user);

	public void archiveUser(String userId);

	/** if moved user to user_archive */
	public void deleteUpgradedUserStatus(String emailId);
	
	public void deleteUser(String emailId);
	
	/**maintains user's history*/
	public void maintainUserHistory(UserHistory user);
   
}
