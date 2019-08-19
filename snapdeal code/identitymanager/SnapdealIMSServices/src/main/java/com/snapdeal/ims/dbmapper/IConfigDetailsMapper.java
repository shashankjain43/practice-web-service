package com.snapdeal.ims.dbmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.ims.dbmapper.entity.ConfigDetails;

public interface IConfigDetailsMapper {
	
	/**
	 * This function will create configurations in IMS database
	 * @param configDetails
	 */
	public void createConfig(ConfigDetails configDetails);

	/**
	 * This function will delete configuration in IMS database
	 * @param configKey
	 * @param configType
	 */
	public void deleteConfig(@Param("configKey") String configKey, 
							 @Param("configType") String configType);

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
	public ConfigDetails getConfig(@Param("configKey") String configKey, 
								   @Param("configType") String configType);

	/**
	 * Get config by key.
	 * @param configKey
	 * @return
	 */
	public List<ConfigDetails>  getConfigsByKey(@Param("configKey") String configKey);

	/**
	 * Get config by configType
	 * @param configType
	 * @return
	 */
	public List<ConfigDetails> getConfigsByType(@Param("configType") String configType);


	/**
	 * Update config by key and type in IMS database
	 * @param configKey
	 * @param configType
	 * @param configValue
	 * @param discription
	 */
	public void updateConfig(@Param("configKey") String configKey,
							 @Param("configType") String configType,
				    		 @Param("configValue") String configValue, 
				 			 @Param("description") String discription);
}
