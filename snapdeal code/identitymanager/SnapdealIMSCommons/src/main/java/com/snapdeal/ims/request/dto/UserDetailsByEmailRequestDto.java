package com.snapdeal.ims.request.dto;

import com.snapdeal.ims.validator.annotation.Email;
import com.snapdeal.ims.validator.annotation.Password;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(exclude={"password"}, callSuper = true)
public class UserDetailsByEmailRequestDto extends UserDetailsRequestDto
		implements Serializable {

	private static final long serialVersionUID = -1357493362672574030L;
	@Password
	private String password;
	@Email
	private String emailId;

}
