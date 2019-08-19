package com.snapdeal.merchant.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.annotation.Email;
import com.snapdeal.merchant.common.constant.CommonConstants;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString(exclude="socialToken")
public class MerchantSocialLoginRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@NotBlank(message=ErrorConstants.SOCIAL_TOKEN_IS_BLANK_CODE)
	private String socialToken;
	
	@Email(mandatory = true)
	private String email;
	
	@NotBlank(message = ErrorConstants.SOCIAL_SOURCE_IS_BLANK_CODE)
	private String source;
}
