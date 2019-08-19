package com.snapdeal.opspanel.clientintegrationscomponent.request;

import java.math.BigDecimal;

import javax.annotation.Nonnegative;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PGNodalRequest {
	
	@NotNull
	@Nonnegative
	private BigDecimal amount;
	@NotNull
	@Nonnegative
	private BigDecimal feeAmount;
	@NotNull
	private String transactionReference;
	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9]*$" )
	private String neftId;
	@NotNull
	private String pgType;
	@NotNull
	private long timestamp;
	@NotNull
	private String eventContext;
}
