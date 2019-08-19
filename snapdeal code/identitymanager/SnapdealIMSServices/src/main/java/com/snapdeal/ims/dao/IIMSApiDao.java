package com.snapdeal.ims.dao;

import java.util.List;

import com.snapdeal.ims.dbmapper.entity.IMSApi;

public interface IIMSApiDao {

   public IMSApi getIMSApiById(int id);
   
   public List<IMSApi> getAllIMSApis();
   
}
