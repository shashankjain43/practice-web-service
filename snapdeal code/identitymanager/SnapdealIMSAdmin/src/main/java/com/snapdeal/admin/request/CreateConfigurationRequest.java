package com.snapdeal.admin.request;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class CreateConfigurationRequest {
	@NotBlank
	private String configType;
	@NotBlank
	private String configKey;
	@NotBlank
	private String configValue;
	private String description;
}
