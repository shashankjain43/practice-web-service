package com.snapdeal.opspanel.promotion.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder(alphabetic=true)
public class ReverseLoadMoneyResponse {
	
	private String status;

}
