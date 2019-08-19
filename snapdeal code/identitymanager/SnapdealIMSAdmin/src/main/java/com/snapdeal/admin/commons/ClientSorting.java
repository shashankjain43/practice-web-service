package com.snapdeal.admin.commons;

import java.util.Comparator;

import com.snapdeal.admin.response.ClientDetails;;

public class ClientSorting {
	
	public static final String CLIENT_ID_ASC = "clientId ASC";
	public static final String CLIENT_ID_DESC = "clientId DESC";
	public static final String CLIENT_NAME_ASC = "clientName ASC";
	public static final String CLIENT_NAME_DESC = "clientName DESC";
	public static final String MERCHANT_ASC = "merchant ASC";
	public static final String MERCHANT_DESC = "merchant DESC";
	public static final String CLIENT_TYPE_ASC = "clientType ASC";
	public static final String CLIENT_TYPE_DESC = "clientType DESC";
	public static final String CLIENT_STATUS_ASC = "clientStatus ASC";
	public static final String CLIENT_STATUS_DESC = "clientStatus DESC";
	public static final String CLIENT_PLATFORM_ASC = "clientPlatform ASC";
	public static final String CLIET_PLATFORM_DESC = "clientPlatform DESC";
	public static final String CREATED_TIME_ASC = "createdTime ASC";
	public static final String CREATED_TIME_DESC = "createdTime DESC";
	public static final String UPDATED_TIME_ASC = "updatedTime ASC";
	public static final String UPDATED_TIME_DESC = "updatedTime DESC";
	
	
	public static Comparator<ClientDetails> getComparator(String sortingParam) {
		
		Comparator<ClientDetails> fieldName = null;
		
		switch (sortingParam){
		
		case CLIENT_ID_ASC: 
			fieldName = ClientDetailsComparator.clientId_sort;
			break;
			
		case CLIENT_ID_DESC:
			fieldName = ClientDetailsComparator.descending(ClientDetailsComparator.clientId_sort);
			break;
		
		case CLIENT_NAME_ASC: 
			fieldName = ClientDetailsComparator.clientName_sort;
			break;
			
		case CLIENT_NAME_DESC:
			fieldName = ClientDetailsComparator.descending(ClientDetailsComparator.clientName_sort);
			break;
			
		case MERCHANT_ASC: 
			fieldName = ClientDetailsComparator.merchant_sort;
			break;
			
		case MERCHANT_DESC:
			fieldName = ClientDetailsComparator.descending(ClientDetailsComparator.merchant_sort);
			break;
		
		case CLIENT_TYPE_ASC: 
			fieldName = ClientDetailsComparator.clientType_sort;
			break;
			
		case CLIENT_TYPE_DESC:
			fieldName = ClientDetailsComparator.descending(ClientDetailsComparator.clientType_sort);
			break;
			
		case CLIENT_STATUS_ASC: 
			fieldName = ClientDetailsComparator.clientStatus_sort;
			break;
			
		case CLIENT_STATUS_DESC:
			fieldName = ClientDetailsComparator.descending(ClientDetailsComparator.clientStatus_sort);
			break;
			
		case CLIENT_PLATFORM_ASC: 
			fieldName = ClientDetailsComparator.clientPlatform_sort;
			break;
			
		case CLIET_PLATFORM_DESC:
			fieldName = ClientDetailsComparator.descending(ClientDetailsComparator.clientPlatform_sort);
			break;
			
		case CREATED_TIME_ASC: 
			fieldName = ClientDetailsComparator.createdTime_sort;
			break;
			
		case CREATED_TIME_DESC:
			fieldName = ClientDetailsComparator.descending(ClientDetailsComparator.createdTime_sort);
			break;
			
		case UPDATED_TIME_ASC: 
			fieldName = ClientDetailsComparator.updatedTime_sort;
			break;
			
		case UPDATED_TIME_DESC:
			fieldName = ClientDetailsComparator.descending(ClientDetailsComparator.updatedTime_sort);
			break;
		
		}
		return fieldName;
	}
	

}
