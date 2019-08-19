package com.snapdeal.admin.response;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Setter
@Getter
public class DeleteConfigurationResponse {
	@JsonProperty("Result")
	private String result;
}
