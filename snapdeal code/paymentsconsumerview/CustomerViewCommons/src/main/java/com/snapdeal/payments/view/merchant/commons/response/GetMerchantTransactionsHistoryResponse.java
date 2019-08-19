package com.snapdeal.payments.view.merchant.commons.response;

import java.util.List;

import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author shashank
 * 
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GetMerchantTransactionsHistoryResponse extends AbstractResponse{
	
	private static final long serialVersionUID = 509075610496806405L;
	
	private List<MVTransactionDTO> merchantTxnsHistoryList;
	
	private Long lastEvaluatedkey;
	
}
