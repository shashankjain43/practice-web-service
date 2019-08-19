package com.snapdeal.ims.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class ActivateClientRequest {

	@NotEmpty
	private String clientId;
}
