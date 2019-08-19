package com.snapdeal.payments.view.merchant.commons.response;

import java.util.List;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
public class GetMerchantSettledTransactionsResponse extends AbstractResponse{
	
	private static final long serialVersionUID = -3954557232995895633L;

	private List<MVTxnDTO> settledMerchantTxnsList;
	
}
