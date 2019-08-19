package com.snapdeal.ims.request;

import javax.validation.GroupSequence;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class})
public class IsTokenValidRequest extends AbstractRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Token
	@NotBlank(message = IMSRequestExceptionConstants.TOKEN_IS_BLANK, groups = First.class)
	private String token ;
}
