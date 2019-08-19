package com.snapdeal.admin.commons;

import java.util.Comparator;

import com.snapdeal.admin.response.ConfigurationDetails;

public class ConfigurationSorting {
	
	public static final String CONFIG_KEY_ASC = "configKey ASC";
	public static final String CONFIG_KEY_DESC = "configKey DESC";
	public static final String CONFIG_TYPE_ASC = "configType ASC";
	public static final String CONFIG_TYPE_DESC = "configType DESC";
	public static final String CONFIG_VALUE_ASC = "configValue ASC";
	public static final String CONFIG_VALUE_DESC = "configValue DESC";
	public static final String DESCRIPTION_ASC = "description ASC";
	public static final String DESCRIPTION_DESC = "description DESC";
	
	public static Comparator<ConfigurationDetails> getComparator(String sortingParam) {
		Comparator<ConfigurationDetails> fieldName = null;
		
		switch (sortingParam) {

		case CONFIG_KEY_ASC:
			fieldName = ConfigurationDetailsComparator.key_sort;
			break;

		case CONFIG_KEY_DESC:
			fieldName = ConfigurationDetailsComparator.descending(ConfigurationDetailsComparator.key_sort);
			break;
			
		case CONFIG_TYPE_ASC:
			fieldName = ConfigurationDetailsComparator.type_sort;
			break;

		case CONFIG_TYPE_DESC:
			fieldName = ConfigurationDetailsComparator.descending(ConfigurationDetailsComparator.type_sort);
			break;
			
		case CONFIG_VALUE_ASC:
			fieldName = ConfigurationDetailsComparator.value_sort;
			break;

		case CONFIG_VALUE_DESC:
			fieldName = ConfigurationDetailsComparator.descending(ConfigurationDetailsComparator.value_sort);
			break;
			
		case DESCRIPTION_ASC:
			fieldName = ConfigurationDetailsComparator.description_sort;
			break;

		case DESCRIPTION_DESC:
			fieldName = ConfigurationDetailsComparator.descending(ConfigurationDetailsComparator.description_sort);
			break;
		}
		 
		return fieldName;
	}
}
