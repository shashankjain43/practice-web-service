package com.snapdeal.ims.request;

import com.snapdeal.ims.validator.annotation.MobileNumber;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MobileOnlyRequest extends AbstractRequest {

	@MobileNumber
	private String mobileNumber;
	
}
