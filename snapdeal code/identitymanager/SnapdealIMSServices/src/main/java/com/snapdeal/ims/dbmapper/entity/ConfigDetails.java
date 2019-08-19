package com.snapdeal.ims.dbmapper.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConfigDetails {
	private String configKey;

	private String configType;

	private String configValue;

	private String description;
}
