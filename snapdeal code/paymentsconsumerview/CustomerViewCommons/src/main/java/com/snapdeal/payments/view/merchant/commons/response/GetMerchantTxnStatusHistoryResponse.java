package com.snapdeal.payments.view.merchant.commons.response;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.merchant.commons.dto.MVTxnStatusDTO;

@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GetMerchantTxnStatusHistoryResponse extends AbstractResponse{
	
	private static final long serialVersionUID = 509075610496806405L;
	
	private List<MVTxnStatusDTO> mvTxnStatusDTO;
	
	
}
