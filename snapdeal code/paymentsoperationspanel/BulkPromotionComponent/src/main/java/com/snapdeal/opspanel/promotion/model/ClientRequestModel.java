package com.snapdeal.opspanel.promotion.model;

import java.util.List;

import lombok.Data;

@Data
public class ClientRequestModel {
	
	private Boolean isSuperUser;
	
	private String clientId;
	
	private String instrumentType;
	
	private List<MerchantModel> merchants;

}
