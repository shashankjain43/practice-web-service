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
public class IsVerifiedMobileExistResponse extends AbstractResponse {

	private static final long serialVersionUID = 9169437105382579679L;
	
	private boolean exist = false;
}