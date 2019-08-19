package com.snapdeal.ims.request;

import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.enums.ConfigureUserBasedOn;
import com.snapdeal.ims.enums.Reason;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.ConfigureUserStateRequestValidation;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.MobileNumber;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author abhishek
 *
 */

@Getter
@Setter
@ToString
@JsonPropertyOrder(alphabetic=true)
@ConfigureUserStateRequestValidation
public class ConfigureUserStateRequest extends AbstractRequest{

	private static final long serialVersionUID = 1L;
	
	@Size( max = 127 , message = IMSRequestExceptionConstants.USER_ID_MAX_LENGTH)
	private String userId;
	
	@Token
	@Size( max = 154 , message = IMSRequestExceptionConstants.TOKEN_MAX_LENGTH)
	private String token;
	
	@Email( mandatory = false)
	private String emailId;
	
	@MobileNumber(mandatory = false)
	private String mobileNumber;
	
	private ConfigureUserBasedOn configureUserBasedOn = ConfigureUserBasedOn.EMAIL;
	
	private boolean enable;
	
	private Reason reason;
	
	private String description;

}
