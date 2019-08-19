package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shachi
 *
 */
@ToString
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class ResetPasswordResponse extends AbstractResponse {

	private static final long serialVersionUID = -2586952100869721370L;
	private boolean success;
}