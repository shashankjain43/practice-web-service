package com.snapdeal.opspanel.promotion.Response;

import java.util.List;

import lombok.Data;

import com.snapdeal.opspanel.promotion.request.MerchantModel;

@Data
public class ClientResponseModel {
	
	private String emailId;
	
	private List<MerchantModel> merchants;
	
	private String instruments;
	
	private boolean isSuperUser;

}
