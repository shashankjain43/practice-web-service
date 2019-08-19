package com.snapdeal.admin.dao.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationEntity implements Serializable {

	private static final long serialVersionUID = -3303233642068743919L;

	private String configType;
	private String configKey;
	private String configValue;
	private String description;

}
