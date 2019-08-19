package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class IsTokenValidResponse  extends AbstractResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean tokenValid;
}
