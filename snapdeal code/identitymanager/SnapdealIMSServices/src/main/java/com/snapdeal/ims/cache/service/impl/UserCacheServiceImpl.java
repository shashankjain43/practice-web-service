package com.snapdeal.ims.cache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.cache.set.IIMSAerospikeSet;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.enums.UserStatus;

@Service
public class UserCacheServiceImpl implements IUserCacheService{
   
   @Autowired
   @Qualifier("userIdUserDetailsSet")
   private IIMSAerospikeSet<String, User> userIdUserDetailsSet;

   @Autowired
   @Qualifier("sdIdUserIdSet")
   private IIMSAerospikeSet<Integer, String> sdIdUserIdSet;

   @Autowired
   @Qualifier("fcIdUserIdSet")
   private IIMSAerospikeSet<Integer, String> fcIdUserIdSet;

   @Autowired
   @Qualifier("sdfcIdUserIdSet")
   private IIMSAerospikeSet<Integer, String> sdfcIdUserIdSet;

   @Autowired
   @Qualifier("emailIdUserIdSet")
   private IIMSAerospikeSet<String, String> emailIdUserIdSet;

   @Autowired
   @Qualifier("mobileNumberUserIdSet")
   private IIMSAerospikeSet<String, String> mobileNumberUserIdSet;

   @Override
   public User getUserById(String userId) {
      return userIdUserDetailsSet.get(userId).getValue();
   }

   @Override
   public User getUserBySdId(int sdUserId) {
      User user = null;
      String userId = sdIdUserIdSet.get(sdUserId).getValue();
      
      if(userId!=null){
         user = getUserById(userId);
      }
      
      return user;
   }

   @Override
   public User getUserByFcId(int fcUserId) {
      User user = null;
      String userId = fcIdUserIdSet.get(fcUserId).getValue();
      
      if(userId!=null){
         user = getUserById(userId);
      }
      
      return user;
   }

   @Override
   public User getUserBySdFcId(int sdFcUserId) {
      User user = null;
      String userId = sdfcIdUserIdSet.get(sdFcUserId).getValue();
      
      if(userId!=null){
         user = getUserById(userId);
      }
      
      return user;
   }

   @Override
   public User getUserByEmail(String email) {
      User user = null;
      String userId = emailIdUserIdSet.get(email).getValue();
      
      if(userId!=null){
         user = getUserById(userId);
      }
      
      return user;
   }

   @Override
   public User getUserByMobile(String mobileNumber) {
      User user = null;
      String userId = mobileNumberUserIdSet.get(mobileNumber).getValue();
      
      if(userId!=null){
         user = getUserById(userId);
      }
      
      return user;
   }

   @Override
   public boolean invalidateUserById(String userId) {
      
      User user = getUserById(userId);
      if(user==null){
         return true;
      }

      //invalidate mobile mapping everytime whenever user is invalidated
      mobileNumberUserIdSet.delete(user.getMobileNumber());
      
      return userIdUserDetailsSet.delete(userId);
   }

   @Override
   public boolean invalidateUserBySdId(int sdUserId) {
      boolean result = true;
      User user = getUserBySdId(sdUserId);
      if(user==null){
         return result;
      }
      
      result = sdIdUserIdSet.delete(sdUserId);
      if(result){
         result = invalidateUserById(user.getUserId());
      }

      return result;
   }

   @Override
   public boolean invalidateUserByFcId(int fcUserId) {
      boolean result = true;
      User user = getUserByFcId(fcUserId);
      if(user==null){
         return result;
      }
      
      result = fcIdUserIdSet.delete(fcUserId);
      if(result){
         result = invalidateUserById(user.getUserId());
      }

      return result;
   }

   @Override
   public boolean invalidateUserBySdFcId(int sdFcUserId) {
      boolean result = true;
      User user = getUserBySdFcId(sdFcUserId);
      if(user==null){
         return result;
      }
      
      result = sdfcIdUserIdSet.delete(sdFcUserId);
      if(result){
         result = invalidateUserById(user.getUserId());
      }

      return result;
   }

   @Override
   public boolean invalidateUserByEmail(String email) {
      boolean result = true;
      User user = getUserByEmail(email);
      if(user==null){
         return result;
      }
      
      result = emailIdUserIdSet.delete(email);
      if(result){
         result = invalidateUserById(user.getUserId());
      }

      return result;
   }

   @Override
   public boolean invalidateUserByMobile(String mobileNumber) {
      boolean result = true;
      User user = getUserByMobile(mobileNumber);
      if(user==null){
         return result;
      }
      
      result = mobileNumberUserIdSet.delete(mobileNumber);
      if(result){
         result = invalidateUserById(user.getUserId());
      }

      return result;
   }

   @Override
   public boolean putUser(User user) {
      boolean writtenSuccessful = true;
      
      if(user==null){
         return false;
      }

      if(user.getStatus()==UserStatus.TEMP){
         // restrict user with temp state to cache it in Aerospike
         return false;
      }

      if(user.getUserId()==null){
         return false;
      }

      writtenSuccessful = userIdUserDetailsSet.insert(user.getUserId(), user);

      //Create mappings of this userid return true if any of the mapping is created
      if(user.getEmailId()!=null && !emailIdUserIdSet.exist(user.getEmailId())){
         emailIdUserIdSet.insert(user.getEmailId(), user.getUserId());
      }
      
      if(user.getMobileNumber()!=null && !mobileNumberUserIdSet.exist(user.getMobileNumber())){
         mobileNumberUserIdSet.insert(user.getMobileNumber(), user.getUserId());
      }
      
      if(user.getSdUserId()!=null && !sdIdUserIdSet.exist(user.getSdUserId())){
         sdIdUserIdSet.insert(user.getSdUserId(), user.getUserId());
      }
      
      if(user.getFcUserId()!=null && !fcIdUserIdSet.exist(user.getFcUserId())){
         fcIdUserIdSet.insert(user.getFcUserId(), user.getUserId());
      }
      
      if(user.getSdFcUserId()!=null && !sdfcIdUserIdSet.exist(user.getSdFcUserId())){
         sdfcIdUserIdSet.insert(user.getSdFcUserId(), user.getUserId());
      }
      
      return writtenSuccessful;
   }
   
   @Override
   public boolean invalidateEmailIdByUserId(String emailId){
	   
	  return  emailIdUserIdSet.delete(emailId);
   }

}
