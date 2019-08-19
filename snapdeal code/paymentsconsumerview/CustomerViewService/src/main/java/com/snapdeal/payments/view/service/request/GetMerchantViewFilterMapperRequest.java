package com.snapdeal.payments.view.service.request;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewFilterRequest;

@Getter
@Setter
public class GetMerchantViewFilterMapperRequest extends
		GetMerchantViewFilterRequest {

	private static final long serialVersionUID = 2817264997954413031L;

	private int offset;
}
