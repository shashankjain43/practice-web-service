package com.snapdeal.opspanel.promotion.model;

import java.util.Date;

import lombok.Data;

@Data
public class CampaignKeyResponse {

	private String fileName;
	private Date uploadTime;
	private String corporateAccount;
	private String status;

}
