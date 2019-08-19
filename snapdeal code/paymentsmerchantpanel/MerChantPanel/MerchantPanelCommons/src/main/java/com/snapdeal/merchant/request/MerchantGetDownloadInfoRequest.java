package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class MerchantGetDownloadInfoRequest extends AbstractMerchantRequest {
	
	private String userId;
	
	private Integer id;

}
