package com.snapdeal.vanila.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class GetAttemptsRequest {
	@NotBlank(message= "Merchant ID can not be left blank")
	private String merchantId;
	@NotBlank(message = "Call Type can not be left blank")
	private String callType;
}
