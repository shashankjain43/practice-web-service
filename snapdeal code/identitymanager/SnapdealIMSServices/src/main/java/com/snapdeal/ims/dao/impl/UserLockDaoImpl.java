package com.snapdeal.ims.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.snapdeal.ims.dao.IUserLockDao;
import com.snapdeal.ims.dbmapper.IUserLockMapper;
import com.snapdeal.ims.dbmapper.entity.UserLockDetails;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Repository
public class UserLockDaoImpl implements IUserLockDao{

   @Autowired
   private IUserLockMapper userLockDetailsMapper;
   
   @Override
   @Timed
   @Marked
   public void lockUserEntry(UserLockDetails userLockDetails) {
      userLockDetailsMapper.lockUserEntry(userLockDetails);
   }

   @Override
   @Timed
   @Marked
   public void unLockUser(String userId) {
      userLockDetailsMapper.unLockUser(userId);
   }

   @Override
   @Timed
   @Marked
   public Optional<UserLockDetails> getLockUserEntry(String userId) {
      UserLockDetails userLockDetails = userLockDetailsMapper.getLockUserEntry(userId);
      if (userLockDetails == null) {
         return Optional.<UserLockDetails> absent();
      }
      return Optional.of(userLockDetails);
   }

   @Override
   @Timed
   @Marked
   public void updateLockUserEntry(UserLockDetails userLockDetails) {
      userLockDetailsMapper.updateLockUserEntry(userLockDetails);
   }
}