package com.snapdeal.opspanel.userpanel.request;

import org.hibernate.validator.constraints.NotBlank;

import com.snapdeal.opspanel.userpanel.enums.ShopoTransactionIdentifier;

import lombok.Data;

@Data
public class SearchShopoTransactionRequest {

	@NotBlank( message = "Please enter transaction identifier" )
	private ShopoTransactionIdentifier identifier;

	@NotBlank( message = "Please enter search Value" )
	private String searchValue;
}
