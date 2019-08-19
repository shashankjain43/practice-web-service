package com.snapdeal.opspanel.userpanel.response;

import com.snapdeal.payments.sdmoney.service.model.GetBankDetailsResponse;
import com.snapdeal.payments.sdmoney.service.model.GetMoneyOutStatusResponse;

import lombok.Data;

@Data
public class SearchWithdrawalFlowResponse {

	private GetBankDetailsResponse bankDetailsResponse= new GetBankDetailsResponse();
	private String error;
	private String transactionStatus;
}
