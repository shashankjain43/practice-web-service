package com.snapdeal.ims.dbmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.ims.dbmapper.entity.IMSApi;

/**
 * @author himanshu
 *
 */
public interface IIMSApiMapper {

   /**
    * @param id
    * @return
    */
   public IMSApi getApiById(@Param("id") int id);

   /**
    * @return
    */
   public List<IMSApi> getAllIMSApis();
   
}
