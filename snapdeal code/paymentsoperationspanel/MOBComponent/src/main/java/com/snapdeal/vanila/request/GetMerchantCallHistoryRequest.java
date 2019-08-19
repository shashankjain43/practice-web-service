package com.snapdeal.vanila.request;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class GetMerchantCallHistoryRequest {
	@NotBlank(message="MerchantId can not be left blank.")
	private String merchantId;

}
