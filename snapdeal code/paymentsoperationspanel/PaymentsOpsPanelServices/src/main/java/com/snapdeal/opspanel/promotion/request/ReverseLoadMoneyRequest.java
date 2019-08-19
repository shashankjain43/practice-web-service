package com.snapdeal.opspanel.promotion.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder(alphabetic=true)
public class ReverseLoadMoneyRequest {
	
	@NotBlank
	private String transactionId;
	
	private String userId;
	
	@Size(max = 256, message = "The field must be less than 256 characters")
	private String reverseReason;
	
	private String reverseType=ReverseType.WALLET_AND_PG.getReverseTypeVal();
	
}
