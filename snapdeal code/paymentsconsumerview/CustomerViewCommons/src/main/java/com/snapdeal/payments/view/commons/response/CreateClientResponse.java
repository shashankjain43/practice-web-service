package com.snapdeal.payments.view.commons.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.request.Client;

@Getter
@Setter
@ToString
public class CreateClientResponse extends AbstractResponse {
	
	private static final long serialVersionUID = 7590256177242424095L;
	
	private Client clientDetails;
   
}
