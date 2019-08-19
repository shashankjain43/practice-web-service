package com.snapdeal.opspanel.promotion.model;

import java.util.List;

import lombok.Data;

@Data
public class PermissionsModel {

    private List<MerchantModel> merchants;
    
    private String instrumentType;
	
	
}
