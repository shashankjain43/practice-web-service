/**
 * 
 */
package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CreateGuestUserResponse extends AbstractResponse {

	private static final long serialVersionUID = 1969395774809568938L;
	private String userId;
	private String sdUserId;
	private String fcUserId;
}