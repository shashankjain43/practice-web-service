package com.snapdeal.opspanel.userpanel.bulkReverseTxn.response;

import lombok.Data;

import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyResponse;

@Data
public class BulkReverseLoadMoneyResponse {
	
	private String status;
	
	private ReverseLoadMoneyResponse reverseLoadMoneyResponse;

}
