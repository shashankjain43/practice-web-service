package com.snapdeal.ims.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSearchRequest extends AbstractRequest {
	private String userId;
	private String name;
	private String email;
	private String mobile;
	private String fromDate;
	private String toDate;
}
