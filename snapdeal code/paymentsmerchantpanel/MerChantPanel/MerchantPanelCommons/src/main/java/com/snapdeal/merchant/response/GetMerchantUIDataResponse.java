package com.snapdeal.merchant.response;

import java.util.List;

import com.snapdeal.merchant.dto.MerchantUIData;

import lombok.Data;

@Data
public class GetMerchantUIDataResponse extends AbstractResponse {
	private static final long serialVersionUID = 1L;
	List<MerchantUIData> merchantUIData;
	
}
