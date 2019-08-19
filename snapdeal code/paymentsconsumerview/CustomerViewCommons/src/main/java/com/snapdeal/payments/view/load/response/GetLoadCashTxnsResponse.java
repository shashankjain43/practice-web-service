package com.snapdeal.payments.view.load.response;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.load.dto.LoadCashTxnDTO;
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
public class GetLoadCashTxnsResponse extends AbstractResponse{
	
	private static final long serialVersionUID = 6941845460515823183L;
	
	private List<LoadCashTxnDTO> lcTransactions;
	
}
