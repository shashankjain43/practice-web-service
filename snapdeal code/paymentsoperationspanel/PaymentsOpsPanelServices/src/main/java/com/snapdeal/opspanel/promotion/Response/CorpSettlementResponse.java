package com.snapdeal.opspanel.promotion.Response;

import java.util.Date;

import lombok.Data;

@Data
public class CorpSettlementResponse {

	private String transactionId;

	private Date transactionTimeStamp;

	private String idempotencyId;

	private String transactionRefrence;

}
