package com.snapdeal.merchant.request;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.errorcodes.ErrorConstants;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude={"password","userIdentifier"})
public class MerchantSetPasswordRequest extends AbstractRequest{

	private static final long serialVersionUID = 9074366098673263900L;
	
	@NotBlank(message= ErrorConstants.PASSWORD_IS_BLANK_CODE)
	private String password ;
	
	@NotBlank(message =  ErrorConstants.USER_IDENTIFIER_IS_BLANK_CODE)
	private String userIdentifier ; 

}
