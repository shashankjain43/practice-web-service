package com.snapdeal.ims.request.dto;

import com.snapdeal.ims.validator.annotation.MobileNumber;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRequestDto extends UserDetailsByEmailRequestDto
		implements Serializable {

	private static final long serialVersionUID = -1357493362672574030L;	
	
	@MobileNumber
	private String mobileNumber;

}
