package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Password;
import com.snapdeal.ims.validator.annotation.UserId;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"newPassword","confirmPassword"})
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,ResetPasswordRequest.class})
public class ResetPasswordRequest extends AbstractRequest {

	private static final long serialVersionUID = -8802051940952294813L;

	@NotBlank(message = IMSRequestExceptionConstants.OTP_ID_IS_BLANK, groups = First.class)
	@Size( max = 127 ,message = IMSRequestExceptionConstants.OTP_ID_MAX_LENGTH, groups = Second.class)
	String otpId;
	@NotBlank(message = IMSRequestExceptionConstants.USER_ID_IS_BLANK, groups = First.class)
	@Size( max = 127, message = IMSRequestExceptionConstants.USER_ID_MAX_LENGTH, groups = Second.class)
	@UserId
	String userId;
	@Password
	String newPassword;
   @Password
	String confirmPassword;
	@NotBlank(message = IMSRequestExceptionConstants.OTP_IS_BLANK, groups = First.class)
	String otp;
}
