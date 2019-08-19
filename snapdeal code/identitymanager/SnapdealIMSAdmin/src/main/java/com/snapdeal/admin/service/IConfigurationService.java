package com.snapdeal.admin.service;

import javax.servlet.http.HttpServletRequest;

import com.snapdeal.admin.request.CreateClientBasedConfigurationRequest;
import com.snapdeal.admin.request.CreateConfigurationRequest;
import com.snapdeal.admin.request.DeleteConfigurationRequest;
import com.snapdeal.admin.request.GetConfigurationByFieldsRequest;
import com.snapdeal.admin.request.UpdateConfigurationRequest;
import com.snapdeal.admin.response.CreateConfigurationResponse;
import com.snapdeal.admin.response.DeleteConfigurationResponse;
import com.snapdeal.admin.response.GetAllConfigurationResponse;
import com.snapdeal.admin.response.GetConfigurationByKeyResponse;
import com.snapdeal.admin.response.GetConfigurationByTypeResponse;
import com.snapdeal.admin.response.GetConfigurationResponse;
import com.snapdeal.admin.response.UpdateConfigurationResponse;
import com.snapdeal.ims.exception.ValidationException;

public interface IConfigurationService {

	public CreateConfigurationResponse createConfiguration(
			CreateConfigurationRequest request);

	public CreateConfigurationResponse CreateClientBasedConfigurationRequest(
			CreateClientBasedConfigurationRequest request);

	public UpdateConfigurationResponse updateConfiguration(
			UpdateConfigurationRequest request);

	public GetAllConfigurationResponse getAllConfiguration(
			HttpServletRequest request);

	public GetAllConfigurationResponse getConfigurationByFields(
			GetConfigurationByFieldsRequest request);

	public DeleteConfigurationResponse deleteConfiguration(
			DeleteConfigurationRequest request);

	/**
	 * Get configs by key and type from IMS database
	 * 
	 * @param configKey
	 * @param configType
	 * @return GetConfigResponse
	 * @throws ValidationException
	 */
	public GetConfigurationResponse getConfig(String configKey,
			String configType) throws ValidationException;

	/**
	 * Get config by key.
	 * 
	 * @param configKey
	 * @return GetConfigByKeyResponse
	 * @throws ValidationException
	 */
	public GetConfigurationByKeyResponse getConfigsByKey(String configKey)
			throws ValidationException;

	/**
	 * Get config by configType
	 * 
	 * @param configType
	 * @return GetConfigByTypeResponse
	 * @throws ValidationException
	 */
	public GetConfigurationByTypeResponse getConfigsByType(String configType)
			throws ValidationException;

}
