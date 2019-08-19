package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.request.dto.UserDetailsRequestDto;
import com.snapdeal.ims.validator.annotation.Token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,UpdateUserByTokenRequest.class})
public class UpdateUserByTokenRequest extends AbstractRequest {

	private static final long serialVersionUID = -1014711460253818631L;
	public UpdateUserByTokenRequest() {
		userDetailsRequestDto=new UserDetailsRequestDto();
	}
	
	
	@Token
	@NotBlank(message = IMSRequestExceptionConstants.TOKEN_IS_BLANK, groups = First.class)
	@Size(max = 154 , message = IMSRequestExceptionConstants.TOKEN_MAX_LENGTH, groups = Second.class)
	private String token;
	@Valid
	private UserDetailsRequestDto userDetailsRequestDto;

}
