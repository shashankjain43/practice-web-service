package com.snapdeal.ims.service;

import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.response.GetAllConfigsResponse;
import com.snapdeal.ims.response.GetConfigByKeyResponse;
import com.snapdeal.ims.response.GetConfigByTypeResponse;
import com.snapdeal.ims.response.GetConfigResponse;

public interface IConfigService {

   /**
    * Get all configs from IMS database
    * 
    * @return
    * @throws ValidationException
    */
   
   public GetAllConfigsResponse getAllConfigs() throws ValidationException;

   /**
    * Get configs by key and type from IMS database
    * 
    * @param configKey
    * @param configType
    * @return GetConfigResponse
    * @throws ValidationException
    */
   public GetConfigResponse getConfig(String configKey, String configType)
      throws ValidationException;

   /**
    * Get config by key.
    * 
    * @param configKey
    * @return GetConfigByKeyResponse
    * @throws ValidationException
    */
   public GetConfigByKeyResponse getConfigsByKey(String configKey) throws ValidationException;

   /**
    * Get config by configType
    * 
    * @param configType
    * @return GetConfigByTypeResponse
    * @throws ValidationException
    */
   public GetConfigByTypeResponse getConfigsByType(String configType) throws ValidationException;

}