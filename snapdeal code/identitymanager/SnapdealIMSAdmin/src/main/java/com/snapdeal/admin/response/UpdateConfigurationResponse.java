package com.snapdeal.admin.response;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class UpdateConfigurationResponse {
	@JsonProperty("Record")
	private ConfigurationDetails configurationDetails;
	
	@JsonProperty("Result")
	private String result;

}
