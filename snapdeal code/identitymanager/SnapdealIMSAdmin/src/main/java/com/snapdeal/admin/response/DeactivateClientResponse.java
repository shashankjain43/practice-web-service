package com.snapdeal.admin.response;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class DeactivateClientResponse {
	private ClientDetails clientDetails;
	@JsonProperty("Result")
	private String Result;
}
