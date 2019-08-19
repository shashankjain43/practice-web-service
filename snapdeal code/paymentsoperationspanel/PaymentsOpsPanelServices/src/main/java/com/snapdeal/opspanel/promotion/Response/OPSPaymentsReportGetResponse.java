package com.snapdeal.opspanel.promotion.Response;

import java.util.List;

import com.snapdeal.payments.sdmoneyreport.api.model.ReportDetails;

import lombok.Data;

@Data
public class OPSPaymentsReportGetResponse {

	List<ReportDetails> reportDetailsList;

}
