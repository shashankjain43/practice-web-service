/**
 * 
 */
package com.snapdeal.ims.response;

import com.snapdeal.ims.enums.StatusEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class VerifyUserResponse extends AbstractResponse {

	private static final long serialVersionUID = 1969395774809568938L;
	private StatusEnum Status;
}