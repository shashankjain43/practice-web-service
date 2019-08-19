package com.snapdeal.admin.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationDetails {

	private String configType;
	private String configKey;
	private String configValue;
	private String description;
}
