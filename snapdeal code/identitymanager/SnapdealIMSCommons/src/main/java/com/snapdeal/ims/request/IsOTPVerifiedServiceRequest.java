package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,IsOTPVerifiedServiceRequest.class})
public class IsOTPVerifiedServiceRequest extends AbstractOTPServiceRequest {

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = IMSRequestExceptionConstants.OTP_ID_IS_BLANK, groups = First.class)
	@Size(max = 255, message = IMSRequestExceptionConstants.OTP_ID_MAX_LENGTH, groups = Second.class)
	private String otpId;
	@NotBlank(message = IMSRequestExceptionConstants.OTP_IS_BLANK, groups = First.class)
	private String otp;
}
