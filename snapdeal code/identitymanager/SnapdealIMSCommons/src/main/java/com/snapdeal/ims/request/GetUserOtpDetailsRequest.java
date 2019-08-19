package com.snapdeal.ims.request;

import com.snapdeal.ims.enums.UserOtpDetailsSearchField;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter

public class GetUserOtpDetailsRequest extends AbstractRequest{
	
	private static final long serialVersionUID = -1626652295737708875L;
	private String value;
	private UserOtpDetailsSearchField searchField;
}
