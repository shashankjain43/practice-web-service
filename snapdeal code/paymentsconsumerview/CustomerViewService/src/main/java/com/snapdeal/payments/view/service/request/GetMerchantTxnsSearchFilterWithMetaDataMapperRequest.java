package com.snapdeal.payments.view.service.request;


import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.merchant.commons.request.GetMerchantTxnsSearchFilterWithMetaDataRequest;

@Getter
@Setter
public class GetMerchantTxnsSearchFilterWithMetaDataMapperRequest extends
		GetMerchantTxnsSearchFilterWithMetaDataRequest {

	private static final long serialVersionUID = 1L;
	
	private int offset;

}
