package com.snapdeal.opspanel.promotion.Response;

import java.util.ArrayList;

import lombok.Data;

@Data	
public class GetEligibleAppsResponse {

	private ArrayList<String> apps= new ArrayList<String>();
}
