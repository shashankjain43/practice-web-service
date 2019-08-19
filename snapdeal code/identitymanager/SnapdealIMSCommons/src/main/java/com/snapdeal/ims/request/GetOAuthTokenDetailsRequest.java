package com.snapdeal.ims.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.OAuthTokenTypes;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetOAuthTokenDetailsRequest extends AbstractRequest {
	 
	private static final long serialVersionUID = 1L;

	@NotBlank(message = IMSRequestExceptionConstants.TOKEN_IS_BLANK)
	@Size(max = 255, message = IMSRequestExceptionConstants.TOKEN_MAX_LENGTH)
	private String token;
	
	private OAuthTokenTypes tokenType;
}
