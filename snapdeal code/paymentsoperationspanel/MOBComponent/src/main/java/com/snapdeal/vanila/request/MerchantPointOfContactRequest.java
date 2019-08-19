package com.snapdeal.vanila.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class MerchantPointOfContactRequest {

	@NotBlank(message="merchantId can not be left blank.")
	@NotNull(message="merchantId can not be left blank.")
	private String merchantId;

	private String name;

	private String emailId;

	private String oldEmailId;
}
