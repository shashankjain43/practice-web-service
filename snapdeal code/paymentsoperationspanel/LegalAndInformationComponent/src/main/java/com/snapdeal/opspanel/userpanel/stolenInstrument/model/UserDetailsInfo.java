package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import lombok.Data;

import com.snapdeal.ims.response.GetUserResponse;

@Data
public class UserDetailsInfo {
	
	GetUserResponse userDetails;
	
	String error;

}
