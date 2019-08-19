package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author kishan
 *
 */

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,LoginWithTokenRequest.class})
public class LoginWithTokenRequest extends AbstractRequest {

	private static final long serialVersionUID = -215784222866082672L;
	
	@NotBlank(message = IMSRequestExceptionConstants.GLOBAL_TOKEN_IS_BLANK, groups = First.class)
	@Size( max = 154 , message = IMSRequestExceptionConstants.GLOBAL_TOKEN_MAX_LENGTH, groups = Second.class)
	@Token
	private String globalToken;
	
}
