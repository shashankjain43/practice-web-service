package com.snapdeal.opspanel.promotion.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest  {

	@NotBlank( message = "Username cannot be blank!" )
	private String username;

	@NotBlank( message = "Password cannot be blank!" )
	private String password;

}
