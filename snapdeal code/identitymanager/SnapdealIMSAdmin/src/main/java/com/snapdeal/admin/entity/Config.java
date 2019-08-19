package com.snapdeal.admin.entity;

import java.io.Serializable;

import lombok.Data;

public @Data class Config implements Serializable {

	private static final long serialVersionUID = 6929720391474231596L;

	public Config() {

	}

	public Config(String configKey, String configType) {
		this.configId = new ConfigId(configKey, configType);
	}

	private ConfigId configId;

	private String configValue;

	@Data
	public static class ConfigId implements Serializable {
		private static final long serialVersionUID = -4199867697579425538L;

		private ConfigId() {

		}

		private ConfigId(String configKey, String configType) {
			this.configKey = configKey;
			this.configType = configType;
		}

		private String configKey;
		private String configType;
	}
}