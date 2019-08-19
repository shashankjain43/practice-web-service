package com.snapdeal.payments.view.service.request;

import lombok.Getter;
import lombok.Setter;

import com.snapdeal.payments.view.load.request.GetLoadCashTxnsRequest;

@Getter
@Setter
public class GetLoadCashTxnsMapperRequest extends GetLoadCashTxnsRequest {

	private static final long serialVersionUID = -4535574824494423901L;
	private int offset;
	private String userId;
	private String txnId;

}
