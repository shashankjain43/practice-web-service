/**
 * 
 */
package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CreateUserResponse extends AbstractResponse {

	private static final long serialVersionUID = 1969395774809568938L;
	private TokenInformationDTO tokenInformationDTO;
	private UserDetailsDTO userDetails;

}
