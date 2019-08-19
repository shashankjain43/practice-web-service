package com.snapdeal.payments.view.service.request;

import java.util.Date;

import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;

import lombok.Data;

public @Data class ExceptionHandlerRequest {

	private String txnId ;
	private String txnType;
	private String merchantId;
	private Date tsmTimeStamp;
	private String key ;
	private ViewTypeEnum viewType ;
}
