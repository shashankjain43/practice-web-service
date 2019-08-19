package com.snapdeal.payments.view.service.request;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchRequest;

@Getter
@Setter
public class GetMerchantViewSearchMapperRequest extends
GetMerchantViewSearchRequest {

	private static final long serialVersionUID = -6736176483284675436L;
	
	private int offset;

}
