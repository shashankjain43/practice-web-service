package com.snapdeal.ims.request;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;
import com.snapdeal.ims.validator.annotation.UserId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,MobileVerificationStatusRequest.class})
public class MobileVerificationStatusRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;
	@NotBlank(message=IMSRequestExceptionConstants.USER_ID_IS_BLANK, groups = First.class)
	@Size(max=128, message=IMSRequestExceptionConstants.USER_ID_MAX_LENGTH, groups = Second.class)
	@UserId
	public String userId;
}
