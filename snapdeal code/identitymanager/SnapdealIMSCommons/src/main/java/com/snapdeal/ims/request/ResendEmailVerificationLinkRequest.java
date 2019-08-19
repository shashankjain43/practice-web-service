package com.snapdeal.ims.request;

import javax.validation.GroupSequence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.enums.First;
import com.snapdeal.ims.enums.Second;
import com.snapdeal.ims.validator.annotation.Email;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder(alphabetic = true)
@ToString(callSuper = true)
@GroupSequence(value = {First.class, Second.class,
		ResendEmailVerificationLinkRequest.class})
public class ResendEmailVerificationLinkRequest extends AbstractRequest {
	private static final long serialVersionUID = 7503629303966322652L;

	@Email
	String emailId;
}