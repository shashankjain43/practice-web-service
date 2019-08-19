package com.snapdeal.merchant.request;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.errorcodes.ErrorConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MerchantGenerateOTPRequest extends AbstractRequest {

	
	private static final long serialVersionUID = 2942483838979431171L;
	
	@NotBlank(message = ErrorConstants.LOGIN_NAME_IS_BLANK_CODE)
	   private String loginName;

}
