package com.snapdeal.payments.view.merchant.commons.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnDTO;

/**
 * 
 * @author shashank
 * 
 */
@ToString
@Getter
@Setter
public class GetMerchantTxnsSearchFilterWithMetaDataResponse extends AbstractResponse{
	
	private static final long serialVersionUID = 5778879918186671146L;
	
	private List<MVTxnDTO> mvTransactions;
	
}
