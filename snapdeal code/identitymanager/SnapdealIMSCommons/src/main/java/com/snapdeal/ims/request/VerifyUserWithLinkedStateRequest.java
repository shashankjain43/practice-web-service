package com.snapdeal.ims.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.Password;
import com.snapdeal.ims.validator.annotation.Token;
import com.snapdeal.ims.validator.annotation.VerifyUserWithLinkedToken;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class VerifyUserWithLinkedStateRequest extends AbstractRequest{
	
	private static final long serialVersionUID = 1457470000495607968L;
	
	@NotBlank( message = IMSRequestExceptionConstants.CODE_IS_BLANK)
   @Size(max = 128, message = IMSRequestExceptionConstants.CODE_MAX_LENGTH)
	private String code;
	
	@Token
	@VerifyUserWithLinkedToken(mandatory=false)
	private String token;
	
	@Email
   private String emailId;
	
	@Password
	private String password; 
	
}
