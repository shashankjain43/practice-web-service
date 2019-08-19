package com.snapdeal.vanila.bulk.merchanthierarchy.dealer.model;

import lombok.Data;

@Data
public class OPSCreateUpdateDealerResponse {

	private String dealerId;
	private String createStatus;
	private String createError;
	private String updateStatus;
	private String updateError;
	private String overallStatus;
	
}
