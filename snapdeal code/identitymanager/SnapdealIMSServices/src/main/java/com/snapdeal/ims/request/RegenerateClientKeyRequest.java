package com.snapdeal.ims.request;

import org.hibernate.validator.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class RegenerateClientKeyRequest {

	@NotEmpty
	private String clientId;
}