package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.ForgotPasswordRequestValidation;
import com.snapdeal.ims.validator.annotation.MobileNumber;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@ForgotPasswordRequestValidation
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,ForgotPasswordRequest.class})
public class ForgotPasswordRequest extends AbstractRequest {

	private static final long serialVersionUID = -4750393410293293257L;
	
	@Email(mandatory=false)
	private String emailId;
	
	@MobileNumber(mandatory=false)
	private String mobileNumber;

}
