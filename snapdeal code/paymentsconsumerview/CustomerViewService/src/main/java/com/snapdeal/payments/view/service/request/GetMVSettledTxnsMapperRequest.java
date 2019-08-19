package com.snapdeal.payments.view.service.request;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantSettledTransactionsRequest;

@Getter
@Setter
public class GetMVSettledTxnsMapperRequest extends
		GetMerchantSettledTransactionsRequest {

	private static final long serialVersionUID = -1628054418838736565L;
	private MVTransactionStatus txnStatus;
	private int offset;

}
