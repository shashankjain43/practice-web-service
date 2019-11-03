package com.snapdeal.admin.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteConfigurationRequest {
	private String configType;
	private String configKey;
	private String configValue;
	private String description;
}