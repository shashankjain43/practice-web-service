package com.snapdeal.ims.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordWithLoginResponse extends LoginUserResponse {

	private static final long serialVersionUID = -2586952100869721370L;
	private boolean success;
	
}