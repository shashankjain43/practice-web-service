package com.snapdeal.ims.service;

import com.snapdeal.ims.enums.GetConfigurationDetails;
import com.snapdeal.ims.response.ConfigurationDetailsResponse;

public interface IConfigurationDetails {

	public ConfigurationDetailsResponse getConfigurationDetails(
			GetConfigurationDetails getConfigurationOf);
}
