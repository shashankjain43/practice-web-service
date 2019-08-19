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
public class CreateSocialUserWithMobileRequest extends AbstractRequest{
   
	private static final long serialVersionUID = 1L;

	private Platform platform;

	@ResourceValidation
	private String resource;

   public CreateSocialUserWithMobileRequest() {
      socialUserDto = new SocialUserRequestDto();
   }
   @Valid
   private SocialUserRequestDto socialUserDto;  

}
