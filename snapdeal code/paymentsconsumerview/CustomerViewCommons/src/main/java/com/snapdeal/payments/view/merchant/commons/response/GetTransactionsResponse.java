package com.snapdeal.payments.view.merchant.commons.response;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;

/**
 * 
 * @author shashank
 * 
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GetTransactionsResponse extends AbstractResponse{
	
	private static final long serialVersionUID = 509075610496806405L;
	
	private List<MVTransactionDTO> mvTransactions;
	
}
