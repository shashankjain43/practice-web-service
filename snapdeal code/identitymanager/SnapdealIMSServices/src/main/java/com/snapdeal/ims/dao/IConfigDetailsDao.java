package com.snapdeal.ims.dao;

import java.util.List;

import com.snapdeal.ims.dbmapper.entity.ConfigDetails;

public interface IConfigDetailsDao {

	/**
	 * This function is used to retrieve all configs from IMS database
	 * @return
	 */
	public List<ConfigDetails> getAllConfigs();

	/**
	 * Get configs by key and type from IMS database
	 * @param configKey
	 * @param configType
	 * @return
	 */
	public ConfigDetails getConfig(String configKey, String configType);

	/**
	 * Get config by key.
	 * @param configKey
	 * @return
	 */
	public List<ConfigDetails>  getConfigsByKey(String configKey);

	/**
	 * Get config by configType
	 * @param configType
	 * @return
	 */
	public List<ConfigDetails> getConfigsByType(String configType);


	   /**
    * Update config by key and type in IMS database
    * 
    * @param configKey
    * @param configType
    * @param configValue
    * @param discription
    */
}
