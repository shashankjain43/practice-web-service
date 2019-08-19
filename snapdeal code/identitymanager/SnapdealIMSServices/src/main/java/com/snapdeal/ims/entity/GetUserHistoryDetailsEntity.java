package com.snapdeal.ims.entity;

import com.snapdeal.ims.request.AbstractRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserHistoryDetailsEntity extends AbstractRequest {
	private static final long serialVersionUID = -4768668181642169060L;
	private String userId;
}