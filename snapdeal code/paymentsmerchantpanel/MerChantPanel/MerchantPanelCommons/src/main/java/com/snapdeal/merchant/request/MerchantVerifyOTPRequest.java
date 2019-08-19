package com.snapdeal.merchant.request;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.merchant.errorcodes.ErrorConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude={"password","otp"})
public class MerchantVerifyOTPRequest extends AbstractRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6407246615299773931L;
	
	@NotBlank(message=ErrorConstants.OTP_ID_CANNOT_BE_BLANK_CODE)
	private String otpId;
	
	@NotBlank(message=ErrorConstants.OTP_CANNOT_BE_BLANK_CODE)
	private String otp;
	
	@NotBlank(message=ErrorConstants.PASSWORD_IS_BLANK_CODE)
	private String password;

}
