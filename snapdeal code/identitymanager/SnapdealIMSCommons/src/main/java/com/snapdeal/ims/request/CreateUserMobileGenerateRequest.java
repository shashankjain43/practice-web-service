/**
 * 
 */
package com.snapdeal.ims.request;

import javax.validation.Valid;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.enums.Platform;
import com.snapdeal.ims.request.dto.UserRequestDto;
import com.snapdeal.ims.validator.annotation.CreateUserRequestValidation;
import com.snapdeal.ims.validator.annotation.ResourceValidation;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@CreateUserRequestValidation
@JsonPropertyOrder(alphabetic=true)
public class CreateUserMobileGenerateRequest extends AbstractRequest {

	private static final long serialVersionUID = -793502817330885971L;

	private Platform platform;
	
	@ResourceValidation
	private String resource;

	public CreateUserMobileGenerateRequest() {
		userRequestDto = new UserRequestDto();
	}

	@Valid
	private UserRequestDto userRequestDto;

}
