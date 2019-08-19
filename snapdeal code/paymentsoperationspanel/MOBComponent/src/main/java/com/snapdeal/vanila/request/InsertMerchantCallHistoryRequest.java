package com.snapdeal.vanila.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class InsertMerchantCallHistoryRequest {
	@NotBlank(message="merchantId can not be left blank.")
	private String merchantId;
	@NotBlank(message="contactType can not be left blank.")
	private String contactType;
	@NotBlank(message="callStatus can not be left blank.")
	private String callStatus;
	@Max(value=10)
	@Min(value=1)
	private int attemptsCount;
	@NotBlank(message="callpath can not be left blank.")
	private String callpath;
	@NotBlank(message="description can not be left blank.")
	private String description;
	@NotBlank
	private String callDate;
}
