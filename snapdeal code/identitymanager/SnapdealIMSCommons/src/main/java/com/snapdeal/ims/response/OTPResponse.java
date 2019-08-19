package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class OTPResponse extends AbstractResponse {

	private static final long serialVersionUID = 7781741068654729645L;

	private String otpId;
	
	private String accountState;
}