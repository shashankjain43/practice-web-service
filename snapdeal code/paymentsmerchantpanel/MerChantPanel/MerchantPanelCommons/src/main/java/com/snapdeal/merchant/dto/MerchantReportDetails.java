package com.snapdeal.merchant.dto;

import java.net.URL;

import lombok.Data;

@Data
public class MerchantReportDetails {

	private URL fileDownloadUrl;
	private String reportName; 
	private String startTime;
	private String endTime;
	private boolean isScheduled;
}
