package com.snapdeal.opspanel.userpanel.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.snapdeal.payments.fps.aht.model.UserTransaction;
import com.snapdeal.payments.fps.aht.model.UserTransactionDetails;
import com.snapdeal.payments.fps.aht.model.UserTransactionEntityType;

@Data
public class GetBlockedTxnsResponse {
	
	private List<UserTransactionDetails> list;
	
	private String lastEvaluatedKey;
	
	private String startDate;
	
	private String endDate;

}
