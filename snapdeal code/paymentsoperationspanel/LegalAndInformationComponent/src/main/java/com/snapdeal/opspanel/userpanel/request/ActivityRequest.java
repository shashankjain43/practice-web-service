package com.snapdeal.opspanel.userpanel.request;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snapdeal.base.annotations.NotNull;
import com.snapdeal.ims.enums.ActivityEntityType;

import lombok.Data;

@Data
public class ActivityRequest {
	@NotBlank
	private String entityId;
	@NotBlank
	private ActivityEntityType entityType;
	
	private String toDate;
	
	private String fromDate;
	
}
