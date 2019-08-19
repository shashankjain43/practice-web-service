package com.snapdeal.opspanel.promotion.request;

import java.util.Date;
import com.snapdeal.opspanel.promotion.enums.OPSPaymentsReportType;
import lombok.Data;

@Data
public class OPSPaymentsReportGenerationRequest {

	Date startDate;
	Date endDate;
	Boolean isOnDemand;
	Boolean isEmailRequired;

	String businessEntity;
	String accountId;
	
	OPSPaymentsReportType opsPaymentsReportType;
}
