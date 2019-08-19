package com.snapdeal.ims.request;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Password;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = { "newPassword", "confirmPassword" })
@JsonPropertyOrder(alphabetic = true)
public class VerifyAndResetPasswordRequest extends AbstractRequest {

	private static final long serialVersionUID = 3902542690011299398L;

	@NotBlank(message = IMSRequestExceptionConstants.CODE_IS_BLANK)
   @Size(max = 128, message = IMSRequestExceptionConstants.CODE_MAX_LENGTH)
	private String verificationCode;

	@Password
	private String newPassword;

	@Password
	private String confirmPassword;
}