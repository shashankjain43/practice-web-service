package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForgotPasswordResponse extends AbstractResponse {

	private static final long serialVersionUID = -880025801926082937L;
	private String otpId;
	private String userId;
}