package com.snapdeal.ims.request;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,Second.class,GetSocialUserRequest.class})
public class GetSocialUserRequest extends AbstractRequest {

	private static final long serialVersionUID = -1335716486413092151L;
	@NotBlank(message = IMSRequestExceptionConstants.SOCIAL_ID_IS_BLANK, groups = First.class)
	@Size( max = 127, message = IMSRequestExceptionConstants.SOCIAL_ID_MAX_LENGTH, groups = Second.class)
	private String socialId;

}
