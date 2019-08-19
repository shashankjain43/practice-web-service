package com.snapdeal.opspanel.promotion.request;

import java.util.Date;
import com.snapdeal.opspanel.promotion.enums.OPSPaymentsReportType;
import lombok.Data;

@Data
public class OPSPaymentsReportGetRequest {
	
	Date startDate;
	Date endDate;
	int pageSize;
	int lastEvaluated;

	OPSPaymentsReportType opsPaymentsReportType;
}
