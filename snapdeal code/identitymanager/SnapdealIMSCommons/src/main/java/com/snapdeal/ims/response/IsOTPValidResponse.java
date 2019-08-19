package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class IsOTPValidResponse extends AbstractResponse{

	private static final long serialVersionUID = -7638503547164120386L;

	/*
	 * will be true only when otp is active on other all cases it is false.
	 */
	private boolean status ;
}
