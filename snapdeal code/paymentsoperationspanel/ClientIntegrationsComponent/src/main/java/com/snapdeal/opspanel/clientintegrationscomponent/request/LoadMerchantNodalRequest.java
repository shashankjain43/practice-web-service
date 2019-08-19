package com.snapdeal.opspanel.clientintegrationscomponent.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.payments.payables.model.request.PayableSettlementRequest;

import lombok.Data;

@Data
public class LoadMerchantNodalRequest {

	@NotBlank
	String merchantId;

	@Valid
	@NotNull
	PayableSettlementRequest payableSettlementRequest;

}
