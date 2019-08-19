package com.snapdeal.opspanel.clientintegrationscomponent.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CorpToCorpMoneyTransferRequest {

	@NotNull
	com.snapdeal.payments.sdmoney.service.model.CorpToCorpMoneyTransferRequest corpToCorpMoneyTransferRequest;

	Boolean enablePayablesEntry;
	String destinationMerchantId;

}
