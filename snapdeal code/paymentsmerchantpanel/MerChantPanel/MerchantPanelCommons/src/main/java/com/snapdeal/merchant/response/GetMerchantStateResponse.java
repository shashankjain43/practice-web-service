package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantFlowDTO;

import lombok.Data;

@Data
public class GetMerchantStateResponse extends AbstractResponse{

	
	private static final long serialVersionUID = 1L;
	List<MerchantFlowDTO> merchantFlowDTO;

}
