package com.snapdeal.admin.response;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class CreateClientResponse {
	@JsonProperty("Record")
	private ClientDetails clientDetails;
	@JsonProperty("Result")
	private String result;
}
