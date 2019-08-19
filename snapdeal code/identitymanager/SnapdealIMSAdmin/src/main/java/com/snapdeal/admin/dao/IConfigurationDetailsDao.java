package com.snapdeal.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.admin.dao.entity.ConfigurationEntity;
import com.snapdeal.admin.request.CreateConfigurationRequest;
import com.snapdeal.admin.request.GetConfigurationByFieldsRequest;
import com.snapdeal.admin.request.UpdateConfigurationRequest;
import com.snapdeal.admin.response.ConfigurationDetails;

public interface IConfigurationDetailsDao {

   public List<ConfigurationEntity> getAllConfiguration();

   public void createConfiguration(CreateConfigurationRequest request);

   public void updateConfiguration(UpdateConfigurationRequest request);

   public List<ConfigurationEntity> getConfigurationByField(GetConfigurationByFieldsRequest request);

   public void deleteConfiguration(@Param("configKey") String configKey,
            @Param("configType") String configType);

   public void deleteConfigurationByKey(@Param("configKey") String configKey);

   /**
    * Get configs by key and type from IMS database
    * 
    * @param configKey
    * @param configType
    * @return
    */
   public List<ConfigurationEntity> getConfig(@Param("configKey") String configKey, @Param("configType")String configType);

   /**
    * Get config by key.
    * 
    * @param configKey
    * @return
    */
   public List<ConfigurationEntity> getConfigsByKey(String configKey);

   /**
    * Get config by configType
    * 
    * @param configType
    * @return
    */
   public List<ConfigurationEntity> getConfigsByType(String configType);

}
