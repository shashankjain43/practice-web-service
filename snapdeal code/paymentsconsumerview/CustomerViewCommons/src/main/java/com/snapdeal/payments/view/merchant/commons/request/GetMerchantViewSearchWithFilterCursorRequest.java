package com.snapdeal.payments.view.merchant.commons.request;

import java.util.Date;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMerchantViewSearchWithFilterCursorRequest extends
		MerchantViewAbstractRequest {

	private static final long serialVersionUID = 4513176499788848322L;

	@Valid
	private MerchantViewFilters filters;

	@Valid
	private MerchantViewSearch searchCriteria;
	
}
