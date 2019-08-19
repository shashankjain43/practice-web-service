/**
 * 
 */
package com.snapdeal.ims.request;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,CreateUserMobileVerifyRequest.class})
public class CreateUserMobileVerifyRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;
	@NotBlank(message = IMSRequestExceptionConstants.OTP_ID_IS_BLANK, groups = First.class)
	@Size(max = 127 ,message = IMSRequestExceptionConstants.OTP_ID_MAX_LENGTH, groups = Second.class)
	private String otpId;
	@NotBlank(message = IMSRequestExceptionConstants.OTP_IS_BLANK, groups = First.class)
	private String otp;

}
