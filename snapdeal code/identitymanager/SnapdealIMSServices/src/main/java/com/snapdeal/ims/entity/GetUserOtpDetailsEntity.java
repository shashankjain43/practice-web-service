package com.snapdeal.ims.entity;

import com.snapdeal.ims.enums.UserOtpDetailsSearchField;
import com.snapdeal.ims.request.AbstractRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter

public class GetUserOtpDetailsEntity extends AbstractRequest{
	private static final long serialVersionUID = -1626652295737708875L;
	private String value;
	private UserOtpDetailsSearchField searchField;
}
