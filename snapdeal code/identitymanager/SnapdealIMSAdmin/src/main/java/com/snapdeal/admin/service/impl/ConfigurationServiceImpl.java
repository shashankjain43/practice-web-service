package com.snapdeal.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.admin.commons.ConfigurationSorting;
import com.snapdeal.admin.constants.CommonConstants;
import com.snapdeal.admin.dao.IConfigurationDetailsDao;
import com.snapdeal.admin.dao.entity.ConfigurationEntity;
import com.snapdeal.admin.request.CreateClientBasedConfigurationRequest;
import com.snapdeal.admin.request.CreateConfigurationRequest;
import com.snapdeal.admin.request.DeleteConfigurationRequest;
import com.snapdeal.admin.request.GetConfigurationByFieldsRequest;
import com.snapdeal.admin.request.UpdateConfigurationRequest;
import com.snapdeal.admin.response.ClientDetails;
import com.snapdeal.admin.response.ConfigurationDetails;
import com.snapdeal.admin.response.CreateConfigurationResponse;
import com.snapdeal.admin.response.DeleteConfigurationResponse;
import com.snapdeal.admin.response.GetAllClientResponse;
import com.snapdeal.admin.response.GetAllConfigurationResponse;
import com.snapdeal.admin.response.GetConfigurationByKeyResponse;
import com.snapdeal.admin.response.GetConfigurationByTypeResponse;
import com.snapdeal.admin.response.GetConfigurationResponse;
import com.snapdeal.admin.response.UpdateConfigurationResponse;
import com.snapdeal.admin.service.IClientService;
import com.snapdeal.admin.service.IConfigurationService;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConfigurationServiceImpl implements IConfigurationService {

	@Autowired
	IConfigurationDetailsDao mapper;

	@Autowired
	IClientService clientService;

	@Override
	public CreateConfigurationResponse createConfiguration(
			CreateConfigurationRequest request) {
		mapper.createConfiguration(request);
		return buildResponse(request);
	}

	@Override
	public UpdateConfigurationResponse updateConfiguration(
			UpdateConfigurationRequest request) {
		mapper.updateConfiguration(request);
		return buildResponse(request);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GetAllConfigurationResponse getAllConfiguration(
			HttpServletRequest request) {

		String configKey = request.getParameter("configKey");
		String configType = request.getParameter("configType");
		List<ConfigurationEntity> entity = null;
		if (StringUtils.isNotBlank(configKey)
				&& StringUtils.isNotBlank(configType)) {
			entity = mapper.getConfig(configKey, configType);
		} else if (StringUtils.isNotBlank(configKey)
				&& StringUtils.isBlank(configType)) {
			entity = mapper.getConfigsByKey(configKey);
		} else if (StringUtils.isBlank(configKey)
				&& StringUtils.isNotBlank(configType)) {
			entity = mapper.getConfigsByType(configType);
		} else {
			entity = mapper.getAllConfiguration();
			List<ConfigurationDetails> configDetails = buildResponse(entity)
					.getConfigurationDetails();

			Set<String> key = (Set<String>) request.getSession().getAttribute(
					"appConfigKeys");
			if (key == null)
				key = new TreeSet<String>();

			Set<String> type = (Set<String>) request.getSession().getAttribute(
					"appConfigType");
			if (type == null)
				type = new TreeSet<String>();

			Date currentTime = new Date();
			Date updatedTime = (Date) request.getSession().getAttribute(
					"configUpdateTime");
			log.info("current Time : " + currentTime + " updatedTime "
					+ updatedTime);
			if (updatedTime == null
					|| currentTime.getTime() - updatedTime.getTime() > 900000) {
				log.info("updating session attributes");
				if (configDetails != null) {
					request.getSession().setAttribute("configUpdateTime",
							new Date());
				}
				for (ConfigurationDetails configDetail : configDetails) {
					key.add(configDetail.getConfigKey());
					request.getSession().setAttribute("appConfigKeys", key);
					type.add(configDetail.getConfigType());
					request.getSession().setAttribute("appConfigType", type);
				}
			}
		}

		GetAllClientResponse getAllClientResponse = clientService
				.getAllClient(request);
		request.getSession().setAttribute("getAllClientResponse",
				getAllClientResponse);
		GetAllConfigurationResponse getAllConfigurationResponse = buildResponse(entity);

		int startPageIndex = Integer.parseInt(request
				.getParameter("jtStartIndex"));
		int numRecordsPerPage = Integer.parseInt(request
				.getParameter("jtPageSize"));
		String sortingParam = request.getParameter("jtSorting");

		List<ConfigurationDetails> list = getAllConfigurationResponse
				.getConfigurationDetails();
		int size = list.size();
		List<ConfigurationDetails> subList = list;
		if (size >= (startPageIndex + numRecordsPerPage)) {
			subList = list.subList(startPageIndex, startPageIndex
					+ numRecordsPerPage);
		} else if (size >= startPageIndex
				&& size < (startPageIndex + numRecordsPerPage)) {
			subList = list.subList(startPageIndex, size);
		}
		
		Comparator<ConfigurationDetails> comparator = ConfigurationSorting.getComparator(sortingParam);

		if (comparator == null) {
			Collections.sort(subList, ConfigurationSorting.getComparator(ConfigurationSorting.CONFIG_KEY_ASC));

		} else {
			Collections.sort(subList, comparator);
		}

		getAllConfigurationResponse.setConfigurationDetails(subList);
		getAllConfigurationResponse.setTotalRecordCount(size);
		getAllConfigurationResponse.setResult("OK");
		return getAllConfigurationResponse;
	}

	@Override
	public GetAllConfigurationResponse getConfigurationByFields(
			GetConfigurationByFieldsRequest request) {
		List<ConfigurationEntity> entity = mapper
				.getConfigurationByField(request);
		return buildResponse(entity);

	}

	@Override
	@Timed
	@Marked
	public GetConfigurationResponse getConfig(String configKey,
			String configType) throws ValidationException {

		final List<ConfigurationEntity> configDetails = mapper.getConfig(
				configKey, configType);
		final GetConfigurationResponse response = new GetConfigurationResponse();
		return response;
	}

	@Override
	@Timed
	@Marked
	public GetConfigurationByKeyResponse getConfigsByKey(String configKey)
			throws ValidationException {
		List<ConfigurationEntity> configList = mapper
				.getConfigsByKey(configKey);
		final GetConfigurationByKeyResponse response = new GetConfigurationByKeyResponse();
		return response;
	}

	@Override
	@Timed
	@Marked
	public GetConfigurationByTypeResponse getConfigsByType(String configType)
			throws ValidationException {
		List<ConfigurationEntity> configList = mapper
				.getConfigsByType(configType);
		final GetConfigurationByTypeResponse response = new GetConfigurationByTypeResponse();
		return response;
	}

	private GetAllConfigurationResponse buildResponse(
			List<ConfigurationEntity> entity) {
		List<ConfigurationDetails> configurationDetailsList = new ArrayList<ConfigurationDetails>();
		GetAllConfigurationResponse response = new GetAllConfigurationResponse();
		response.setConfigurationDetails(configurationDetailsList);
		for (ConfigurationEntity en : entity) {
			ConfigurationDetails details = new ConfigurationDetails();
			details.setConfigKey(en.getConfigKey());
			details.setConfigType(en.getConfigType());
			details.setConfigValue(en.getConfigValue());
			details.setDescription(en.getDescription());
			configurationDetailsList.add(details);
		}
		return response;
	}

	private CreateConfigurationResponse buildResponse(
			CreateConfigurationRequest request) {
		CreateConfigurationResponse response = new CreateConfigurationResponse();
		ConfigurationDetails details = new ConfigurationDetails();
		details.setConfigKey(request.getConfigKey());
		details.setConfigType(request.getConfigType());
		details.setConfigValue(request.getConfigValue());
		details.setDescription(request.getDescription());
		response.setConfigurationDetails(details);
		return response;
	}

	private UpdateConfigurationResponse buildResponse(
			UpdateConfigurationRequest request) {
		UpdateConfigurationResponse response = new UpdateConfigurationResponse();
		ConfigurationDetails details = new ConfigurationDetails();
		details.setConfigKey(request.getConfigKey());
		details.setConfigType(request.getConfigType());
		details.setConfigValue(request.getConfigValue());
		details.setDescription(request.getDescription());
		response.setConfigurationDetails(details);
		response.setResult(CommonConstants.OK);
		return response;
	}

	@Override
	public DeleteConfigurationResponse deleteConfiguration(
			DeleteConfigurationRequest request) {

		GetConfigurationByFieldsRequest getConfigurationByFieldsRequest = new GetConfigurationByFieldsRequest();
		getConfigurationByFieldsRequest.setConfigKey(request.getConfigKey());
		getConfigurationByFieldsRequest.setConfigType(request.getConfigType());
		getConfigurationByFieldsRequest.setConfigValue(request.getConfigType());
		getConfigurationByFieldsRequest
				.setDescription(request.getDescription());

		final List<ConfigurationEntity> existingConfig = mapper
				.getConfigurationByField(getConfigurationByFieldsRequest);

		if (existingConfig == null) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CONFIG_NOT_EXISTS.errCode(),
					IMSServiceExceptionCodes.CONFIG_NOT_EXISTS.errMsg());
		}
		/*
		 * mapper.deleteConfiguration(request.getConfigKey(),
		 * request.getConfigType());
		 */
		mapper.deleteConfiguration(request.getConfigKey(),
				request.getConfigType());
		DeleteConfigurationResponse response = new DeleteConfigurationResponse();
		response.setResult("OK");
		return response;
	}

	@Override
	public CreateConfigurationResponse CreateClientBasedConfigurationRequest(
			CreateClientBasedConfigurationRequest request) {
		String merchant_Client = request.getMerchant_clientName();
		String merchant = merchant_Client.split("&&")[0].trim();
		String clientName = merchant_Client.split("&&")[1].trim();
		String clientId = clientService.getClientIDByMerchantAndClientName(
				clientName, merchant);

		CreateConfigurationRequest createConfigurationRequest = new CreateConfigurationRequest();
		createConfigurationRequest.setConfigType(clientId);
		createConfigurationRequest.setConfigKey(request.getConfigKey());
		createConfigurationRequest.setConfigValue(request.getConfigValue());
		createConfigurationRequest.setDescription(request.getDescription());
		mapper.createConfiguration(createConfigurationRequest);
		return buildResponse(createConfigurationRequest);
	}

}
