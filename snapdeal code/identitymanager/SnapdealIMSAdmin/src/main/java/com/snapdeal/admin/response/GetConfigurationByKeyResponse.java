package com.snapdeal.admin.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetConfigurationByKeyResponse {

	private List<ConfigurationDetails> configList;
}
