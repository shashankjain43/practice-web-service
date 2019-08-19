
package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.OTPRequestChannel;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.CustomNotBlank;
import com.snapdeal.ims.validator.annotation.OTPPurposeGenricValidation;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author kishan
 *
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,GenerateOTPRequest.class})
@CustomNotBlank(message=IMSRequestExceptionConstants.TOKEN_IS_BLANK)
public class GenerateOTPRequest extends AbstractRequest {

	private static final long serialVersionUID = 8866300354838623736L;
		
	@Token
	//@NotBlank(message=IMSRequestExceptionConstants.TOKEN_IS_BLANK, groups = First.class)
	@Size( max = 154,message = IMSRequestExceptionConstants.TOKEN_MAX_LENGTH, groups = Second.class)
	private String token;
	
	private String mobileNumber;
	
	@Size( max = 128 ,message = IMSRequestExceptionConstants.EMAIL_MAX_LENGTH)
	private String emailId;
	
	@OTPPurposeGenricValidation
	private OTPPurpose purpose;
	

}
