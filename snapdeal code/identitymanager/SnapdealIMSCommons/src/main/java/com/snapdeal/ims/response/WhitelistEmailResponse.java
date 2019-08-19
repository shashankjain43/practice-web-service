package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString

public class WhitelistEmailResponse extends AbstractResponse {
	
	private static final long serialVersionUID = -2118672878568448261L;
	private boolean success = false ;
	

}
