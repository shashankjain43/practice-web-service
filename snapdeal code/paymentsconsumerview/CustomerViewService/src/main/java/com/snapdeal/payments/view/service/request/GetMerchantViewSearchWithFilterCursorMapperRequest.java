package com.snapdeal.payments.view.service.request;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterCursorRequest;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewAbstractRequest;

@Getter
@Setter
public class GetMerchantViewSearchWithFilterCursorMapperRequest extends GetMerchantViewSearchWithFilterCursorRequest{

	private static final long serialVersionUID = -533490129192601423L;
	private Date cursorKey;

}
