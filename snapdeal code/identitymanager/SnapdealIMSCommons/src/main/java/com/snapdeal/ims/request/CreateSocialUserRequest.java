package com.snapdeal.ims.request;

import javax.validation.Valid;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.enums.Platform;
import com.snapdeal.ims.request.dto.SocialUserRequestDto;
import com.snapdeal.ims.validator.annotation.ResourceValidation;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
public class CreateSocialUserRequest extends AbstractRequest {

	private static final long serialVersionUID = -5095307640285370712L;
	

	private Platform platform;

	@ResourceValidation
	private String resource;

	public CreateSocialUserRequest() {
		socialUserDto = new SocialUserRequestDto();
	}
   @Valid
   private SocialUserRequestDto socialUserDto;	

}
