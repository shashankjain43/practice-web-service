package com.snapdeal.admin.dao;

import java.util.List;

import com.snapdeal.admin.dao.entity.IMSAPIDetails;

public interface IImsApisDao {

   public List<IMSAPIDetails> getAllApis();

   public void insert(IMSAPIDetails imsApiDetails);

   public void update(IMSAPIDetails imsApiDetails);

}
