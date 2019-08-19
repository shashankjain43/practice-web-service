package com.snapdeal.admin.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateConfigurationRequest {
	private String configType;
	private String configKey;
	private String configValue;
	private String description;
}
