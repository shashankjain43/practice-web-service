package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,UniqueMobileVerificationRequest.class})
public class UniqueMobileVerificationRequest extends AbstractRequest {
	
	private static final long serialVersionUID = 1L;
	@Token
	@NotBlank(message=IMSRequestExceptionConstants.TOKEN_IS_BLANK, groups = First.class)
	@Size(max = 154, message = IMSRequestExceptionConstants.TOKEN_MAX_LENGTH, groups = Second.class)
	private String token;
	@NotBlank(message=IMSRequestExceptionConstants.USER_ID_IS_BLANK, groups = First.class)
	@Size(max = 127, message = IMSRequestExceptionConstants.USER_ID_MAX_LENGTH, groups = Second.class)
	private String userId;
	@NotBlank(message=IMSRequestExceptionConstants.OTP_ID_IS_BLANK, groups = First.class)
	@Size(max = 255, message = IMSRequestExceptionConstants.OTP_ID_MAX_LENGTH, groups = Second.class)
	private String otpId;
	@NotBlank(message=IMSRequestExceptionConstants.OTP_IS_BLANK, groups = First.class)
	private String otp;

}
