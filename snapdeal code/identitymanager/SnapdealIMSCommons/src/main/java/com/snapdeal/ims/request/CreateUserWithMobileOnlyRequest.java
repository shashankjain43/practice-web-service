package com.snapdeal.ims.request;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.enums.Platform;
import com.snapdeal.ims.request.dto.UserDetailsRequestDto;
import com.snapdeal.ims.validator.annotation.MobileNumber;
import com.snapdeal.ims.validator.annotation.ResourceValidation;

@Getter
@Setter
@ToString
public class CreateUserWithMobileOnlyRequest extends AbstractRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7499300762680201035L;

	@MobileNumber(mandatory=true)
	String mobileNumber;
	
	Platform platform;
	
	@ResourceValidation
	String resource;
	
	public  CreateUserWithMobileOnlyRequest(){
		userDetailsRequestDto =new UserDetailsRequestDto();
	}

	@Valid
	private UserDetailsRequestDto userDetailsRequestDto;

}
