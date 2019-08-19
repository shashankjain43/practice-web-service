package com.snapdeal.opspanel.userpanel.request;

import com.snapdeal.payments.sdmoney.service.model.GetMoneyOutStatusRequest;

import lombok.Data;

@Data
public class SearchWithdrawalFlowRequest {
	GetMoneyOutStatusRequest moneyOutStatusRequest;
}
