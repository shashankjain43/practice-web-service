package com.snapdeal.payments.view.merchant.commons.response;

import java.util.List;

import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnWithMetaDataDTO;

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
public class GetMerchantTxnsWithMetaDataResponse extends AbstractResponse{
	
	private static final long serialVersionUID = -9180888620562773742L;

	private List<MVTxnWithMetaDataDTO> mvTransactions;
	
}
