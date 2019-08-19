package com.snapdeal.merchant.request;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.errorcodes.ErrorConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MerchantResendOTPRequest extends AbstractRequest{


	private static final long serialVersionUID = -932931780378921224L;
	
	@NotBlank(message=ErrorConstants.OTP_ID_CANNOT_BE_BLANK_CODE)
	private String otpId;
	
}
