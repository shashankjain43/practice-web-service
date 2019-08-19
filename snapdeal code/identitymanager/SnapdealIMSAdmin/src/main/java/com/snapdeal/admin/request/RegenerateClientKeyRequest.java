package com.snapdeal.admin.request;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;


@Getter
@Setter
public class RegenerateClientKeyRequest {
	@NotEmpty
	private String clientId;
}