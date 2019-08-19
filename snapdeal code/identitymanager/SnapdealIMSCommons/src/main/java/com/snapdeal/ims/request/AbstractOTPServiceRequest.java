package com.snapdeal.ims.request;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,AbstractOTPServiceRequest.class})
public abstract class AbstractOTPServiceRequest implements Serializable {
	private static final long serialVersionUID = -994997348298086241L;
	
	@Token
	@Size( max = 154 , message = IMSRequestExceptionConstants.TOKEN_MAX_LENGTH)
	protected String token;
	@NotBlank(message=IMSRequestExceptionConstants.CLIENT_ID_IS_BLANK, groups = First.class)
	@Size( max = 127, message = IMSRequestExceptionConstants.CLIENT_ID_MAX_LENGTH, groups = Second.class )
	protected String clientId;

}