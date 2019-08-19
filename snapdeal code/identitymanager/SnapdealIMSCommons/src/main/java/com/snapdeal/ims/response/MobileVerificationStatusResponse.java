package com.snapdeal.ims.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = false)
public class MobileVerificationStatusResponse extends AbstractResponse {

	private static final long serialVersionUID = 9169437105382579679L;
    private boolean success;
}