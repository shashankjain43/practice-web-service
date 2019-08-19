package com.snapdeal.opspanel.userpanel.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.snapdeal.payments.p2pengine.model.enums.TSMTransactionState;

import lombok.Data;

@Data
@JsonIgnoreProperties( { "merchantId", "disbursementTransactionReference" } )
public class ShopoTransactionDetails {

	private String timestamp;

	private String transactionId;

	private String idempotencyId;

	private String orderId;

	private TSMTransactionState txnStatus;

	private String paymentType;

	private BigDecimal amount;

	private String productDeliveryDate;

	private String settlementDate;

	private String settlementID;

	private String bankURN;

	private String merchantId;

	private String disbursementTransactionReference;
}
