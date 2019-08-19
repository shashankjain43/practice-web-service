package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Password;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"oldPassword","newPassword","confirmNewPassword"})
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,ChangePasswordRequest.class})
public class ChangePasswordRequest extends AbstractRequest {

	private static final long serialVersionUID = -1088978773779492699L;

	@Token
	@NotBlank(message = IMSRequestExceptionConstants.TOKEN_IS_BLANK, groups = First.class)
	@Size( max = 154 , message = IMSRequestExceptionConstants.TOKEN_MAX_LENGTH, groups = Second.class)
	private String token;
	@NotBlank(message = IMSRequestExceptionConstants.OLD_PASSWORD_IS_BLANK)
	private String oldPassword;
	@Password
	private String newPassword;
	@Password
	private String confirmNewPassword;

}
