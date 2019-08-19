package com.snapdeal.ims.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ims.dao.IIMSApiDao;
import com.snapdeal.ims.dbmapper.IIMSApiMapper;
import com.snapdeal.ims.dbmapper.entity.IMSApi;

@Repository("IMSApiDaoImpl")
public class IMSApiDaoImpl implements IIMSApiDao{

   @Autowired
   private IIMSApiMapper imsApiMapper;
   
   @Override
   public IMSApi getIMSApiById(int id) {
      return imsApiMapper.getApiById(id);
   }

   @Override
   public List<IMSApi> getAllIMSApis() {
      return imsApiMapper.getAllIMSApis();
   }
   
}
