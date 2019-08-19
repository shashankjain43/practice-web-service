package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MPTransactionDTO;

import lombok.Data;

@Data
public class MerchantGetTransactionResponse extends AbstractResponse{

	
	private static final long serialVersionUID = 1L;

	private List<MPTransactionDTO> mpTransactions;
}
