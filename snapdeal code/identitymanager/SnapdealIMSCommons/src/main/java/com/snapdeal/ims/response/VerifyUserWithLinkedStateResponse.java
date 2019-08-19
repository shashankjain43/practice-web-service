package com.snapdeal.ims.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VerifyUserWithLinkedStateResponse extends AbstractResponse{
	

	private static final long serialVersionUID = -4834285958528454751L;
	
	private boolean success = false ;
	private LoginUserResponse loginUserResponse;

}
