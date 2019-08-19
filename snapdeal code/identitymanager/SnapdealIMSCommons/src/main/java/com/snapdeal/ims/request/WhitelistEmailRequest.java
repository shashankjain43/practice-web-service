package com.snapdeal.ims.request;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.validator.annotation.Email;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic = true)

public class WhitelistEmailRequest extends AbstractRequest {
	private static final long serialVersionUID = 1L;

	@Email
	private String emailId;

}
