package com.snapdeal.merchant.response;

import lombok.Data;

@Data
public class MerchantBulkRefundURLResponse extends AbstractResponse{

	private static final long serialVersionUID = 7471987226996574311L;
	String url;
}
