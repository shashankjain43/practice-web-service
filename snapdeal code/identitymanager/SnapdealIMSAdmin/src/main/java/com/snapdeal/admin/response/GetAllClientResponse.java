package com.snapdeal.admin.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class GetAllClientResponse {
	@JsonProperty("Records")
	private List<ClientDetails> clientList;
	@JsonProperty("Result")
	private String result;
	@JsonProperty("TotalRecordCount")
	private Integer totalResult;
}
