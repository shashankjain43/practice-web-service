package com.snapdeal.admin.commons;

import java.util.Comparator;

import com.snapdeal.admin.response.ConfigurationDetails;

public enum ConfigurationDetailsComparator implements Comparator<ConfigurationDetails> {

	type_sort {
		public int compare(ConfigurationDetails details1, ConfigurationDetails details2) {

			if (details1.getConfigType() == null) {
				return -1;
			}
			if (details2.getConfigType() == null) {
				return 1;
			}

			return (details1.getConfigType().compareTo(details2.getConfigType()));
		}
	},

	key_sort {
		public int compare(ConfigurationDetails details1, ConfigurationDetails details2) {

			if (details1.getConfigKey() == null) {
				return -1;
			}
			if (details2.getConfigKey() == null) {
				return 1;
			}

			return (details1.getConfigKey().compareTo(details2.getConfigKey()));
		}
	},

	value_sort {
		public int compare(ConfigurationDetails details1, ConfigurationDetails details2) {

			if (details1.getConfigValue() == null) {
				return -1;
			}
			if (details2.getConfigValue() == null) {
				return 1;
			}

			return (details1.getConfigValue().compareTo(details2.getConfigValue()));
		}
	},

	description_sort {
		public int compare(ConfigurationDetails details1, ConfigurationDetails details2) {

			if (details1.getDescription() == null) {
				return -1;
			}
			if (details2.getDescription() == null) {
				return 1;
			}

			return (details1.getDescription().compareTo(details2.getDescription()));
		}
	};

	public static Comparator<ConfigurationDetails> descending(final Comparator<ConfigurationDetails> other) {
		return new Comparator<ConfigurationDetails>() {
			public int compare(ConfigurationDetails details1, ConfigurationDetails details2) {
				return -1 * other.compare(details1, details2);
			}
		};
	}
}