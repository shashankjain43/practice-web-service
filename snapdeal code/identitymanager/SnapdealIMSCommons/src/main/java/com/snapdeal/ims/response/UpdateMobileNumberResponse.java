package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.dto.UserDetailsDTO;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class UpdateMobileNumberResponse extends AbstractResponse {

	private static final long serialVersionUID = -9222796307549679914L;
	
	private UserDetailsDTO userDetails;
}