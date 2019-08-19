package com.snapdeal.ims.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetUserHistoryDetailsRequest extends AbstractRequest {
	private static final long serialVersionUID = -4768668181642169060L;
	private String userId;
}