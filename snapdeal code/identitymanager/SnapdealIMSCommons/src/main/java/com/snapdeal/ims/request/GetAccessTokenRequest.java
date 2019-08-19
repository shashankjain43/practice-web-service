package com.snapdeal.ims.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GetAccessTokenRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = IMSRequestExceptionConstants.AUTH_CODE_NOT_BLANK)
	@Size(max = 255, message = IMSRequestExceptionConstants.AUTH_CODE_MAX_LENGTH)
	private String authCode;

	@NotBlank(message = IMSRequestExceptionConstants.MERCHANT_NOT_BLANK)
	@Size(max = 255, message = IMSRequestExceptionConstants.MERCHANT_MAX_LENGTH)
	private String merchantId;

}
