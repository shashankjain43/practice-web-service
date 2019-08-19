package com.snapdeal.ims.response;

import com.snapdeal.ims.enums.UserStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MobileOnlyResponse extends AbstractResponse {

	private boolean status;
	private boolean mobileOnly;
}
