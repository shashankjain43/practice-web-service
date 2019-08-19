package com.snapdeal.payments.view.service.request;

import lombok.Data;

public @Data class GetRecordByGblTxnIdRequest {
	private String glbobalTxnId ;
	private String userId ;
	private String txnType ;
}
