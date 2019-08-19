package com.snapdeal.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.dao.IConfigDetailsDao;
import com.snapdeal.ims.dbmapper.entity.ConfigDetails;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.response.GetAllConfigsResponse;
import com.snapdeal.ims.response.GetConfigByKeyResponse;
import com.snapdeal.ims.response.GetConfigByTypeResponse;
import com.snapdeal.ims.response.GetConfigResponse;
import com.snapdeal.ims.service.IConfigService;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Component
public class ConfigServiceImpl implements IConfigService{
	@Autowired
	private IConfigDetailsDao configDetailsDao;

	@Override
	@Timed
	@Marked
	public GetAllConfigsResponse getAllConfigs() throws ValidationException {

		List<ConfigDetails> configList = configDetailsDao.getAllConfigs();
		final GetAllConfigsResponse response = new GetAllConfigsResponse();
		response.setConfigList(configList);
		return response;
	}

	@Override
	@Timed
   @Marked
	public GetConfigResponse getConfig(String configKey, String configType)
			throws ValidationException {

		final ConfigDetails configDetails = configDetailsDao.getConfig(configKey, configType);
		final GetConfigResponse response = new GetConfigResponse();
		response.setConfigDetails(configDetails);
		return response;
	}

	@Override
	@Timed
   @Marked
	public GetConfigByKeyResponse getConfigsByKey(String configKey)
			throws ValidationException {
		List<ConfigDetails> configList = configDetailsDao.getConfigsByKey(configKey);
		final GetConfigByKeyResponse response = new GetConfigByKeyResponse();
		response.setConfigList(configList);
		return response;
	}

	@Override
	@Timed
   @Marked
	public GetConfigByTypeResponse getConfigsByType(String configType)
			throws ValidationException {
		List<ConfigDetails> configList = configDetailsDao.getConfigsByType(configType);
		final GetConfigByTypeResponse response = new GetConfigByTypeResponse();
		response.setConfigList(configList);
		return response;
	}
}
