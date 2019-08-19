package com.snapdeal.admin.dao;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.admin.dao.entity.WhitelistAPIDetails;

public interface IWhitelistApisDao {

   public WhitelistAPIDetails getWhitelistApiByClientIdAndImsApiId(@Param("clientId") String clientId, @Param("imsApiId") long imsApiId);

   public void insert(WhitelistAPIDetails whitelistApiRecord);

   public void updateStatus(WhitelistAPIDetails whitelistApiRecord);
}
