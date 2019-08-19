package com.snapdeal.ims.common;

import java.io.IOException;
import java.util.Map;

import com.securitymanager.connection.KeyManagerConnectionException;
import com.snapdeal.base.utils.MD5ChecksumUtils;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.cache.ICache;
import com.snapdeal.ims.cache.VaultCache;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.enums.EnvironmentEnum;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.vault.Vault;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class to get configuration with static access.
 */
@Slf4j
public class Configuration {

	/**
	 * Get property for global type, of configuration which are common.
	 * 
	 * @param key
	 *           : {@link String}
	 * @return {@link String}
	 */
	public static String getGlobalProperty(ConfigurationConstants key) {
		return getProperty(ServiceCommonConstants.GLOBAL_CONFIG_TYPE, key);
	}

	public static String getGlobalProperty(ConfigurationConstants key, String dynamicValue) {
		return getProperty(ServiceCommonConstants.GLOBAL_CONFIG_TYPE, key, dynamicValue);
	}

	/**
	 * Get specific property for a config type and key.
	 * 
	 * @param configType
	 *           : config type/config class of type {@link String}
	 * @param key
	 *           : {@link String}
	 * @return {@link String}
	 */
	public static String getProperty(String configType, ConfigurationConstants key) {
		return getProperty(configType, key, null);
	}

	/**
	 * Get specific property for a config type and key.
	 * 
	 * @param configType
	 *           : config type/config class of type {@link String}
	 * @param key
	 *           : {@link String}
	 * @return {@link String}
	 */
	private static String getProperty(String configType,
			ConfigurationConstants key,
			String dynamicValue) {

		if (null == configType || null == key) {
			log.error("ConfigType and Key are mandatory parameters.");
			throw new InternalServerException(
					IMSInternalServerExceptionCodes.CONFIGURATION_NOT_PRESENT.errCode(),
					IMSInternalServerExceptionCodes.CONFIGURATION_NOT_PRESENT.errMsg());
		}
		ICache<String, Map<String, String>> configCache = CacheManager.getInstance().getCache(
				ConfigCache.class);
		if (null == configCache) {
			log.error("Configuration not present for configType:" + configType + " or key : " + key.getKey());
			throw new InternalServerException(
					IMSInternalServerExceptionCodes.CONFIGURATION_NOT_PRESENT.errCode(),
					IMSInternalServerExceptionCodes.CONFIGURATION_NOT_PRESENT.errMsg());
		}

		String key1 = key.getKey();
		if (dynamicValue != null) {
			key1 = key1 + dynamicValue;
		}

		if (configCache.get(configType)==null || configCache.get(configType).get(key1) == null) {
			log.debug("Configuration not present for configType:" + 
								configType + " or key" + key.getKey() + " Picking default value: " + 	
								key.getDefaultValue());
         return key.getDefaultValue();
		}

		return configCache.get(configType).get(key1);
	}

	/**
	 * Get all the properties of configuration type.
	 * 
	 * @param configType
	 *           : String
	 * @return {@link Map}
	 */
	public static Map<String, String> getProperties(String configType) {
		ICache<String, Map<String, String>> configCache = CacheManager.getInstance().getCache(
				ConfigCache.class);
		if (null == configCache || null == configCache.get(configType)) {
			log.error("Configuration not present for configType:" + configType);
			throw new InternalServerException(
					IMSInternalServerExceptionCodes.CONFIGURATION_NOT_PRESENT.errCode(),
					IMSInternalServerExceptionCodes.CONFIGURATION_NOT_PRESENT.errMsg());
		}
		return configCache.get(configType);
	}

	/**
	 * Utility method to get environment.
	 */
	public static EnvironmentEnum getEnvironment() {
		String env = getGlobalProperty(ConfigurationConstants.ENVIRONMENT);
		return EnvironmentEnum.valueOf(env);
	}

	/**
	 * This method is used for fetching client(user facing) 	
	 * properties from configuration cache. This will return default 	
	 * value mentioned in code(ConfigurationConstants enum) 
	 * in case no value is present in cache. 
	 * User will have to put null/blank checks in case default value is null (in enum)   
	 * @param merchant
	 * @param key
	 * @return
	 */
	public static String getClientProperty(String merchant,
			ConfigurationConstants key) {
		return getProperty(merchant, key, null);
	}
	
	public static String getCipherKey(){
		String UNIQUE_KEY;
		if(Boolean.valueOf(Configuration.getGlobalProperty(ConfigurationConstants.VAULT_ENABLED))){
			String storedKey = CacheManager.getInstance().getCache(VaultCache.class).get(NewConfigurationConstant.CIPHER_UNIQUE_KEY.getValue());	
			if(storedKey==null){
				storedKey = Vault.getStoredKey(NewConfigurationConstant.CIPHER_UNIQUE_KEY.getValue());
				CacheManager.getInstance().getCache(VaultCache.class).put(NewConfigurationConstant.CIPHER_UNIQUE_KEY.getValue(),storedKey);
			}
			UNIQUE_KEY = storedKey;
		}else{
			UNIQUE_KEY = Configuration.getGlobalProperty(ConfigurationConstants.CIPHER_UNIQUE_KEY);
		}
		return UNIQUE_KEY;
	}
	
	public static String getMD5EncodedPassword(String text) throws KeyManagerConnectionException, IOException {
		String md5Password = CacheManager.getInstance().getCache(VaultCache.class).get(NewConfigurationConstant.MD5_SALT_PASSWORD_ENCRYPTION.getValue());
		if(md5Password==null){
			md5Password = Vault.getStoredKey(NewConfigurationConstant.MD5_SALT_PASSWORD_ENCRYPTION.getValue());
			CacheManager.getInstance().getCache(VaultCache.class).put(NewConfigurationConstant.MD5_SALT_PASSWORD_ENCRYPTION.getValue(),Vault.getStoredKey(NewConfigurationConstant.MD5_SALT_PASSWORD_ENCRYPTION.getValue()));
		}
		return MD5ChecksumUtils.md5Encode(text, md5Password);

	}
}