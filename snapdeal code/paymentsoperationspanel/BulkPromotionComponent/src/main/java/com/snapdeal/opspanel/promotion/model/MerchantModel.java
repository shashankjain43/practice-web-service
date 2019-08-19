package com.snapdeal.opspanel.promotion.model;

import java.util.List;

import com.snapdeal.opspanel.promotion.model.UserAccountModel;

import lombok.Data;

@Data
public class MerchantModel {
	
	private String merchantName;
	
	private List<UserAccountModel> corporateIds;

}
