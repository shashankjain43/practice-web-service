package com.snapdeal.opspanel.promotion.model;

import java.util.List;

import lombok.Data;

@Data
public class ClientResponseModel {
	
	private String emailId;
	
	private List<MerchantModel> merchants;
	
	private String instruments;
	
	private boolean isSuperUser;

}
