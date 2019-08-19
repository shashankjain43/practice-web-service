package com.snapdeal.opspanel.promotion.model;

import java.util.List;

import lombok.Data;

@Data
public class DeleteClientRequestModel {
	
	private String clientId;

	private List<MerchantModel> merchants;
	
}
