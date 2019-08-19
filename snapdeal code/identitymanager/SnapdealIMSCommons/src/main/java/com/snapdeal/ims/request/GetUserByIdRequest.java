/**
 * 
 */
package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.UserId;

/**
 * @author shachi
 *
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,GetUserByIdRequest.class})
public class GetUserByIdRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;
	@NotBlank(message = IMSRequestExceptionConstants.USER_ID_IS_BLANK, groups = First.class)
	@Size( max = 127 , message = IMSRequestExceptionConstants.USER_ID_MAX_LENGTH, groups = Second.class)
	@UserId
	private String userId;

}
