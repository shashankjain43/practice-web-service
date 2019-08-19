package com.snapdeal.opspanel.promotion.service;

import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.promotion.enums.OPSPaymentsReportType;

@Service("subReportAuthorization")
public interface ReportSubAuthorization {

	public Boolean authorize(String reportHead, OPSPaymentsReportType opsPaymentsReportType);
	
}
