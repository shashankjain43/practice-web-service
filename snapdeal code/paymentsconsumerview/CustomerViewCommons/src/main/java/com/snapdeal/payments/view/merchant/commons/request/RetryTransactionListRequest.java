package com.snapdeal.payments.view.merchant.commons.request;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.merchant.commons.dto.RetryTransactionDTO;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
public class RetryTransactionListRequest extends AbstractRequest{
	
	private static final long serialVersionUID = 1L;
	
	public List<RetryTransactionDTO> transactionList ;
	
}
