package com.snapdeal.admin.request;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;

@Data
public class CreateClientBasedConfigurationRequest {
	@NotBlank
	private String merchant_clientName;
	@NotBlank
	private String configKey;
	@NotBlank
	private String configValue;
	private String description;
}
