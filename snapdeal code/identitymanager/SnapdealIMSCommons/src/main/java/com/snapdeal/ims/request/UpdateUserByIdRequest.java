/**
 * 
 */
package com.snapdeal.ims.request;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.request.dto.UserDetailsRequestDto;
import com.snapdeal.ims.validator.annotation.UserId;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shachi
 *
 */

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,UpdateUserByIdRequest.class} )
public class UpdateUserByIdRequest extends AbstractRequest {

	private static final long serialVersionUID = -8559964873470494856L;

	public UpdateUserByIdRequest() {
		userDetailsRequestDto = new UserDetailsRequestDto();
	}

	@NotBlank(message = IMSRequestExceptionConstants.USER_ID_IS_BLANK, groups = First.class)
	@Size( max  = 127 , message = IMSRequestExceptionConstants.USER_ID_MAX_LENGTH, groups = Second.class)
	@UserId
	private String userId;
	@Valid
	private UserDetailsRequestDto userDetailsRequestDto;

}
